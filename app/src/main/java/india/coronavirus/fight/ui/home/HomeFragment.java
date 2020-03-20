package india.coronavirus.fight.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import india.coronavirus.fight.dataAdapter.DataAdapterMain;
import india.coronavirus.fight.model.HeaderData;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<ArrayList<HeaderData>> headerDatalist = new ArrayList<ArrayList<HeaderData>>();;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        DataAdapterMain dataAdapter = new DataAdapterMain(headerDatalist, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);
        homeViewModel.getData().observe(Objects.requireNonNull(getActivity()), data1 -> {
            if (data1 != null) {
                headerDatalist.add(data1);
                dataAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

}
