/**
 * 
 */
package com.alexnevsky.web.controller;

import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alexnevsky.web.domain.content.Story;
import com.alexnevsky.web.domain.media.Gallery;
import com.alexnevsky.web.domain.media.Image;
import com.alexnevsky.web.service.IGeneralService;
import com.alexnevsky.web.util.GlobalConstants;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;

/**
 * @author Alex Nevsky
 * 
 */
@Controller
public class ImageController implements MessageSourceAware {

	@Autowired
	private IGeneralService iGeneralService;

	private MessageSource messageSource;

	@Autowired
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/gallery/{galleryId}", method = RequestMethod.GET)
	public String redirectToGalleryWithName(HttpServletRequest request, @PathVariable Long galleryId) {
		Search s = new Search(Gallery.class);
		s.addFilterEqual("id", galleryId);
		s.setResultMode(ISearch.RESULT_SINGLE);
		s.addField("name");

		String galleryName = (String) this.iGeneralService.searchUnique(s);
		if (galleryName != null) {
			StringBuilder sb = new StringBuilder(128);
			sb.append("redirect:/");
			sb.append("gallery");
			sb.append("/");
			sb.append(galleryId);
			sb.append("/");
			sb.append(galleryName);

			String queryString = request.getQueryString();
			if (queryString != null) {
				sb.append("?");
				sb.append(queryString);
			}

			return sb.toString();
		} else {
			return "404";
		}
	}

	@RequestMapping(value = "/gallery/{galleryId}/{galleryName}", method = RequestMethod.GET)
	public String viewGallery(@PathVariable Long galleryId, Locale locale, Model model) {
		Gallery gallery = this.iGeneralService.find(Gallery.class, galleryId);

		if (gallery != null) {
			String pageTitle = gallery.getName() + " - " + this.messageSource.getMessage("main.title", null, locale);

			model.addAttribute("pageTitle", pageTitle);
			model.addAttribute("domainUrl", GlobalConstants.DOMAIN_URL);
			model.addAttribute("galleryUrl", "/gallery/" + galleryId);
			model.addAttribute("gallery", gallery);

			// tags
			Search s = new Search(Story.class);
			s.addFilterSome("galleries", Filter.equal("id", galleryId));
			s.setResultMode(ISearch.RESULT_SINGLE);

			List<Story> stories = this.iGeneralService.search(s);

			if (!stories.isEmpty()) {
				Story story = stories.get(0);
				List<String> tags = story.getTags();
				model.addAttribute("tags", tags);
			}

			return "x-gallery/all-images";
		} else {
			// no gallery with such id
			return "404";
		}
	}

	@RequestMapping(value = "/gallery/{galleryId}/{galleryName}/image/{imageId}", method = RequestMethod.GET)
	public String redirectToImageWithName(HttpServletRequest request, @PathVariable Long galleryId,
			@PathVariable String galleryName, @PathVariable Long imageId) {
		Search s = new Search(Image.class);
		s.addFilterEqual("id", imageId);
		s.setResultMode(ISearch.RESULT_SINGLE);
		s.addField("name");

		String imageName = (String) this.iGeneralService.searchUnique(s);

		if (imageName != null) {
			StringBuilder sb = new StringBuilder(192);
			sb.append("redirect:/");
			sb.append("gallery");
			sb.append("/");
			sb.append(galleryId);
			sb.append("/");
			sb.append(galleryName);
			sb.append("/");
			sb.append("image");
			sb.append("/");
			sb.append(imageId);
			sb.append("/");
			sb.append(imageName);

			String queryString = request.getQueryString();
			if (queryString != null) {
				sb.append("?");
				sb.append(queryString);
			}

			return sb.toString();
		} else {
			return "404";
		}
	}

