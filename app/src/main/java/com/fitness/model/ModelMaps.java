package com.fitness.model;

import java.io.Serializable;

public class ModelMaps implements Serializable {
    private String id;
    private String longitudeFit;
    private String latitudeFit;
    private String name;
    private String lokasi;
    private String deskripsi;
    private String namaEvent;
    private String jamStart;
    private String jamEnd;
    private String durasi;
    private String hari;
    private String image;
    private String pelatih;
    private Boolean view;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitudeFit() {
        return longitudeFit;
    }

    public void setLongitudeFit(String longitudeFit) {
        this.longitudeFit = longitudeFit;
    }

    public String getLatitudeFit() {
        return latitudeFit;
    }

    public void setLatitudeFit(String latitudeFit) {
        this.latitudeFit = latitudeFit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
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

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPelatih() {
        return pelatih;
    }

    public void setPelatih(String pelatih) {
        this.pelatih = pelatih;
    }

    public Boolean getView() {
        return view;
    }

    public void setView(Boolean view) {
        this.view = view;
    }
}
