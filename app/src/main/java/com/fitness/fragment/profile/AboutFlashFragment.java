package com.fitness.fragment.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;

public class AboutFlashFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String label_bar;
    private ImageView imageFlash;

    public AboutFlashFragment() {}

    public static AboutFlashFragment newInstance() {
        return newInstance("","");
    }

    public static AboutFlashFragment newInstance(String param1, String param2) {
        AboutFlashFragment fragment = new AboutFlashFragment();
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
        imageFlash = (ImageView) view.findViewById(R.id.imageFlash);
        Glide.with(getBaseActivity())
                .load("https://res.cloudinary.com/dvrv7bd38/image/upload/v1583412903/fitness/flash_fitness_ng58yw.jpg")
                .centerCrop()
                .fitCenter()
                .dontAnimate()
                .placeholder(R.drawable.icon_flash)
                .error(R.drawable.icon_flash)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageFlash);
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
        return label_bar;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_about_flash;
    }
}
