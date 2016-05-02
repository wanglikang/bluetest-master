package com.myapp.bluetest.blue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.myapp.bluetest.MainActivity;
import com.myapp.bluetest.R;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BlueService extends Service{
	private BluetoothAdapter adapter = null;
	
	private BluetoothDevice bluedevice = null;
	
	private BluetoothSocket socket = null;
	private InputStream is = null;
	private OutputStream os = null;
	
	
	
	public static final int test = 1;
	private final mBinder mbinder =  new mBinder();

	public class mBinder extends Binder{
		public InputStream getInputStream(){
			return is;
			
		}
		public OutputStream getOutputStream(){
			return os;
			
		}
		public int getService(){
			return test;
		}
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		Log.e("service_onbind", "onbind");
		return mbinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("service_debug", "Service onCreate");
		adapter = BluetoothAdapter.getDefaultAdapter();
		bluedevice = adapter.getRemoteDevice("20:15:04:16:14:91");
		try {
			socket = bluedevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		Notification notification = new Notification.Builder(getApplicationContext()).build();
		notification.when = System.currentTimeMillis();
		notification.icon = R.drawable.notif_pic;
		notification.color = Color.BLUE;
		long[] ls= {1000,2000,3000};
		
		notification.vibrate = ls;
		String str = "’‚ «TickerText";
		int end = str.length();
		notification.tickerText = str.subSequence(0, end);
		
	
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		
		notification.setLatestEventInfo(this, "notification_title".subSequence(0, 17),
				"notification_content".subSequence(0, 17), pendingIntent);
		startForeground(1, notification);
		
		
		new Thread(){
			@Override
			public void run() {
				try {
					socket.connect();
					is = socket.getInputStream();
					os = socket.getOutputStream();
							
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.run();
			}
		}.start();
		
		
		
		
	}

	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.e("service_debug", "Service onStart");
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("service_debug", "Service onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.e("service_debug", "Service onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.e("service_debug", "Service onDestroy");
		super.onDestroy();
	}

}
