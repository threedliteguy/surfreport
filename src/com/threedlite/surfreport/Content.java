package com.threedlite.surfreport;

import java.util.ArrayList;
import java.util.List;

public class Content {
	
	private static List<TabItem> mContentUrls = new ArrayList<TabItem>();
	static {
		mContentUrls.add(new TabItem("http://forecast.weather.gov/product.php?site=IWX&issuedby=BRO&product=CWF&format=TXT&version=1&glossary=0", "nws", "RIO GRANDE OUT 20 NM"));
		mContentUrls.add(new TabItem("http://www.spadre.com/southpadreislandbeachandsurfreport.rss", "rss-encoded-item", ""));
	}

	public static List<TabItem> getContentUrls() {
		return mContentUrls;
	}

}
