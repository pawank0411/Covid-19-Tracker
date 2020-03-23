package india.coronavirus.fight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.onesignal.OneSignal;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private String api;
    private String version;
    private SharedPreferences.Editor editor;
    private String new_version;
    private String newVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        MaterialTextView textView = findViewById(R.id.marquee);
        textView.setSelected(true);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        SharedPreferences sharedPreferences = this.getSharedPreferences("API", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        CollectionReference apiCollection = FirebaseFirestore.getInstance().collection("apilink");
        apiCollection.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    api = documentChange.getDocument().getString("api");
                    new_version = documentChange.getDocument().getString("version");
                    editor.putString("new_version", version);
                    editor.putString("API", api);
                    editor.apply();
                }
            }
            try {
                PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                newVersion = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e1) {
                e1.printStackTrace();
            }
            if (new_version.equals(newVersion)) {
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                        .build();
                navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupWithNavController(navView, navController);
            } else {
                Intent intent = new Intent(this, About.class);
                intent.putExtra("fromMainActivity", true);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav, menu);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        MenuCompat.setGroupDividerEnabled(menu, true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about_app) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    private void openPowerSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        context.startActivity(intent);
    }
}
