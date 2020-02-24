package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "language")
public class LanguageEntity extends BaseEntity implements Serializable {
    public static final String LANGUAGE = "language";

    @DatabaseField(columnName = LANGUAGE)
    private String Language;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }
}
