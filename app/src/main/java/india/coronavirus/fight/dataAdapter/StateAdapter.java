package india.coronavirus.fight.dataAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.StateData;

import static android.content.ContentValues.TAG;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
    private ArrayList<ArrayList<StateData>> stateDataList;
    private Context mContext;
    private boolean isClicked;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public StateAdapter(ArrayList<ArrayList<StateData>> stateDataList, Context mContext) {
        this.stateDataList = stateDataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {

        holder.statename.setText(stateDataList.get(position).get(position).getStatename());
        holder.cases.setText(stateDataList.get(position).get(position).getCases());
        holder.cured.setText(stateDataList.get(position).get(position).getCured());
        holder.deaths.setText(stateDataList.get(position).get(position).getDeath());
        holder.subheading1.setText(stateDataList.get(position).get(position).getSubheading1());
        holder.subheading3.setText(stateDataList.get(position).get(position).getSubheading2());
        holder.subheading2.setText(stateDataList.get(position).get(position).getSubheading3());

        holder.case_predict.setOnClickListener(view -> {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            Map<String, String> postParam = new HashMap<String, String>();
            postParam.put("state", stateDataList.get(position).get(position).getStatename());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "http://6ccad673.ngrok.io/api/predict", new JSONObject(postParam),
                    response -> {
                        try {
                            JSONObject json = new JSONObject(String.valueOf(response));
                            holder.case_predict.setText(df2.format(json.getDouble("probability")) + " %");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> VolleyLog.d(TAG, "Error: " + error.getMessage())) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            jsonObjReq.setTag(TAG);
            // Adding request to request queue
            queue.add(jsonObjReq);
        });
    }

    @Override
    public int getItemCount() {
        return stateDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.heading)
        MaterialButton statename;
        @BindView(R.id.totalcase)
        MaterialTextView cases;
        @BindView(R.id.subheading1)
        MaterialTextView subheading1;
        @BindView(R.id.totalcured)
        MaterialTextView cured;
        @BindView(R.id.subheading2)
        MaterialTextView subheading2;
        @BindView(R.id.newcase)
        MaterialTextView deaths;
        @BindView(R.id.subheading3)
        MaterialTextView subheading3;
        @BindView(R.id.predicted)
        MaterialTextView case_predict;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
