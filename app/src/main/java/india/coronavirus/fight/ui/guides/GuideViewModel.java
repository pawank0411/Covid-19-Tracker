package india.coronavirus.fight.ui.guides;

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

import india.coronavirus.fight.model.GuideData;

public class GuideViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<GuideData>> dataMutableLiveData;
    private ArrayList<GuideData> headerData = new ArrayList<>();

    public GuideViewModel(Application application) {
        super(application);
    }

    LiveData<ArrayList<GuideData>> getData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<>();
            refreshData();
        }
        return dataMutableLiveData;
    }

    private void refreshData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://6ccad673.ngrok.io/api/guides", response -> {
            try {
                Log.d("json", String.valueOf(response));
                JSONObject json = new JSONObject(response);
                JSONArray jsonA = json.getJSONArray("guides");
                for (int j = 0; j < jsonA.length(); j++) {
                    JSONObject js = jsonA.getJSONObject(j);
                    headerData.add(new GuideData(js.getString("title"), js.getString("link"), js.getString("description")));
                    dataMutableLiveData.setValue(headerData);
                }
            } catch (JSONException e) {
                Log.e("Error", String.valueOf(e));
            }
        }, error -> Log.d("Error", Objects.requireNonNull(error.toString())));
        requestQueue.add(stringRequest);
    }
}