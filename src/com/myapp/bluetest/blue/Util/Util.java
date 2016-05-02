package com.myapp.bluetest.blue.Util;

import java.nio.ByteBuffer;

import android.widget.EditText;

public class Util {
	
	public static byte[] getByte(EditText et_x){
		byte[] setData = new byte[2];
		String number = et_x.getText().toString();
		int pos_1;
		if(number == null || number.equals(""))
			 pos_1 = 0;
		else pos_1 = Integer.valueOf(number+"");
		setData[0] = ((byte) ((pos_1>>8) & 0xff));
		setData[1] = ((byte) (pos_1 & 0xff));
		return setData;
	}
	
	public static int getInt(EditText et_x){
		String number = et_x.getText().toString();
		int pos_1;
		if(number == null || number.equals(""))
			 pos_1 = 0;
		else pos_1 = Integer.valueOf(number+"");
		return pos_1; 
	}

}
