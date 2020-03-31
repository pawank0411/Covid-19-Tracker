package india.coronavirus.fight.utilities;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.StateData;

public class LoadPDF extends AppCompatActivity {
    private MenuItem searchItem;
    public static boolean isSearchBarOpen = false;
    private ArrayList<String> stateDataArrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templayout);
        PDFView pdfView = findViewById(R.id.pdfView);
        File file = new File("/storage/emulated/0/covid19 India/districtData.pdf");
        if (file.exists()) {
            pdfView.fromFile(new File("/storage/emulated/0/covid19 India/districtData.pdf"))
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    // allows to draw something on the current page, usually visible in the middle of the screen
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .load();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
