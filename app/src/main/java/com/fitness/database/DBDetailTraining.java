package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.DetailTrainingEntity;
import com.fitness.model.DetailTrainingModel;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBDetailTraining {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<DetailTrainingEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBDetailTraining(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<DetailTrainingEntity, ?>) dbHelper.getDao(DetailTrainingEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parse(DetailTrainingModel response) {
        DetailTrainingEntity entity = this.getById(response.getId());

        if (entity == null) {
            entity = new DetailTrainingEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setIdTraining(StringUtil.checkNullString(response.getIdTraining()));
        entity.setTraining(StringUtil.checkNullString(response.getTraining()));
        this.upsertToDatabase(entity);
    }

    private void upsertToDatabase(DetailTrainingEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(DetailTrainingEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(DetailTrainingEntity artikel) {
        try {
            dao.delete(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDelete(int languageId) {
        DetailTrainingEntity artikel = getById(languageId);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(DetailTrainingEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DetailTrainingEntity getById(int id) {
        List<DetailTrainingEntity> data = new ArrayList<DetailTrainingEntity>();
        try {
            data = dao.queryBuilder().where().eq(DetailTrainingEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<DetailTrainingEntity> getByIdJenis(String id) {
        List<DetailTrainingEntity> data = new ArrayList<DetailTrainingEntity>();
        List<DetailTrainingEntity> dataAll = new ArrayList<DetailTrainingEntity>();
        try {
            dataAll = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(dataAll != null && dataAll.size()>0){
            for(DetailTrainingEntity entity:dataAll) {
                if(entity.getIdTraining().equals(id)){
                    data.add(entity);
                }
            }
        }
        return data;
    }

    public List<DetailTrainingEntity> getAll() {
        List<DetailTrainingEntity> data = new ArrayList<DetailTrainingEntity>();
        try {
            QueryBuilder<DetailTrainingEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(DetailTrainingEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void delete(DetailTrainingEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(int Id){

        List<DetailTrainingEntity> data = new ArrayList<DetailTrainingEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(data != null && data.size()>0){
            for(DetailTrainingEntity entity:data) {
                if(entity.getId() == Id){
                    delete(entity);
                }
            }
        }
    }

    public void update(String language) {
        DetailTrainingEntity entity = getById(1);
        if(entity != null){
            entity.setTraining(language);
            upsertToDatabase(entity);
        }
    }

    public int getAllData(){
        List<DetailTrainingEntity> data = new ArrayList<DetailTrainingEntity>();
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
