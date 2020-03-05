package com.fitness.fragment.faq;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.ListFAQAdapter;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;
import com.fitness.view.TextViewBold;

import net.cachapa.expandablelayout.ExpandableLayout;

public class FaqFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView list_data_gym, list_data_emergency;
    private ImageView imageFlash;
    private LinearLayout linearGym, linearEmergency;
    private TextViewBold plusGym, plusEmergency;
    private ExpandableLayout exp_gym, exp_emergency;
    private ListFAQAdapter adapterGym, adapterEmergency;

    private String[][] gym = new String[][]{{"What are the club opening hours ?",
            "You have the flexibility to work out any time, day or night. You may refer to our club opening times atFind A Club."},
            {"Can I bring a guest to the club for a free trial ?", "Absolutely! We understand that one of the best ways to stick with a healthy " +
                    "lifestyle is to train with a buddy. You can bring a friend for a one-time visit only. You can get your friend to register for a free trial appointment"},
            {"When's the quietest time to visit the club ?", "On weekdays, the clubs are generally busiest before work, at lunchtime and after work. " +
                    "If you prefer a quieter atmosphere, pay a visit just after lunch or in the afternoon."},
            {"Can my children wait for me in the members' lounge while I work out ?", "No. We are concerned about your children's safety as our facilities are not child-proof."}};
    private String[][] emergency = new String[][]{{"What should I do if the fire alarm sounds ?",
            "Don't panic. Our fully trained team will tell you what to do and guide you to safety. If the alarm stops after a short time, it was simply a false alarm or a test."},
            {"Are the team trained in first aid ?",
                    "Yes, we always have a number of first aiders on duty. In the unlikely event that someone is injured, there are also first aid boxes located at reception."}};

    public FaqFragment() {}

    public static FaqFragment newInstance() {
        return newInstance("","");
    }

    public static FaqFragment newInstance(String param1, String param2) {
        FaqFragment fragment = new FaqFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        list_data_gym = (RecyclerView) view.findViewById(R.id.list_data_gym);
        list_data_emergency = (RecyclerView) view.findViewById(R.id.list_data_emergency);
        linearGym = (LinearLayout) view.findViewById(R.id.linearGym);
        linearEmergency = (LinearLayout) view.findViewById(R.id.linearEmergency);
        plusGym = (TextViewBold) view.findViewById(R.id.plusGym);
        plusEmergency = (TextViewBold) view.findViewById(R.id.plusEmergency);
        exp_gym = (ExpandableLayout) view.findViewById(R.id.exp_gym);
        exp_emergency = (ExpandableLayout) view.findViewById(R.id.exp_emergency);

        LinearLayoutManager mLayoutManagerGym = new LinearLayoutManager(getActivity());
        list_data_gym.setHasFixedSize(true);
        list_data_gym.setLayoutManager(mLayoutManagerGym);
        adapterGym = new ListFAQAdapter(getBaseActivity(), gym);
        list_data_gym.setAdapter(adapterGym);


        LinearLayoutManager mLayoutManagerEmergency = new LinearLayoutManager(getActivity());
        list_data_emergency.setHasFixedSize(true);
        list_data_emergency.setLayoutManager(mLayoutManagerEmergency);
        adapterEmergency = new ListFAQAdapter(getBaseActivity(), emergency);
        list_data_emergency.setAdapter(adapterEmergency);
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
        linearGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exp_gym.isExpanded()){
                    exp_gym.collapse(true);
                    plusGym.setText("+");
                }else{
                    exp_gym.expand(true);
                    exp_emergency.collapse(true);
                    plusEmergency.setText("+");
                    plusGym.setText("-");
                }
            }
        });
        linearEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exp_emergency.isExpanded()){
                    exp_emergency.collapse(true);
                    plusEmergency.setText("+");
                }else{
                    exp_emergency.expand(true);
                    exp_gym.collapse(true);
                    plusEmergency.setText("-");
                    plusGym.setText("+");
                }
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
        return getResources().getString(R.string.label_faq);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_faq;
    }
}
