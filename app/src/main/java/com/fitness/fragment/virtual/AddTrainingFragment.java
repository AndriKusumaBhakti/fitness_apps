package com.fitness.fragment.virtual;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.AddTrainingAdapter;
import com.fitness.aplication.APP;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBDetailTraining;
import com.fitness.database.DBEventTraining;
import com.fitness.database.DBTraining;
import com.fitness.entities.DetailTrainingEntity;
import com.fitness.entities.TrainingEntity;
import com.fitness.fragment.BaseFragment;
import com.fitness.model.DetailTrainingModel;
import com.fitness.model.EventTrainingModel;
import com.fitness.util.Constants;
import com.fitness.view.ButtonRegular;
import com.fitness.view.CustomSpinnerView;
import com.fitness.view.DateInput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddTrainingFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView list_data;
    private DBDetailTraining dbDetailTraining;
    private List<DetailTrainingEntity> listDetailTraining;
    private AddTrainingAdapter adapter;
    private ArrayList<DetailTrainingModel> detailTrainingModel;
    private String nama_label;
    private int id = 1;
    private ButtonRegular save_btn, cancel_btn;
    private DateInput date_start;

    private DBEventTraining dbEventTraining;

    public AddTrainingFragment() {}

    public static AddTrainingFragment newInstance() {
        return newInstance("","");
    }

    public static AddTrainingFragment newInstance(String param1, String param2) {
        AddTrainingFragment fragment = new AddTrainingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            nama_label = getArguments().getString("nama_label");
            id = getArguments().getInt("id");
        }
        dbDetailTraining = new DBDetailTraining(getBaseActivity());
        dbEventTraining = new DBEventTraining(getBaseActivity());
        APP.log(""+dbDetailTraining.getAll());
        detailTrainingModel = new ArrayList<>();
        listDetailTraining = new ArrayList<>();
        listDetailTraining = dbDetailTraining.getByIdJenis(String.valueOf(id));
        if (listDetailTraining.size()>0){
            for (int i = 0; i<listDetailTraining.size(); i++){
                DetailTrainingModel model = new DetailTrainingModel();
                model.setId(listDetailTraining.get(i).getId());
                model.setIdTraining(listDetailTraining.get(i).getIdTraining());
                model.setTraining(listDetailTraining.get(i).getTraining());
                model.setStatus(false);
                detailTrainingModel.add(model);
            }
        }
    }

    @Override
    public void initView(View view) {
        list_data = (RecyclerView) view.findViewById(R.id.list_data);
        save_btn = (ButtonRegular) view.findViewById(R.id.save_btn);
        cancel_btn = (ButtonRegular) view.findViewById(R.id.cancel_btn);
        date_start = (DateInput) view.findViewById(R.id.date_start);
        date_start.setHint(getResources().getString(R.string.select_date));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_data.setHasFixedSize(true);
        list_data.setLayoutManager(mLayoutManager);
        adapter = new AddTrainingAdapter(getBaseActivity(), detailTrainingModel);
        list_data.setAdapter(adapter);
    }

    @Override
    public void setUICallbacks() {
        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                getFragmentManager().popBackStack();
            }

            @Override
            public void onRightIconClick() {

            }

            @Override
            public void onRight2IconClick() {

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL);
                if (adapter.getList().size()>0) {
                    if (!date_start.getDateString().isEmpty()) {
                        ArrayList<DetailTrainingModel> data = adapter.getList();
                        for (int i = 0; i < data.size(); i++) {
                            EventTrainingModel model = new EventTrainingModel();
                            model.setId(dbEventTraining.getIdEvent());
                            model.setIdTraining(id);
                            model.setDateEvent(String.valueOf(formatter.format(date_start.getDate())));
                            model.setIdJenisTraining(data.get(i).getId());
                            dbEventTraining.parse(model);
                        }
                        getFragmentManager().popBackStack();
                    }else{
                        Toast.makeText(getBaseActivity(), "silahkan pilih tanggal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseActivity(), "silahkan pilih terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.back);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(true);
        DashboardActivity.instance.setBarView(true);
    }

    @Override
    public String getPageTitle() {
        return nama_label;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_add_training;
    }
}
