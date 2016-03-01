package com.example.jared.simplebroadcasterreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
                    Intent broadcast = new Intent().setAction(CustomActions.SIMPLE_BROADCAST_ACTION);
                    sendBroadcast(broadcast);
            }
        }
    };
}
