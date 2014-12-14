package com.step.handakote;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import org.apache.commons.logging.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rubberyuzu on 2014/12/14.
 */

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final Context mmContext;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    final int MESSAGE_READ = 9999;

    public ConnectedThread(BluetoothSocket socket, Context mContext) {
        mmSocket = socket;
        mmContext = mContext;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run(Handler mHandler) {
        byte[] buffer = new byte[1024];
        int bytes;
        while (true) {
            try {
                System.out.println("coming to connected thread");
                bytes = mmInStream.read(buffer);
                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                break;
            }
//            ReadWriteModel rw = new ReadWriteModel(mmContext, mmSocket,);
//            rw.start();
        }
    }

    public void write(String str) {
        try {
            System.out.println("coming to write");
            byte[] bytes = str.getBytes("UTF-8");
            mmOutStream.write(bytes);
            System.out.println("write done");
        } catch (IOException e) { }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}