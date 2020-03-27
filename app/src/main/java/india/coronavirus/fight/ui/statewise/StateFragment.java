package india.coronavirus.fight.ui.statewise;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.HeatMap;
import india.coronavirus.fight.R;
import india.coronavirus.fight.dataAdapter.StateAdapter;
import india.coronavirus.fight.model.StateData;

public class StateFragment extends Fragment {
    private ArrayList<ArrayList<StateData>> stateDatalist = new ArrayList<ArrayList<StateData>>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.quote_layout)
    LinearLayout linearLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        StateViewModel stateViewModel = ViewModelProviders.of(this).get(StateViewModel.class);

        View root = inflater.inflate(R.layout.fragment_statewise, container, false);
        ButterKnife.bind(this, root);
        progressBar.setVisibility(View.VISIBLE);
        StateAdapter stateAdapter = new StateAdapter(stateDatalist, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(stateAdapter);

        stateViewModel.getData().observe(Objects.requireNonNull(getActivity()), data1 -> {
            if (data1 != null) {
                stateDatalist.add(data1);
                stateAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HeatMap.class);
                startActivity(intent);
            }
        });
        return root;
    }

}
