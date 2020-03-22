package india.coronavirus.fight.ui.home;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import india.coronavirus.fight.model.HeaderData;

import static android.content.Context.MODE_PRIVATE;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<HeaderData>> dataMutableLiveData;
    private ArrayList<HeaderData> headerData = new ArrayList<>();
    private int new_cases, new_cured, new_death, oldhospitalized;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public HomeViewModel(Application application) {
        super(application);
        preferences = getApplication().getSharedPreferences("NEWDATA", MODE_PRIVATE);
    }

    LiveData<List<HeaderData>> getData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<>();
            refreshData();
        }
        return dataMutableLiveData;
    }

    private void refreshData() {
        //predict and state => POST
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringReques = new StringRequest(Request.Method.GET, "http://6ccad673.ngrok.io/api/new", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                new_cases = jsonObject.getInt("cases");
                new_cured = jsonObject.getInt("cured");
                new_death = jsonObject.getInt("death");
                editor = preferences.edit();
                oldhospitalized = new_cases - (new_cured + new_death);
                if (oldhospitalized != 0) {
                    editor.putInt("new_hospitalized", oldhospitalized);
                    editor.apply();
                }
                if (new_cases != 0) {
                    editor.putInt("new_case", new_cases);
                    editor.apply();
                }
                if (new_cured != 0) {
                    editor.putInt("new_cured", new_cured);
                    editor.apply();
                }
                if (new_death != 0) {
                    editor.putInt("new_death", new_death);
                    editor.apply();
                }

            } catch (JSONException e) {
                Log.d("Error", e.getMessage());
            }
        }, error -> {
        });
        requestQueue.add(stringReques);
        //Need to be continued -> sharedPrefernces
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("API", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sharedPreferences.getString("API", "http://6ccad673.ngrok.io") + "/api/total", response -> {
            try {
                JSONObject json = new JSONObject(response);
                Log.d("response", String.valueOf(json));
                String filtered = "{\"stats\": [{\"stat1\":\"" + json.getInt("cases") +
                        "\"," + "\"oldcount\":\"" + preferences.getInt("new_case", 0) + "\",\"heading\":\"Total CASES\",\"subheading\":Total}," +
                        "{\"stat1\":\"" + json.getInt("cured") +
                        "\"," + "\"oldcount\":\"" + preferences.getInt("new_cured", 0) + "\",\"heading\":\"Recovered CASES\",\"subheading\":Recovered}," +
                        "{\"stat1\":\"" + json.getInt("death") +
                        "\"," + "\"oldcount\":\"" + preferences.getInt("new_death", 0) + "\",\"heading\":\"Death CASES\",\"subheading\":Deaths}," +
                        "{\"stat1\":\"" + json.getInt("hospitalized") +
                        "\"," + "\"oldcount\":\"" + preferences.getInt("new_hospitalized", 0) + "\",\"heading\":\"Hospitalized CASES\",\"subheading\":Hospitalized}]}";

                JSONObject jsonObject = new JSONObject(filtered);
                JSONArray jsonArray = jsonObject.getJSONArray("stats");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    headerData.add(new HeaderData(jsonObject1.getString("stat1"), jsonObject1.getString("heading"), jsonObject1.getString("subheading"), jsonObject1.getString("oldcount")));
                    dataMutableLiveData.setValue(headerData);
                }
            } catch (JSONException e) {
                Log.e("Error", String.valueOf(e));
            }
        }, error -> {
            Log.d("Error", Objects.requireNonNull(error.toString()));
        });
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}