package india.coronavirus.fight.ui.news;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import india.coronavirus.fight.model.HeaderData;

public class NewsViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<HeaderData>> dataMutableLiveData;
    private ArrayList<HeaderData> headerData = new ArrayList<>();

    public NewsViewModel(Application application) {
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://6ccad673.ngrok.io/api/news", response -> {
            try {
                JSONObject json = new JSONObject(response);
                String[] news = json.getString("news").split("\n");

                for (int i = 0; i < news.length; i++) {
                    headerData.add(new HeaderData(news[i]));
                    dataMutableLiveData.setValue(headerData);
                }
            } catch (JSONException e) {
                Log.e("Error", String.valueOf(e));
            }
        }, error -> Log.d("Error", Objects.requireNonNull(error.toString())));
        requestQueue.add(stringRequest);
    }
}