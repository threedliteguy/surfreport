package com.threedlite.surfreport;

public class TabItem {
	
	private String url;
	private String type;
	private String search;
	
	public TabItem(String url, String type, String search) {
		this.url = url;
		this.type = type;
		this.search = search;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String toString() {
		return url+" "+type+" "+search;
	}

}
