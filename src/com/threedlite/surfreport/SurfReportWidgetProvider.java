/*
	Copyrights (C) 
	2012 Dan Meany
	2011 The Android Open Source Project

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
 */

package com.threedlite.surfreport;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;


public class SurfReportWidgetProvider extends AppWidgetProvider {

	public static final String TAG = "SurfReportProvider";

	public static final String PREFS_NAME = "SurfReportWidget";

	public static final String REFRESH_ACTION = "com.example.android.surfreportwidget.REFRESH";

	public static int mCurrentTab = 0;

	protected int getCurrentTab(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		int currentTab = settings.getInt("tab", 0);
		if (currentTab > Content.getContentUrls().size()-1) currentTab = 0;
		mCurrentTab = currentTab;
		return currentTab;
	}

	protected void saveCurrentTab(Context context, int currentTab) {

		if (currentTab > Content.getContentUrls().size()-1) currentTab = 0;

		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("tab", currentTab);
		editor.commit();
		mCurrentTab = currentTab;

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		context.startService(new Intent(context, SurfReportWidgetService.class));

		for (int i = 0; i < appWidgetIds.length; ++i) {

			final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

			// Bind the click intent for the refresh button on the widget
			final Intent refreshIntent = new Intent(context, SurfReportWidgetProvider.class);
			refreshIntent.setAction(SurfReportWidgetProvider.REFRESH_ACTION);
			final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
					refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setOnClickPendingIntent(R.id.refresh, refreshPendingIntent);


			// Data adaptor to service
			Intent intent = new Intent(context, SurfReportWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

			rv.setRemoteAdapter(appWidgetIds[i], R.id.content_list, intent);


			appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);

	}

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (action.equals(REFRESH_ACTION)) {

			saveCurrentTab(context, getCurrentTab(context)+1);

			final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
			final ComponentName cn = new ComponentName(context, SurfReportWidgetProvider.class);

			mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.content_list);

		}

		super.onReceive(context, intent);
	}


}