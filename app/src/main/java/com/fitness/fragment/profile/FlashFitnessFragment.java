package com.fitness.fragment.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.FlashFitnessAdapter;
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
import com.fitness.view.ButtonRegular;
import com.fitness.view.CustomSpinnerView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class FlashFitnessFragment extends BaseFragment implements OnMapReadyCallback {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String label_bar;
    private RecyclerView list_data;
    ViewAnimator select_filter_animator;
    private boolean isShowSelectProfile;
    private CustomSpinnerView spinnerClass;
    private CustomSpinnerView spinnerClub;
    private DBClub dbClub;
    private DBClass dbClass;
    private DBEventClub dbEventClub;
    private ArrayList<String> arrClass;
    private ArrayList<String> arrClub;
    private List<ClubEntity> entitiCLub;
    private List<ClassEntity> entitiCLass;
    private List<EventClubEntity> entitiEventCLub;
    private FlashFitnessAdapter adapter;
    private ArrayList<ModelMaps> list;
    private GoogleMap myMapKu;
    private SupportMapFragment supportMapFragment;

    private ButtonRegular save_apply;

    public FlashFitnessFragment() {}

    public static FlashFitnessFragment newInstance() {
        return newInstance("","");
    }

    public static FlashFitnessFragment newInstance(String param1, String param2) {
        FlashFitnessFragment fragment = new FlashFitnessFragment();
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
            label_bar = getArguments().getString(Constants.labelName);
        }
        dbClub = new DBClub(getBaseActivity());
        dbClass = new DBClass(getBaseActivity());
        dbEventClub = new DBEventClub(getBaseActivity());
        entitiCLub = new ArrayList<>();
        entitiCLass = new ArrayList<>();
        entitiEventCLub = new ArrayList<>();
        arrClass = new ArrayList<>();
        arrClass.add(getActivity().getResources().getString(R.string.select_class));
        arrClub = new ArrayList<>();
        arrClub.add(getActivity().getResources().getString(R.string.select_branch));
        list = new ArrayList<>();
        if (dbClub.getAllData()>0){
            entitiCLub = dbClub.getAll();
            for (int i= 0; i<entitiCLub.size(); i++){
                arrClub.add(entitiCLub.get(i).getNamaClub());
                ModelMaps modelMaps = new ModelMaps();
                modelMaps.setName(entitiCLub.get(i).getNamaClub());
                modelMaps.setLongitudeFit(entitiCLub.get(i).getLongitude());
                modelMaps.setLatitudeFit(entitiCLub.get(i).getLatitude());
                modelMaps.setLokasi(entitiCLub.get(i).getLokasi());
                list.add(modelMaps);
            }
        }
        if (dbClass.getAllData()>0){
            entitiCLass = dbClass.getAll();
            for (int i= 0; i<entitiCLass.size(); i++){
                arrClass.add(entitiCLass.get(i).getNamaClass());
            }
        }
        if (dbEventClub.getAllData()>0){
            entitiEventCLub = dbEventClub.getAll();
        }
    }

    @Override
    public void initView(View view) {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fitness);
        supportMapFragment.getMapAsync(this);
        list_data = (RecyclerView) view.findViewById(R.id.list_data);
        select_filter_animator = (ViewAnimator) view.findViewById(R.id.select_filter_animator);
        spinnerClub = (CustomSpinnerView) view.findViewById(R.id.spinnerClub);
        spinnerClass = (CustomSpinnerView) view.findViewById(R.id.spinnerClass);
        save_apply = (ButtonRegular) view.findViewById(R.id.save_apply);
        spinnerClass.setArray(arrClass);
        spinnerClass.setSelection(0);
        spinnerClub.setArray(arrClub);
        spinnerClub.setSelection(0);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_data.setHasFixedSize(true);
        list_data.setLayoutManager(mLayoutManager);
        adapter = new FlashFitnessAdapter(getActivity(), list);
        list_data.setAdapter(adapter);
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
                processAnimateSelectorFilter();
            }

            @Override
            public void onRight2IconClick() {

            }
        });
        save_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAnimateSelectorFilter();
            }
        });
    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.back);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(R.drawable.icon_filter);
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
        return R.layout.fragment_flash_fitness;
    }

    private void processAnimateSelectorFilter(){

        final Animation inAnim = AnimationUtils.loadAnimation(getBaseActivity(), R.anim.slide_in_top);
        final Animation outAnim = AnimationUtils.loadAnimation(getBaseActivity(), R.anim.slide_in_bottom);

        select_filter_animator.setInAnimation(inAnim);
        select_filter_animator.setOutAnimation(outAnim);

        if(isShowSelectProfile) {
            isShowSelectProfile = false;
            select_filter_animator.setAnimation(outAnim);
            select_filter_animator.setVisibility(View.GONE);
        }else{
            isShowSelectProfile = true;
            select_filter_animator.setAnimation(inAnim);
            select_filter_animator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.myMapKu = googleMap;
        tampilMaps();
    }

    private void tampilMaps(){
        if (myMapKu!= null){
            myMapKu.clear();
            if (list.size()>0){
                for (int i=0; i<list.size(); i++){
                    Double lat = Double.parseDouble(list.get(i).getLatitudeFit());
                    Double longi = Double.parseDouble(list.get(i).getLongitudeFit());
                    LatLng lldoctor = new LatLng(lat, longi);
                    MarkerOptions markerDoctor = new MarkerOptions();
                    markerDoctor.position(lldoctor);
                    markerDoctor.title(String.valueOf(list.get(i).getName()));
                    markerDoctor.snippet(String.valueOf(list.get(i).getLokasi()));
                    markerDoctor.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                    myMapKu.addMarker(markerDoctor);

                    myMapKu.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    myMapKu.getUiSettings().setCompassEnabled(true);
                    myMapKu.getUiSettings().setZoomControlsEnabled(true);
                    myMapKu.getUiSettings().setMyLocationButtonEnabled(true);
                    myMapKu.animateCamera(CameraUpdateFactory.newLatLngZoom(lldoctor, 17));
                    myMapKu.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker arg0) {
                            // TODO Auto-generated method stub
                            try {
                                StringBuilder urlString = new StringBuilder();
                                String daddr = (String.valueOf(arg0.getPosition().latitude)+","+String.valueOf(arg0.getPosition().longitude));
                                urlString.append("http://maps.google.com/maps?f=d&hl=en");
                                urlString.append("&saddr="+String.valueOf(myMapKu.getMyLocation().getLatitude())+","+String.valueOf(myMapKu.getMyLocation().getLongitude()));
                                urlString.append("&daddr="+daddr);
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString.toString()));
                                getBaseActivity().startActivity(i);
                            } catch (Exception ee) {
                                Toast.makeText(getBaseActivity(), getBaseActivity().getResources().getString(R.string.gps_not_active), Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                }
            }
        }
    }
}
