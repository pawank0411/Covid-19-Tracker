package india.coronavirus.fight.ui.statewise;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
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

public class StateViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<StateData>> dataMutableLiveData;
    private ArrayList<StateData> stateDataArrayList = new ArrayList<>();

    public StateViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<ArrayList<StateData>> getData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<ArrayList<StateData>>();
            refreshData();
        }
        return dataMutableLiveData;
    }

    private void refreshData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("API", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sharedPreferences.getString("API", "http://ac41bf31.ngrok.io") + "/api/all", response -> {
            try {
                JSONObject json = new JSONObject(response);
                JSONArray jsonA = json.getJSONArray("all");
                for (int j = 0; j < jsonA.length(); j++) {
                    JSONObject js = jsonA.getJSONObject(j);
                    String statename = toTitleCase(js.getString("state"));
                    stateDataArrayList.add(new StateData(statename, js.getString("cases"),
                            js.getString("cured"), js.getString("death"), "Cases",
                            "Cured", "Deaths"));
                    dataMutableLiveData.setValue(stateDataArrayList);
                }
            } catch (JSONException e) {
                Log.e("Error", String.valueOf(e));
            }
        }, error -> Log.d("Error", Objects.requireNonNull(error.toString())));
        requestQueue.add(stringRequest);
    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
}

