package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "detailtraining")
public class DetailTrainingEntity extends BaseEntity implements Serializable {
    public static final String TRAINING = "TRAINING";
    public static final String ID_TRAINING = "ID_TRAINING";

    @DatabaseField(columnName = TRAINING)
    private String training;
    @DatabaseField(columnName = ID_TRAINING)
    private String idTraining;

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(String idTraining) {
        this.idTraining = idTraining;
    }
}
