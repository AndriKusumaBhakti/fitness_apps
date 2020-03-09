package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.EventLogEntity;
import com.fitness.entities.EventTrainingEntity;
import com.fitness.model.EventLogModel;
import com.fitness.model.EventTrainingModel;
import com.fitness.util.Constants;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBEventLog {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<EventLogEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBEventLog(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<EventLogEntity, ?>) dbHelper.getDao(EventLogEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parse(EventLogModel response) {
        EventLogEntity entity = this.getById(response.getId());

        if (entity == null) {
            entity = new EventLogEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setIdEventTraining(response.getIdEventTraining());
        entity.setValue1(response.getValue_1());
        entity.setValue2(response.getValue_2());
        this.upsertToDatabase(entity);
    }

    private void upsertToDatabase(EventLogEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(EventLogEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(EventLogEntity artikel) {
        try {
            dao.delete(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDelete(int languageId) {
        EventLogEntity artikel = getById(languageId);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(EventLogEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventLogEntity getById(int id) {
        List<EventLogEntity> data = new ArrayList<EventLogEntity>();
        try {
            data = dao.queryBuilder().where().eq(EventLogEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<EventLogEntity> getAllById(int id) {
        List<EventLogEntity> data = new ArrayList<EventLogEntity>();
        List<EventLogEntity> data1 = new ArrayList<EventLogEntity>();
        try {
            QueryBuilder<EventLogEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(EventLogEntity.ID, true);
            data1 = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data1 != null && data1.size()>0){
            for(EventLogEntity entity:data1) {
                if(entity.getIdEventTraining() == id){
                    data.add(entity);
                }
            }
        }
        return data;
    }

    public List<EventLogEntity> getAll() {
        List<EventLogEntity> data = new ArrayList<EventLogEntity>();
        try {
            QueryBuilder<EventLogEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(EventLogEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getAllData(){
        List<EventLogEntity> data = new ArrayList<EventLogEntity>();
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

    public int getDataById(){
        List<EventLogEntity> data = new ArrayList<EventLogEntity>();
        try {
            QueryBuilder<EventLogEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(EventLogEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (data.size()>0){
            return data.get(data.size()-1).getId()+1;
        }
        return 1;
    }

    public void delete(EventLogEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(int Id){

        List<EventLogEntity> data = new ArrayList<EventLogEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data != null && data.size()>0){
            for(EventLogEntity entity:data) {
                if(entity.getId() == Id){
                    delete(entity);
                }
            }
        }
    }
}
