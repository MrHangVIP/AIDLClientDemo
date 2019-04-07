package com.szh.aidlclientdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.szh.aidldemo.AidlDemoFile;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SZH_AIDL";
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "client coon");
            if (iBinder != null) {
                service = AidlDemoFile.Stub.asInterface(iBinder);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "client onServiceDisconnected");
        }
    };

    private AidlDemoFile service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    private void initViews() {

        final Button coon = (Button) findViewById(R.id.conn);
        Button chat = (Button) findViewById(R.id.chat);
        Button dis = (Button) findViewById(R.id.dis);

        final Intent intent = new Intent();
        intent.setAction("com.szh.aidldemo.service.action");

        coon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "client bind onClick");
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (service != null) {
                    try {
                        Log.i(TAG, "client data onClick");
//                        String msg="client";
//                        service.chat(msg);
                        Bundle bundle = new Bundle();
                        bundle.putString("aidl", "client");
                        service.data(bundle);
                        Log.i(TAG, "client data bundle " + bundle.get("aidl"));
//                        Log.i(TAG, "client return data  " + data.get("aidl"));
                    } catch (RemoteException e) {


                    }
                }
            }
        });

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "client unbind onClick");
                unbindService(serviceConnection);
            }
        });

    }
}
