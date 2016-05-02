package com.myapp.bluetest;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.myapp.bluetest.blue.*;
import com.myapp.bluetest.blue.Util.Util;
import com.myapp.bluetooth.MyListener.MyListerner;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private MyListerner listener;
	
	public BlueService.mBinder blueservice_binder;
	
	public Thread receThread;
	private Context context = MainActivity.this;
	private Button bt_toread;
	private Button bt_connect;
	private Button bt_set;
	private Button bt_savedata;
	private Button bt_accurate_debug;
	private Button bt_ctrl_car;
	private static EditText[] ets = new EditText[21];
	private SeekBar seekbar = null;
	private InputStream is = null;
	private OutputStream os = null;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			int what = msg.what;
			switch (what) {
			case 111:

				break;
			case constant.SHOE_TOAST:
				Toast.makeText(getApplicationContext(), bundle.getString("toast"), Toast.LENGTH_SHORT).show();

			default:
				break;
			}
		}
	};

	ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			blueservice_binder = (BlueService.mBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		
		
		new Thread() {
			@Override
			public void run() {
				Intent intent = new Intent(MainActivity.this, BlueService.class);
				
				startService(intent);
				bindService(intent, conn, Context.BIND_AUTO_CREATE);
				super.run();
			}
		}.start();
		
		listener = new MyListerner(getApplicationContext(),ets);
		Log.e("MainActivity_debug", "MainActivity onCreate");
	
		seekbar.setOnSeekBarChangeListener(listener);
		bt_savedata.setOnClickListener(listener);
		bt_ctrl_car.setOnClickListener(listener);
		bt_toread.setOnClickListener(listener);

		bt_set.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				ByteBuffer bf = getEditData();

				try {
					os.write(0xcc);
					os.write(bf.array());
					//os.write(new byte[]{0x0d,0x0a});
				} catch (IOException e) {
					Log.e("send bluetooth msg", "bluetooth write error");
					e.printStackTrace();
				}
			}
		});
		
		bt_connect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				os = blueservice_binder.getOutputStream();
				is = blueservice_binder.getInputStream();
				constant.static_os = os;
				Toast.makeText(getApplicationContext(), "get is and os", Toast.LENGTH_SHORT).show();

				new Thread() {
					int len = 0;
					byte[] buffer;
					ByteBuffer message = null;

					@Override
					public void run() {
						Looper.prepare();
						Log.i("xxxxxxxxxxx", "xxxxx");
						buffer = new byte[1024];
						byte abyte;
						message = ByteBuffer.allocate(64);
						while (true) {
							try {
								if (is != null) {
									DataInputStream dis = new DataInputStream(is);
									int length = 0, count = 0;
									len = 0;

									len = dis.read(buffer);
									message.put(buffer, 0, len);
									length += len;
									// msg(constant.SHOE_TOAST,"toast","first
									// reced "+length+" bytes");

									len = dis.read(buffer);
									message.put(buffer, 0, len);
									length += len;
									// msg(constant.SHOE_TOAST,"toast","total
									// reced "+length+" bytes");

									msg(constant.SHOE_TOAST, "toast", new String(message.array(), 0, length));
									Log.i("bbbbbbbbb", new String(message.array(), 0, length));
									message.clear();
								} else {
									Log.i("bbbbb", "inputStream is null");
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				}.start();
			
			}
		});
	
		bt_accurate_debug.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int intentPos = 0;
				int intentSpe = 0;
				int intentId = 0;
				for(int q = 1;q <= 10;q++)
					if(ets[q].isFocused()){
						intentId = q;
						break;
					}
				
				if(intentId == 0)
					for(int q = 11;q <= 20;q++)
						if(ets[q].isFocused()){
							intentId = q-10;
							break;
						}
				intentPos = Integer.valueOf(ets[intentId].getText().toString());
				intentSpe = Integer.valueOf(ets[intentId+10].getText().toString());
				Intent intent = new Intent(MainActivity.this,Accurate_debugActivity.class);
		
				Bundle options = new Bundle();
				options.putInt("intentId", intentId);
				options.putInt("intentPos", intentPos);
				options.putInt("intentSpe", intentSpe);
				intent.putExtras(options);
				
				startActivityForResult(intent, constant.REQUEST_ACCCURATE_DEBUG_CODE , options);
				
//				unbindService(conn);
//				stopService(new Intent(MainActivity.this,BlueService.class));
				
				
				 
				
			}
		});
	}

	public ByteBuffer getEditData() {
		ByteBuffer bytebuffer = ByteBuffer.allocate(2 * 20);
		
		for (int i = 1; i <= 10 ;i++){
			bytebuffer.put(Util.getByte(ets[i]));
			bytebuffer.put(Util.getByte(ets[i+10]));
			
		}
		return bytebuffer;
	}

	public static String getEditStringData() {
		StringBuilder strbuffer = new StringBuilder();
		for (int i = 1; i <= 10; i++){
			strbuffer.append(Util.getInt(ets[i])+" ");
			strbuffer.append(Util.getInt(ets[i*2])+" ");
		}
		return strbuffer.toString();
	}

	public void msg(int what, String key, String value) {
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putString(key, value);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	/**
	 * findView()
	 * 得到主界面的各个组件
	 * 
	 * */
	public void findView() {
		
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		
		ets[1] = (EditText) findViewById(R.id.et_postion_1);
		ets[2] = (EditText) findViewById(R.id.et_postion_2);
		ets[3] = (EditText) findViewById(R.id.et_postion_3);
		ets[4] = (EditText) findViewById(R.id.et_postion_4);
		ets[5] = (EditText) findViewById(R.id.et_postion_5);
		ets[6] = (EditText) findViewById(R.id.et_postion_6);
		ets[7] = (EditText) findViewById(R.id.et_postion_7);
		ets[8] = (EditText) findViewById(R.id.et_postion_8);
		ets[9] = (EditText) findViewById(R.id.et_postion_9);
		ets[10] = (EditText) findViewById(R.id.et_postion_10);
		
		ets[11] = (EditText) findViewById(R.id.et_speed_1);
		ets[12] = (EditText) findViewById(R.id.et_speed_2);
		ets[13] = (EditText) findViewById(R.id.et_speed_3);
		ets[14] = (EditText) findViewById(R.id.et_speed_4);
		ets[15] = (EditText) findViewById(R.id.et_speed_5);
		ets[16] = (EditText) findViewById(R.id.et_speed_6);
		ets[17] = (EditText) findViewById(R.id.et_speed_7);
		ets[18] = (EditText) findViewById(R.id.et_speed_8);
		ets[19] = (EditText) findViewById(R.id.et_speed_9);
		ets[20] = (EditText) findViewById(R.id.et_speed_10);
		
		bt_connect = (Button) findViewById(R.id.bt_connect);
		bt_toread = (Button) findViewById(R.id.bt_Toread);
		bt_set = (Button) findViewById(R.id.bt_set);
		bt_savedata = (Button) findViewById(R.id.bt_savedata);
		bt_accurate_debug = (Button) findViewById(R.id.bt_accurate_debug);
		bt_ctrl_car = (Button) findViewById(R.id.bt_ctrl_car);

	}

	/**
	 * msg()
	 * @param key 
	 * 			关键字
	 * @param value
	 * 			byte[]
	 * */
	public void msg(int what, String key, byte[] value) {
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putByteArray(key, value);
		msg.setData(bundle);
		handler.sendMessage(msg);
		
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK)
			ets[data.getIntExtra("accurate_Id", 1)].setText(data.getIntExtra("accurate_pos", 1)+"");	
		Log.e("result", "收到result");
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(conn);
		super.onDestroy();
	}
	
	

	
}
