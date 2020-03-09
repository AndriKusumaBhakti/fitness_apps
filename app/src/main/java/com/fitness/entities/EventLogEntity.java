package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "eventlog")
public class EventLogEntity extends BaseEntity implements Serializable {
    public static final String VALUE_1 = "VALUE_1";
    public static final String VALUE_2 = "VALUE_2";
    public static final String ID_EVENT_TRAINING = "ID_EVENT_TRAINING";

    @DatabaseField(columnName = VALUE_1)
    private String value1;
    @DatabaseField(columnName = VALUE_2)
    private String value2;
    @DatabaseField(columnName = ID_EVENT_TRAINING)
    private int idEventTraining;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public int getIdEventTraining() {
        return idEventTraining;
    }

    public void setIdEventTraining(int idEventTraining) {
        this.idEventTraining = idEventTraining;
    }
}
