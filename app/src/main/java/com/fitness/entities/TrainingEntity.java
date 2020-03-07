package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "training")
public class TrainingEntity extends BaseEntity implements Serializable {
    public static final String JENIS_TRAININGG = "JENIS_TRAININGG";

    @DatabaseField(columnName = JENIS_TRAININGG)
    private String jenisTraining;

    public String getJenisTraining() {
        return jenisTraining;
    }

    public void setJenisTraining(String jenisTraining) {
        this.jenisTraining = jenisTraining;
    }
}
