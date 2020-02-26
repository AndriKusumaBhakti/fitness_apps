package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "eventclub")
public class EventClubEntity extends BaseEntity implements Serializable {
    public static final String ID_CLASS = "ID_CLASS";
    public static final String HARI = "HARI";
    public static final String DURASI = "DURASI";
    public static final String JAM_START = "JAM_START";
    public static final String JAM_END = "JAM_END";
    public static final String PELATIH = "PELATIH";
    public static final String ID_CLUB = "ID_CLUB";

    @DatabaseField(columnName = ID_CLASS)
    private int idClass;
    @DatabaseField(columnName = ID_CLUB)
    private int idClub;
    @DatabaseField(columnName = HARI)
    private String hari;
    @DatabaseField(columnName = DURASI)
    private String durasi;
    @DatabaseField(columnName = JAM_START)
    private String jamStart;
    @DatabaseField(columnName = JAM_END)
    private String jamEnd;
    @DatabaseField(columnName = PELATIH)
    private String pelatih;

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getJamStart() {
        return jamStart;
    }

    public void setJamStart(String jamStart) {
        this.jamStart = jamStart;
    }

    public String getJamEnd() {
        return jamEnd;
    }

    public void setJamEnd(String jamEnd) {
        this.jamEnd = jamEnd;
    }

    public String getPelatih() {
        return pelatih;
    }

    public void setPelatih(String pelatih) {
        this.pelatih = pelatih;
    }
}
