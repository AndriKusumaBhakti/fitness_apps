package com.fitness.fragment.profile;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.MenuFlashAdapter;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBClub;
import com.fitness.entities.ClubEntity;
import com.fitness.fragment.BaseFragment;
import com.fitness.fragment.home.DetailPromoFragment;
import com.fitness.util.Constants;
import com.fitness.view.TextViewLight;
import com.fitness.view.TextViewRegular;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

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
    CarouselView carouselView;
    final String[][] mThumbIds1 = {{"https://res.cloudinary.com/dvrv7bd38/image/upload/w_1000,ar_16:9,c_fill,g_auto,e_sharpen/v1583250163/fitness/promo/fitnessPromo2_aeoydt.jpg",
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
        /*dbClub = new DBClub(getBaseActivity());
        nameClub = new ArrayList<>();*/
    }

    @Override
    public void initView(View view) {
        list_data = (RecyclerView) view.findViewById(R.id.list_data);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(mThumbIds1.length);
        carouselView.setSlideInterval(3000);

        carouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater().inflate(R.layout.item_card_promo, null);
                ImageView gambarCard = (ImageView) customView.findViewById(R.id.gambarCardView);

                Glide.with(getActivity())
                        .load(mThumbIds1[position][0])
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
                        bundle.putString(Constants.diskripsiPromo, mThumbIds1[position][1]);
                        bundle.putString(Constants.imagePromo, mThumbIds1[position][0]);
                        bundle.putString(Constants.linkShare, mThumbIds1[position][2]);
                        fragment.setArguments(bundle);
                        dashboard.pushFragmentDashboard(fragment);
                    }
                });
                return customView;
            }
        });
        data = getResources().getStringArray(R.array.menuFlash);
        mThumbIds = new Integer[]{R.drawable.icon_flash,
                R.drawable.icon_latihan, R.drawable.icon_struktur,
                R.drawable.icon_personal_training, R.drawable.icon_about, R.drawable.icon_language
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
                bundle.putString(Constants.labelName, nameLabel);
                bundle.putBoolean(Constants.mapsLocation, true);
                bundle.putInt(Constants.menuFlash, 1);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 1){
                /*FlashFitnessFragment fragment = new FlashFitnessFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.labelName, nameLabel);
                bundle.putBoolean(Constants.mapsLocation, false);
                bundle.putInt(Constants.menuFlash, 2);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);*/
                FlashFitnessFragment fragment = new FlashFitnessFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.labelName, nameLabel);
                bundle.putBoolean(Constants.mapsLocation, false);
                bundle.putInt(Constants.menuFlash, 3);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 2){
                /*FlashFitnessFragment fragment = new FlashFitnessFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.labelName, nameLabel);
                bundle.putBoolean(Constants.mapsLocation, false);
                bundle.putInt(Constants.menuFlash, 3);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);*/
                InstrukturFragment fragment = new InstrukturFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                bundle.putBoolean("intruktur", false);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 3){
                InstrukturFragment fragment = new InstrukturFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                bundle.putBoolean("intruktur", true);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 4){
                AboutFlashFragment fragment = new AboutFlashFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
            }else if (position == 5){
                ChangeLanguageFragment fragment = new ChangeLanguageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("label_bar", nameLabel);
                fragment.setArguments(bundle);
                dashboard.pushFragmentDashboard(fragment);
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
