package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.TrainingEntity;
import com.fitness.model.TrainingModel;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBTraining {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<TrainingEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBTraining(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<TrainingEntity, ?>) dbHelper.getDao(TrainingEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parse(TrainingModel response) {
        TrainingEntity entity = this.getById(response.getId());

        if (entity == null) {
            entity = new TrainingEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setJenisTraining(StringUtil.checkNullString(response.getJenisTraining()));
        this.upsertToDatabase(entity);
    }

    private void upsertToDatabase(TrainingEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(TrainingEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(TrainingEntity artikel) {
        try {
            dao.delete(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDelete(int languageId) {
        TrainingEntity artikel = getById(languageId);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(TrainingEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TrainingEntity getById(int id) {
        List<TrainingEntity> data = new ArrayList<TrainingEntity>();
        try {
            data = dao.queryBuilder().where().eq(TrainingEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<TrainingEntity> getAll() {
        List<TrainingEntity> data = new ArrayList<TrainingEntity>();
        try {
            QueryBuilder<TrainingEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(TrainingEntity.JENIS_TRAININGG, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void delete(TrainingEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(int Id){

        List<TrainingEntity> data = new ArrayList<TrainingEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(data != null && data.size()>0){
            for(TrainingEntity entity:data) {
                if(entity.getId() == Id){
                    delete(entity);
                }
            }
        }
    }

    public void update(String language) {
        TrainingEntity entity = getById(1);
        if(entity != null){
            entity.setJenisTraining(language);
            upsertToDatabase(entity);
        }
    }
}
