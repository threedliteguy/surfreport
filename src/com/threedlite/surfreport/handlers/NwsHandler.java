package com.threedlite.surfreport.handlers;

import java.util.List;

public class NwsHandler extends HandlerBase {

	@Override
	public void parse(String url, List<String> list, String search) throws Exception {
		
		byte[] b = getUrlContent(url);
		
		String content = new String(b);
		int ind = content.indexOf("<pre ");
		if (ind == -1) ind = content.indexOf("<pre>");
		if (ind == -1) {
			list.add(content.substring(0,20));
		} else {

			ind = content.indexOf(">", ind);
			content = content.substring(ind+1);
			ind = content.indexOf("</pre>");
			content = content.substring(0,ind);

			boolean found = false;
			String[] sections = content.split("\\$\\$");
			for (int i = 0; i < sections.length; i++) {
				String section = sections[i];
				if (section.contains(search)) {
					found = true;
					String[] lines = section.split("\n");
					for (int j = 0; j < lines.length; j++) {
						String line = lines[j];
						if (line.trim().length() == 0) continue;
						if (list.size() == 0 || line.startsWith(".")) {
							if (line.startsWith(".")) line = line.substring(1);
							list.add(line);
						} else {
							String append = list.get(list.size()-1) + " " + line;
							list.set(list.size()-1, append);
						}
					}
					break;
				}
			}

			if (!found) for (String s: content.split("\n")) list.add(s);
		}
		
	}

}
