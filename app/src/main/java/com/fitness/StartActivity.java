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
import com.fitness.activity.BaseActivity;
import com.fitness.activity.DashboardActivity;
import com.fitness.aplication.APP;
import com.fitness.aplication.DBHelper;
import com.fitness.database.DBLanguage;
import com.fitness.entities.LanguageEntity;
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
    int[] mThumbIds;
    String[] deskripsi;
    String[] title;
    LinearLayout groupLinear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isPushToMain = false;
        DBHelper helper = new DBHelper(this);
        helper.getWritableDatabase();
        helper.close();
        changeLanguage();
        runApps();
        super.onCreate(savedInstanceState);
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
        Intent intent = null;
        intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        title = getResources().getStringArray(R.array.titles);
        deskripsi = getResources().getStringArray(R.array.overviews);
        mThumbIds = new int[]{R.drawable.gambar_1, R.drawable.gambar_2, R.drawable.gambar_3, R.drawable.gambar_4, R.drawable.gambar_5, R.drawable.gambar_6, R.drawable.gambar_7, R.drawable.gambar_4};
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
            gambarCard.setImageResource(mThumbIds[position]);
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
