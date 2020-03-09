package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.EventTrainingEntity;
import com.fitness.entities.TrainingEntity;
import com.fitness.model.EventTrainingModel;
import com.fitness.model.TrainingModel;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBEventTraining {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<EventTrainingEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBEventTraining(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<EventTrainingEntity, ?>) dbHelper.getDao(EventTrainingEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parse(EventTrainingModel response) {
        EventTrainingEntity entity = this.getById(response.getId());

        if (entity == null) {
            entity = new EventTrainingEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setIdTraining(response.getIdTraining());
        entity.setIdJenisTraining(response.getIdTraining());
        entity.setTanggalEvent(response.getDateEvent());
        this.upsertToDatabase(entity);
    }

    private void upsertToDatabase(EventTrainingEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(EventTrainingEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(EventTrainingEntity artikel) {
        try {
            dao.delete(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDelete(int languageId) {
        EventTrainingEntity artikel = getById(languageId);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(EventTrainingEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventTrainingEntity getById(int id) {
        List<EventTrainingEntity> data = new ArrayList<EventTrainingEntity>();
        try {
            data = dao.queryBuilder().where().eq(EventTrainingEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<EventTrainingEntity> getAll() {
        List<EventTrainingEntity> data = new ArrayList<EventTrainingEntity>();
        try {
            QueryBuilder<EventTrainingEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(EventTrainingEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getIdEvent() {
        List<EventTrainingEntity> data = new ArrayList<EventTrainingEntity>();
        try {
            QueryBuilder<EventTrainingEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(EventTrainingEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (data.size()>0){
            return data.get(data.size()-1).getId() + 1;
        }
        return 1;
    }

    public int getAllData(){
        List<EventTrainingEntity> data = new ArrayList<EventTrainingEntity>();
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

    public void delete(EventTrainingEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(int Id){

        List<EventTrainingEntity> data = new ArrayList<EventTrainingEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data != null && data.size()>0){
            for(EventTrainingEntity entity:data) {
                if(entity.getId() == Id){
                    delete(entity);
                }
            }
        }
    }
}
