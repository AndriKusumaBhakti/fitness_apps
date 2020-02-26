package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "club")
public class ClubEntity extends BaseEntity implements Serializable {
    public static final String NAMA_CLUB = "NAMA_CLUB";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String LOKASI = "LOKASI";

    @DatabaseField(columnName = NAMA_CLUB)
    private String namaClub;
    @DatabaseField(columnName = LONGITUDE)
    private String longitude;
    @DatabaseField(columnName = LATITUDE)
    private String latitude;
    @DatabaseField(columnName = LOKASI)
    private String lokasi;

    public String getNamaClub() {
        return namaClub;
    }

    public void setNamaClub(String namaClub) {
        this.namaClub = namaClub;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
