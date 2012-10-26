/**
 * 
 */
package com.alexnevsky.web.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alexnevsky.web.domain.content.Story;
import com.alexnevsky.web.service.IGeneralService;
import com.alexnevsky.web.util.GlobalConstants;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;

/**
 * @author Alex Nevsky
 * 
 */
@Controller
public class StoryController implements MessageSourceAware {

	@Autowired
	private IGeneralService iGeneralService;

	private MessageSource messageSource;

	@Autowired
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/story/{storyId}", method = RequestMethod.GET)
	public String redirectToStoryWithName(HttpServletRequest request, @PathVariable Long storyId) {
		Search s = new Search(Story.class);
		s.addFilterEqual("id", storyId);
		s.setResultMode(ISearch.RESULT_SINGLE);
		s.addField("title");

		String storyTitle = (String) this.iGeneralService.searchUnique(s);
		if (storyTitle != null) {
			StringBuilder sb = new StringBuilder(128);
			sb.append("redirect:/");
			sb.append("story");
			sb.append("/");
			sb.append(storyId);
			sb.append("/");

			String lowerSafe = storyTitle.toLowerCase().replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_\\-]", "");

			sb.append(lowerSafe);

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

	@RequestMapping(value = "/story/{storyId}/{storyTitle}/**", method = RequestMethod.GET)
	public String viewStory(@PathVariable Long storyId, Locale locale, Model model) {
		Story story = this.iGeneralService.find(Story.class, storyId);

		if (story != null) {
			String pageTitle = story.getTitle() + " - " + this.messageSource.getMessage("main.title", null, locale);

			model.addAttribute("pageTitle", pageTitle);
			model.addAttribute("domainUrl", GlobalConstants.DOMAIN_URL);
			model.addAttribute("story", story);

			// tags cloud
			Search tagsSearch = new Search(Story.class);
			List<Story> tagsStories = this.iGeneralService.search(tagsSearch);

			Map<String, Integer> tagsCloud = new TreeMap<String, Integer>();

			for (Story storyIterator : tagsStories) {
				List<String> tags = storyIterator.getTags();

				for (String tag : tags) {
					if (tagsCloud.containsKey(tag)) {
						int count = tagsCloud.get(tag);
						tagsCloud.put(tag, count + 1);
					} else {
						tagsCloud.put(tag, 1);
					}
				}
			}

			model.addAttribute("tagsCloud", tagsCloud);

			return "story";
		} else {
			// no story with such id
			return "404";
		}

	}
}