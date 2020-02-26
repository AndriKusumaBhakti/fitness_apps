package com.fitness.fragment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.fitness.R;
import com.fitness.StartActivity;
import com.fitness.activity.DashboardActivity;
import com.fitness.aplication.APP;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBLanguage;
import com.fitness.entities.LanguageEntity;
import com.fitness.fragment.BaseFragment;

import java.util.List;

public class ChangeLanguageFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DBLanguage dbLanguage;
    private List<LanguageEntity> languageEntity;
    private LanguageEntity entity;
    private String bahasaLanguage;
    private CheckBox checkEng;
    private CheckBox checkIndo;
    private String label_bar;

    private LinearLayout langLayout1, langLayout2;

    public ChangeLanguageFragment() {}

    public static ChangeLanguageFragment newInstance() {
        return newInstance("","");
    }

    public static ChangeLanguageFragment newInstance(String param1, String param2) {
        ChangeLanguageFragment fragment = new ChangeLanguageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbLanguage = new DBLanguage(getActivity());
        languageEntity = dbLanguage.getAllLanguage();
        bahasaLanguage = languageEntity.get(0).getLanguage();
        APP.log("Bahasa : "+bahasaLanguage);
        if(getArguments()!=null) {
            label_bar = getArguments().getString("label_bar");
        }
    }

    @Override
    public void initView(View view) {
        checkEng = (CheckBox) view.findViewById(R.id.checkEng);
        checkIndo = (CheckBox) view.findViewById(R.id.checkIndo);
        langLayout1 = (LinearLayout) view.findViewById(R.id.langLayout1);
        langLayout2 = (LinearLayout) view.findViewById(R.id.langLayout2);

        if(bahasaLanguage.toString().equals("in")){
            checkEng.setChecked(false);
            checkIndo.setChecked(true);
        }else if(bahasaLanguage.toString().equals("en")){
            checkEng.setChecked(true);
            checkIndo.setChecked(false);
        }
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
        langLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbLanguage.updateMaxHeartrate("in");
                Intent intent = new Intent(getBaseActivity(), StartActivity.class);
                startActivity(intent);
                getBaseActivity().finish();
            }
        });
        langLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbLanguage.updateMaxHeartrate("en");
                Intent intent = new Intent(getBaseActivity(), StartActivity.class);
                startActivity(intent);
                getBaseActivity().finish();
            }
        });
        checkIndo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkEng.setChecked(false);
                dbLanguage.updateMaxHeartrate("in");
                Intent intent = new Intent(getBaseActivity(), StartActivity.class);
                startActivity(intent);
                getBaseActivity().finish();
            }
        });
        checkEng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkIndo.setChecked(false);
                dbLanguage.updateMaxHeartrate("en");
                Intent intent = new Intent(getBaseActivity(), StartActivity.class);
                startActivity(intent);
                getBaseActivity().finish();
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
