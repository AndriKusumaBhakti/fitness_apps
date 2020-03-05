package com.fitness.fragment.virtual;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.aplication.APP;
import com.fitness.aplication.Preference;
import com.fitness.aplication.RepeatListener;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;
import com.fitness.util.Constants;
import com.fitness.view.ButtonRegular;
import com.fitness.view.TextViewRegular;

public class TimerIntervalFragment extends BaseFragment implements View.OnClickListener {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isBackPressedTwice;

    private TextViewRegular setsTextView;
    private TextViewRegular workTextView;
    private TextViewRegular restTextView;
    private ButtonRegular setsMinusBtn;
    private ButtonRegular setsPlusBtn;
    private ButtonRegular workMinusBtn;
    private ButtonRegular workPlusBtn;
    private ButtonRegular restMinusBtn;
    private ButtonRegular restPlusBtn;

    // Current parameters
    private int sets;
    private int workSecs;
    private int workMins;
    private int restSecs;
    private int restMins;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isBackPressedTwice = false;
    }

    @Override
    public void initView(View view) {
        this.setsMinusBtn = view.findViewById(R.id.setsMinusBtn);
        this.setsPlusBtn = view.findViewById(R.id.setsPlusBtn);
        this.workMinusBtn = view.findViewById(R.id.workMinusBtn);
        this.workPlusBtn = view.findViewById(R.id.workPlusBtn);
        this.restMinusBtn = view.findViewById(R.id.restMinusBtn);
        this.restPlusBtn = view.findViewById(R.id.restPlusBtn);
        this.setsTextView = view.findViewById(R.id.setsQuantity);
        this.workTextView = view.findViewById(R.id.workQuantity);
        this.restTextView = view.findViewById(R.id.restQuantity);
        TextViewRegular stv = view.findViewById(R.id.setsText);
        TextViewRegular wtv = view.findViewById(R.id.workText);
        TextViewRegular rtv = view.findViewById(R.id.restText);
        ButtonRegular startBtn = view.findViewById(R.id.startBtn);
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
        this.setsPlusBtn.setOnTouchListener(new RepeatListener(600, 50, v -> setsPlusBtn.performClick()));
        this.setsMinusBtn.setOnTouchListener(new RepeatListener(600, 50, v -> setsMinusBtn.performClick()));
        this.workPlusBtn.setOnTouchListener(new RepeatListener(600, 25, v -> workPlusBtn.performClick()));
        this.workMinusBtn.setOnTouchListener(new RepeatListener(600, 25, v -> workMinusBtn.performClick()));
        this.restPlusBtn.setOnTouchListener(new RepeatListener(600, 25, v -> restPlusBtn.performClick()));
        this.restMinusBtn.setOnTouchListener(new RepeatListener(600, 25, v -> restMinusBtn.performClick()));
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
        return getResources().getString(R.string.label_interval_timer);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_interval_timer;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (APP.getIntPref(getBaseActivity(), Preference.DEFAULT_SETS) > 0) {
            this.sets = APP.getIntPref(getBaseActivity(), Preference.DEFAULT_SETS);
            this.workSecs = APP.getIntPref(getBaseActivity(), Preference.DEFAULT_WORK_SECS);
            this.workMins = APP.getIntPref(getBaseActivity(), Preference.DEFAULT_WORK_MINS);
            this.restSecs = APP.getIntPref(getBaseActivity(), Preference.DEFAULT_REST_SECS);
            this.restMins = APP.getIntPref(getBaseActivity(), Preference.DEFAULT_REST_MINS);
        } else {
            this.initializeDefaultValues();
        }

        this.updateData();
    }

    private void initializeDefaultValues() {
        this.sets = Constants.DEFAULT_SETS;
        this.workSecs = Constants.DEFAULT_WORK_SECS;
        this.workMins = Constants.DEFAULT_WORK_MINS;
        this.restSecs = Constants.DEFAULT_REST_SECS;
        this.restMins = Constants.DEFAULT_REST_MINS;
    }

    @Override
    public void onPause() {
        super.onPause();
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_SETS, this.sets);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_WORK_SECS, this.workSecs);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_WORK_MINS, this.workMins);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_REST_SECS, this.workMins);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_REST_MINS, this.restMins);
    }

    @Override
    public void onStop() {
        super.onStop();
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_SETS, this.sets);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_WORK_SECS, this.workSecs);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_WORK_MINS, this.workMins);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_REST_SECS, this.workMins);
        APP.setPreference(getBaseActivity(), Preference.DEFAULT_REST_MINS, this.restMins);
    }

    private void updateData() {
        this.updateSets();
        this.updateWork();
        this.updateRest();
    }

    @SuppressLint("SetTextI18n")
    private void updateSets() {
        if (this.sets > 9) {
            this.setsTextView.setText(" " + this.sets + "  ");
            return;
        }

        this.setsTextView.setText("  0" + this.sets + "   ");
    }

    @SuppressLint("SetTextI18n")
    private void updateWork() {
        if (this.workMins > 9 && this.workSecs > 9) {
            this.workTextView.setText("" + this.workMins + " : " + this.workSecs);
        } else if (this.workMins > 9) {
            this.workTextView.setText("" + this.workMins + " : 0" + this.workSecs);
        } else if (this.workSecs > 9) {
            this.workTextView.setText("0" + this.workMins + " : " + this.workSecs);
        } else {
            this.workTextView.setText("0" + this.workMins + " : 0" + this.workSecs);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateRest() {
        if (this.restMins > 9 && this.restSecs > 9) {
            this.restTextView.setText("" + this.restMins + " : " + this.restSecs);
        } else if (this.restMins > 9) {
            this.restTextView.setText("" + this.restMins + " : 0" + this.restSecs);
        } else if (this.restSecs > 9) {
            this.restTextView.setText("0" + this.restMins + " : " + this.restSecs);
        } else {
            this.restTextView.setText("0" + this.restMins + " : 0" + this.restSecs);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.setsMinusBtn){
            if (this.sets > 1) {
                this.sets--;
            }

            this.updateSets();
        }else if (v.getId() == R.id.setsPlusBtn){
            this.sets++;

            if (this.sets == 100) {
                this.sets = 1;
            }

            this.updateSets();
        }else if (v.getId() == R.id.workMinusBtn){
            if (this.workMins == 0 && this.workSecs == 1) {
                return;
            }

            if (this.workSecs >= 0) {
                this.workSecs--;

                if (this.workSecs == -1) {
                    this.workSecs = 59;
                    this.workMins--;

                    if (this.workMins == -1) {
                        this.workSecs = 0;
                        this.workMins = 0;
                    }
                }
            }

            this.updateWork();
        }else if (v.getId() == R.id.workPlusBtn){
            this.workSecs++;

            if (this.workSecs == 60) {
                this.workSecs = 0;
                this.workMins++;

                if (this.workMins == 60) {
                    this.workSecs = 0;
                    this.workMins = 0;
                }
            }

            this.updateWork();
        }else if (v.getId() == R.id.restMinusBtn){
            if (this.restSecs >= 0) {
                this.restSecs--;

                if (this.restSecs == -1) {
                    this.restSecs = 59;
                    this.restMins--;

                    if (this.restMins == -1) {
                        this.restSecs = 0;
                        this.restMins = 0;
                    }
                }
            }

            this.updateRest();
        }else if (v.getId() == R.id.restPlusBtn){
            this.restSecs++;

            if (this.restSecs == 60) {
                this.restSecs = 0;
                this.restMins++;

                if (this.restMins == 60) {
                    this.restSecs = 0;
                    this.restMins = 0;
                }
            }

            this.updateRest();
        }else if (v.getId() == R.id.startBtn){

        }
    }
}
