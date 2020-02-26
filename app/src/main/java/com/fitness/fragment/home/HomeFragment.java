package com.fitness.fragment.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.ViewGroup.LayoutParams;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.DrawableRes;
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
import com.fitness.util.DecodeBitmapTask;
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
import java.util.Random;

public class HomeFragment extends BaseFragment  implements OnMapReadyCallback {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //card image slider
    private final int[][] dotCoords = new int[5][2];
    private String[] pics;
    /*private String[] descriptions;*/
    private String[] hari;
//    private final String[] places = {"Bangkalan", "Kediri", "Blitar", "Sumenep", "Sidoarjo"};
//    private final String[] countries = {"Advance Classes", "Body Jam Classes", "Classes Body Balance", "Classes Yoga", "Coreex Classes"};
//    private final String[] temperatures = {"60MINS", "45MINS", "60MINS", "30MINS", "60MINS"};
//    private final String[] times = {"Aug 1 - Dec 15    7:00-18:00", "Sep 5 - Nov 10    8:00-16:00", "Mar 8 - May 21    7:00-18:00", "Mar 8 - May 21    7:00-18:00", "Aug 1 - Dec 15    7:00-18:00"};
    /*private final String[] longit = {"112.7450068", "112.032356", "112.162762", "113.9060624", "112.7173389"};
    private final String[] latit = {"-7.0306912", " -7.809356", "-8.1014419", "-6.9253999", " -7.4530278"};*/

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

    private DBClub dbClub;
    private DBClass dbClass;
    private DBEventClub dbEventClub;
    private List<EventClubEntity> listEntity;
    private Date currentTime;
    private String searchHari = "Senin";
    private ClubEntity entitiClub;
    private ClassEntity entitiClass;

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return newInstance("","");
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        dbClass = new DBClass(getBaseActivity());
        dbEventClub = new DBEventClub(getBaseActivity());
        listEntity = new ArrayList<>();
        dataMaps = new ArrayList<>();
        entitiClub = new ClubEntity();
        entitiClass = new ClassEntity();
    }

    @Override
    public void initView(View view) {
        currentTime = Calendar.getInstance().getTime();
        hari = getResources().getStringArray(R.array.hari);
        for (int i = 0; i<hari.length; i++){
            if (i == currentTime.getDay() - 1){
                searchHari = hari[i];
            }
        }
        APP.log("searchHari : "+searchHari);
        listEntity = dbEventClub.getAllDay(String.valueOf(currentTime.getDay()));
        if (listEntity.size()>0) {
            for (int i = 0; i < listEntity.size(); i++) {
                ModelMaps model = new ModelMaps();
                //get data club untuk maps di now
                entitiClub = dbClub.getByIdCLub(listEntity.get(i).getIdClub());
                if (entitiClub != null){
                    model.setLatitudeFit(entitiClub.getLatitude());
                    model.setLongitudeFit(entitiClub.getLongitude());
                    model.setName(entitiClub.getNamaClub());
                    model.setLokasi(entitiClub.getLokasi());
                }
                //get data class now
                entitiClass = dbClass.getById(listEntity.get(i).getIdClass());
                if (entitiClass != null){
                    model.setNamaEvent(entitiClass.getNamaClass());
                    model.setDeskripsi(entitiClass.getDeskripsi());
                    model.setImage(entitiClass.getImage());
                }
                //get data event club now
                model.setId(String.valueOf(listEntity.get(i).getId()));
                model.setDurasi(listEntity.get(i).getDurasi());
                model.setJamStart(listEntity.get(i).getJamStart());
                model.setJamEnd(listEntity.get(i).getJamEnd());
                model.setPelatih(listEntity.get(i).getPelatih());
                model.setHari(listEntity.get(i).getHari());
                dataMaps.add(model);
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
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(false);
        DashboardActivity.instance.setBarView(false);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.label_home);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_home;
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
            /*if (clickedPosition == activeCardPosition) {
                final Intent intent = new Intent(StartActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.BUNDLE_IMAGE_ID, pics[activeCardPosition % pics.length]);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent);
                } else {
                    final CardView cardView = (CardView) view;
                    final View sharedView = cardView.getChildAt(cardView.getChildCount() - 1);
                    final ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(StartActivity.this, sharedView, "shared");
                    startActivity(intent, options.toBundle());
                }
            } else if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }*/
        }
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }

        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        setCountryText(dataMaps.get(pos).getNamaEvent(), left2right);

        temperatureSwitcher.setInAnimation(getBaseActivity(), animH[0]);
        temperatureSwitcher.setOutAnimation(getBaseActivity(), animH[1]);
        temperatureSwitcher.setText(dataMaps.get(pos).getDurasi());

        placeSwitcher.setInAnimation(getBaseActivity(), animV[0]);
        placeSwitcher.setOutAnimation(getBaseActivity(), animV[1]);
        placeSwitcher.setText(dataMaps.get(pos).getPelatih());

        clockSwitcher.setInAnimation(getBaseActivity(), animV[0]);
        clockSwitcher.setOutAnimation(getBaseActivity(), animV[1]);
        clockSwitcher.setText(searchHari+", "+dataMaps.get(pos).getJamStart()+" - "+dataMaps.get(pos).getJamEnd());

        descriptionsSwitcher.setText(dataMaps.get(pos).getDeskripsi());

        tampilMaps(pos);

        ViewCompat.animate(greenDot)
                .translationX(dotCoords[pos % dotCoords.length][0])
                .translationY(dotCoords[pos % dotCoords.length][1])
                .start();

        currentPosition = pos;
    }

    private void setCountryText(String text, boolean left2right) {
        final TextViewSemiBold invisibleText;
        final TextViewSemiBold visibleText;
        if (country1TextView.getAlpha() > country2TextView.getAlpha()) {
            visibleText = country1TextView;
            invisibleText = country2TextView;
        } else {
            visibleText = country2TextView;
            invisibleText = country1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = countryOffset2;
        } else {
            invisibleText.setX(countryOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", countryOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
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
        clockSwitcher.setCurrentText(searchHari+", "+dataMaps.get(0).getJamStart()+" - "+dataMaps.get(0).getJamEnd());

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
