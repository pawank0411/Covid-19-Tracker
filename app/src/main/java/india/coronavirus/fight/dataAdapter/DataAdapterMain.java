package india.coronavirus.fight.dataAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.HeaderData;

import static android.content.Context.MODE_PRIVATE;

public class DataAdapterMain extends RecyclerView.Adapter<DataAdapterMain.ViewHolder> {
    private List<HeaderData> headerDataList;
    private Context mContext;
    private SharedPreferences sharedPreferences ;

    public DataAdapterMain(ArrayList<HeaderData> headerDatalist, Context context) {
        this.headerDataList = headerDatalist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DataAdapterMain.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DataAdapterMain.ViewHolder holder, int position) {
        sharedPreferences = mContext.getSharedPreferences("API", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("showgraph",true)) {
            holder.grahView.setVisibility(View.GONE);
        }
        HeaderData headerData = headerDataList.get(position);
        holder.grahView.setScrollbarFadingEnabled(true);
        holder.grahView.setVerticalScrollBarEnabled(false);
        holder.grahView.setHorizontalScrollBarEnabled(false);
        if (headerData.getHeader().trim().equals("CONFIRMED")) {
            holder.grahView.loadUrl("http://ac41bf31.ngrok.io/api/graphsvg/cases");
        } else if (headerData.getHeader().trim().equals("ACTIVE")) {
            holder.grahView.loadUrl("http://ac41bf31.ngrok.io/api/graphsvg/active");
        } else if (headerData.getHeader().trim().equals("RECOVERED")) {
            holder.grahView.loadUrl("http://ac41bf31.ngrok.io/api/graphsvg/cured");
        } else if (headerData.getHeader().trim().equals("DEATH")) {
            holder.grahView.loadUrl("http://ac41bf31.ngrok.io/api/graphsvg/death");
        }
        if (headerData.getColor().trim().equals("red")) {
            holder.heading_card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#34FB0032")));
            holder.heading_card.setTextColor(Color.parseColor("#B0FB0032"));
            holder.total_case.setTextColor(Color.parseColor("#B0FB0032"));
            holder.new_case.setTextColor(Color.parseColor("#B0FB0032"));
        } else if (headerData.getColor().trim().equals("green")) {
            holder.heading_card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2748A107")));
            holder.heading_card.setTextColor(Color.parseColor("#D348A107"));
            holder.total_case.setTextColor(Color.parseColor("#D348A107"));
            holder.new_case.setTextColor(Color.parseColor("#D348A107"));
        } else if (headerData.getColor().trim().equals("blue")) {
            holder.heading_card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2000C5F6")));
            holder.heading_card.setTextColor(Color.parseColor("#D300C5F6"));
            holder.total_case.setTextColor(Color.parseColor("#D300C5F6"));
            holder.new_case.setTextColor(Color.parseColor("#D300C5F6"));
        } else if (headerData.getColor().trim().equals("gray")) {
            holder.heading_card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22828383")));
            holder.heading_card.setTextColor(Color.parseColor("#D0828383"));
            holder.total_case.setTextColor(Color.parseColor("#D0828383"));
            holder.new_case.setTextColor(Color.parseColor("#D0828383"));
        }
        holder.heading_card.setText(headerDataList.get(position).getHeader());
        holder.total_case.setText(headerDataList.get(position).getCases());
        holder.sub_heading_1.setText(headerDataList.get(position).getSubheader());
        holder.new_case.setText(headerDataList.get(position).getNewcase());
    }

    @Override
    public int getItemCount() {
        return headerDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.heading)
        MaterialButton heading_card;
        @BindView(R.id.totalcase)
        MaterialTextView total_case;
        @BindView(R.id.newcase)
        MaterialTextView new_case;
        @BindView(R.id.subheading1)
        MaterialTextView sub_heading_1;
        @BindView(R.id.subheading2)
        MaterialTextView sub_heading_2;
        @BindView(R.id.graph)
        WebView grahView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
