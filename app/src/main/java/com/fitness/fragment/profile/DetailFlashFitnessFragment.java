package com.fitness.fragment.profile;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.StyleRes;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.SliderAdapter;
import com.fitness.aplication.APP;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBClass;
import com.fitness.database.DBClub;
import com.fitness.database.DBEventClub;
import com.fitness.entities.ClassEntity;
import com.fitness.entities.ClubEntity;
import com.fitness.entities.EventClubEntity;
import com.fitness.fragment.BaseFragment;
import com.fitness.model.ModelMaps;
import com.fitness.util.Constants;
import com.fitness.view.TextViewSemiBold;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailFlashFitnessFragment extends BaseFragment  implements OnMapReadyCallback {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //card image slider
    private final int[][] dotCoords = new int[5][2];

    private SliderAdapter sliderAdapter;

    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;
    private TextSwitcher temperatureSwitcher;
    private TextSwitcher placeSwitcher;
    private TextSwitcher clockSwitcher;
    private TextSwitcher descriptionsSwitcher;
    private View greenDot;

    private TextViewSemiBold country1TextView;
    private TextViewSemiBold country2TextView;
    private int countryOffset1;
    private int countryOffset2;
    private long countryAnimDuration;
    private int currentPosition;

    private SupportMapFragment supportMapFragment;
    private GoogleMap myMap;
    private ArrayList<ModelMaps> dataMaps;

    ModelMaps modelMaps;
    private String label_bar;
    private String[] hari;

    public DetailFlashFitnessFragment() {}

    public static DetailFlashFitnessFragment newInstance() {
        return newInstance("","");
    }

    public static DetailFlashFitnessFragment newInstance(String param1, String param2) {
        DetailFlashFitnessFragment fragment = new DetailFlashFitnessFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataMaps = new ArrayList<>();
        if(getArguments()!=null) {
            label_bar = getArguments().getString(Constants.labelBardetail);
            modelMaps = (ModelMaps) getArguments().getSerializable(Constants.detailFlash);
        }
    }

    @Override
    public void initView(View view) {
        hari = getResources().getStringArray(R.array.hari);
        for (int i = 0; i < 5; i++) {
            dataMaps.add(modelMaps);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        sliderAdapter = new SliderAdapter(getBaseActivity(), dataMaps, dataMaps.size() - 1, new OnCardClickListener());
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(recyclerView);

        initCountryText(view);
        initSwitchers(view);
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
        return R.layout.fragment_detail_flash;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.myMap = googleMap;
        tampilMaps(0);
    }

    private void tampilMaps(int position){
        APP.log("data : "+dataMaps.size());
        myMap.clear();
        Double lat = Double.parseDouble(dataMaps.get(position).getLatitudeFit());
        Double longi = Double.parseDouble(dataMaps.get(position).getLongitudeFit());
        LatLng lldoctor = new LatLng(lat, longi);
        MarkerOptions markerDoctor = new MarkerOptions();
        markerDoctor.position(lldoctor);
        markerDoctor.title(String.valueOf(dataMaps.get(position).getName()));
        markerDoctor.snippet(String.valueOf(dataMaps.get(position).getLokasi()));
        markerDoctor.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        myMap.addMarker(markerDoctor);

        myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        myMap.getUiSettings().setCompassEnabled(true);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setMyLocationButtonEnabled(true);
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lldoctor, 17));
        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                try {
                    StringBuilder urlString = new StringBuilder();
                    String daddr = (String.valueOf(arg0.getPosition().latitude)+","+String.valueOf(arg0.getPosition().longitude));
                    urlString.append("http://maps.google.com/maps?f=d&hl=en");
                    urlString.append("&saddr="+String.valueOf(myMap.getMyLocation().getLatitude())+","+String.valueOf(myMap.getMyLocation().getLongitude()));
                    urlString.append("&daddr="+daddr);
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString.toString()));
                    startActivity(i);
                } catch (Exception ee) {
                    Toast.makeText(getBaseActivity(), getResources().getString(R.string.gps_not_active), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm =  (CardSliderLayoutManager) recyclerView.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
        }
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }
    }

    private void initCountryText(View view) {
        countryAnimDuration = 150;
        countryOffset1 = getResources().getDimensionPixelSize(R.dimen.medbooks_60dp);
        countryOffset2 = getResources().getDimensionPixelSize(R.dimen.medbooks_150dp);
        country1TextView = (TextViewSemiBold) view.findViewById(R.id.tv_country_1);
        country2TextView = (TextViewSemiBold) view.findViewById(R.id.tv_country_2);

        country1TextView.setX(countryOffset1);
        country2TextView.setX(countryOffset2);
        country1TextView.setText(dataMaps.get(0).getNamaEvent());
        country2TextView.setAlpha(0f);

        country1TextView.setTypeface(Typeface.createFromAsset(getBaseActivity().getAssets(), "ProximaNova-Bold.otf"));
        country2TextView.setTypeface(Typeface.createFromAsset(getBaseActivity().getAssets(), "ProximaNova-Bold.otf"));
    }

    private void initSwitchers(View view) {
        temperatureSwitcher = (TextSwitcher) view.findViewById(R.id.ts_temperature);
        temperatureSwitcher.setFactory(new TextViewFactory(R.style.TemperatureTextView, true));
        temperatureSwitcher.setCurrentText(dataMaps.get(0).getDurasi());

        placeSwitcher = (TextSwitcher) view.findViewById(R.id.ts_place);
        placeSwitcher.setFactory(new TextViewFactory(R.style.PlaceTextView, false));
        placeSwitcher.setCurrentText(dataMaps.get(0).getPelatih());

        clockSwitcher = (TextSwitcher) view.findViewById(R.id.ts_clock);
        clockSwitcher.setFactory(new TextViewFactory(R.style.ClockTextView, false));
        clockSwitcher.setCurrentText(hari[Integer.parseInt(dataMaps.get(0).getHari())]+", "+dataMaps.get(0).getJamStart()+" - "+dataMaps.get(0).getJamEnd());

        descriptionsSwitcher = (TextSwitcher) view.findViewById(R.id.ts_description);
        descriptionsSwitcher.setInAnimation(getBaseActivity(), android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(getBaseActivity(), android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new TextViewFactory(R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(dataMaps.get(0).getDeskripsi());

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fitness);
        supportMapFragment.getMapAsync(this);

    }

    private class TextViewFactory implements  ViewSwitcher.ViewFactory {
        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextViewSemiBold textView = new TextViewSemiBold(getBaseActivity());

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(getBaseActivity(), styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }
}
