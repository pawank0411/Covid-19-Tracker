package india.coronavirus.fight.dataAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.HeaderData;

public class DataAdapterMain extends RecyclerView.Adapter<DataAdapterMain.ViewHolder> {
    private ArrayList<ArrayList<HeaderData>> headerDataList;
    private Context mContext;

    public DataAdapterMain(ArrayList<ArrayList<HeaderData>> headerDatalist, Context context) {
        this.headerDataList = headerDatalist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DataAdapterMain.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapterMain.ViewHolder holder, int position) {
        holder.heading_card.setText(headerDataList.get(position).get(position).getHeader());
        holder.total_case.setText(headerDataList.get(position).get(position).getCases());
        holder.sub_heading_1.setText(headerDataList.get(position).get(position).getSubheader());
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
        //        @BindView(R.id.newcase)
//        MaterialTextView new_case;
        @BindView(R.id.subheading1)
        MaterialTextView sub_heading_1;
//        @BindView(R.id.subheading2)
//        MaterialTextView sub_heading_2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
