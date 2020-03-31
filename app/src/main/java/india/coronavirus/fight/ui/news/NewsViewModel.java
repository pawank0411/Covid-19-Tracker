package india.coronavirus.fight.ui.news;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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
import india.coronavirus.fight.model.NewData;

import static android.content.Context.MODE_PRIVATE;

public class NewsViewModel extends AndroidViewModel {

    private MutableLiveData<List<NewData>> dataMutableLiveData;
    private ArrayList<NewData> headerData = new ArrayList<>();

    public NewsViewModel(Application application) {
        super(application);
    }

    LiveData<List<NewData>> getData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<>();
            refreshData();
        }
        return dataMutableLiveData;
    }

    private void refreshData() {
        //predict and state => POST
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("API", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,   sharedPreferences.getString("API","http://ac41bf31.ngrok.io")+ "/api/news", response -> {
            try {
                JSONObject json = new JSONObject(response);
                JSONArray jsonArray = json.getJSONArray("news");
                for (int i= 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    headerData.add(new NewData(jsonObject.getString("title"),jsonObject.getString("link"),jsonObject.getString("time")));
                    dataMutableLiveData.setValue(headerData);
                }
                saveData(headerData);
            } catch (JSONException e) {
                loadData();
                Log.e("Error2", String.valueOf(e));
            }
        }, error -> {
            loadData();
            Log.d("Error1", Objects.requireNonNull(error.toString()));
        });
        requestQueue.add(stringRequest);
    }

    public void saveData(ArrayList headerData) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(headerData);
        editor.putString("newslist", json);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("newslist", null);
        Type type = new TypeToken<ArrayList<NewData>>() {
        }.getType();
        headerData = gson.fromJson(json, type);

        if (headerData == null) {
            headerData = new ArrayList<>();
        }
        dataMutableLiveData.setValue(headerData);
    }
}