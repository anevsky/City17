/**
 * 
 */
package com.alexnevsky.web.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

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
import com.googlecode.genericdao.search.Search;

/**
 * @author Alex Nevsky
 * 
 */
@Controller
public class FeedController implements MessageSourceAware {

	@Autowired
	private IGeneralService iGeneralService;

	private MessageSource messageSource;

	@Autowired
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String redirectToFeed() {
		return "redirect:/news/page/1";
	}

	@RequestMapping(value = "/news/page/{pageNumParam}", method = RequestMethod.GET)
	public String feed(@PathVariable Integer pageNumParam, Locale locale, Model model) {
		final int PER_PAGE_RESULTS = 15;

		Search s = new Search(Story.class);

		int totalStories = this.iGeneralService.count(s);
		int totalPages = (totalStories + (PER_PAGE_RESULTS - 1)) / PER_PAGE_RESULTS;

		s.addSort("id", true); // descending
		s.setMaxResults(PER_PAGE_RESULTS); // results per page

		int pageNum = 1;
		if (pageNumParam != null) {
			pageNum = pageNumParam;
		}

		s.setPage(pageNum - 1); // the first is 0

		int prevPageNum = 1;
		int nextPageNum = 1;

		if (pageNum == 1) {
			prevPageNum = totalPages;
			nextPageNum = pageNum + 1;
		} else if (pageNum > 1) {
			prevPageNum = pageNum - 1;
			nextPageNum = pageNum + 1;
		}

		if (pageNum == totalPages) {
			nextPageNum = 1;
		}

		String pagesNumStr = null;
		if (pageNumParam != null) {
			pagesNumStr = this.messageSource.getMessage("one.of.total", new Object[] { pageNumParam, totalPages },
					locale);
		} else {
			pagesNumStr = this.messageSource.getMessage("one.of.total", new Object[] { 1, totalPages }, locale);
		}

		List<Story> stories = this.iGeneralService.search(s);

		String pageTitle = this.messageSource.getMessage("news.feed", null, locale) + " - "
				+ this.messageSource.getMessage("main.title", null, locale);

		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("domainUrl", GlobalConstants.DOMAIN_URL);
		model.addAttribute("stories", stories);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("prevPage", prevPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("lastPage", totalPages);
		model.addAttribute("pagesNumStr", pagesNumStr);

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

		return "feed";
	}
}
