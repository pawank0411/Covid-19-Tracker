package india.coronavirus.fight.dataAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.model.StateData;

public class HelplineAdapter extends RecyclerView.Adapter<HelplineAdapter.ViewHolder> {
    private List<StateData> headerDataList;
    private Context mContext;

    public HelplineAdapter(List<StateData> headerDatalist, Context context) {
        this.headerDataList = headerDatalist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public HelplineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.helpline_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelplineAdapter.ViewHolder holder, int position) {
        holder.statename.setText(headerDataList.get(position).getStatename());
        holder.phoneNumber.setText(headerDataList.get(position).getHelpline());

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + headerDataList.get(position).getHelpline().trim()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return headerDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.statename)
        MaterialTextView statename;
        @BindView(R.id.phoneNumber)
        MaterialTextView phoneNumber;
        @BindView(R.id.phoneIcon)
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
