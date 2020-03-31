package india.coronavirus.fight;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class About extends AppCompatActivity {
private  boolean update;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        MaterialTextView check = findViewById(R.id.update_available1);
        MaterialTextView check2 = findViewById(R.id.update_available2);
        MaterialTextView pawan = findViewById(R.id.pawan);
        MaterialTextView sppedx = findViewById(R.id.speedx);
        MaterialTextView desptitle = findViewById(R.id.desptitle);
        MaterialTextView maintitle = findViewById(R.id.maintitle);
        MaterialTextView or = findViewById(R.id.or);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            update = Objects.requireNonNull(getIntent().getExtras()).getBoolean("fromMainActivity");
            if (update) {
                pawan.setVisibility(View.INVISIBLE);
                sppedx.setVisibility(View.INVISIBLE);
                desptitle.setVisibility(View.INVISIBLE);
                maintitle.setVisibility(View.INVISIBLE);

                check.setVisibility(View.VISIBLE);
                or.setVisibility(View.VISIBLE);
                check2.setVisibility(View.VISIBLE);
                check.setOnClickListener(view -> {
                    Uri uri = Uri.parse("http://tiny.cc/covid-19india");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                });

                check2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://github.com/Pawan0411/Covid-19/releases");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }
        pawan.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://pawan0411.github.io/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        sppedx.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/TheSpeedX");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        if (update) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }
}
