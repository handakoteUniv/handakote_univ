/*
 *  Copyright(C) 2014 NTT IT CORPORATION. All rights reserved.
 */
package jp.co.nttit.SpeechRec.sample;

import java.util.HashMap;
import java.util.Set;

import jp.co.nttit.SpeechRec.sample.R;
import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button buttonStart;
    private TextView textResult = null;
    private RadioButton radioHigh;
    private RadioButton radioLow;
    private String mResultString = "";
    private HashMap<String, String> wordMap;
    
    private Button buttonOrange;
    private Button buttonPink;
    private Button buttonGreen;
    private Button buttonBlue;
    private Button buttonOff;
    

    /** 音声認識アクティビティのリクエストID */
    private static final int RECOGNIZE_ACTIVITY_REQUEST_ID = 1;

    // from Handakote
    private BluetoothAdapter mBluetoothAdapter = null;
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothSocket mmSocket;
    private ConnectedThread connectedThread;

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

        wordMap = new HashMap<String, String>();
        wordMap.put("です", "green");
        wordMap.put("ます", "green");

        wordMap.put("ゆうき", "pink");
        wordMap.put("ダーリン", "pink");

        wordMap.put("ちゃん", "orange");

        wordMap.put("酔っちゃった", "blue");
        wordMap.put("よっちゃった", "blue");
        wordMap.put("お酒", "blue");
        wordMap.put("おさけ", "blue");
        wordMap.put("パーティ", "blue");
        
        wordMap.put("オフ", "off");
        wordMap.put("青", "blue");
        wordMap.put("オレンジ", "orange");
        wordMap.put("緑", "green");
        wordMap.put("ピンク", "pink");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        System.out.println("---------------");
        System.out.println(pairedDevices);
        System.out.println("---------------");

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                ConnectThread bluetoothConnect = new ConnectThread(device, this);
                mmSocket = bluetoothConnect.run(mBluetoothAdapter);
                if (true) {
                    break;
                }
            }
            connectedThread = new ConnectedThread(mmSocket, this);
        } else {
            // String noDevices =
            // getResources().getText(R.string.none_paired).toString();
            // mPairedDevicesArrayAdapter.add(noDevices);
            Log.d("device", "not found");
        }

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            // if(mSignalService == null) setupSignal();
            Log.d("BT", "adapter enabled!");
        }
        

        
        buttonOrange = (Button)findViewById(R.id.buttonOrange);
        buttonOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("orange\n");
            }
        });
        
        buttonGreen = (Button)findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("green\n");
            }
        });
        
        buttonBlue = (Button)findViewById(R.id.buttonBlue);
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("blue\n");
            }
        });
        
        buttonPink = (Button)findViewById(R.id.buttonPink);
        buttonPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("pink\n");
            }
        });
        
        
        buttonOff = (Button)findViewById(R.id.buttonOff);
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("off\n");
            }
        });
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
            mResultString = data
                    .getStringExtra((resultCode == RESULT_OK) ? "replace_key"
                            : "error_message");

            textResult.setText(mResultString);
            for (String key : wordMap.keySet()) {
                if (mResultString.indexOf(key) != -1) {
                    connectedThread.write(wordMap.get(key) + "\n");
                }
            }
            break;
        }
    }

    public void onClickStart(final View view) {
        int sbm_mode = radioHigh.isChecked() ? 0 : 1;
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.putExtra(RecognitionActivity.KEY_SBM_MODE, sbm_mode);
        // 別途発行されるAPIキーを設定してください(以下の値はダミーです)
        intent.putExtra(
                RecognitionActivity.KEY_API_KEY,
                "72634749316e75537a6433305371576862375a367759616e514a674e2f48706e5331445a2e442e4b434431");
        startActivityForResult(intent, RECOGNIZE_ACTIVITY_REQUEST_ID);
    }
}
