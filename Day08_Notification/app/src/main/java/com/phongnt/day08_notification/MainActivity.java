package com.phongnt.day08_notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
private NotificationManager manager;
private int notiID = 6789;
private int numMsg = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToSend(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("New Message");
        builder.setContentText("Notification Demo: message has received");
        builder.setTicker("Message Alert");
        builder.setSmallIcon(R.drawable.ic_action_unread);
        builder.setNumber(++numMsg);

        TaskStackBuilder stack = TaskStackBuilder.create(this);
        Intent intent = new Intent(this, NotificationActivity.class);
        stack.addNextIntent(intent);
        PendingIntent pendingIntent = stack.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notiID, builder.build());

    }

    public void clickToCancel(View view) {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notiID);
    }

    public void clickToSendMultiple(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("New Message");
        builder.setContentText("Notification Demo: message has received");
        builder.setTicker("Message Alert");
        builder.setSmallIcon(R.drawable.ic_action_unread);
        builder.setNumber(++numMsg);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] content = {"HP +5"};
        inboxStyle.setBigContentTitle("LV UP");
        for (int i = 0; i < content.length; i++) {
            inboxStyle.addLine(content[i]);
        }
        builder.setStyle(inboxStyle);

        TaskStackBuilder stack = TaskStackBuilder.create(this);
        Intent intent = new Intent(this, NotificationActivity.class);
        stack.addNextIntent(intent);
        PendingIntent pendingIntent = stack.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notiID, builder.build());
    }
}
