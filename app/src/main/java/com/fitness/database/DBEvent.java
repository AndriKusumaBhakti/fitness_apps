package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.EventEntity;
import com.fitness.model.EventModel;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBEvent {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<EventEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBEvent(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<EventEntity, ?>) dbHelper.getDao(EventEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parseEvent(EventModel response) {
        EventEntity entity = this.getLanguageById(response.getId());

        if (entity == null) {
            entity = new EventEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setNamaClass(StringUtil.checkNullString(response.getNamaClass()));
        entity.setJamEvent(StringUtil.checkNullString(response.getJamEvent()));
        entity.setTanggalEvent(StringUtil.checkNullString(response.getDateEvent()));

        this.upsertToDatabase(entity);
    }

    public EventEntity getLanguageById(int id) {
        List<EventEntity> data = new ArrayList<EventEntity>();
        try {
            data = dao.queryBuilder().where().eq(EventEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    private void upsertToDatabase(EventEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(EventEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLanguageData(EventEntity artikel) {
        try {
            dao.delete(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDeleteLanguage(int languageId) {
        EventEntity artikel = getLanguageById(languageId);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(EventEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventEntity EventEntity(int id) {
        List<EventEntity> data = new ArrayList<EventEntity>();
        try {
            data = dao.queryBuilder().where().eq(EventEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<EventEntity> getAllLanguage() {
        List<EventEntity> data = new ArrayList<EventEntity>();
        try {
            QueryBuilder<EventEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(EventEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void deleteLanguage(EventEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllReminder(int Id){

        List<EventEntity> data = new ArrayList<EventEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(data != null && data.size()>0){
            for(EventEntity entity:data) {
                if(entity.getId() == Id){
                    deleteLanguage(entity);
                }
            }
        }
    }
}
