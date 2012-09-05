
package com.threedlite.surfreport;

import java.util.ArrayList;
import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.threedlite.surfreport.handlers.NwsHandler;
import com.threedlite.surfreport.handlers.RssEncodedItemHandler;


public class SurfReportWidgetService extends RemoteViewsService {

	public static final String TAG = "SurfReportWidgetService";

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new SurfReportRemoteViewsFactory(this.getApplicationContext(), intent);
	}
}


class SurfReportRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	public static final String TAG = "SurfReportRemoteViewsFactory";


	private Context mContext;
	private int mAppWidgetId;

	public SurfReportRemoteViewsFactory(Context context, Intent intent) {
		mContext = context;
		mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	public void onCreate() {
	}

	public void onDestroy() {
	}

	public int getCount() {
		return mContent.size();
	}

	public RemoteViews getViewAt(int position) {
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.light_widget_item);
		rv.setTextViewText(R.id.widget_item, mContent.get(position));
		return rv;
	}

	public RemoteViews getLoadingView() {
		return null;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public long getItemId(int position) {
		return position;
	}

	public boolean hasStableIds() {
		return true;
	}

	public void onDataSetChanged() {
		TabItem tabItem = Content.getContentUrls().get(SurfReportWidgetProvider.mCurrentTab);
		readContent(tabItem);
	}

	private List<String> mContent;

	private void readContent(TabItem tabItem) {

		mContent = new ArrayList<String>();

		List<String> temp = new ArrayList<String>();
		try {

			if (tabItem.getType().equals("nws")) {
				new NwsHandler().parse(tabItem.getUrl(), temp, tabItem.getSearch());
			}			
			if (tabItem.getType().equals("rss-encoded-item")) {
				new RssEncodedItemHandler().parse(tabItem.getUrl(), temp, tabItem.getSearch());
			}
			mContent = temp;
		} catch (Exception e) {
			mContent.add(e.getMessage());
		}

	}

}
