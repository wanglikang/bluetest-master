package com.myapp.bluetest;

import java.io.IOException;
import java.io.OutputStream;

import com.myapp.bluetest.blue.constant;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class CtrlCarActivity extends Activity implements OnTouchListener {
	private OutputStream os = null;
	private Button bt_turn_forward;
	private Button bt_turn_back;
	private Button bt_turn_left;
	private Button bt_turn_right;

	private Button bt_look_up;
	private Button bt_look_down;
	private Button bt_look_left;
	private Button bt_look_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ctrl_car_main);
		os = constant.static_os;
		findView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ctrl_car, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void findView() {
		bt_turn_forward = (Button) findViewById(R.id.bt_go_forward);
		bt_turn_forward.setOnTouchListener(this);
		bt_turn_back = (Button) findViewById(R.id.bt_go_back);
		bt_turn_back.setOnTouchListener(this);
		bt_turn_left = (Button) findViewById(R.id.bt_go_left);
		bt_turn_left.setOnTouchListener(this);
		bt_turn_right = (Button) findViewById(R.id.bt_go_right);
		bt_turn_right.setOnTouchListener(this);

		bt_look_up = (Button) findViewById(R.id.bt_look_up);
		bt_look_up.setOnTouchListener(this);
		bt_look_down = (Button) findViewById(R.id.bt_look_down);
		bt_look_down.setOnTouchListener(this);
		bt_look_left = (Button) findViewById(R.id.bt_look_left);
		bt_look_left.setOnTouchListener(this);
		bt_look_right = (Button) findViewById(R.id.bt_look_right);
		bt_look_right.setOnTouchListener(this);
	}

	public void onClick(View v) {}
	
	public void send(String str){
		try {
			os.write(str.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.e("action","°´ÏÂbutton");
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int id = v.getId();
			switch (id) {
			case R.id.bt_look_up:			
				send("look_up");	
				break;
			case R.id.bt_look_down:
				send("look_down");
				break;
			case R.id.bt_look_left:
				send("look_left");
				break;
			case R.id.bt_look_right:
				send("look_right");
				break;

			case R.id.bt_go_forward:
				send("go_ahead");
				break;
			case R.id.bt_go_back:
				send("go_back");
				break;
			case R.id.bt_go_left:
				send("go_left");
				break;
			case R.id.bt_go_right:
				send("go_right");
				break;

			}
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
			int id = v.getId();
			switch(id){
			case R.id.bt_look_up:
			case R.id.bt_look_down:
			case R.id.bt_look_left:
			case R.id.bt_look_right:
				send("look_stop");
				break;
			case R.id.bt_go_forward:
			case R.id.bt_go_back:
			case R.id.bt_go_left:
			case R.id.bt_go_right:
				send("go_stop");
				break;
			}
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){
			Log.e("action", "actio_move");
			
			
		}
		return false;
	}
}
