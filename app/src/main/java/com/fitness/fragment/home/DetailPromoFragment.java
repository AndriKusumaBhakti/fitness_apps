package com.fitness.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;
import com.fitness.util.Constants;
import com.fitness.view.TextViewRegular;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import toan.android.floatingactionmenu.FloatingActionButton;

public class DetailPromoFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CarouselView carouselView;
    private TextViewRegular diskripsi;
    private FloatingActionButton floatingShare;
    private String label_bar = "Promo";
    private String diskripsiPromo;
    private String imagePromo;
    private String linkShare;

    public DetailPromoFragment() {}

    public static DetailPromoFragment newInstance() {
        return newInstance("","");
    }

    public static DetailPromoFragment newInstance(String param1, String param2) {
        DetailPromoFragment fragment = new DetailPromoFragment();
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
            label_bar = getArguments().getString(Constants.labelBardetail);
            diskripsiPromo = getArguments().getString(Constants.diskripsiPromo);
            imagePromo = getArguments().getString(Constants.imagePromo);
            linkShare = getArguments().getString(Constants.linkShare);
        }
    }

    @Override
    public void initView(View view) {
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(1);
        carouselView.setSlideInterval(3000);

        carouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater().inflate(R.layout.item_card_promo, null);
                ImageView gambarCard = (ImageView) customView.findViewById(R.id.gambarCardView);

                Glide.with(getActivity())
                        .load(imagePromo)
                        .centerCrop()
                        .fitCenter()
                        .dontAnimate()
                        .placeholder(R.drawable.fitness_romo1)
                        .error(R.drawable.fitness_romo1)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(gambarCard);
                return customView;
            }
        });
        diskripsi = (TextViewRegular) view.findViewById(R.id.diskripsi);
        diskripsi.setText(diskripsiPromo);
        floatingShare = (FloatingActionButton) view.findViewById(R.id.floatingShare);
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
        floatingShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, linkShare);
                try {
                    activity.startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getBaseActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
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
        return label_bar;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_detail_promo;
    }
}
