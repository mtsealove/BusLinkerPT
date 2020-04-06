package com.mtsealove.github.buslinkerpt.Services;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mtsealove.github.buslinkerpt.Database.AlertOpenHelper;
import com.mtsealove.github.buslinkerpt.LoginActivity;
import com.mtsealove.github.buslinkerpt.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FireBaseMessagingService extends FirebaseMessagingService {
    AlertOpenHelper openHelper;
    SQLiteDatabase db;
    SimpleDateFormat dateFormat;

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "test");
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {

            sendNotification(remoteMessage.getData().get("message"));
            openHelper = new AlertOpenHelper(getApplicationContext(), AlertOpenHelper.Table, null, 1);
            db = openHelper.getWritableDatabase();
            dateFormat = new SimpleDateFormat("MM-dd");
            Date date = new Date();
            String time = dateFormat.format(date);
            openHelper.InsertAlert(db, "스케줄 변경", remoteMessage.getData().get("message"), time, "schedule");
            openHelper.close();
            db.close();
            Log.e("schedule", "true");
        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "80";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logo_negative)
                        .setContentTitle("BusLinker")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "alert";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

}
