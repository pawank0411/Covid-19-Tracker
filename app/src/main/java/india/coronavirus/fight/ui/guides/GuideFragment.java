package india.coronavirus.fight.ui.guides;

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
import india.coronavirus.fight.dataAdapter.GuideAdapter;
import india.coronavirus.fight.model.GuideData;

public class GuideFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //NO use
    private ArrayList<GuideData> headerDatalist = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GuideViewModel guideViewModel = ViewModelProviders.of(this).get(GuideViewModel.class);
        View root = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, root);
        GuideAdapter dataAdapter = new GuideAdapter(headerDatalist, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);

        guideViewModel.getData().observe(Objects.requireNonNull(getActivity()), newData -> {
            if (newData != null) {
                headerDatalist.clear();
                headerDatalist.addAll(newData);
                dataAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }

}
