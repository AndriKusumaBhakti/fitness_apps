package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.LanguageEntity;
import com.fitness.model.Language;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBLanguage {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<LanguageEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBLanguage(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<LanguageEntity, ?>) dbHelper.getDao(LanguageEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parseLanguage(Language response) {
        LanguageEntity entity = this.getLanguageById(response.getId());

        if (entity == null) {
            entity = new LanguageEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setLanguage(StringUtil.checkNullString(response.getLanguage()));
        this.upsertToDatabase(entity);
    }

    private void upsertToDatabase(LanguageEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(LanguageEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLanguageData(LanguageEntity artikel) {
        try {
            dao.delete(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDeleteLanguage(int languageId) {
        LanguageEntity artikel = getLanguageById(languageId);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(LanguageEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LanguageEntity getLanguageById(int id) {
        List<LanguageEntity> data = new ArrayList<LanguageEntity>();
        try {
            data = dao.queryBuilder().where().eq(LanguageEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<LanguageEntity> getAllLanguage() {
        List<LanguageEntity> data = new ArrayList<LanguageEntity>();
        try {
            QueryBuilder<LanguageEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(LanguageEntity.LANGUAGE, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void deleteLanguage(LanguageEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllReminder(int Id){

        List<LanguageEntity> data = new ArrayList<LanguageEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(data != null && data.size()>0){
            for(LanguageEntity entity:data) {
                if(entity.getId() == Id){
                    deleteLanguage(entity);
                }
            }
        }
    }
}
