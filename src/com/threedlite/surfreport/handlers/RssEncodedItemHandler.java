package com.threedlite.surfreport.handlers;

import java.io.ByteArrayInputStream;
import java.util.List;

import android.util.Log;

import com.threedlite.surfreport.RssMessage;
import com.threedlite.surfreport.RssParser;

public class RssEncodedItemHandler extends HandlerBase {

	@Override
	public void parse(String url, List<String> list, String search) throws Exception {
		byte[] b = getUrlContent(url);
		ByteArrayInputStream in = new ByteArrayInputStream(b);
		List<RssMessage> messages = new RssParser(in).parse();
		Log.w(TAG, "parsed messages:"+messages.size());
		for (RssMessage m: messages) {
			list.add(m.getTitle());
			list.add(m.getDate());
			list.add(m.getDescription());
			list.add(m.getLink());
			String content = m.getContent();
			if (content == null) content = "";
			content = stripHtml(content);
			for (String s: content.split("\n")) if (s.trim().length() > 0) list.add(s.trim());
		}
		
	}
	
	private String stripHtml(String s) {
		
		s = s.replaceAll("<br/>", "\n");
		
		boolean on = true;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '<') {
				on = false;
			} 
			if (c == '>') {
				on = true;
			} else {
				if (on) sb.append(c);
			}
		}

		s = s.replaceAll("&amp;", "&");
		s = s.replaceAll("&apos;", "'");
		s = s.replaceAll("&quot;", "\"");
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");

		return sb.toString();
	}
}
