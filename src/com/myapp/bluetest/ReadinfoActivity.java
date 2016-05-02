package com.myapp.bluetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class ReadinfoActivity extends Activity {
	private TextView[] ets = new TextView[11];
	private Button bt_goback;
	private Button bt_saveandback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readinfo);
		findView();

	}

	private void findView() {

		bt_goback = (Button) findViewById(R.id.bt_saveandback);
		bt_goback = (Button) findViewById(R.id.bt_goback);
		ets[1] = (TextView) findViewById(R.id.tv_readinfo_1);
		ets[2] = (TextView) findViewById(R.id.tv_readinfo_2);
		ets[3] = (TextView) findViewById(R.id.tv_readinfo_3);
		ets[4] = (TextView) findViewById(R.id.tv_readinfo_4);
		ets[5] = (TextView) findViewById(R.id.tv_readinfo_5);
		ets[6] = (TextView) findViewById(R.id.tv_readinfo_6);
		ets[7] = (TextView) findViewById(R.id.tv_readinfo_7);
		ets[8] = (TextView) findViewById(R.id.tv_readinfo_8);
		ets[9] = (TextView) findViewById(R.id.tv_readinfo_9);
		ets[10] = (TextView) findViewById(R.id.tv_readinfo_10);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.readinfo, menu);
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
}
