/**
 * 
 */
package com.alexnevsky.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.alexnevsky.web.domain.content.Story;
import com.alexnevsky.web.util.GlobalConstants;

/**
 * @author Alex Nevsky
 * 
 */
public class DisplayStoryTag extends TagSupport {

	private static final Logger logger = Logger.getLogger(DisplayStoryTag.class);

	private static final String MORE_ID = "<a id=\"more\"></a>";

	private static final long serialVersionUID = 1L;

	private Story story;
	private boolean showMore;

	public void setStory(Story story) {
		this.story = story;
	}

	public void setShowMore(boolean showMore) {
		this.showMore = showMore;
	}

	/**
	 * Provides logics for custom tag.
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();

		String content = this.story.getContent();

		String dataToView = null;
		if (!this.showMore) {
			String[] parts = content.split(GlobalConstants.VIEW_MORE_TAG);
			dataToView = parts[0];
		} else {
			dataToView = content.replace(GlobalConstants.VIEW_MORE_TAG, GlobalConstants.VIEW_MORE_TAG + MORE_ID);
		}

		try {
			out.write(dataToView);
		} catch (IOException e) {
			logger.error(e, e);
		}

		return SKIP_BODY;
	}
}
