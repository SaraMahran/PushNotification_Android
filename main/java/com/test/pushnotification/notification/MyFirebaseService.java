package com.test.pushnotification.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.test.pushnotification.MainActivity;
import com.test.pushnotification.R;

import java.util.Objects;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID = "101";
    FirebaseDatabase rootNode;
    DatabaseReference reference;




    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
//        Log.d(TAG, "onMessageReceived:"+remoteMessage.getNotification().getTitle());
//        Log.d(TAG, "onMessageReceived:"+remoteMessage.getNotification().getBody());
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        rootNode = FirebaseDatabase.getInstance();
        reference= rootNode.getReference((remoteMessage.getNotification().getTitle()));
        reference.setValue(remoteMessage.getNotification().getBody());


    }
    private void showNotification(String title, String message){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

}
