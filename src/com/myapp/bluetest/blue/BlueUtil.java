package com.myapp.bluetest.blue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

import android.bluetooth.*;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class BlueUtil extends Thread {
	private Context context;
	BluetoothDevice bluedevice = null;
	BluetoothSocket socket = null;
	BluetoothAdapter blueadapter = null;
	InputStream is = null;
	OutputStream os = null;

	public BlueUtil(Context context) {
		this.context = context;
		blueadapter = BluetoothAdapter.getDefaultAdapter();
		bluedevice = blueadapter.getRemoteDevice("20:15:04:16:14:91");

		try {
			socket = bluedevice
					.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, "get blueSocket failed!!!", Toast.LENGTH_SHORT).show();
		}
	}

	public InputStream getIs() {
		if(is == null)
			try {
				is = socket.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return is;
	}

	public void run() {
		Looper.prepare();
		try {
			socket.connect();
			if (socket != null) {
				is = socket.getInputStream();
				os = socket.getOutputStream();
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Looper.loop();
	}

	public void sendmsg(byte[] bytes){
		try {
			os.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendmsg(String str){
		byte[] bytes = str.getBytes();
		try {
			os.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public OutputStream getOs() {
		if(os == null)
			try {
				os = socket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return os;
	}

}

