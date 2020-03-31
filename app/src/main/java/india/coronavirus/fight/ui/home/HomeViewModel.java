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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import india.coronavirus.fight.model.HeaderData;

import static android.content.Context.MODE_PRIVATE;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<HeaderData>> dataMutableLiveData;
    private ArrayList<HeaderData> headerData = new ArrayList<>();
    private int new_cases, new_cured, new_death, oldhospitalized;
    private String info,  dist_link;
    private String column;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public HomeViewModel(Application application) {
        super(application);
        sharedPreferences = getApplication().getSharedPreferences("API", MODE_PRIVATE);

    }

    LiveData<List<HeaderData>> getData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<>();
            loadData();
            refreshData();
        }

        return dataMutableLiveData;
    }

    public void refreshData() {
        //predict and state => POST
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        editor = sharedPreferences.edit();
        StringRequest re = new StringRequest(Request.Method.GET, sharedPreferences.getString("API", "http://ac41bf31.ngrok.io") + "/api/extras", response2 -> {
            try {
                JSONObject jsonObject2 = new JSONObject(response2);
                info = jsonObject2.getString("info");
                column = jsonObject2.getString("column");
                dist_link = jsonObject2.getString("dist_link");
                editor.putString("DIST", dist_link);
                editor.apply();
            } catch (JSONException e) {

            }
        }, error -> {
//
            Log.d("Error1", Objects.requireNonNull(error.toString()));
        });
        requestQueue.add(re);

        StringRequest stringReques = new StringRequest(Request.Method.GET, sharedPreferences.getString("API", "http://ac41bf31.ngrok.io") + "/api/new", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                new_cases = jsonObject.getInt("cases");
                new_cured = jsonObject.getInt("cured");
                new_death = jsonObject.getInt("death");
                if (new_cases >= new_cured + new_death) {
                    oldhospitalized = new_cases - (new_cured + new_death);
                } else {
                    oldhospitalized = (new_cured + new_death) - new_cases;
                }
                StringRequest stringRequest = new StringRequest(Request.Method.GET, sharedPreferences.getString("API", "http://ac41bf31.ngrok.io") + "/api/total", response1 -> {
                    try {
                        JSONObject json = new JSONObject(response1);
                        Log.d("response", String.valueOf(json));
                        String filtered = "{\"stats\": [{\"stat1\":\"" + json.getInt("cases") +
                                "\"," + "\"oldcount\":\"" + new_cases + "\",\"heading\":\"CONFIRMED\",\"subheading\":Total,\"color\":red}," +
                                "{\"stat1\":\"" + json.getInt("hospitalized") +
                                "\"," + "\"oldcount\":\"" + oldhospitalized + "\",\"heading\":\"ACTIVE\",\"subheading\":Active,\"color\":blue}," +
                                "{\"stat1\":\"" + json.getInt("cured") +
                                "\"," + "\"oldcount\":\"" + new_cured + "\",\"heading\":\"RECOVERED\",\"subheading\":Recovered,\"color\":green}," +
                                "{\"stat1\":\"" + json.getInt("death") +
                                "\"," + "\"oldcount\":\"" + new_death + "\",\"heading\":\"DEATH\",\"subheading\":Deaths,\"color\":gray}]}";

                        JSONObject jsonObject2 = new JSONObject(filtered);
                        JSONArray jsonArray = jsonObject2.getJSONArray("stats");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            headerData.add(new HeaderData(jsonObject1.getString("stat1"), jsonObject1.getString("heading"), jsonObject1.getString("subheading"), jsonObject1.getString("oldcount"),
                                    jsonObject1.getString("color"), info, column));
                            dataMutableLiveData.setValue(headerData);
                        }
                        saveData(headerData);
                    } catch (JSONException e) {
                        Log.e("Error2", String.valueOf(e));
                    }
                }, error -> {
                    Log.d("Error", Objects.requireNonNull(error.toString()));
                });
                //adding the string request to request queue
                requestQueue.add(stringRequest);

            } catch (JSONException e) {
                Log.d("Error3", e.getMessage());
            }
        }, error -> {
//
            Log.d("Error4", Objects.requireNonNull(error.toString()));
        });
        requestQueue.add(stringReques);
        //Need to be continued -> sharedPrefernces
    }

    public void saveData(ArrayList headerData) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(headerData);
        editor.putString("headerlist", json);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("headerlist", null);
        Type type = new TypeToken<ArrayList<HeaderData>>() {
        }.getType();
        headerData = gson.fromJson(json, type);

        if (headerData == null) {
            headerData = new ArrayList<>();
        }
        dataMutableLiveData.setValue(headerData);
    }
}