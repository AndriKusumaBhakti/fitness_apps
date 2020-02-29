package com.fitness.fragment.virtual;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.SimpleTrainingAdapter;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;
import com.fitness.model.SimpleTrainingModel;

import java.util.ArrayList;

public class LatihanFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SimpleTrainingAdapter adapter;
    private RecyclerView listVideo;
    private ArrayList<SimpleTrainingModel> data;

    public LatihanFragment() {}

    public static LatihanFragment newInstance() {
        return newInstance("","");
    }

    public static LatihanFragment newInstance(String param1, String param2) {
        LatihanFragment fragment = new LatihanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        for (int i=0; i<1; i++){
            SimpleTrainingModel model = new SimpleTrainingModel();
            model.setStatusClick(false);
            model.setChannel("Andri Kusuma Bhakti");
            model.setDeskripsi("Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.");
            model.setTitle("Body Building");
            model.setUrlYoutobe("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
            model.setPlayVideo(false);
            data.add(model);
        }

    }

    @Override
    public void initView(View view) {
        listVideo = (RecyclerView) view.findViewById(R.id.listVideo);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listVideo.setHasFixedSize(true);
        listVideo.setLayoutManager(mLayoutManager);
        adapter = new SimpleTrainingAdapter(getBaseActivity(), data, new SimpleTrainingAdapter.ClickListener() {
            @Override
            public void onDetail(SimpleTrainingModel model) {

            }
        });
        listVideo.setAdapter(adapter);
    }

    @Override
    public void setUICallbacks() {
        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {

            }

            @Override
            public void onRightIconClick() {

            }

            @Override
            public void onRight2IconClick() {

            }
        });
    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.icon_latihan);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(R.drawable.icon_clock_red);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(true);
        DashboardActivity.instance.setBarView(true);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.label_virtual);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_latihan;
    }
}
