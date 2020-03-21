package india.coronavirus.fight.ui.news;

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
import java.util.Objects;

import india.coronavirus.fight.model.HeaderData;
import india.coronavirus.fight.model.NewData;

import static android.content.Context.MODE_PRIVATE;

public class NewsViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<NewData>> dataMutableLiveData;
    private ArrayList<NewData> headerData = new ArrayList<>();

    public NewsViewModel(Application application) {
        super(application);
    }

    LiveData<ArrayList<NewData>> getData() {
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET,   sharedPreferences.getString("API","http://6ccad673.ngrok.io")+ "/api/news", response -> {
            try {
                JSONObject json = new JSONObject(response);
                JSONArray jsonArray = json.getJSONArray("news");
                for (int i= 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    headerData.add(new NewData(jsonObject.getString("title"),jsonObject.getString("link"),jsonObject.getString("time")));
                    dataMutableLiveData.setValue(headerData);
                }
            } catch (JSONException e) {
                Log.e("Error", String.valueOf(e));
            }
        }, error -> Log.d("Error", Objects.requireNonNull(error.toString())));
        requestQueue.add(stringRequest);
    }
}