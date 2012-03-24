package nu.kabo.android.beat;

import android.app.PendingIntent;
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

  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
  {
    super.onUpdate(context, appWidgetManager, appWidgetIds);

    for (int i : appWidgetIds)
    {
      Intent intent = new Intent(context, BeatSettings.class);            
      PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);  
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);            
      views.setOnClickPendingIntent(R.id.widget_entirety, pendingIntent); 

      appWidgetManager.updateAppWidget(i, views);

      Log.d(TAG, "BEAT WIDGET CLICK");
    }
  }
	
}
