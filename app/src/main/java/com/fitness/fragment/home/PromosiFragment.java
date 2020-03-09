package com.fitness.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.FlashNewAdapter;
import com.fitness.adapter.FlashNowAdapter;
import com.fitness.aplication.APP;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBClass;
import com.fitness.database.DBClub;
import com.fitness.database.DBEventClub;
import com.fitness.entities.ClassEntity;
import com.fitness.entities.ClubEntity;
import com.fitness.entities.EventClubEntity;
import com.fitness.fragment.BaseFragment;
import com.fitness.fragment.profile.DetailFlashFitnessFragment;
import com.fitness.fragment.profile.FlashFitnessFragment;
import com.fitness.fragment.profile.FlashFitnessNowFragment;
import com.fitness.model.ModelMaps;
import com.fitness.model.ModelNewMaps;
import com.fitness.util.Constants;
import com.fitness.view.ButtonRegular;
import com.fitness.view.TextViewBold;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PromosiFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CarouselView carouselView;
    final String[][] mThumbIds = {{"https://res.cloudinary.com/dvrv7bd38/image/upload/w_1000,ar_16:9,c_fill,g_auto,e_sharpen/v1583250163/fitness/promo/fitnessPromo2_aeoydt.jpg",
            "Ayo Segera Bergabung Bersama Kami Flash Fitness Indonesia, Dengan Fasilitas Olahraga Terlengkap Di Kota Surabaya, HANYA 250rb/bln..., " +
                    "Tunggu apalagi segera yuuukkk...!!! @ Flash Fitness CITO",
            "https://www.facebook.com/FlashFitnessCito.SBY/photos/pcb.1794041050607569/1794040913940916/?type=3&theater"},
            {"https://res.cloudinary.com/dvrv7bd38/image/upload/w_1000,ar_16:9,c_fill,g_auto,e_sharpen/v1583388094/fitness/promo/promo2_ykujcd.jpg",
            "Lippo Malls Indonesia dan Styles menghadirkan program istimewa “Gelegar Milyaran Hadiah”, yang diselenggarakan di 8 Lippo Malls Jawa Timur yaitu: Malang Town Square, Kediri Town Square, Plaza Madiun, City of Tomorrow, Lippo Plaza Batu, Lippo Plaza Sidoarjo, Lippo Plaza Jember dan Lippo Plaza Gresik.\n" +
                    "\n" + "AYO TRANSAKSI DI @flashfitnessindonesia di FLASH CITO dan LIPPO SEKARANG! Dengan minimal Rp. 50.000,-, mengunduh aplikasi Styles dan menukarkan struk belanja ke Customer Service masing-masing mal, kamu berhak mendapatkan 1 kupon undian Lucky Styles berlaku kelipatan*. Program ini dimulai dari tanggal 1 Feb 2020 hingga 30 April 2020. Pengundian TAHAP II akan dilakukan tanggal 10 MEI 2020 di CITY OF TOMMOROW SURABAYA.\n" +
                    "Semakin banyak belanja semakin besar kesempatan kamu untuk mendapatkan hadiahnya!\n" +
                    "\n" + "Untuk info lebih lanjut, cek My Promo di aplikasi Styles. Hubungi Call Center Styles di WA/Call 08111-456-456, email ke: info@styles.id, atau Customer Service @cito_sby dan @lippoplazasidoarjo .\n" +
                    ".\n" + "*Syarat dan Ketentuan berlaku.",
                    "https://www.facebook.com/FlashFitnessCito.SBY/photos/a.1453638174647860/3044716835539978/?type=3&theater"},
            {"https://res.cloudinary.com/dvrv7bd38/image/upload/w_1000,ar_16:9,c_fill,g_auto,e_sharpen/v1583388176/fitness/promo/promo3_yissza.jpg",
                    "Ayo Dapatkan Keanggotaan Tipe Khusus untuk Anda, Tipe Baru untuk Liburan Fitness... hanya Rp. 200,000 tahun,-",
                    "https://www.facebook.com/FlashFitnessCito.SBY/photos/a.1457938427551168/2166931379985199/?type=3&theater"}};

    private ArrayList<ModelMaps> dataMaps;

    private DBClub dbClub;
    private DBClass dbClass;
    private DBEventClub dbEventClub;
    private List<EventClubEntity> listEntity;
    private List<ClassEntity> listClass;
    private Date currentTime;
    private String searchHari = "Senin";
    private ClubEntity entitiClub;
    private ClassEntity entitiClass;
    private String[] hari;
    private RecyclerView listClassNow;
    private FlashNewAdapter adapter;
    private TextViewBold dayNow;
    private ButtonRegular btn_detail;
    private ArrayList<ModelNewMaps> dataNewMaps;

    public PromosiFragment() {}

    public static PromosiFragment newInstance() {
        return newInstance("","");
    }

    public static PromosiFragment newInstance(String param1, String param2) {
        PromosiFragment fragment = new PromosiFragment();
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
        dataNewMaps = new ArrayList<>();
        entitiClub = new ClubEntity();
        entitiClass = new ClassEntity();
        listClass = new ArrayList<>();
    }

    @Override
    public void initView(View view) {

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        dayNow = (TextViewBold) view.findViewById(R.id.dayNow);
        listClassNow = (RecyclerView) view.findViewById(R.id.listClassNow);
        btn_detail = (ButtonRegular) view.findViewById(R.id.btn_detail);

        viewData();
    }

    private void viewData(){
        dataNewMaps.clear();
        carouselView.setPageCount(mThumbIds.length);
        carouselView.setSlideInterval(3000);

        carouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater().inflate(R.layout.item_card_promo, null);
                ImageView gambarCard = (ImageView) customView.findViewById(R.id.gambarCardView);

                Glide.with(getActivity())
                        .load(mThumbIds[position][0])
                        .centerCrop()
                        .fitCenter()
                        .dontAnimate()
                        .placeholder(R.drawable.fitness_romo1)
                        .error(R.drawable.fitness_romo1)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(gambarCard);
                gambarCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DashboardActivity dashboard = DashboardActivity.instance;
                        DetailPromoFragment fragment = new DetailPromoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.labelBardetail, "Promo");
                        bundle.putString(Constants.diskripsiPromo, mThumbIds[position][1]);
                        bundle.putString(Constants.imagePromo, mThumbIds[position][0]);
                        bundle.putString(Constants.linkShare, mThumbIds[position][2]);
                        fragment.setArguments(bundle);
                        dashboard.pushFragmentDashboard(fragment);
                    }
                });
                return customView;
            }
        });

        currentTime = Calendar.getInstance().getTime();
        hari = getResources().getStringArray(R.array.hari);

        /*for (int i = 0; i < hari.length; i++) {
            if (currentTime.getDay() == 0){

            }else {
                if (i == currentTime.getDay() - 1) {
                    searchHari = hari[i];
                }
            }
        }*/
        searchHari = hari[currentTime.getDay()];
        APP.log("searchHari : "+currentTime.getDay());
        dayNow.setText(getResources().getString(R.string.label_jadwal)+" : "+searchHari);
        listClass = dbClass.getAll();
        if (listClass.size()>0) {
            for (int j=0; j<listClass.size(); j++){
                listEntity = dbEventClub.getAllDayAndClass(String.valueOf(currentTime.getDay()), listClass.get(j).getId());
                if (listEntity.size() > 0) {
                    dataMaps.clear();
                    for (int i = 0; i < listEntity.size(); i++) {
                        ModelMaps model = new ModelMaps();
                        //get data club untuk maps di now
                        entitiClub = dbClub.getByIdCLub(listEntity.get(i).getIdClub());
                        if (entitiClub != null) {
                            model.setLatitudeFit(entitiClub.getLatitude());
                            model.setLongitudeFit(entitiClub.getLongitude());
                            model.setName(entitiClub.getNamaClub());
                            model.setLokasi(entitiClub.getLokasi());
                        }
                        //get data class now
                        entitiClass = dbClass.getById(listEntity.get(i).getIdClass());
                        if (entitiClass != null) {
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
                    ModelNewMaps newMaps = new ModelNewMaps();
                    newMaps.setId(j);
                    newMaps.setNamaEvent(listClass.get(j).getNamaClass());
                    newMaps.setDataMaps(dataMaps);
                    if (dataNewMaps.size()<1){
                        newMaps.setView(true);
                    }else{
                        newMaps.setView(false);
                    }
                    dataNewMaps.add(newMaps);
                }
            }
        }
        ArrayList<ModelNewMaps> dataNewMaps1 = new ArrayList<>();
        if (dataNewMaps.size()>2){
            dataNewMaps1.add(dataNewMaps.get(0));
            dataNewMaps1.add(dataNewMaps.get(1));
        }else{
            dataNewMaps1 = dataNewMaps;
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listClassNow.setHasFixedSize(true);
        listClassNow.setLayoutManager(mLayoutManager);
        adapter = new FlashNewAdapter(getActivity(), dataNewMaps1, false);
        listClassNow.setAdapter(adapter);
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
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity dashboard = DashboardActivity.instance;
                FlashFitnessNowFragment fragment = new FlashFitnessNowFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.labelName, getResources().getStringArray(R.array.menuFlash)[2]);
                bundle.putBoolean(Constants.mapsLocation, false);
                bundle.putInt(Constants.menuFlash, 3);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
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
        return getResources().getString(R.string.label_home);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_promosi;
    }

    @Override
    public void onResume() {
        super.onResume();
        DashboardActivity.instance.showBottomMenu();
    }
}
