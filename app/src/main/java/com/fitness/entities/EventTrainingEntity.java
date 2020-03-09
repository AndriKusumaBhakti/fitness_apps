package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "eventtraining")
public class EventTrainingEntity extends BaseEntity implements Serializable {
    public static final String ID_TRAINING = "ID_TRAINING";
    public static final String ID_JENIS_TRAINING = "ID_JENIS_TRAINING";
    public static final String TANGGAL_EVENT = "TANGGAL_EVENT";

    @DatabaseField(columnName = ID_TRAINING)
    private int idTraining;
    @DatabaseField(columnName = ID_JENIS_TRAINING)
    private int idJenisTraining;
    @DatabaseField(columnName = TANGGAL_EVENT)
    private String tanggalEvent;

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

    public String getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(String tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }
}
