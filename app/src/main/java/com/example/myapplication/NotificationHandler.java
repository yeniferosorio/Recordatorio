package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

public class NotificationHandler extends ContextWrapper {
    private NotificationManager manager;
    public static final  String CHANNEL_HIGH_ID="1";
    private final String CHANNEL_HIHG_NAME="HIHG CHANNEL";

    public static final String CHANNEL_LOW_ID="2";
    private final String CHANNEL_LOW_NAME="HIHG CHANNEL";

    public NotificationHandler(Context context) {
        super(context);
        createChannels();
    }

    public NotificationManager getManager(){
      if(manager==null){
          manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return manager;
    }

    private void createChannels(){
        if(Build.VERSION.SDK_INT>= 26){
            //CREAR HIGH CHANNEL
            NotificationChannel highChannel =new NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIHG_NAME,NotificationManager.IMPORTANCE_HIGH);

            //extra config

            highChannel.enableLights(true);
            highChannel.setLightColor(Color.GREEN);
            highChannel.setShowBadge(true);
            highChannel.enableVibration(true);


            NotificationChannel lowChannel =new NotificationChannel(CHANNEL_LOW_ID,CHANNEL_LOW_NAME,NotificationManager.IMPORTANCE_LOW);

            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);


        }

    }


    public Notification.Builder createNotification(String title,String message,String fecha,String hora,boolean isHighImportance){
       if(Build.VERSION.SDK_INT>=26){
           if(isHighImportance){
               return this.createNotificationWithChannel(title,message,fecha,hora,CHANNEL_HIGH_ID);
           }
           return this.createNotificationWithChannel(title,message,fecha,hora,CHANNEL_LOW_ID);
       }
       return this.createNotificationWithoutChannel(title,message);
    }

    private Notification.Builder createNotificationWithChannel(String title,String message,String fecha,String hora,String channelId){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        return new Notification.Builder(getApplicationContext(),channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setContentText(fecha)
                .setContentText(hora)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true);
    }
return null;
    }

    private Notification.Builder createNotificationWithoutChannel(String title,String message){
        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true);


    }

}
