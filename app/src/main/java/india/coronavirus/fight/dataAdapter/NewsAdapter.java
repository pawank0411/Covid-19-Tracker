package india.coronavirus.fight.dataAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.HeaderData;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<ArrayList<HeaderData>> headerDataList;
    private Context mContext;
    private OnItemClickListner listner;

    public NewsAdapter(ArrayList<ArrayList<HeaderData>> headerDataList, Context mContext, OnItemClickListner listner) {
        this.headerDataList = headerDataList;
        this.mContext = mContext;
        this.listner = listner;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.new_Card.setOnClickListener(view -> {
            if (listner != null) {
                listner.onNewsClicked(headerDataList.get(position).get(position).getNews());
            }
        });
    }

    @Override
    public int getItemCount() {
        return headerDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newCard)
        CardView new_Card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListner {
        void onNewsClicked(String newsLink);
    }
}
