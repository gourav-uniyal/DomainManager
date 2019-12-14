package com.android.testmessenger.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.android.testmessenger.libs.ConnectionHelper;

public class CheckNetworkService extends Service {

    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static BroadcastReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter filter = new IntentFilter();
        filter.addAction(CONNECTIVITY_CHANGE_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (CONNECTIVITY_CHANGE_ACTION.equals(action)) {

                    if (!ConnectionHelper.isConnectedOrConnecting(context)) {

                        if (!ConnectionHelper.isConnected(context)) {
                            Log.v("NETWORK123", "Connection lost");
                            Toast.makeText( context, "No Internet Connection", Toast.LENGTH_SHORT ).show( );
//                            Intent intent1 = new Intent(context, DialogActivity.class);
//                            intent1.putExtra("key1", "There is no network connection right now");
//                            intent1.putExtra("key2", "0");
//                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent1);
                        }

                    } else {
                        Intent intent2 = new Intent("finish_activity");
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sendBroadcast(intent2);
                        Log.v("NETWORK123", "Connected");
                    }
                }
            }
        };
        registerReceiver(receiver, filter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}