package com.example.jared.simplebroadcasterreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_COUNT = "EXTRA_COUNT";
    TextView countText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = (Button)findViewById(R.id.button);
        Button stopBtn = (Button)findViewById(R.id.button2);
        Button broadcastBtn = (Button)findViewById(R.id.button3);
        startBtn.setOnClickListener(btnListener);
        stopBtn.setOnClickListener(btnListener);
        broadcastBtn.setOnClickListener(btnListener);
        countText = (TextView)findViewById(R.id.textView2);
        IntentFilter intentFilter = new IntentFilter(CustomActions.COUNT_UPDATE_ACTION);
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    Intent startIntent = new Intent(MainActivity.this,NotificationBroadcastReceiverService.class);
                    startIntent.setAction(CustomActions.START_FOREGROUND_SERVICE);
                    startService(startIntent);
                    break;
                case R.id.button2:
                    Intent stopIntent = new Intent(MainActivity.this, NotificationBroadcastReceiverService.class);
                    stopIntent.setAction(CustomActions.STOP_FOREGROUND_SERVICE);
                    startService(stopIntent);
                    break;
                case R.id.button3:
                    Intent broadcast = new Intent(MainActivity.this,NotificationBroadcastReceiverService.class);
                    broadcast.setAction(CustomActions.SIMPLE_BROADCAST_ACTION);
                    startService(broadcast);
            }
        }
    };

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CustomActions.COUNT_UPDATE_ACTION)){
                Log.i(TAG,"Simple Broadcast Action Detected");
                int count = intent.getExtras().getInt("EXTRA_COUNT");
                Log.i(TAG,Integer.toString(count));
                countText.setText(Integer.toString(count));
            }
        }
    };
}
