package com.threedlite.surfreport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

public class RssParser  {

	
	static final String CONTENT = "http://purl.org/rss/1.0/modules/content/";
	static final String ENCODED = "encoded";
	
	static final String PUB_DATE = "pubDate";
	static final  String DESCRIPTION = "description";
	static final  String LINK = "link";
	static final  String TITLE = "title";
	static final  String ITEM = "item";
	
	private InputStream input;

	public RssParser(InputStream input) {
		this.input = input;
	}

	public List<RssMessage> parse() {
		
		final RssMessage[] currentMessage = new RssMessage[]{new RssMessage()};
		RootElement root = new RootElement("rss");
		final List<RssMessage> messages = new ArrayList<RssMessage>();
		Element channel = root.getChild("channel");
		Element item = channel.getChild(ITEM);
		
		item.setEndElementListener(new EndElementListener(){
			public void end() {
				messages.add(currentMessage[0]);
				currentMessage[0] = new RssMessage();
			}
		});
		item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage[0].setTitle(body);
			}
		});
		item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage[0].setLink(body);
			}
		});
		item.getChild(DESCRIPTION).setEndTextElementListener(new 
				EndTextElementListener(){
			public void end(String body) {
				currentMessage[0].setDescription(body);
			}
		});
		item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage[0].setDate(body);
			}
		});
		item.getChild(CONTENT, ENCODED).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage[0].setContent(body);
			}
		});
		try {
			Xml.parse(this.input, Xml.Encoding.UTF_8, 
					root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return messages;
	}
	

}
