package com.myapp.bluetest;

import java.io.IOException;
import java.io.OutputStream;

import com.myapp.bluetest.blue.BlueService;
import com.myapp.bluetest.blue.constant;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Accurate_debugActivity extends Activity implements OnClickListener {
	private OutputStream os = null;
	public BlueService.mBinder blueservice_binder;
	private Button bt_plus;
	private Button bt_plusplus;
	private Button bt_minus;
	private Button bt_minusminus;
	private EditText et_pos;
	private Button bt_set;
	private int intentPos = 0;
	private int intentId = 0;
	private int intentSpe = 0;
	private int pos = 0;
	
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
		setContentView(R.layout.activity_accurate_debug);
		intentPos 	= getIntent().getExtras().getInt("intentPos");
		intentId	= getIntent().getExtras().getInt("intentId");
		intentSpe	= getIntent().getExtras().getInt("intentSpe");
		pos = intentPos;
				
		
//		Intent intent  = new Intent(Accurate_debugActivity.this,BlueService.class);
//	
//		bindService(intent, conn, Context.BIND_AUTO_CREATE);
//		
//		os = blueservice_binder.getOutputStream();
		os = constant.static_os;
		
		bt_plusplus = (Button) findViewById(R.id.bt_accurate_plusplus);
		bt_plusplus.setOnClickListener(this);
		bt_plus = (Button) findViewById(R.id.bt_accurate_plus);
		bt_plus.setOnClickListener(this);
		bt_minus = (Button) findViewById(R.id.bt_accurate_minus);
		bt_minus.setOnClickListener(this);
		bt_minusminus = (Button) findViewById(R.id.bt_accurate_minusminus);
		bt_minusminus.setOnClickListener(this);
		
		et_pos = (EditText) findViewById(R.id.et_accurate_data);
		bt_set = (Button) findViewById(R.id.bt_set);
		bt_set.setOnClickListener(this);
		et_pos.setText(pos+"");
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		switch (id) {
		case R.id.bt_accurate_plusplus:
			pos+=50;
			et_pos.setText(pos+"");
			sendPos(intentId, pos, intentSpe);
			break;
		case R.id.bt_accurate_plus:
			pos+=10;
			et_pos.setText(pos+"");
			sendPos(intentId, pos, intentSpe);
			break;
		case R.id.bt_accurate_minusminus:
			pos-=50;
			et_pos.setText(pos+"");
			sendPos(intentId, pos, intentSpe);
			break;
		case R.id.bt_accurate_minus:
			pos-=10;
			et_pos.setText(pos+"");
			sendPos(intentId, pos, intentSpe);
			break;
		case R.id.bt_set:
			Intent resultIntent = new Intent();
			resultIntent.putExtra("accurate_pos", pos);
			resultIntent.putExtra("accurate_Id", intentId);
			setResult(RESULT_OK, resultIntent);
			finish();
			break;
		}

	}
	public void sendPos(int id ,int pos,int spe){
		byte[] bytes = new byte[6];
		

		bytes[0] = (byte) 0x99;
		
		bytes[1] = ((byte) ((pos>>8) & 0xff));
		bytes[2] = ((byte) (pos & 0xff));
	
		bytes[3] = ((byte) ((spe>>8) & 0xff));
		bytes[4] = ((byte) (spe & 0xff));
		bytes[5] = (byte) id;
		
		try {
			os.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("debug_accurate", "发送数据异常");
		}	
	}

}
