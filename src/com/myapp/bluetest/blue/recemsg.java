package com.myapp.bluetest.blue;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class recemsg extends Thread {
	
	private Context context;
	private Handler handler = null;
	private InputStream is ;
	private int len = 0;
	private byte[] buffer = null;
	private ByteBuffer message = null;
	
	public void msg(int what,String key,String value){
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putString(key, value);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	
	public void msg(int what,String key,byte[] value){
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putByteArray(key, value);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	public recemsg(Context context, Handler handler, BlueUtil util) {
		this.context = context;
		this.handler = handler;
		is = util.getIs();
	}

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
					int length = 0,count = 0;
					len = 0;
					
					len = dis.read(buffer);
					message.put(buffer,0,len);
					length+=len;
					msg(constant.SHOE_TOAST,"toast","first reced "+length+" bytes");
					
					len = dis.read(buffer);
					message.put(buffer,0,len);
					length+=len;
					msg(constant.SHOE_TOAST,"toast","total reced "+length+" bytes");
					
					msg(constant.SHOE_TOAST,"toast",new String(message.array(),0,length));
					Log.i("bbbbbbbbb", new String(message.array(),0,length));
					message.clear();
				}else {
					Log.i("bbbbb", "inputStream is null");		
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
