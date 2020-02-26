package com.fitness.fragment.profile;

import android.os.Bundle;
import android.view.View;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;

public class FlashFitnessFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String label_bar;

    public FlashFitnessFragment() {}

    public static FlashFitnessFragment newInstance() {
        return newInstance("","");
    }

    public static FlashFitnessFragment newInstance(String param1, String param2) {
        FlashFitnessFragment fragment = new FlashFitnessFragment();
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
            label_bar = getArguments().getString("label_bar");
        }
    }

    @Override
    public void initView(View view) {

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
        getBaseActivity().setLeftIcon(R.drawable.icon_language);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(true);
        DashboardActivity.instance.setBarView(true);
    }

    @Override
    public String getPageTitle() {
        return label_bar;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_change_language;
    }
}
