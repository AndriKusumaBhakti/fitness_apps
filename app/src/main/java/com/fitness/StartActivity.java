package com.fitness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.activity.BaseActivity;
import com.fitness.activity.DashboardActivity;
import com.fitness.aplication.APP;
import com.fitness.aplication.DBHelper;
import com.fitness.database.DBClass;
import com.fitness.database.DBClub;
import com.fitness.database.DBEventClub;
import com.fitness.database.DBLanguage;
import com.fitness.entities.LanguageEntity;
import com.fitness.model.ClassModel;
import com.fitness.model.ClubModel;
import com.fitness.model.EventClubModel;
import com.fitness.model.Language;
import com.fitness.util.FunctionUtil;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewRegular;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.List;
import java.util.Locale;


public class StartActivity extends BaseActivity {
    AsyncTask waitingTask;
    boolean isPushToMain;
    String[] PERMISSIONS = {

    };
    private List<LanguageEntity> languageEntity;
    private String bahasaLanguage;

    CarouselView carouselView;
    String[] mThumbIds;
    String[] deskripsi;
    String[] title;
    LinearLayout groupLinear;
    String[][] cabang = new String[][]{{"CITO CLASS", "112.7095938", "-7.3020108", "Jl. Ahmad Yani No.288, Dukuh Menanggal, Kec. Gayungan, Kabupaten Sidoarjo, Jawa Timur 60234"},
            {"GAYUNGSARI CLASS", "112.6900431", "-7.2950908", "Jl. Gayungsari Bar. X No.31, Gayungan, Kec. Gayungan, Kota SBY, Jawa Timur 60235"},
            {"LIPPO CLASS", "112.6525474", "-7.3509914", "Jl. Jati Raya No.1, Jati, Sidoarjo Sub-Distrcit, Sidoarjo Regency, East Java 61226"}};
    String[][] namaClass = new String[][]{{"Yoga", "Manfaat yoga untuk kesehatan mental dan jiwa ternyata lebih dari sekedar memperbaiki mood." +
            " Ya, mereka yang pernah melakukan yoga pasti setuju jika olahraga tersebut membuat perasaan lebih baik.\n" +
            "\n" + "Lebih dari sekedar menyeimbangkan tubuh serta pikiran, gerakan yoga juga mengajarkan kita untuk " +
            "lebih care terhadap kemampuan tubuh. Melalui yoga, kita menjadi sadar bahwa tubuh juga perlu istirahat, serta waktu untuk pulih atau sembuh. \n" +
            "\n" + "Gerakan yoga sangat banyak dan beragam. Mulai dari gerakan pemula hingga gerakan kompleks yang membutuhkan keseimbangan, kekuatan otot, kelenturan dan jangkauan tubuh esktra.\n" +
            "\n" + "Jangan menyerah jika terasa sulit melakukan beberapa gerakan yoga di awal. Teruslah berlatih, kelincahan tubuhmu akan meningkat dengan sendirinya.",
            "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
            {"Kick Boxing", "Sensasi gerakan memukul, meninju, dan menendang hingga tercipta bunyi dentuman yang keras, " + "merupakan kunci dari olahraga Kick Boxing yang sangat menguras tenaga serta keringat.\n" +
                    "\n" + "Selain untuk menurunkan berat badan, latihan kick boxing terbukti dapat mengencangkan otot-otot. " +
                    "Tujuannya, tentu saja, untuk meningkatkan kesehatan kardiovaskular. Sebab, pukulan dan " +
                    "tendangan pada latihan ini merupakan variasi gerakan yang dibutuhkan tubuh untuk kesehatan sekaligus program perampingan.\n" +
                    "\n" + "Kelas kick boxing di Flash Fitness bisa diikuti oleh segala rentang usia, dari nol hingga mahir. Anda siap..??",
                    "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
            {"Boxing", "Olahraga boxing merupakan salah satu jenis cabang olahraga yang cukup banyak diminati, " +
                    "terutama di oleh para fitness mania. Karena memiliki beberapa persamaan yang bisa di manfaatkan untuk program pembentukan otot.\n" +
                    "\n" + "Bagi anda yang mengalami kebosanan dalam melakukan fitnes, tentunya ini menjadi pilihan yang baik untuk melakukan variasi " +
                    "olahraga yang memiliki manfaat yang hampir sama untuk pertumbuhan masssa otot.\n" +
                    "\n" + "Olahraga fitnes bukan hanya sekedar pembentukan otot, namun ada bagian lain yang harus di latih, " +
                    "yakni kelenturan tubuh menjadi lebih baik dan lain sebagainya. Olahraga boxing bisa menjadi paduan yang pas bagi anda.",
                    "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
            {"Body Pump", "Body Pump adalah cara tercepat dan terbaik dalam membakar lemak sekaligus membentuk otot-otot tubuh. " +
                    "Kelas ini mengkondisikan dan melatih otot-otot tubuh dengan beban yang dapat disesuaikan dengan kemampuan bagian tubuh yang dilatih. Olahraga ini cocok dan sempurna bagi pria maupun wanita. \n" +
                    "\n" + "Pemilihan musik dan koreografi akan membuat anda semakin bersemangat mengikuti kelas ini. Kelebihan dari kelas " +
                    "BodyPump adalah Anda dapat mengatur beban yang digunakan, mulai dari 0,5kg, 1,25kg, 2,5kg, sampai 5kg. Anda bebas memilih beban yang akan digunakan. Anda juga bisa mengombinasikan beban-beban tersebut.",
                    "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
            {"Pound Fitness", "Rasa bosan saat olahraga bisa diatasi dengan menghadirkan alat-alat yang tidak biasa. Stik drum misalnya, bisa juga digunakan sebagai alat bantu dalam olahraga yang lagi ngehits, Pound Fit.\n" +
                    "\n" + "Jika biasanya stik drum digunakan untuk bermain musik, dalam olahraga ini alat tersebut hanya dipukul-pukulkan ke matras. Bahkan hanya diayun-ayunkan, dan sesekali beradu untuk menghasilkan irama tertentu.\n" +
                    "\n" + "Karena unik, olahraga yang menggabungkan berbagai gerakan ini jadi terasa menyenangkan.",
                    "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
            {"RPM", "RPM merupakan program kelas untuk membakar lemak dan kalori dengan cara mengayuh sepeda statis. " +
                    "Biasanya RPM dilakukan dalam 50 menit. RPM terdiri dari 9 level latihan. Level 1 adalah pemanasan, dengan lagu yang easy listening, " +
                    "kita bisa menikmati pemanasan dengan santai. Level 2 hingga 8 adalah latihan inti. Disini tubuh kita ditantang untuk terus berada dalam kondisi puncak. \n" +
                    "\n" + "Dengan mengikuti kelas RPM, lemak dalam tubuhmu bisa terbakar maksimal.",
                    "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"}};
    //tempat, class, hari, durasi, start, end, pelatih, unggulan
    String[][] eventClass = new String[][]{{"1", "4", "1", "60mins", "08:00", "09:00", "LIEM", "false"}, {"1", "1", "1", "60mins", "09:00", "10:00", "STEVANO", "false"}, {"1", "6", "1", "60mins", "18:00", "19:00", "FUAD", "false"},
            {"1", "4", "2", "60mins", "18:00", "19:00", "LIEM", "false"}, {"1", "1", "2", "60mins", "19:00", "20:00", "FFC COACH", "false"}, {"1", "6", "2", "60mins", "19:00", "20:00", "LUKI", "false"},
            {"1", "4", "3", "60mins", "08:00", "09:00", "LIEM", "false"}, {"1", "1", "3", "60mins", "18:00", "19:00", "VONNY", "false"}, {"1", "6", "3", "60mins", "18:00", "19:00", "VONNY", "false"},
            {"1", "4", "4", "60mins", "19:00", "20:00", "ARMAN", "false"}, {"1", "1", "4", "60mins", "18:00", "19:00", "VONNY", "false"}, {"1", "6", "4", "60mins", "09:00", "10:00", "FUAD", "false"},
            {"1", "4", "5", "60mins", "08:00", "09:00", "JOSUA", "false"}, {"1", "1", "5", "60mins", "09:00", "10:00", "LILIANA", "false"}, {"1", "6", "5", "60mins", "09:00", "10:00", "FUAD", "false"},
            {"1", "2", "6", "60mins", "08:00", "09:00", "FARID", "false"}, {"1", "1", "6", "60mins", "15:00", "16:00", "EDIE", "false"}, {"1", "6", "6", "60mins", "09:00", "10:00", "FUAD", "false"},
            {"1", "4", "7", "60mins", "08:00", "09:00", "LIEM", "false"}, {"1", "6", "7", "60mins", "09:00", "10:00", "LIEM", "false"},
            {"2", "1", "1", "50mins", "18:10", "19:00", "HARIS", "false"},
            {"2", "1", "2", "60mins", "08:00", "09:00", "VONNY", "false"},
            {"2", "1", "3", "60mins", "07:00", "08:00", "VONNY", "false"},
            {"3", "4", "1", "60mins", "18:00", "19:00", "MELISA", "false"}, {"3", "3", "1", "60mins", "20:00", "21:00", "DANDY", "false"}, {"3", "6", "1", "60mins", "19:00", "20:00", "PIPIT", "false"},
            {"3", "1", "2", "60mins", "18:00", "19:00", "VONNY", "false"}, {"3", "6", "2", "60mins", "08:00", "09:00", "PIPIT", "false"}, {"3", "6", "2", "60mins", "19:00", "20:00", "PIPIT", "false"},
            {"3", "1", "3", "60mins", "19:00", "20:00", "MELISA", "false"}, {"3", "6", "3", "60mins", "08:00", "09:00", "PIPIT", "false"},
            {"3", "1", "4", "60mins", "09:00", "10:00", "VONNY", "false"}, {"3", "1", "4", "60mins", "19:00", "20:00", "MUE", "false"},
            {"3", "4", "5", "60mins", "09:00", "10:00", "MELISA", "false"}, {"3", "1", "5", "60mins", "18:00", "19:00", "VONNY", "false"}, {"3", "6", "5", "60mins", "19:00", "20:00", "PATRICK", "false"},
            {"3", "1", "6", "60mins", "07:00", "08:00", "EDIE", "false"}, {"3", "1", "6", "60mins", "08:00", "09:00", "EDIE", "false"}, {"3", "6", "6", "60mins", "08:00", "09:00", "LUCKY", "false"},
            {"3", "3", "7", "60mins", "19:00", "20:00", "DANDY", "false"}};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isPushToMain = false;
        DBHelper helper = new DBHelper(this);
        helper.getWritableDatabase();
        helper.close();
        changeLanguage();
        loadData();
        runApps();
        super.onCreate(savedInstanceState);
    }

    private void loadData(){
        DBClub dbClub = new DBClub(this);
        DBClass dbClass = new DBClass(this);
        DBEventClub dbEventClub = new DBEventClub(this);
        if (dbClub.getAllData() == 0) {
            for (int i = 0; i < cabang.length; i++) {
                ClubModel model = new ClubModel();
                model.setId(i+1);
                model.setNamaClub(cabang[i][0]);
                model.setLongitude(cabang[i][1]);
                model.setLatitude(cabang[i][2]);
                model.setLokasi(cabang[i][3]);
                dbClub.parseClub(model);
            }
        }
        if (dbClass.getAllData() == 0) {
            for (int i = 0; i < namaClass.length; i++) {
                ClassModel model = new ClassModel();
                model.setId(i+1);
                model.setNamaClass(namaClass[i][0]);
                model.setDeskripsi(namaClass[i][1]);
                model.setImage(namaClass[i][2]);
                dbClass.parseClub(model);
            }
        }
        if (dbEventClub.getAllData() == 0){
            for (int i = 0; i< eventClass.length; i++){
                EventClubModel model = new EventClubModel();
                model.setId(i+1);
                model.setIdClub(Integer.parseInt(eventClass[i][0]));
                model.setIdClass(Integer.parseInt(eventClass[i][1]));
                model.setHari(eventClass[i][2]);
                model.setDurasi(eventClass[i][3]);
                model.setJamStart(eventClass[i][4]);
                model.setJamEnd(eventClass[i][5]);
                model.setPelatih(eventClass[i][6]);
                model.setUnggulan(eventClass[i][7]);
                dbEventClub.parseClub(model);
            }
        }
    }

    private void runApps() {
        if(FunctionUtil.hasPermissions(this, PERMISSIONS)){
            loading();
        }else{
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if(FunctionUtil.hasPermissions(this, PERMISSIONS)){
                    loading();
                }else {
                    StartActivity.this.finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(waitingTask != null){
            try{
                if(waitingTask.getStatus() ==  AsyncTask.Status.RUNNING){
                    waitingTask.cancel(true);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void loading(){
        if(isPushToMain == false) {

            isPushToMain = true;
            waitingTask = new AsyncTask<Void, Integer, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "";
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    chekTokenAtApp();
                }
            };

            waitingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        }
    }

    private void chekTokenAtApp(){
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        title = getResources().getStringArray(R.array.titles);
        deskripsi = getResources().getStringArray(R.array.overviews);
        mThumbIds = new String[]{"https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250563/fitness/splashscreen/gambar_1_fdq3ps.png",
                "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250563/fitness/splashscreen/gambar_2_se8rj0.png",
                "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250558/fitness/splashscreen/gambar_3_xaietn.png",
                "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250562/fitness/splashscreen/gambar_4_cm15t3.png",
                "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250560/fitness/splashscreen/gambar_5_qgib3n.png",
                "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250560/fitness/splashscreen/gambar_6_vikgsx.png",
                "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250562/fitness/splashscreen/gambar_7_o6jmql.png",
                "https://res.cloudinary.com/dvrv7bd38/image/upload/v1583250562/fitness/splashscreen/gambar_4_cm15t3.png"};
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(mThumbIds.length);
        carouselView.setSlideInterval(1500);

        carouselView.setViewListener(viewListener);
        groupLinear = (LinearLayout) findViewById(R.id.groupLinear);
    }

    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.item_card_content, null);
            TextViewBold titleCard = (TextViewBold) customView.findViewById(R.id.titleCard);
            ImageView gambarCard = (ImageView) customView.findViewById(R.id.gambarCard);
            TextViewRegular deskripsiCard = (TextViewRegular) customView.findViewById(R.id.ketranganCard);
            titleCard.setText(title[position]);
            deskripsiCard.setText(deskripsi[position]);
            Glide.with(StartActivity.this)
                    .load(mThumbIds[position])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(gambarCard);
            return customView;
        }
    };

    @Override
    public void setUICallbacks() {
        groupLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chekTokenAtApp();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    public void updateUI() {

    }

    private void changeLanguage(){
        DBLanguage dbLanguage = new DBLanguage(this);
        bahasaLanguage = Locale.getDefault().getLanguage();
        languageEntity = dbLanguage.getAllLanguage();

        if (languageEntity.size() == 0){
            Language bahasa=new Language();
            bahasa.setId(1);
            bahasa.setLanguage(bahasaLanguage);
            dbLanguage.parseLanguage(bahasa);
            APP.log("Bahasa 1 : "+bahasaLanguage);
        }else{
            bahasaLanguage = languageEntity.get(0).getLanguage();
            APP.log("Bahasa 2 : "+bahasaLanguage);
        }

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        settings.edit().putString("pref_locale", bahasaLanguage).commit();

        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale   = new Locale(bahasaLanguage);
        Locale.setDefault(locale);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else{
            config.locale = locale;
        }

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
