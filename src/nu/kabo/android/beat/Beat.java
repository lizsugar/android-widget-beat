package nu.kabo.android.beat;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class Beat extends AppWidgetProvider {
	
	protected static final String TAG = "Beat";
    
	public void onEnabled(Context context) {
		Intent intent = new Intent();
		intent.setClassName(context.getPackageName(), context.getPackageName()+".BeatService");
		intent.setAction("nu.kabo.android.beat.START_SERVICE");
		Log.d(TAG, "sending start-intent to service");
		context.startService(intent);
	}
	
	public void onDisabled(Context context) {
		// exit, causing the notifyingThread to die as well
		//System.exit(0);
		Intent intent = new Intent();
		intent.setClassName(context.getPackageName(), context.getPackageName()+".BeatService");
		intent.setAction("nu.kabo.android.beat.STOP_SERVICE");
		Log.d(TAG, "sending stop-intent to service");
		context.stopService(intent);
	}
	
	public void onReceive(Context context, Intent intent) {
 		super.onReceive(context, intent);
 		//Log.d(TAG, intent.getAction());
 		if (intent.getAction().equals("nu.kabo.android.beat.UPDATE")) {
 			ComponentName thisWidget = new ComponentName(context, Beat.class);
 			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
 			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
 			String t = intent.getStringExtra("time");
 			Log.d(TAG, "Updating widget time to "+t);
 			remoteViews.setTextViewText(R.id.widget_textview, t);
 			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
 		}
	}
	
}
