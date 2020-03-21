package india.coronavirus.fight;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class APIService extends Service {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public APIService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        sharedPreferences = this.getSharedPreferences("NOTIFICATION", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://6ccad673.ngrok.io/api/new", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("report");

                if (!message.isEmpty()) {
                    if (!sharedPreferences.getString("report", "").equals(message)) {
                        editor.putString("report", message);
                        editor.apply();
                        PowerManager pm = (PowerManager) getApplication().getSystemService(POWER_SERVICE);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MINUTE, 1);
                        Intent intent1 = new Intent(getApplicationContext(), Notification.class);
                        if (pm != null && pm.isDeviceIdleMode()) {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getApplication().getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                            }
                        } else {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getApplication().getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                Log.d("Error", e.getMessage());
            }

        }, error -> {
        });
        requestQueue.add(stringRequest);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        sharedPreferences = this.getSharedPreferences("NOTIFICATION", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://6ccad673.ngrok.io/api/new", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("report");

                if (!message.isEmpty()) {
                    if (!sharedPreferences.getString("report", "").equals(message)) {
                        editor.putString("report", message);
                        editor.apply();
                        PowerManager pm = (PowerManager) getApplication().getSystemService(POWER_SERVICE);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MINUTE, 1);
                        Intent intent1 = new Intent(getApplicationContext(), Notification.class);
                        if (pm != null && pm.isDeviceIdleMode()) {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getApplication().getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                            }
                        } else {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getApplication().getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                Log.d("Error", e.getMessage());
            }

        }, error -> {
        });
        requestQueue.add(stringRequest);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        sharedPreferences = this.getSharedPreferences("NOTIFICATION", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://6ccad673.ngrok.io/api/new", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("report");

                if (!message.isEmpty()) {
                    if (!sharedPreferences.getString("report", "").equals(message)) {
                        editor.putString("report", message);
                        editor.apply();
                        PowerManager pm = (PowerManager) getApplication().getSystemService(POWER_SERVICE);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MINUTE, 1);
                        Intent intent1 = new Intent(getApplicationContext(), Notification.class);
                        if (pm != null && pm.isDeviceIdleMode()) {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getApplication().getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                            }
                        } else {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getApplication().getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                Log.d("Error", e.getMessage());
            }

        }, error -> {
        });
        requestQueue.add(stringRequest);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }
}
