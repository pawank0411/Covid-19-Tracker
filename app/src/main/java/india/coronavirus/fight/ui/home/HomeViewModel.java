package india.coronavirus.fight.ui.home;

import android.app.Application;
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
import java.util.Objects;

import india.coronavirus.fight.model.HeaderData;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<HeaderData>> dataMutableLiveData;
    private ArrayList<HeaderData> headerData = new ArrayList<>();
    private int new_cases, new_cured, new_death;
    public HomeViewModel(Application application) {
        super(application);
    }

    LiveData<ArrayList<HeaderData>> getData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<>();
            refreshData();
        }
        return dataMutableLiveData;
    }

    private void refreshData() {
        //predict and state => POST
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringReques = new StringRequest(Request.Method.GET,"http://6ccad673.ngrok.io/api/total",response -> {
            try {
                JSONObject jsonObject = new JSONObject();
                new_cases = Integer.parseInt(jsonObject.getString("cases"));
                new_cured = Integer.parseInt(jsonObject.getString("cured"));
                new_death = Integer.parseInt(jsonObject.getString("death"));
            } catch (JSONException e) {
                Log.d("Error", e.getMessage());
            }
        }, error -> {

        });
        //Need to be continued -> sharedPrefernces
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://6ccad673.ngrok.io/api/total", response -> {
            try {
                JSONObject json = new JSONObject(response);
                String filtered = "{\"stats\": [{\"stat1\":\"" + json.getInt("cases") +
                        "\",\"heading\":\"Total CASES\",\"subheading\":Total}," +
                        "{\"stat1\":\"" + json.getInt("cured") +
                        "\",\"heading\":\"Recovered CASES\",\"subheading\":Recovered}," +
                        "{\"stat1\":\"" + json.getInt("hospitalized") +
                        "\",\"heading\":\"Hospitalized CASES\",\"subheading\":Hospita}," +
                        "{\"stat1\":\"" + json.getInt("death") +
                        "\",\"heading\":\"Death CASES\",\"subheading\":Deaths}]}";

                JSONObject jsonObject = new JSONObject(filtered);
                JSONArray jsonArray = jsonObject.getJSONArray("stats");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    headerData.add(new HeaderData(jsonObject1.getString("stat1"), jsonObject1.getString("heading"), jsonObject1.getString("subheading")));
                    dataMutableLiveData.setValue(headerData);
                }
            } catch (JSONException e) {
                Log.e("Error", String.valueOf(e));
            }
        }, error -> Log.d("Error", Objects.requireNonNull(error.toString())));
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}