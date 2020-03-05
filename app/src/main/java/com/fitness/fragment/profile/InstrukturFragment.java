package com.fitness.fragment.profile;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.InstrukturAdapter;
import com.fitness.base.OnActionbarListener;
import com.fitness.fragment.BaseFragment;

public class InstrukturFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String label_bar;
    private RecyclerView list_data;
    private InstrukturAdapter adapter;
    String[][] data;
    private boolean intruktur = false;

    String[][] instruktur = new String[][]{{"Ferry Mardian", "Atlit sekaligus pelatih tinju nasional. Pria kelahiran tahun 1980 ini sudah " +
            "mengemban profesi sebagai seorang pelatih boxing sejak tahun 1992 dan sudah melatih banyak pihak mulai dari yang masih duduk di bangku SMA hingga yang sudah bekerja.\n" +
            "\n" + "Dalam debut profesionalnya, 30 Juli 1995 di Timor Leste, Pak Aan menang angka atas Azadin Anhar. " +
            "Dua pertandingan setelahnya, melawan petinju berpengalaman Somboonyod Singsamang dan mantan juara dunia IBF kelas terbang super Ju-Do Chun, " +
            "juga ia menangkan dengan gemilang. Tiga tahun kemudian Pak Hom meraih juara nasional kelas bulu yunior.",
            "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583399081/fitness/splashscreen/foto_profile_1_dawqis.jpg", "true"},
            {"Eddie", "Atlit sekaligus pelatih tinju nasional. Pria kelahiran tahun 1980 ini sudah " +
                    "mengemban profesi sebagai seorang pelatih boxing sejak tahun 1992 dan sudah melatih banyak pihak mulai dari yang masih duduk di bangku SMA hingga yang sudah bekerja.\n" +
                    "\n" + "Dalam debut profesionalnya, 30 Juli 1995 di Timor Leste, Pak Aan menang angka atas Azadin Anhar. " +
                    "Dua pertandingan setelahnya, melawan petinju berpengalaman Somboonyod Singsamang dan mantan juara dunia IBF kelas terbang super Ju-Do Chun, " +
                    "juga ia menangkan dengan gemilang. Tiga tahun kemudian Pak Hom meraih juara nasional kelas bulu yunior.",
                    "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583399082/fitness/splashscreen/foto_profile_3_v4omzo.jpg", "false"},
            {"Liliana", "Atlit sekaligus pelatih tinju nasional. Pria kelahiran tahun 1980 ini sudah " +
                    "mengemban profesi sebagai seorang pelatih boxing sejak tahun 1992 dan sudah melatih banyak pihak mulai dari yang masih duduk di bangku SMA hingga yang sudah bekerja.\n" +
                    "\n" + "Dalam debut profesionalnya, 30 Juli 1995 di Timor Leste, Pak Aan menang angka atas Azadin Anhar. " +
                    "Dua pertandingan setelahnya, melawan petinju berpengalaman Somboonyod Singsamang dan mantan juara dunia IBF kelas terbang super Ju-Do Chun, " +
                    "juga ia menangkan dengan gemilang. Tiga tahun kemudian Pak Hom meraih juara nasional kelas bulu yunior.",
                    "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583399080/fitness/splashscreen/foto_profile_2_groo4a.jpg", "false"}};

    String[][] personal = new String[][]{{"Ferry Mardian", "Pria kelahiran tahun 1990 ini adalah alumni Universitas Negeri Surabaya " +
            "jurusan Ilmu Keolahragaan. Memiliki spesialisasi HIIT Training, Muscle Toning & Building, dan Rehabilitation Training. " +
            "Dengan lebih dari 1 dekade pengalaman di industri kebugaran sebagai Personal Trainer, Ferry akan membantu mewujudkan target yang anda canangkan. ",
            "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583399081/fitness/splashscreen/foto_profile_1_dawqis.jpg", "true"},
            {"Eddie", "Pria kelahiran tahun 1990 ini adalah alumni Universitas Negeri Surabaya " +
                    "jurusan Ilmu Keolahragaan. Memiliki spesialisasi HIIT Training, Muscle Toning & Building, dan Rehabilitation Training. " +
                    "Dengan lebih dari 1 dekade pengalaman di industri kebugaran sebagai Personal Trainer, Ferry akan membantu mewujudkan target yang anda canangkan. ",
                    "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583399082/fitness/splashscreen/foto_profile_3_v4omzo.jpg", "false"},
            {"Liliana", "Pria kelahiran tahun 1990 ini adalah alumni Universitas Negeri Surabaya " +
                    "jurusan Ilmu Keolahragaan. Memiliki spesialisasi HIIT Training, Muscle Toning & Building, dan Rehabilitation Training. " +
                    "Dengan lebih dari 1 dekade pengalaman di industri kebugaran sebagai Personal Trainer, Ferry akan membantu mewujudkan target yang anda canangkan. ",
                    "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583399080/fitness/splashscreen/foto_profile_2_groo4a.jpg", "false"}};

    public InstrukturFragment() {}

    public static InstrukturFragment newInstance() {
        return newInstance("","");
    }

    public static InstrukturFragment newInstance(String param1, String param2) {
        InstrukturFragment fragment = new InstrukturFragment();
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
            intruktur = getArguments().getBoolean("intruktur");
        }
        if (intruktur){
            data = instruktur;
        }else{
            data = personal;
        }
    }

    @Override
    public void initView(View view) {
        list_data = (RecyclerView) view.findViewById(R.id.list_data);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_data.setHasFixedSize(true);
        list_data.setLayoutManager(mLayoutManager);
        adapter = new InstrukturAdapter(getBaseActivity(), data);
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
        return R.layout.fragment_intruktur;
    }
}
