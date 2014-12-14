/*
 *  Copyright(C) 2014 NTT IT CORPORATION. All rights reserved.
 */
package jp.co.nttit.SpeechRec.sample;

import jp.co.nttit.SpeechRec.sample.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button buttonStart;
	private TextView textResult = null;
	private RadioButton radioHigh;
	private RadioButton radioLow;
	private String mResultString = "";

	/** 音声認識アクティビティのリクエストID */
	private static final int RECOGNIZE_ACTIVITY_REQUEST_ID = 1;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.main);
		buttonStart = (Button) findViewById(R.id.button_start);
		buttonStart.setEnabled(false);
		textResult = (TextView) findViewById(R.id.text_result);
		radioHigh = (RadioButton) findViewById(R.id.radio_high);
		radioLow = (RadioButton) findViewById(R.id.radio_low);

		textResult.setTextSize(28.0f);

		buttonStart.setEnabled(true);
		radioHigh.setEnabled(true);
		radioLow.setEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RECOGNIZE_ACTIVITY_REQUEST_ID:
			mResultString = data.getStringExtra((resultCode == RESULT_OK) ? "replace_key" : "error_message");
			textResult.setText(mResultString);
			break;
		}
	}

	public void onClickStart(final View view) {
		int sbm_mode = radioHigh.isChecked() ? 0 : 1;
		Intent intent = new Intent(this, RecognitionActivity.class);
		intent.putExtra(RecognitionActivity.KEY_SBM_MODE, sbm_mode);
		// 別途発行されるAPIキーを設定してください(以下の値はダミーです)
		intent.putExtra(RecognitionActivity.KEY_API_KEY, "123456789");
		startActivityForResult(intent, RECOGNIZE_ACTIVITY_REQUEST_ID);
	}
}
