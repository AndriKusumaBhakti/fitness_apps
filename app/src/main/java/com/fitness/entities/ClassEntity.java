package com.fitness.entities;

import com.fitness.database.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "class")
public class ClassEntity extends BaseEntity implements Serializable {
    public static final String NAMA_CLASS = "NAMA_CLASS";
    public static final String DESKRIPSI = "DESKRIPSI";
    public static final String IMAGE = "IMAGE";
    public static final String UNGGULAN = "UNGGULAN";

    @DatabaseField(columnName = NAMA_CLASS)
    private String namaClass;
    @DatabaseField(columnName = DESKRIPSI)
    private String deskripsi;
    @DatabaseField(columnName = IMAGE)
    private String image;
    @DatabaseField(columnName = UNGGULAN)
    private String unggulan;

    public String getNamaClass() {
        return namaClass;
    }

    public void setNamaClass(String namaClass) {
        this.namaClass = namaClass;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnggulan() {
        return unggulan;
    }

    public void setUnggulan(String unggulan) {
        this.unggulan = unggulan;
    }
}