	@RequestMapping(value = "/gallery/{galleryId}/{galleryName}/image/{imageId}/{imageName}", method = RequestMethod.GET)
	public String viewImage(@PathVariable Long galleryId, @PathVariable Long imageId,
			@RequestParam(value = "imageNum", required = false) Long imageNumParam, Locale locale, Model model) {
		Gallery gallery = this.iGeneralService.find(Gallery.class, galleryId);

		if (gallery != null) {
			TreeSet<Image> imageSet = gallery.getImages();
			int totalImages = imageSet.size();

			Image image = this.iGeneralService.find(Image.class, imageId);

			String nextImageNum = null;
			Image nextImage = imageSet.higher(image);
			if (nextImage == null) {
				nextImage = imageSet.first();
				nextImageNum = "1";
			}

			String prevImageNum = null;
			Image prevImage = imageSet.lower(image);
			if (prevImage == null) {
				prevImage = imageSet.last();
				prevImageNum = String.valueOf(totalImages);
			}

			String galleryName = gallery.getName();

			StringBuilder pageTitleSb = new StringBuilder(128);
			pageTitleSb.append(image.getName());
			pageTitleSb.append(" Image");
			pageTitleSb.append(" - ");
			pageTitleSb.append(galleryName);
			pageTitleSb.append(" Gallery");
			pageTitleSb.append(" - ");
			pageTitleSb.append(this.messageSource.getMessage("main.title", null, locale));
			pageTitleSb.append(" - ");
			pageTitleSb.append(this.messageSource.getMessage("photo", null, locale));

			String galleryUrl = "/gallery/" + galleryId + "/" + galleryName;

			String imageNumStr = null;
			if (imageNumParam != null) {
				imageNumStr = this.messageSource.getMessage("one.of.total",
						new Object[] { imageNumParam, totalImages }, locale);
			} else {
				imageNumStr = this.messageSource.getMessage("one.of.total", new Object[] { 1, totalImages }, locale);
			}

			StringBuilder prevImageUrlSb = new StringBuilder(128);
			prevImageUrlSb.append(galleryUrl);
			prevImageUrlSb.append("/image/");
			prevImageUrlSb.append(prevImage.getId());
			prevImageUrlSb.append("/");
			prevImageUrlSb.append(prevImage.getName());

			if (prevImageNum != null) {
				prevImageUrlSb.append("?imageNum=");
				prevImageUrlSb.append(prevImageNum);
			} else if (imageNumParam != null) {
				prevImageUrlSb.append("?imageNum=");
				prevImageUrlSb.append(imageNumParam - 1);
			}

			StringBuilder nextImageUrlSb = new StringBuilder(128);
			nextImageUrlSb.append(galleryUrl);
			nextImageUrlSb.append("/image/");
			nextImageUrlSb.append(nextImage.getId());
			nextImageUrlSb.append("/");
			nextImageUrlSb.append(nextImage.getName());

			if (nextImageNum != null) {
				nextImageUrlSb.append("?imageNum=");
				nextImageUrlSb.append(nextImageNum);
			} else if (imageNumParam != null) {
				nextImageUrlSb.append("?imageNum=");
				nextImageUrlSb.append(imageNumParam + 1);
			}

			model.addAttribute("domainUrl", GlobalConstants.DOMAIN_URL);
			model.addAttribute("pageTitle", pageTitleSb.toString());
			model.addAttribute("galleryUrl", galleryUrl);
			model.addAttribute("galleryName", galleryName);
			model.addAttribute("image", image);
			model.addAttribute("imageNumStr", imageNumStr);
			model.addAttribute("prevImageUrl", prevImageUrlSb.toString());
			model.addAttribute("nextImageUrl", nextImageUrlSb.toString());

			// tags
			Search s = new Search(Story.class);
			s.addFilterSome("galleries", Filter.equal("id", galleryId));
			s.setResultMode(ISearch.RESULT_SINGLE);

			List<Story> stories = this.iGeneralService.search(s);

			if (!stories.isEmpty()) {
				Story story = stories.get(0);
				List<String> tags = story.getTags();
				model.addAttribute("tags", tags);
			}

			return "x-gallery/single-image";
		} else {
			// no gallery with such id
			return "404";
		}
	}
}
