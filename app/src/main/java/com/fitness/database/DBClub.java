package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.ClubEntity;
import com.fitness.model.ClubModel;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBClub {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<ClubEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBClub(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<ClubEntity, ?>) dbHelper.getDao(ClubEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parseClub(ClubModel response) {
        ClubEntity entity = this.getById(response.getId());

        if (entity == null) {
            entity = new ClubEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setNamaClub(StringUtil.checkNullString(response.getNamaClub()));
        entity.setLongitude(StringUtil.checkNullString(response.getLongitude()));
        entity.setLatitude(StringUtil.checkNullString(response.getLatitude()));
        entity.setLokasi(StringUtil.checkNullString(response.getLokasi()));

        this.upsertToDatabase(entity);
    }

    public ClubEntity getById(int id) {
        List<ClubEntity> data = new ArrayList<ClubEntity>();
        try {
            data = dao.queryBuilder().where().eq(ClubEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public ClubEntity getByIdCLub(int id) {
        List<ClubEntity> data = new ArrayList<ClubEntity>();
        try {
            data = dao.queryBuilder().where().eq(ClubEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    private void upsertToDatabase(ClubEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(ClubEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDeleteLanguage(int id) {
        ClubEntity artikel = getById(id);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(ClubEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ClubEntity getData(int id) {
        List<ClubEntity> data = new ArrayList<ClubEntity>();
        try {
            data = dao.queryBuilder().where().eq(ClubEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<ClubEntity> getAll() {
        List<ClubEntity> data = new ArrayList<ClubEntity>();
        try {
            QueryBuilder<ClubEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(ClubEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getAllData(){
        List<ClubEntity> data = new ArrayList<ClubEntity>();
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

    public void deleteData(ClubEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllReminder(int Id){

        List<ClubEntity> data = new ArrayList<ClubEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(data != null && data.size()>0){
            for(ClubEntity entity:data) {
                if(entity.getId() == Id){
                    deleteData(entity);
                }
            }
        }
    }
}
