package india.coronavirus.fight;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.content.Context.ALARM_SERVICE;

public class Notification extends BroadcastReceiver {
    private static final String TAG = "Notification";
    private NotificationCompat.Builder notificationBuilder;
    @SuppressWarnings("FieldCanBeLocal")
    private int notificationId = 100;
    @SuppressWarnings("FieldCanBeLocal")
    private String CHANNEL_ID = "my_channel_01";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notifications";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setSound(null, null);
            mChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.drawable.bell_ring)
                    .setContentIntent(PendingIntent.getActivity(context, 131314, intent1,
                            PendingIntent.FLAG_UPDATE_CURRENT))
                    //Message
                    .setContentTitle("Check new updates on Covid-19 India!")
                    .setContentText("Data updated")
                    .setChannelId(CHANNEL_ID)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(null)
                    .setLights(Color.GREEN, 3000, 3000)
                    .setColor(Color.parseColor("#12921F"))
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setAutoCancel(true);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
            if (notificationManager != null) {
                notificationManager.notify(notificationId, notificationBuilder.build());
            }
            Log.v(TAG, "Notification sent");
            Intent intent2 = new Intent(context, Notification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.drawable.bell_ring)
                    .setContentIntent(PendingIntent.getActivity(context, 131314, intent1,
                            PendingIntent.FLAG_UPDATE_CURRENT))
                    //Message
                    .setContentTitle("Check new updates on Covid-19 India!")
                    .setContentText("Data updated")
                    .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                    .setSound(null)
                    .setLights(Color.GREEN, 3000, 3000)
                    .setColor(Color.parseColor("#12921F"))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setAutoCancel(true);
            if (notificationManager != null) {
                notificationManager.notify(notificationId, notificationBuilder.build());
            }
            Log.v(TAG, "Notification sent");
            Intent intent2 = new Intent(context, Notification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
        }
    }
}


