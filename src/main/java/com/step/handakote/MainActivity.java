package com.step.handakote;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;


public class MainActivity extends ActionBarActivity{
    private BluetoothAdapter mBluetoothAdapter = null;
    private static final int REQUEST_ENABLE_BT = 1;
    private Context mContext;
    private BluetoothSocket mmSocket;
    private Button buttonGreen;
    private ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title_paired_devices = new TextView(this);
        title_paired_devices = (TextView)findViewById(R.id.title_paired_devices);

        buttonGreen = (Button)findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("hello\n");
            }
        });


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        System.out.println("---------------");
        System.out.println(pairedDevices);
        System.out.println("---------------");



        if(pairedDevices.size() > 0){
            findViewById(R.id.title_paired_devices);
            for (BluetoothDevice device : pairedDevices) {
                ConnectThread bluetoothConnect = new ConnectThread(device, this);
                mmSocket = bluetoothConnect.run(mBluetoothAdapter);
                if (true) {
                    break;
                }
            }
            connectedThread = new ConnectedThread(mmSocket, this);
        } else {
//            String noDevices = getResources().getText(R.string.none_paired).toString();
//            mPairedDevicesArrayAdapter.add(noDevices);
            Log.d("device", "not found");
        }


        if(mBluetoothAdapter == null){
            Toast.makeText(this, "Bluetooth is not available.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if(!mBluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }else{
//            if(mSignalService == null) setupSignal();
            Log.d("BT", "adapter enabled!");
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == Activity.RESULT_OK){
                    Log.d("BT result", "okay!!!");
                }else{
                    Log.e("error", "BT not enabled");
//                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
//                    finish();
                }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
