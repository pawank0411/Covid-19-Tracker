package india.coronavirus.fight.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.R;
import india.coronavirus.fight.dataAdapter.NewsAdapter;
import india.coronavirus.fight.model.HeaderData;

public class NewsFragment extends Fragment implements NewsAdapter.OnItemClickListner {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private NewsAdapter newsAdapter;
    private ArrayList<ArrayList<HeaderData>> headerDataArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel dashboardViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        ButterKnife.bind(this,root);
        newsAdapter = new NewsAdapter(headerDataArrayList, getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);

        dashboardViewModel.getData().observe(Objects.requireNonNull(getActivity()), data1 -> {
            if (data1 != null) {
                headerDataArrayList.add(data1);
                newsAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void onNewsClicked(String link) {
        Toast.makeText(getContext(), "Clicked : " + link, Toast.LENGTH_SHORT).show();
    }
}
