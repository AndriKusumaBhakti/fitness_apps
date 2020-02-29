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
    String[][] cabang = new String[][]{{"AEON Mall BSD Cycling", "106.6277385", "-6.2164932", "AON Mall BSD City Lt. GF, Jalan BSD Raya Utama, Pagedangan, Kec. Pagedangan, Tangerang, Banten 15345"},
            {"AEON Mall BSD Main", "106.6291854", "-6.2164932", "Jl. BSD Raya Utama, Pagedangan, Kec. Pagedangan, Tangerang, Banten 15345"},
            {"AEON Mall BSD Playground Area", "106.6291854", "-6.2164932", "Jl. BSD Raya Utama, Pagedangan, Kec Pagedangan, Tangerang, Banten 15345"}};
    String[][] namaClass = new String[][]{{"PELOTON", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"GROUP SUSPENSION TRAINING",  "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"HATHA YOGA", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"PILATES", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"CORE EX", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"BODY COMBAT", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"RACE 30", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"RPM", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"BODY PUMP", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"FLEXIBILITY", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"PLAYGROUND", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"FASTFIT", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"DNA CORE MOTION", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"ZUMBA", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"DNA INSANITY",  "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"FLOATING YOGA", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.","https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"DNA RAW", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"DNA SEDUCE", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"VINYASA YOGA", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"DNA FLAVOR", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"BASIC YOGA", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"GENTLE YOGA", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"},
                    {"BODY BALANCE", "Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.", "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634530/fitness/coreex_classes_xtbp7u.png"}};

    String[][] eventClass = new String[][]{{"1", "1", "1", "50mins", "08:10", "09:00", "SULIES", "false"}, {"2", "2", "1", "50mins", "08:10", "09:00", "HOLLY", "false"},
            {"2", "9", "1", "50mins", "09:10", "10:00", "HOLLY", "false"}, {"3", "11", "1", "30mins", "10:10", "10:40", "AZIS", "true"},
            {"2", "14", "1", "50mins", "10:10", "11:00", "SULIES", "false"}, {"2", "2", "1", "50mins", "17:00", "17:50", "NIRMALA", "false"},
            {"2", "14", "1", "60mins", "18:00", "19:00", "NIRMALA", "false"}, {"1", "8", "1", "50mins", "18:10", "19:00", "AVEL", "true"},
            {"2", "6", "1", "60mins", "19:10", "20:10", "AVEL", "false"}, {"3", "11", "1", "30mins", "19:10", "19:40", "FARHAN", "false"},
            {"2", "3", "1", "50mins", "20:20", "21:10", "FARID", "false"},
                    {"2", "2", "2", "50mins", "08:10", "09:00", "DEWA", "true"}, {"1", "7", "2", "30mins", "08:10", "08:40", "SONNY", "false"},
                    {"2", "10", "2", "50mins", "09:10", "10:00", "SONNY", "false"}, {"2", "10", "2", "50mins", "10:10", "11:00", "AVEL", "false"},
                    {"3", "11", "2", "60mins", "10:10", "11:10", "LIANI", "false"}, {"2", "12", "2", "50mins", "11:10", "12:00", "AVEL", "true"},
                    {"2", "20", "2", "50mins", "17:00", "17:50", "BONNY", "true"}, {"2", "13", "2", "50mins", "18:00", "18:50", "BONNY", "false"},
                    {"3", "11", "2", "30mins", "18:10", "18:40", "DIAN W", "false"}, {"1", "8", "2", "50mins", "18:10", "19:00", "DEWA", "false"},
                    {"2", "6", "2", "60mins", "19:10", "20:10", "HERI", "false"}, {"2", "23", "2", "50mins", "20:20", "21:10", "HERI", "false"},
                    {"2", "3", "3", "50mins", "08:10", "09:00", "DWI HS", "false"}, {"1", "8", "3", "50mins", "08:10", "09:00", "ALDO", "false"},
                    {"2", "6", "3", "50mins", "09:10", "10:00", "ALDO", "false"}, {"3", "11", "3", "30mins", "10:10", "10:40", "RONAL", "true"},
                    {"2", "15", "3", "50mins", "10:10", "11:00", "HERIS", "true"}, {"2", "17", "3", "30mins", "11:10", "12:00", "HERIS", "false"},
                    {"2", "21", "3", "50mins", "17:00", "17:50", "AFINI", "false"}, {"2", "6", "3", "30mins", "18:00", "18:50", "ALDO", "false"},
                    {"3", "16", "3", "30mins", "19:10", "19:40", "FARHAN", "false"}, {"2", "9", "3", "60mins", "19:10", "20:10", "MICHAEL A", "true"},
                    {"1", "7", "3", "30mins", "19:10", "19:40", "ALDO", "false"}, {"2", "3", "3", "60mins", "20:20", "21:20", "AFINI", "false"},
                    {"2", "4", "4", "50mins", "08:10", "09:00", "SONNY", "true"}, {"1", "1", "4", "50mins", "09:00", "09:50", "ANDI S", "false"},
                    {"2", "9", "4", "50mins", "09:10", "10:00", "RICO", "false"}, {"2", "6", "4", "50mins", "10:10", "11:00", "RICO", "false"},
                    {"3", "11", "4", "50mins", "10:10", "10:40", "AZIS", "false"}, {"2", "3", "4", "50mins", "17:00", "17:50", "DWI HS", "false"},
                    {"2", "14", "4", "50mins", "18:10", "19:00", "REVA", "false"}, {"1", "7", "4", "30mins", "18:10", "18:40", "ERRY", "true"},
                    {"2", "6", "4", "60mins", "19:10", "20:10", "ERRY", "false"}, {"2", "16", "4", "50mins", "20:20", "21:10", "FARID", "false"},
                    {"2", "5", "5", "50mins", "08:10", "09:00", "SONNY", "true"}, {"1", "7", "5", "30mins", "09:10", "09:40", "SONNY", "false"},
                    {"2", "2", "5", "50mins", "09:10", "10:00", "ANDI S", "false"}, {"2", "12", "5", "50mins", "10:10", "11:00", "ANDI S", "false"},
                    {"2", "16", "5", "50mins", "11:00", "11:50", "DELLA", "false"}, {"2", "22", "5", "50mins", "17:00", "17:50", "SONNY", "true"},
                    {"2", "9", "5", "50mins", "18:10", "19:00", "DAVID H", "false"}, {"1", "8", "5", "50mins", "19:10", "20:00", "DAVID H", "false"},
                    {"3", "11", "5", "30mins", "19:10", "19:40", "RONAL", "false"}, {"2", "20", "5", "50mins", "19:10", "20:00", "TOMMY D", "false"},
                    {"2", "6", "5", "60mins", "20:20", "21:20", "TOMMY D", "true"},
                    {"2", "1", "6", "50mins", "08:10", "09:00", "AVEL", "false"}, {"1", "1", "6", "50mins", "09:10", "10:00", "MICHAEL A", "false"},
                    {"2", "6", "6", "50mins", "10:10", "11:00", "AVEL", "false"}, {"2", "13", "6", "50mins", "11:10", "12:00", "BONNY", "false"},
                    {"2", "18", "6", "50mins", "12:10", "13:00", "BONNY", "true"},
                    {"2", "6", "7", "50mins", "10:10", "11:00", "HERI", "false"}, {"1", "1", "7", "50mins", "10:10", "11:00", "ARI FRASTA", "false"},
                    {"2", "9", "7", "50mins", "11:10", "12:00", "ARI FRASTA", "false"}, {"2", "3", "7", "50mins", "12:10", "13:00", "NANDO(FERNANDO)", "false"},
                    {"2", "19", "7", "50mins", "13:10", "14:00", "NANDO(FERNANDO)", "true"}};

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
        mThumbIds = new String[]{"https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634488/fitness/gambar_1_fbc1hp.png",
                "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634509/fitness/gambar_2_dqvvd1.png",
                "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634509/fitness/gambar_3_n5zyk7.png",
                "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634510/fitness/gambar_4_fvvmfy.png",
                "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634510/fitness/gambar_5_qaetda.png",
                "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634510/fitness/gambar_6_rvomy0.png",
                "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634510/fitness/gambar_7_npd75b.png",
                "https://res.cloudinary.com/andrikusumabhakti/image/upload/v1582634510/fitness/gambar_4_fvvmfy.png"};
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
//            gambarCard.setImageResource(mThumbIds[position]);
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
