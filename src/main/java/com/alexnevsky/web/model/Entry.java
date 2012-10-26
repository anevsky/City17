/**
 * 
 */
package com.alexnevsky.web.model;

/**
 * This class represents a transient object for system uses.
 * 
 * @author Alex Nevsky
 * 
 */
public class Entry {

	private String url;
	private String header;
	private String dateStr;
	private String content;

	public Entry() {
	}

	public Entry(String url, String header, String dateStr, String content) {
		this.url = url;
		this.header = header;
		this.dateStr = dateStr;
		this.content = content;
	}

	public String getUrl() {
		return this.url;
	}

	public String getHeader() {
		return this.header;
	}

	public String getDateStr() {
		return this.dateStr;
	}

	public String getContent() {
		return this.content;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
