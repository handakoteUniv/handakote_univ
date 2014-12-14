package com.step.handakote;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by rubberyuzu on 2014/12/14.
 */
public class ReadWriteModel extends Thread{

    public static InputStream in;
    public static OutputStream out;
    private String inputString;
    private Context mContext;

    public ReadWriteModel(Context context, BluetoothSocket socket, String string){
        inputString = string;
        mContext = context;

        try {
            //接続済みソケットからI/Oストリームをそれぞれ取得
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void write(byte[] buf){
        //Outputストリームへのデータ書き込み
        try {
            out.write(buf);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        byte[] buf = new byte[1024];
        String output = null;
        int tmpBuf = 0;

        try {
            write(inputString.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
//            TODO Auto-generated catch block
            e.printStackTrace();
        }

        while(true){
            try {
                tmpBuf = in.read(buf);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(tmpBuf!=0){
                try {
                    output = new String(buf, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            Intent i = new Intent(mContext, MainActivity.class);
            i.putExtra("OUTPUT", output);
            mContext.startActivity(i);
        }
    }
}
