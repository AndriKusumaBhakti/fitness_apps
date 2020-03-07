package com.fitness.model;

import java.util.ArrayList;

public class ModelNewMaps {
    private int id;
    private String namaEvent;
    private ArrayList<ModelMaps> dataMaps;
    private boolean view;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public ArrayList<ModelMaps> getDataMaps() {
        return dataMaps;
    }

    public void setDataMaps(ArrayList<ModelMaps> dataMaps) {
        this.dataMaps = dataMaps;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }
}
