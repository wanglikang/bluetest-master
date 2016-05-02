package com.myapp.bluetooth.MyListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.myapp.bluetest.CtrlCarActivity;
import com.myapp.bluetest.MainActivity;
import com.myapp.bluetest.R;
import com.myapp.bluetest.ReadinfoActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class MyListerner implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
	public static int action_count = 0;
	Context context;
	EditText[] ets;

	public MyListerner(Context context, EditText[] ets) {
		this.context = context;
		this.ets = ets;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.bt_Toread:
			Intent intent = new Intent(context,ReadinfoActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.startActivity(intent);
			break;
		case R.id.bt_savedata:
			Action_savedata();
			break;
		case R.id.bt_ctrl_car:
			Intent intent2 = new Intent(context,CtrlCarActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.getApplicationContext().startActivity(intent2);
			break;
		case R.id.action_settings:
			
			break;
			
			
		}

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub

		int q = 1;
		for (; q < ets.length; q++)
			if (ets[q].isFocused())
				break;

		ets[q].setText(new String(progress + ""));

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	public void Action_savedata() {

		action_count++;
		File file = new File(Environment.getExternalStorageDirectory(), "/ACTION_DATA.txt");
		try {
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileWriter fw = null;

		String str = "action " + action_count + " is :" + '\r' + '\n' + MainActivity.getEditStringData() + '\n';

		try {
			fw = new FileWriter(file, true);
		} catch (IOException e) {
			Log.e("file", "cann't new FileWriter");
			e.printStackTrace();
		}
		try {
			fw.append(str.subSequence(0, str.length()));
			Log.e("file", str.subSequence(0, str.length()).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fw.flush();
			Log.e("file", "file flush success!!!");
		} catch (IOException e) {
			Log.e("file", "file flush error?!");
			e.printStackTrace();
		}

		try {
			fw.close();
			Log.e("file", "file close success");
		} catch (IOException e) {
			Log.e("file", "file close errror");
			e.printStackTrace();
		}

	}

}
