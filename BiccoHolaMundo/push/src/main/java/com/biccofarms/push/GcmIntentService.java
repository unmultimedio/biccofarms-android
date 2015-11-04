package com.biccofarms.push;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle args = intent.getExtras();

        if (!args.isEmpty()){
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
            switch (gcm.getMessageType(intent)){
                case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE:
                    notifyUser(args.getString("message", "Llegó un intent sin 'message'"));
                    break;
                default:
                    notifyUser("Llegó un intent diferente a MESSAGE_TYPE_MESSAGE");
            }
        } else {
            notifyUser("Llegó un intent sin extras");
        }
    }

    private void notifyUser(String s) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Notificación GCM")
                .setContentText(s)
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
