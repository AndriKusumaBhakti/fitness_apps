package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.ClassEntity;
import com.fitness.model.ClassModel;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBClass {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<ClassEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBClass(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<ClassEntity, ?>) dbHelper.getDao(ClassEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parseClub(ClassModel response) {
        ClassEntity entity = this.getById(response.getId());

        if (entity == null) {
            entity = new ClassEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setNamaClass(StringUtil.checkNullString(response.getNamaClass()));
        entity.setDeskripsi(StringUtil.checkNullString(response.getDeskripsi()));
        entity.setImage(StringUtil.checkNullString(response.getImage()));

        this.upsertToDatabase(entity);
    }

    public ClassEntity getById(int id) {
        List<ClassEntity> data = new ArrayList<ClassEntity>();
        try {
            data = dao.queryBuilder().where().eq(ClassEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    private void upsertToDatabase(ClassEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(ClassEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDeleteLanguage(int id) {
        ClassEntity artikel = getById(id);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(ClassEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ClassEntity getData(int id) {
        List<ClassEntity> data = new ArrayList<ClassEntity>();
        try {
            data = dao.queryBuilder().where().eq(ClassEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<ClassEntity> getAll() {
        List<ClassEntity> data = new ArrayList<ClassEntity>();
        try {
            QueryBuilder<ClassEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(ClassEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void deleteData(ClassEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllReminder(int Id){

        List<ClassEntity> data = new ArrayList<ClassEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(data != null && data.size()>0){
            for(ClassEntity entity:data) {
                if(entity.getId() == Id){
                    deleteData(entity);
                }
            }
        }
    }

    public int getAllData(){
        List<ClassEntity> data = new ArrayList<ClassEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (data.size()>0){
            return data.size();
        }
        return 0;
    }
}
