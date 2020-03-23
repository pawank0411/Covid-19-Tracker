package india.coronavirus.fight.ui.helpline;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import india.coronavirus.fight.model.StateData;

import static android.content.Context.MODE_PRIVATE;

public class HelplineViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<StateData>> dataMutableLiveData;
    private ArrayList<StateData> headerData = new ArrayList<>();

    public HelplineViewModel(Application application) {
        super(application);
    }

    LiveData<ArrayList<StateData>> getData() {
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sharedPreferences.getString("API", "http://ac41bf31.ngrok.io") + "/api/helpline", response -> {
            try {
                JSONObject json = new JSONObject(response);
                JSONArray jsonArray = json.getJSONArray("helpline");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String sr;
                    if (jsonObject.getString("state").contains("&amp;")) {
                        sr = jsonObject.getString("state").replace("&amp;", "&");
                        headerData.add(new StateData(sr, jsonObject.getString("phone")));
                        dataMutableLiveData.setValue(headerData);
                    } else {
                        headerData.add(new StateData(jsonObject.getString("state"), jsonObject.getString("phone")));
                        dataMutableLiveData.setValue(headerData);
                    }
                }
            } catch (JSONException e) {
                Log.e("Error", String.valueOf(e));
            }
        }, error -> Log.d("Error", Objects.requireNonNull(error.toString())));
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}