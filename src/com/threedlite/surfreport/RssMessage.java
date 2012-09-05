package com.threedlite.surfreport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RssMessage {

	private String title;
	private String link;
	private String description;
	private Date date;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	private static final String FORMAT_STRING = "EEE, dd MMM yyyy HH:mm:ss Z";

	public String getDate() {
		return  new SimpleDateFormat(FORMAT_STRING).format(this.date);
	}

	public void setDate(String date) {
		while (!date.endsWith("00")){
			date += "0";
		}
		try {
			this.date = new SimpleDateFormat(FORMAT_STRING).parse(date.trim());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return title+" "+link+" "+description+" "+date;
	}

}
