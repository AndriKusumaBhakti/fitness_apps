package com.fitness.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.fitness.R;
import com.fitness.fragment.BaseFragment;
import com.fitness.fragment.home.HomeFragment;
import com.fitness.fragment.profile.ProfileFragment;
import com.fitness.fragment.reward.RewardFragment;
import com.fitness.fragment.schedule.ScheduleFragment;
import com.fitness.fragment.virtual.LatihanFragment;

import java.lang.ref.WeakReference;

public class DashboardActivity extends BaseActivity{
    private Toolbar toolbar;
    private AHBottomNavigation bottomNavigation;

    public static DashboardActivity instance;
    private int currentSelectedTab;
    private WeakReference<Activity> wrActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wrActivity = new WeakReference<Activity>(this);
        instance = this;
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        bottomNavigation= (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setSupportActionBar(toolbar);

        this.createNavigationItems();

        replaceFragmentwithTag(R.id.container, HomeFragment.newInstance(), false, "HOME");
    }

     private void createNavigationItems() {

         AHBottomNavigationItem news = new AHBottomNavigationItem(getResources().getString(R.string.label_home), R.drawable.icon_home);
         AHBottomNavigationItem schedule = new AHBottomNavigationItem(getResources().getString(R.string.label_schedule), R.drawable.icon_calender);
         AHBottomNavigationItem latihan=new AHBottomNavigationItem(getResources().getString(R.string.label_virtual), R.drawable.icon_latihan);
         AHBottomNavigationItem reward=new AHBottomNavigationItem(getResources().getString(R.string.label_reward), R.drawable.icon_reward);
         AHBottomNavigationItem profil=new AHBottomNavigationItem(getResources().getString(R.string.label_profile), R.drawable.icon_profil);

         bottomNavigation.addItem(news);
         bottomNavigation.addItem(schedule);
         bottomNavigation.addItem(latihan);
         bottomNavigation.addItem(reward);
         bottomNavigation.addItem(profil);

         bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.greywhite));

         bottomNavigation.setCurrentItem(0);

         bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

         bottomNavigation.setAccentColor(getResources().getColor(R.color.red_bar));
         bottomNavigation.setInactiveColor(getResources().getColor(R.color.grey1));

         bottomNavigation.setBehaviorTranslationEnabled(false);
    }

    @Override
    public void setUICallbacks() {
         bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        currentSelectedTab = 0;
                        showBottomMenu();
                        replaceFragmentwithTag(R.id.container, HomeFragment.newInstance(), false, "HOME");
                        break;
                    case 1:
                        currentSelectedTab = 1;
                        showBottomMenu();
                        replaceFragmentwithTag(R.id.container, ScheduleFragment.newInstance(), false, "SCHEDULE");
                        break;
                    case 2:
                        currentSelectedTab = 2;
                        showBottomMenu();
                        replaceFragmentwithTag(R.id.container, LatihanFragment.newInstance(), false, "LATIHAN");
                        break;
                    case 3:
                        currentSelectedTab = 3;
                        showBottomMenu();
                        replaceFragmentwithTag(R.id.container, RewardFragment.newInstance(), false, "REWARD");
                        break;
                    case 4:
                        currentSelectedTab = 4;
                        showBottomMenu();
                        replaceFragmentwithTag(R.id.container, ProfileFragment.newInstance(), false, "PROFILE");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_dashboard;
    }

    @Override
    public void updateUI() {

    }

    public void pushFragmentDashboard(BaseFragment fragment) {
        hideBottomMenu();
        if (currentSelectedTab == 0) {
            pushFragmentToHome(fragment);
        } else if (currentSelectedTab == 1) {
            pushFragmentToAbsenMasuk(fragment);
        } else if (currentSelectedTab == 2) {
            pushFragmentToAbsenKeluar(fragment);
        } else if (currentSelectedTab == 3) {
            pushFragmentToMore(fragment);
        }
    }

    public void pushFragmentToHome(BaseFragment fragment){
        replaceFragment(R.id.container, "Home", fragment, true);
    }
    public void pushFragmentToAbsenMasuk(BaseFragment fragment) {
        replaceFragment(R.id.container, "Jenis Surat", fragment, true);
    }
    public void pushFragmentToAbsenKeluar(BaseFragment fragment) {
        replaceFragment(R.id.container, "File Saya", fragment, true);
    }
    public void pushFragmentToMore(BaseFragment fragment) {
        replaceFragment(R.id.container, "Lainnya", fragment, true);
    }

    public void showBottomMenu() {
        bottomNavigation.setVisibility(View.VISIBLE);
    }

    public void hideBottomMenu() {
        bottomNavigation.setVisibility(View.GONE);
    }
}
