package nu.kabo.android.beat;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class BeatService extends Service {

	protected static final String TAG = "BeatService";
	protected Thread notifyingThread;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			UpdateTime();
		}
	};
	
	private Runnable mTask = new Runnable() {
        public void run() {
        	try {
        		handler.sendEmptyMessage(0);
        		double cur_time = GetTime();
        		double cur_time_dec = cur_time-(int)cur_time;
        		//Thread.sleep(5000);
				Thread.sleep((int)Math.ceil(90000*(1-cur_time_dec))); // some extra time here
			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				Log.e(TAG, "Error: " + e.getMessage(), e);
			}
        	while(true) {
        		try {
            		handler.sendEmptyMessage(0);
            		//Thread.sleep(5000);
            		Thread.sleep(86400); // sleep one beat
				} catch (InterruptedException e) {
					Log.d(TAG, "Caught interrupt, exiting thread");
					return;
				} catch (Exception e) {
					Log.e(TAG, "Error: " + e.getMessage(), e);
				}
        	}
        }
    };
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Bind received");
		return null;
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "starting thread");
		notifyingThread = new Thread(mTask, "NotifyingService");
		notifyingThread.start();
		return START_STICKY;
	}
	
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "stoping thread");
		notifyingThread.interrupt();
	}
	
	private void UpdateTime() {
		//Log.d(TAG, "Updating time");
		Intent intent = new Intent();
		intent.setAction("nu.kabo.android.beat.UPDATE");
		intent.putExtra("time", GetTimeString());
		intent.setClassName("nu.kabo.android.beat", "nu.kabo.android.beat.Beat");
		sendBroadcast(intent);
	}
	
	public double GetTime() {
		Calendar t = Calendar.getInstance(TimeZone.getTimeZone("GMT+0100"));
		return (double)(( ( ( t.get(Calendar.HOUR_OF_DAY) + (t.get(Calendar.MINUTE)/60.0) + (t.get(Calendar.SECOND)/3600.0) ) ) / 24.0 ) * 1000.0);
	}
	public String GetTimeString() {
		String ITime = "00" + (int)GetTime();
		return "@"+(ITime.substring(ITime.length()-3));
	}

}
