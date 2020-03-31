package india.coronavirus.fight.dataAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.NewData;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewData> headerDataList;
    private Context mContext;
    private OnItemClickListner listner;

    public NewsAdapter(List<NewData> headerDataList, Context mContext, OnItemClickListner listner) {
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
                listner.onNewsClicked(headerDataList.get(position).getLink());
            }
        });
        holder.title.setText(headerDataList.get(position).getTitle());
        holder.time.setText(headerDataList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return headerDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newCard)
        CardView new_Card;
        @BindView(R.id.news_title)
        MaterialTextView title;
        @BindView(R.id.news_time)
        MaterialTextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListner {
        void onNewsClicked(String newsLink);
    }
}
