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

    private int count = 0;
    boolean initialized = false;
    public NotificationBroadcastReceiverService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(CustomActions.START_FOREGROUND_SERVICE)){
            initialized = true;
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
            count = 0;

        }
        else if(intent.getAction().equals(CustomActions.STOP_FOREGROUND_SERVICE)) {
            if (initialized) {
                Log.i(TAG, "Received Stop Foreground Service Intent");
                stopForeground(true);
                stopSelf();
            } else{
                Log.i(TAG,"Service has not been initialized");
            }
        }
        else if(intent.getAction().equals(CustomActions.SIMPLE_BROADCAST_ACTION)) {
            if (initialized) {
                Log.i(TAG, "Received Simple Broadcast Action");
                Log.i(TAG, "Updating Count");
                count++;
                Intent countIntent = new Intent(CustomActions.COUNT_UPDATE_ACTION).putExtra("EXTRA_COUNT", count);
                sendBroadcast(countIntent);
            } else{
                Log.i(TAG,"Service has not been initialized");
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }


}
