package india.coronavirus.fight.dataAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.GuideData;


public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {
    private ArrayList<ArrayList<GuideData>> headerDataList;
    private Context mContext;

    public GuideAdapter(ArrayList<ArrayList<GuideData>> headerDataList, Context mContext) {
        this.headerDataList = headerDataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GuideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guides_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideAdapter.ViewHolder holder, int position) {
        holder.guideTitle.setText(headerDataList.get(position).get(position).getTitle());
        holder.guideDesp.setText(headerDataList.get(position).get(position).getDesp());

        holder.cardView.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(headerDataList.get(position).get(position).getLink()));
            mContext.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return headerDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.guide_title)
        MaterialTextView guideTitle;
        @BindView(R.id.guide_desp)
        MaterialTextView guideDesp;
        @BindView(R.id.newCard)
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
