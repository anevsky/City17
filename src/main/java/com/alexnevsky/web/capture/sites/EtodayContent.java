package com.alexnevsky.web.capture.sites;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.alexnevsky.web.domain.content.Story;
import com.alexnevsky.web.domain.media.Gallery;
import com.alexnevsky.web.domain.media.Image;
import com.alexnevsky.web.service.IGeneralService;
import com.alexnevsky.web.util.Downloader;
import com.alexnevsky.web.util.FileUtil;
import com.alexnevsky.web.util.GlobalConstants;
import com.alexnevsky.web.util.ImageUtil;
import com.alexnevsky.web.util.StringUtil;
import com.googlecode.genericdao.search.Search;

/**
 * @author Alex Nevsky
 * 
 */
@Component
public class EtodayContent {

	private static final Logger logger = Logger.getLogger(EtodayContent.class);

	private static final String ETODAY_SITE = "http://www.etoday.ru";

	private final String CATEGORY = " - Glamour";
	private final String TAGS = "glamour,stream-1";

	private IGeneralService iGeneralService;

	public EtodayContent() {
	}

	public EtodayContent(IGeneralService iGeneralService) {
		this.setIGeneralService(iGeneralService);
	}

	public void setIGeneralService(IGeneralService iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	public void update() {
		try {
			Document doc = Jsoup.connect(ETODAY_SITE).timeout(15 * 1000).get();
			Element entries = doc.getElementById("entries");
			Elements posts = entries.getElementsByClass("post");

			// oldest first
			Collections.reverse(posts);

			for (Element i : posts) {
				Element header = i.getElementsByTag("h2").first();
				String postFullLink = header.getElementsByTag("a").first().absUrl("href");

				if (!this.isStoryExist(postFullLink)) {
					this.processPost(postFullLink);
				}
			}
		} catch (MalformedURLException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		} catch (NullPointerException e) {
			logger.error(e, e);
		}
	}

	public void processPost(String postFullLink) {
		try {
			Document doc = Jsoup.connect(postFullLink).timeout(15 * 1000).get();
			Element post = doc.getElementsByClass("post").first();

			Element header = post.getElementsByTag("h2").first();

			String postTitle = StringUtil.toAlphanumericSpaceHyphen(header.getElementsByTag("a").first().text()
					+ this.CATEGORY);

			Element firstTextParagraph = post.getElementsByTag("p").first();

			// need paragraph only with text
			final int lookDeep = 3;
			for (int i = 1; i <= lookDeep; ++i) {
				if (firstTextParagraph.getElementsByTag("img").size() > 0) {
					firstTextParagraph = post.getElementsByTag("p").get(i);
				} else {
					break;
				}
			}

			firstTextParagraph = this.removeLinks(firstTextParagraph);

			if (!firstTextParagraph.text().isEmpty() && !firstTextParagraph.html().contains("Etoday")
					&& !firstTextParagraph.html().contains("etoday")) {
				StringBuilder postTextSb = new StringBuilder(512);
				postTextSb.append("<p>");
				postTextSb.append(firstTextParagraph.html());
				postTextSb.append("</p>");

				postTextSb.append(GlobalConstants.VIEW_MORE_TAG);

				String iframeCode = this.getIframeCode(post);
				if (!iframeCode.isEmpty()) {
					postTextSb.append("<p>");
					postTextSb.append(iframeCode);
					postTextSb.append("</p>");
				}

				postTextSb.append("<p style=\"font-size: 7pt; text-align:right;\">");
				postTextSb.append("from <a href=\"");
				postTextSb.append(postFullLink);
				postTextSb.append("\" target=\"_blank\" style=\"color:gray\">etoday.ru</a>");
				postTextSb.append("</p>");

				String postText = postTextSb.toString();

				Story story = new Story(postTitle, postText, postFullLink);

				Element tagBlock = post.getElementsByClass("tags").first();
				Elements tags = tagBlock.getElementsByTag("a");
				StringBuilder tagsSb = new StringBuilder(64);
				for (Element e : tags) {
					tagsSb.append(e.text());
					tagsSb.append(",");
				}
				tagsSb.append(this.TAGS);
				tagsSb.append(",photo,photo-set");

				Elements headerLinks = header.getElementsByTag("a");
				if (headerLinks.size() > 1) {
					String postCategory = header.getElementsByTag("a").get(1).text();

					tagsSb.append(",");
					tagsSb.append(postCategory);
				}

				story.addCommaSeparatedTags(tagsSb.toString());

				Element entry = post.getElementsByClass("entry").first();
				Gallery gallery = this.processImages(entry, postTitle);
				if (gallery != null) {
					List<Gallery> galleries = new ArrayList<Gallery>();
					galleries.add(gallery);
					story.setGalleries(galleries);
				}

				this.iGeneralService.save(story);
			}
		} catch (MalformedURLException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		} catch (NullPointerException e) {
			logger.error(e, e);
		}
	}

	private Gallery processImages(Element entry, String title) throws MalformedURLException {
		Gallery gallery = null;
		Elements imageParagraphs = entry.select("p:has(img)");

		if (!imageParagraphs.isEmpty()) {
			gallery = new Gallery(title, title);

			long currentDate = new Date().getTime();

			int count = 0;

			for (Element e : imageParagraphs) {
				count += 1;

				String paragraphText = e.text();

				if (!paragraphText.isEmpty()) {
					paragraphText = paragraphText + " - ";
				}

				if (paragraphText.length() > 150) {
					paragraphText = "";
				}

				Element imageElement = e.getElementsByTag("img").first();

				String imageUrl = imageElement.absUrl("src");
				String imageTitle = paragraphText + title;

				String fileExtension = FileUtil.getPathExtension(imageUrl).toLowerCase();

				String thumbPath = GlobalConstants.GALLERY_DIR_PATH + "glamour/" + currentDate + "_" + "images"
						+ "/thumb/" + "glamour_image" + "_" + count + "-thumb." + fileExtension;
				String thumbPathFull = GlobalConstants.DOMAIN_DIR_PATH + thumbPath;

				String imagePath = GlobalConstants.GALLERY_DIR_PATH + "glamour/" + currentDate + "_" + "images" + "/"
						+ "glamour_image" + "_" + count + "." + fileExtension;
				String imagePathFull = GlobalConstants.DOMAIN_DIR_PATH + imagePath;

				Downloader.downloadFile(new URL(imageUrl), new File(imagePathFull));

				// create thumb image
				ImageUtil.resizeImageByWidth(new File(imagePathFull), new File(thumbPathFull), 200);

				Image image = new Image(imageTitle, imageTitle, imagePath);
				image.setThumbPath(thumbPath);
				image.setCategory(GlobalConstants.CATEGORY_ETODAY_GLAMOUR);

				gallery.addImage(image);
			}
		}

		return gallery;
	}

	private String getIframeCode(Element post) {
		StringBuilder codeSb = new StringBuilder(256);

		Elements iframeParagraphs = post.select("p:has(iframe)");
		for (Element p : iframeParagraphs) {
			p.getElementsByTag("iframe").first().attr("width", "720");
			p.getElementsByTag("iframe").first().attr("height", "405");
			codeSb.append(p.html());
		}

		return codeSb.toString();
	}

	private Element removeLinks(Element e) {
		for (Element el : e.getAllElements()) {
			if (el.hasAttr("href")) {
				el.removeAttr("href");
			}
		}

		String code = e.html();
		code = code.replace("<a>", "");
		code = code.replace("</a>", "");
		e.html(code);

		return e;
	}

	public boolean isStoryExist(String source) {
		boolean isExist = false;

		Search s = new Search(Story.class);
		s.addFilterEqual("source", source);

		int count = this.iGeneralService.count(s);

		if (count > 0) {
			isExist = true;
		}

		return isExist;
	}
}
