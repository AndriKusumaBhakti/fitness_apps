package com.fitness.fragment.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.FlashFitnessAdapter;
import com.fitness.adapter.FlashNewAdapter;
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
import com.fitness.model.ModelNewMaps;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FlashFitnessNowFragment extends BaseFragment implements OnMapReadyCallback {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String label_bar;
    private boolean mapsStatus;
    private int menuStatus = 3;
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
    private ClubEntity clubData;
    private ClassEntity classData;
    private FlashFitnessAdapter adapter;
    private ArrayList<ModelMaps> list;
    private GoogleMap myMapKu;
    private SupportMapFragment supportMapFragment;
    private LinearLayout mapsLocation;
    private ArrayList<ModelNewMaps> dataNewMaps;

    private List<ClassEntity> listClass;

    private ButtonRegular save_apply;
    private FlashNewAdapter adapterNew;

    private Date currentTime;
    private String searchHari = "Senin";
    private String[] hari;

    public FlashFitnessNowFragment() {}

    public static FlashFitnessNowFragment newInstance() {
        return newInstance("","");
    }

    public static FlashFitnessNowFragment newInstance(String param1, String param2) {
        FlashFitnessNowFragment fragment = new FlashFitnessNowFragment();
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
            mapsStatus = getArguments().getBoolean(Constants.mapsLocation);
            menuStatus = getArguments().getInt(Constants.menuFlash);
        }
        clubData = new ClubEntity();
        classData = new ClassEntity();
        dbClub = new DBClub(getBaseActivity());
        dbClass = new DBClass(getBaseActivity());
        dbEventClub = new DBEventClub(getBaseActivity());
        entitiCLub = new ArrayList<>();
        entitiCLass = new ArrayList<>();
        listClass = new ArrayList<>();
        entitiEventCLub = new ArrayList<>();
        dataNewMaps = new ArrayList<>();
        arrClass = new ArrayList<>();
        arrClass.add(getActivity().getResources().getString(R.string.select_class));
        arrClub = new ArrayList<>();
        arrClub.add(getActivity().getResources().getString(R.string.select_branch));
        list = new ArrayList<>();
        if (menuStatus == 1){
            if (dbClub.getAllData()>0){
                entitiCLub = dbClub.getAll();
                for (int i= 0; i<entitiCLub.size(); i++){
                    arrClub.add(entitiCLub.get(i).getNamaClub());
                    ModelMaps modelMaps = new ModelMaps();
                    modelMaps.setName(entitiCLub.get(i).getNamaClub());
                    modelMaps.setLongitudeFit(entitiCLub.get(i).getLongitude());
                    modelMaps.setLatitudeFit(entitiCLub.get(i).getLatitude());
                    modelMaps.setLokasi(entitiCLub.get(i).getLokasi());
                    modelMaps.setView(false);
                    list.add(modelMaps);
                }
            }
        }else if (menuStatus == 2){
            if (dbClub.getAllData()>0){
                entitiCLub = dbClub.getAll();
                for (int i= 0; i<entitiCLub.size(); i++){
                    arrClub.add(entitiCLub.get(i).getNamaClub());
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
                if (entitiEventCLub.size()>0){
                    for (int i= 0; i<entitiEventCLub.size(); i++) {
                        if (Boolean.parseBoolean(entitiEventCLub.get(i).getUnggulan())) {
                            ModelMaps modelMaps = new ModelMaps();
                            modelMaps.setId(String.valueOf(entitiEventCLub.get(i).getId()));
                            modelMaps.setDurasi(entitiEventCLub.get(i).getDurasi());
                            modelMaps.setHari(entitiEventCLub.get(i).getHari());
                            modelMaps.setJamEnd(entitiEventCLub.get(i).getJamEnd());
                            modelMaps.setJamStart(entitiEventCLub.get(i).getJamStart());
                            modelMaps.setPelatih(entitiEventCLub.get(i).getPelatih());
                            clubData = dbClub.getById(entitiEventCLub.get(i).getIdClub());
                            if (clubData != null) {
                                modelMaps.setName(clubData.getNamaClub());
                                modelMaps.setLatitudeFit(clubData.getLatitude());
                                modelMaps.setLongitudeFit(clubData.getLongitude());
                                modelMaps.setLokasi(clubData.getLokasi());
                            }
                            classData = dbClass.getById(entitiEventCLub.get(i).getIdClass());
                            if (classData != null) {
                                modelMaps.setNamaEvent(classData.getNamaClass());
                                modelMaps.setImage(classData.getImage());
                                modelMaps.setDeskripsi(classData.getDeskripsi());
                            }
                            modelMaps.setView(false);
                            list.add(modelMaps);
                        }
                    }
                }
            }
        }else {
            dataNewMaps.clear();
            if (dbClub.getAllData()>0){
                entitiCLub = dbClub.getAll();
                for (int i= 0; i<entitiCLub.size(); i++){
                    arrClub.add(entitiCLub.get(i).getNamaClub());
                }
            }
            if (dbClass.getAllData()>0){
                entitiCLass = dbClass.getAll();
                for (int i= 0; i<entitiCLass.size(); i++){
                    arrClass.add(entitiCLass.get(i).getNamaClass());
                }
            }
            listClass = dbClass.getAll();
            currentTime = Calendar.getInstance().getTime();
            hari = getResources().getStringArray(R.array.hari);
            for (int i = 0; i<hari.length; i++){
                if (i == currentTime.getDay() - 1){
                    searchHari = hari[i];
                }
            }
            if (listClass.size()>0) {
                for (int j=0; j<listClass.size(); j++){
                    entitiEventCLub = dbEventClub.getAllDayAndClass(String.valueOf(currentTime.getDay()), listClass.get(j).getId());
                    if (entitiEventCLub.size() > 0) {
                        list.clear();
                        for (int i = 0; i < entitiEventCLub.size(); i++) {
                            ModelMaps model = new ModelMaps();
                            //get data club untuk maps di now
                            clubData = dbClub.getByIdCLub(entitiEventCLub.get(i).getIdClub());
                            if (clubData != null) {
                                model.setLatitudeFit(clubData.getLatitude());
                                model.setLongitudeFit(clubData.getLongitude());
                                model.setName(clubData.getNamaClub());
                                model.setLokasi(clubData.getLokasi());
                            }
                            //get data class now
                            classData = dbClass.getById(entitiEventCLub.get(i).getIdClass());
                            if (classData != null) {
                                model.setNamaEvent(classData.getNamaClass());
                                model.setDeskripsi(classData.getDeskripsi());
                                model.setImage(classData.getImage());
                            }
                            //get data event club now
                            model.setId(String.valueOf(entitiEventCLub.get(i).getId()));
                            model.setDurasi(entitiEventCLub.get(i).getDurasi());
                            model.setJamStart(entitiEventCLub.get(i).getJamStart());
                            model.setJamEnd(entitiEventCLub.get(i).getJamEnd());
                            model.setPelatih(entitiEventCLub.get(i).getPelatih());
                            model.setHari(entitiEventCLub.get(i).getHari());
                            list.add(model);
                        }
                        ModelNewMaps newMaps = new ModelNewMaps();
                        newMaps.setId(j);
                        newMaps.setNamaEvent(listClass.get(j).getNamaClass());
                        newMaps.setDataMaps(list);
                        if (dataNewMaps.size()<1){
                            newMaps.setView(true);
                        }else{
                            newMaps.setView(false);
                        }
                        dataNewMaps.add(newMaps);
                    }
                }
            }
        }
    }

    @Override
    public void initView(View view) {
        mapsLocation = (LinearLayout) view.findViewById(R.id.mapsLocation);
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
        if (menuStatus == 1) {
            adapter = new FlashFitnessAdapter(getActivity(), menuStatus, list, new FlashFitnessAdapter.ClickListener() {
                @Override
                public void onClick(int posisi) {
                    tampilMaps(posisi);
                }

                @Override
                public void onDetail(ModelMaps modelMaps) {
                    APP.log("" + modelMaps.getNamaEvent());
                    DashboardActivity dashboard = DashboardActivity.instance;
                    DetailFlashFitnessFragment fragment = new DetailFlashFitnessFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.labelBardetail, modelMaps.getNamaEvent());
                    bundle.putSerializable(Constants.detailFlash, modelMaps);
                    fragment.setArguments(bundle);
                    dashboard.pushFragmentDashboard(fragment);
                }
            });
            list_data.setAdapter(adapter);
        }else{
            adapterNew = new FlashNewAdapter(getActivity(), dataNewMaps, true);
            list_data.setAdapter(adapterNew);
        }
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
                spinnerClub.setSelection(0);
                spinnerClass.setSelection(0);
                processAnimateSelectorFilter();
            }

            @Override
            public void onRight2IconClick() {

            }
        });
        save_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ModelNewMaps> dataNewMapsSearch = new ArrayList<>();
                if (dataNewMaps.size()>0){
                    String selectClass = null;
                    String selectClub = null;
                    if (spinnerClub.getSelectedItemPosition()>0){
                        selectClub = spinnerClub.getSelectedItem();
                    }
                    if (spinnerClass.getSelectedItemPosition()>0){
                        selectClass = spinnerClass.getSelectedItem();
                    }
                    APP.log(""+selectClub+", "+selectClass);
                    for (int i=0; i<dataNewMaps.size(); i++){
                        ArrayList<ModelMaps> listSearch = new ArrayList<>();
                        if (selectClub!=null){
                            if (selectClass!=null){
                                if (selectClass.equals(dataNewMaps.get(i).getNamaEvent())){
                                    if (dataNewMaps.get(i).getDataMaps().size()>0) {
                                        for (int j = 0; j < dataNewMaps.get(i).getDataMaps().size(); j++) {
                                            if (selectClub.equals(dataNewMaps.get(i).getDataMaps().get(j).getName())) {
                                                listSearch.add(dataNewMaps.get(i).getDataMaps().get(j));
                                            }
                                        }
                                        ModelNewMaps newMaps = new ModelNewMaps();
                                        newMaps.setId(i);
                                        newMaps.setNamaEvent(dataNewMaps.get(i).getNamaEvent());
                                        newMaps.setDataMaps(listSearch);
                                        if (dataNewMapsSearch.size() < 1) {
                                            newMaps.setView(true);
                                        } else {
                                            newMaps.setView(false);
                                        }
                                        dataNewMapsSearch.add(newMaps);
                                    }
                                }
                            }else{
                                if (dataNewMaps.get(i).getDataMaps().size()>0) {
                                    for (int j = 0; j < dataNewMaps.get(i).getDataMaps().size(); j++) {
                                        if (selectClub.equals(dataNewMaps.get(i).getDataMaps().get(j).getName())) {
                                            listSearch.add(dataNewMaps.get(i).getDataMaps().get(j));
                                        }
                                    }
                                    if (listSearch.size()>0) {
                                        ModelNewMaps newMaps = new ModelNewMaps();
                                        newMaps.setId(i);
                                        newMaps.setNamaEvent(dataNewMaps.get(i).getNamaEvent());
                                        newMaps.setDataMaps(listSearch);
                                        if (dataNewMapsSearch.size() < 1) {
                                            newMaps.setView(true);
                                        } else {
                                            newMaps.setView(false);
                                        }
                                        dataNewMapsSearch.add(newMaps);
                                    }
                                }
                            }
                        }else if (selectClass!=null){
                            if (selectClass.equals(dataNewMaps.get(i).getNamaEvent())){
                                ModelNewMaps newMaps = new ModelNewMaps();
                                newMaps.setId(i);
                                newMaps.setNamaEvent(dataNewMaps.get(i).getNamaEvent());
                                newMaps.setDataMaps(dataNewMaps.get(i).getDataMaps());
                                if (dataNewMapsSearch.size() < 1) {
                                    newMaps.setView(true);
                                } else {
                                    newMaps.setView(false);
                                }
                                dataNewMapsSearch.add(newMaps);
                            }
                        }else{
                            dataNewMapsSearch.add(dataNewMaps.get(i));
                        }
                    }
                    adapterNew.updateListSearch(dataNewMapsSearch);
                }
                processAnimateSelectorFilter();
            }
        });
    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.back);
        getBaseActivity().setRightIcon2(0);
        if (mapsStatus){
            mapsLocation.setVisibility(View.VISIBLE);
            getBaseActivity().setRightIcon(0);
        }else{
            getBaseActivity().setRightIcon(R.drawable.icon_filter);
            mapsLocation.setVisibility(View.GONE);
        }
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(true);
        DashboardActivity.instance.setBarView(true);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.label_jadwal_today);
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
        tampilMaps(0);
    }

    private void tampilMaps(int posisi){
        if (myMapKu!= null){
            myMapKu.clear();
            if (list.size()>0){
                Double lat = Double.parseDouble(list.get(posisi).getLatitudeFit());
                Double longi = Double.parseDouble(list.get(posisi).getLongitudeFit());
                LatLng lldoctor = new LatLng(lat, longi);
                MarkerOptions markerDoctor = new MarkerOptions();
                markerDoctor.position(lldoctor);
                markerDoctor.title(String.valueOf(list.get(posisi).getName()));
                markerDoctor.snippet(String.valueOf(list.get(posisi).getLokasi()));
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
