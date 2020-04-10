package india.coronavirus.fight.ui.statewise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import india.coronavirus.fight.HeatMap;
import india.coronavirus.fight.R;
import india.coronavirus.fight.dataAdapter.StateAdapter;
import india.coronavirus.fight.model.StateData;
import india.coronavirus.fight.utilities.AppStatus;
import india.coronavirus.fight.utilities.DownloadDistrictFile;
import india.coronavirus.fight.utilities.LoadPDF;

import static android.content.Context.MODE_PRIVATE;

public class StateFragment extends Fragment {
    private ArrayList<StateData> stateDatalist = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.quote_layout)
    LinearLayout linearLayout;
    @BindView(R.id.announcementLayout)
    LinearLayout announcementLayout;
    @BindView(R.id.districtWise)
    MaterialTextView disytrictwise;

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
                stateDatalist.clear();
                stateDatalist.addAll(data1);
                stateAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
//        blink();
        AppStatus appStatus = new AppStatus(getContext());

        if (!appStatus.haveNetworkConnection()) {
            linearLayout.setVisibility(View.GONE);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HeatMap.class);
                startActivity(intent);
            }
        });
        File file = new File("/storage/emulated/0/covid19India/districtData.pdf");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("API", MODE_PRIVATE);
        announcementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file.exists()) {
                    Intent intent = new Intent(getContext(), LoadPDF.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Download in progress. Please after some time.", Toast.LENGTH_SHORT).show();
                    DownloadDistrictFile downloadDistrictFile = new DownloadDistrictFile(getContext());
                    downloadDistrictFile.newDownload(sharedPreferences.getString("DIST", ""));
                    Objects.requireNonNull(getActivity()).recreate();
                }
            }
        });

        return root;
    }

    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (disytrictwise.getVisibility() == View.VISIBLE) {
                            disytrictwise.setVisibility(View.INVISIBLE);
                        } else {
                            disytrictwise.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
}