package india.coronavirus.fight.ui.guides;

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

import india.coronavirus.fight.model.GuideData;
import india.coronavirus.fight.model.HeaderData;

import static android.content.Context.MODE_PRIVATE;

public class GuideViewModel extends AndroidViewModel {
    private MutableLiveData<List<GuideData>> dataMutableLiveData;
    private ArrayList<GuideData> headerData = new ArrayList<>();

    public GuideViewModel(Application application) {
        super(application);
    }

    LiveData<List<GuideData>> getData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<>();
            refreshData();
        }
        return dataMutableLiveData;
    }

    private SharedPreferences sharedPreferences = getApplication().getSharedPreferences("API", MODE_PRIVATE);

    private void refreshData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sharedPreferences.getString("API", "") + "/api/guides", response -> {
            try {
                Log.d("json", String.valueOf(response));
                JSONObject json = new JSONObject(response);
                JSONArray jsonA = json.getJSONArray("guides");
                for (int j = 0; j < jsonA.length(); j++) {
                    JSONObject js = jsonA.getJSONObject(j);
                    headerData.add(new GuideData(js.getString("title"), js.getString("link"), js.getString("description")));
                    saveData(headerData);
                    dataMutableLiveData.setValue(headerData);
                }
            } catch (JSONException e) {
                loadData();
                Log.e("Error", String.valueOf(e));
            }
        }, error -> {
            loadData();
            Log.d("Error", Objects.requireNonNull(error.toString()));
        });
        requestQueue.add(stringRequest);
    }
    public void saveData(ArrayList headerData) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(headerData);
        editor.putString("guidelist", json);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("guidelist", null);
        Type type = new TypeToken<ArrayList<GuideData>>() {
        }.getType();
        headerData = gson.fromJson(json, type);

        if (headerData == null) {
            headerData = new ArrayList<>();
        }
        dataMutableLiveData.setValue(headerData);
    }
}