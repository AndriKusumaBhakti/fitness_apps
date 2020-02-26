package com.fitness.fragment.profile;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.MenuFlashAdapter;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBClub;
import com.fitness.entities.ClubEntity;
import com.fitness.fragment.BaseFragment;
import com.fitness.view.TextViewLight;
import com.fitness.view.TextViewRegular;

import java.util.ArrayList;
import java.util.List;

public class ProfileFlashFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView list_data;
    private MenuFlashAdapter adapter;
    private String[] data;
    Integer[] mThumbIds;
    private TextViewRegular namaCabang;
    private DBClub dbClub;
    private List<ClubEntity> nameClub;

    public ProfileFlashFragment() {}

    public static ProfileFlashFragment newInstance() {
        return newInstance("","");
    }

    public static ProfileFlashFragment newInstance(String param1, String param2) {
        ProfileFlashFragment fragment = new ProfileFlashFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbClub = new DBClub(getBaseActivity());
        nameClub = new ArrayList<>();
    }

    @Override
    public void initView(View view) {
        list_data = (RecyclerView) view.findViewById(R.id.list_data);
        namaCabang = (TextViewRegular) view.findViewById(R.id.namaCabang);
        nameClub = dbClub.getAll();
        String nameClubDetail = null;
        if (nameClub.size()>0){
            for (int i = 0; i<nameClub.size(); i++){
                if (i == 0){
                    nameClubDetail = " - "+nameClub.get(i).getNamaClub();
                }else {
                    nameClubDetail = nameClubDetail + "\n" + " - " +nameClub.get(i).getNamaClub();
                }
            }
        }
        namaCabang.setText(nameClubDetail);
        data = getResources().getStringArray(R.array.menuFlash);
        mThumbIds = new Integer[]{R.drawable.icon_flash,
                R.drawable.icon_new_agenda, R.drawable.icon_latihan,
                R.drawable.icon_struktur, R.drawable.icon_language, R.drawable.icon_about
        };
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        list_data.setHasFixedSize(true);
        list_data.setLayoutManager(mLayoutManager);
        adapter = new MenuFlashAdapter(getBaseActivity(), data, mThumbIds, new myAdapterListener());
        list_data.setAdapter(adapter);
    }

    private class myAdapterListener implements MenuFlashAdapter.ClickListener{
        @Override
        public void onCLick(int position, String nameLabel) {
            DashboardActivity dashboard = DashboardActivity.instance;
            if (position == 0){
                FlashFitnessFragment fragment = new FlashFitnessFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 1){
                FlashFitnessFragment fragment = new FlashFitnessFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 2){
                FlashFitnessFragment fragment = new FlashFitnessFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 3){
                Toast.makeText(getBaseActivity(), "Comming soon", Toast.LENGTH_LONG).show();
            }else if (position == 4){
                ChangeLanguageFragment fragment = new ChangeLanguageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 5){
                Toast.makeText(getBaseActivity(), "Comming soon", Toast.LENGTH_LONG).show();
            }
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
    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(0);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(false);
        DashboardActivity.instance.setBarView(false);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.label_flash);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_flash;
    }

    @Override
    public void onResume() {
        super.onResume();
        DashboardActivity.instance.showBottomMenu();
    }
}
