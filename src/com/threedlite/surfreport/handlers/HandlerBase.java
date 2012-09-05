package com.threedlite.surfreport.handlers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public abstract class HandlerBase {
	
	public static final String TAG = "SurfReportWidgetHandler";
	
	public abstract void parse(String url, List<String> list, String search) throws Exception;

	protected byte[] getUrlContent(String url) throws Exception {
		URLConnection aurl = new URL(url).openConnection();
		InputStream input = aurl.getInputStream();
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024 * 4];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return output.toByteArray();
		} finally {
			input.close();
		}
	}


	
}
