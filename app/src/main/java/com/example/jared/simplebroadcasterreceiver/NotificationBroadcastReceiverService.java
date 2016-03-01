package com.example.jared.simplebroadcasterreceiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class NotificationBroadcastReceiverService extends Service {
    private static final String TAG = NotificationBroadcastReceiverService.class.getSimpleName();
    public NotificationBroadcastReceiverService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Registering Broadcast Receiver");
        IntentFilter intentFilter = new IntentFilter(CustomActions.SIMPLE_BROADCAST_ACTION);
        registerReceiver(receiver,intentFilter);
        Log.i(TAG,"Receiver Registered Successfully");

        if (intent.getAction().equals(CustomActions.START_FOREGROUND_SERVICE)){
            Intent activityIntent = new Intent(this,MainActivity.class);
            activityIntent.setAction(CustomActions.MAIN_ACTION);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);
            Log.i(TAG, "Received Start Foreground Service Intent");
            Notification.Builder nBuilder = new Notification.Builder(this)
                    .setContentTitle("Foreground Service")
                    .setContentText("Running..")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.black_circle)
                    .setOngoing(true);
            Notification notification = nBuilder.build();
            startForeground(CustomActions.FOREGROUND_SERVICE_ID, notification);

        }
        else if(intent.getAction().equals(CustomActions.STOP_FOREGROUND_SERVICE)) {
            Log.i(TAG, "Received Stop Foreground Service Intent");
            stopForeground(true);
            stopSelf();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        Log.i(TAG, "Receiver Unregistered");
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CustomActions.SIMPLE_BROADCAST_ACTION)){
                Log.i(TAG,"Simple Broadcast Action Detected");
            }
        }
    };

}
