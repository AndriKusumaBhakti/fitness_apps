package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "event")
public class EventEntity extends BaseEntity implements Serializable {
    public static final String NAMA_CLASS = "NAMA_CLASS";
    public static final String TANGGAL_EVENT = "TANGGAL_EVENT";
    public static final String JAM_EVENT = "JAM_EVENT";

    @DatabaseField(columnName = NAMA_CLASS)
    private String namaClass;
    @DatabaseField(columnName = TANGGAL_EVENT)
    private String tanggalEvent;
    @DatabaseField(columnName = JAM_EVENT)
    private String jamEvent;

    public String getNamaClass() {
        return namaClass;
    }

    public void setNamaClass(String namaClass) {
        this.namaClass = namaClass;
    }

    public String getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(String tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }

    public String getJamEvent() {
        return jamEvent;
    }

    public void setJamEvent(String jamEvent) {
        this.jamEvent = jamEvent;
    }
}
