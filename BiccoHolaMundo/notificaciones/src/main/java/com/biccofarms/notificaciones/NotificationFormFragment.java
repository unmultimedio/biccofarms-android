package com.biccofarms.notificaciones;

/**
 * Created by julian on 10/26/15.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotificationFormFragment extends Fragment {

    NotificationCommunication myParentInterface;
    Activity myParentActivity;
    EditText titleET, contentET;
    int notCount = 3;

    public NotificationFormFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
        try {
            myParentInterface = (NotificationCommunication) activity;
        } catch (ClassCastException e){
            Log.e("bicco", "La actividad no implementa " +
                    NotificationCommunication.class.getCanonicalName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        titleET = (EditText) rootView.findViewById(R.id.et_title);
        contentET = (EditText) rootView.findViewById(R.id.et_content);
        return rootView;
    }

    public void makeNotification(int button) {
        String title = titleET.getText().toString();
        String content = contentET.getText().toString();

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(myParentActivity)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content);

        builder.setTicker("Hola! " + content)
                .setAutoCancel(true)
                .setContentInfo("2 msj");
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(myParentActivity, ResultsActivity.class);
        intent.putExtra("title",title);
        PendingIntent pi = PendingIntent.getActivity(myParentActivity,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pi);

        long[] patternVibe = {50,400,100,400,100,400,100,1000};
        builder.setVibrate(patternVibe);
        builder.setLights(getResources().getColor(R.color.pink), 500, 2000);

        builder.addAction(android.R.drawable.ic_input_add, "Responder", pi);

        Intent resultIntent = new Intent(myParentActivity, ResultsActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(myParentActivity);
// Adds the back stack
        stackBuilder.addParentStack(ResultsActivity.class);
// Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
// Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(android.R.drawable.ic_input_delete, "Borrar", resultPendingIntent);

        NotificationCompat.InboxStyle is = new NotificationCompat.InboxStyle()
                .setBigContentTitle(title.toUpperCase());

        for(int i=0; i<5; i++){
            is.addLine("L("+i+") "+content);
        }

        builder.setStyle(is);

        final NotificationManager nm = (NotificationManager)
                myParentActivity.getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification n = builder.build();

        int notId = 0;
        switch (button){
            case R.id.notify_btn:
                notId = 1;
                break;
            case R.id.progress_notify_btn:
                notId = 2;

                final int finalNotId = notId;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        builder.setStyle(null);
                        builder.setSound(null);
                        int maximum = 100;
                        for(int increment=0; increment<=maximum; increment+=3){
                            builder.setProgress(maximum, increment, false);
                            builder.setContentText("Descargado: "+increment+"%");
                            nm.notify(finalNotId, builder.build());
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.e("bicco","Error durmiendo");
                            }
                        }
                        builder.setProgress(0,0,false);
                        builder.setContentText("Descarga completa");
                        nm.notify(finalNotId, builder.build());
                    }
                };

                Thread newThread = new Thread(runnable);
                newThread.start();

                break;
            case R.id.new_notify_btn:
                notId = notCount++;
                break;
        }

        nm.notify(notId, builder.build());

    }
}
