package com.fitness.fragment.virtual;

import android.os.Bundle;
import android.view.View;

import com.fitness.R;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;

public class TimerIntervalFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public TimerIntervalFragment() {}

    public static TimerIntervalFragment newInstance() {
        return newInstance("","");
    }

    public static TimerIntervalFragment newInstance(String param1, String param2) {
        TimerIntervalFragment fragment = new TimerIntervalFragment();
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
        getBaseActivity().setLeftIcon(R.drawable.back_white);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(true);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.label_interval_timer);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_interval_timer;
    }
}
