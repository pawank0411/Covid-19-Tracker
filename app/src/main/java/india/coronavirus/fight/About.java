package india.coronavirus.fight;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

public class About extends AppCompatActivity {
    private boolean update;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        MaterialTextView check = findViewById(R.id.update_available);
        MaterialTextView pawan = findViewById(R.id.pawan);
        MaterialTextView sppedx = findViewById(R.id.speedx);
        MaterialTextView desp = findViewById(R.id.desp);
        CollectionReference apiCollection = FirebaseFirestore.getInstance().collection("update");
        apiCollection.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    update = documentChange.getDocument().getBoolean("available");
                    if (update) {
                        check.setVisibility(View.VISIBLE);
                        desp.setVisibility(View.VISIBLE);
                        check.setText(Html.fromHtml("<u>Update Available : http://tiny.cc/covid-19india</u>"));
                    }

                }
            }
        });
        pawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://pawan0411.github.io/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        sppedx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/TheSpeedX");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}
