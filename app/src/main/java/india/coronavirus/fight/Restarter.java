package india.coronavirus.fight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.i("Broadcast Listened", "Service tried to stop");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, APIService.class));
        } else {
            context.startService(new Intent(context, APIService.class));
        }
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            //start your sercice
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, APIService.class));
            } else {
                Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
                context.startService(new Intent(context, APIService.class));
            }
        }
        if (Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, APIService.class));
            } else {
                Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
                context.startService(new Intent(context, APIService.class));
            }
        }
        if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, APIService.class));
            } else {
                Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
                context.startService(new Intent(context, APIService.class));
            }
        }
    }
}
