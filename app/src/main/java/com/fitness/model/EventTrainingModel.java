package com.fitness.model;

public class EventTrainingModel {
    private int id;
    private int idTraining;
    private int idJenisTraining;
    private String dateEvent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }

    public int getIdJenisTraining() {
        return idJenisTraining;
    }

    public void setIdJenisTraining(int idJenisTraining) {
        this.idJenisTraining = idJenisTraining;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }
}
