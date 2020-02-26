package com.fitness.model;

public class EventClubModel {
    private int id;
    private int idClass;
    private int idClub;
    private String hari;
    private String durasi;
    private String jamStart;
    private String jamEnd;
    private String pelatih;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
