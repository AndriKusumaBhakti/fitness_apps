package com.fitness.fragment.virtual;

import android.os.Bundle;
import android.view.View;

import com.fitness.R;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;

public class AddTrainingFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        getBaseActivity().setLeftIcon(R.drawable.icon_profil);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(true);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.add_training);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_reward;
    }
}
