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
public class GalyonkinContent {

	private static final Logger logger = Logger.getLogger(EtodayContent.class);

	private static final String GALYONKIN_SITE = "http://www.galyonkin.com";

	private final String CATEGORY = " - Games";
	private final String TAGS = "games,stream-2";

	private IGeneralService iGeneralService;

	public GalyonkinContent() {
	}

	public GalyonkinContent(IGeneralService iGeneralService) {
		this.setIGeneralService(iGeneralService);
	}

	public void setIGeneralService(IGeneralService iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	public void update() {
		try {
			Document doc = Jsoup.connect(GALYONKIN_SITE).timeout(15 * 1000).get();
			Element content = doc.getElementById("content");
			Elements divs = content.getElementsByTag("div");

			Elements posts = new Elements();
			for (Element div : divs) {
				if (div.attr("id").startsWith("post-")) {
					posts.add(div);
				}
			}

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
			Element container = doc.getElementById("container");

			Element header = container.getElementsByTag("h1").first();

			String postTitle = StringUtil.toAlphanumericSpaceHyphen(header.getElementsByTag("a").first().text()
					+ this.CATEGORY);

			Element post = null;
			Elements divs = container.getElementById("content").getElementsByTag("div");
			for (Element div : divs) {
				if (div.attr("id").startsWith("post-")) {
					post = div;
				}
			}

			StringBuilder postTextSb = new StringBuilder(512);
			Elements paragraphs = post.getElementsByTag("p");

			boolean isAddMoreTag = true;

			int pCount = 0;
			for (Element i : paragraphs) {
				pCount += 1;
				postTextSb.append(i.outerHtml());

				if (isAddMoreTag && pCount == 2) {
					postTextSb.append(GlobalConstants.VIEW_MORE_TAG);
					isAddMoreTag = false;
				}
			}

			if (isAddMoreTag) {
				postTextSb.append(GlobalConstants.VIEW_MORE_TAG);
				isAddMoreTag = false;
			}

			postTextSb.append("<p style=\"font-size: 7pt; text-align:right;\">");
			postTextSb.append("from <a href=\"");
			postTextSb.append(postFullLink);
			postTextSb.append("\" target=\"_blank\" style=\"color:gray\">galyorkin.com</a>");
			postTextSb.append("</p>");

			String postText = postTextSb.toString();

			Story story = new Story(postTitle, postText, postFullLink);

			Element tagBlock = post.getElementsByClass("meta").first();
			Elements tags = tagBlock.getElementsByTag("a");
			tags.remove(0);
			StringBuilder tagsSb = new StringBuilder(64);
			for (Element e : tags) {
				tagsSb.append(e.text());
				tagsSb.append(",");
			}
			tagsSb.append(this.TAGS);
			tagsSb.append(",galyonkin");

			story.addCommaSeparatedTags(tagsSb.toString());

			Gallery gallery = this.processImages(container, postTitle);
			if (gallery != null) {
				List<Gallery> galleries = new ArrayList<Gallery>();
				galleries.add(gallery);
				story.setGalleries(galleries);
			}

			this.iGeneralService.save(story);

		} catch (MalformedURLException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		} catch (NullPointerException e) {
			logger.error(e, e);
		}
	}

	private Gallery processImages(Element container, String title) throws MalformedURLException {
		Gallery gallery = new Gallery(title, title);

		Element imageElement = container.select("a[rel=lightbox]:has(img)").first();
		String imageUrl = imageElement.attr("abs:href");

		long currentDate = new Date().getTime();
		int count = 1;

		String imageTitle = title;

		String fileExtension = FileUtil.getPathExtension(imageUrl).toLowerCase();

		String thumbPath = GlobalConstants.GALLERY_DIR_PATH + "games/" + currentDate + "_" + "images" + "/thumb/"
				+ "games_image" + "_" + count + "-thumb." + fileExtension;
		String thumbPathFull = GlobalConstants.DOMAIN_DIR_PATH + thumbPath;

		String imagePath = GlobalConstants.GALLERY_DIR_PATH + "games/" + currentDate + "_" + "images" + "/"
				+ "games_image" + "_" + count + "." + fileExtension;
		String imagePathFull = GlobalConstants.DOMAIN_DIR_PATH + imagePath;

		Downloader.downloadFile(new URL(imageUrl), new File(imagePathFull));

		// create thumb image
		ImageUtil.resizeImageByWidth(new File(imagePathFull), new File(thumbPathFull), 200);

		Image image = new Image(imageTitle, imageTitle, imagePath);
		image.setThumbPath(thumbPath);
		image.setCategory(GlobalConstants.CATEGORY_GALYORKIN_GAMES);

		gallery.addImage(image);

		return gallery;
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