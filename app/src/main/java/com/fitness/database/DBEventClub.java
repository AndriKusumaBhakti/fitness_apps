package com.fitness.database;

import android.content.Context;

import com.fitness.aplication.DBHelper;
import com.fitness.entities.EventClubEntity;
import com.fitness.model.EventClubModel;
import com.fitness.util.Constants;
import com.fitness.util.StringUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBEventClub {
    private Context ctx;
    String tag = null;
    private static DBHelper dbHelper;
    protected Dao<EventClubEntity, ?> dao;

    SimpleDateFormat sdfTimeZone = new SimpleDateFormat(Constants.FORMAT_ISO);

    public DBEventClub(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<EventClubEntity, ?>) dbHelper.getDao(EventClubEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void parseClub(EventClubModel response) {
        EventClubEntity entity = this.getById(response.getId());

        if (entity == null) {
            entity = new EventClubEntity();
            entity.setId(response.getId());
        }

        entity.setIsDeleted(false);
        entity.setIdClass(response.getIdClass());
        entity.setIdClub(response.getIdClub());
        entity.setHari(StringUtil.checkNullString(response.getHari()));
        entity.setDurasi(StringUtil.checkNullString(response.getDurasi()));
        entity.setJamStart(StringUtil.checkNullString(response.getJamStart()));
        entity.setJamEnd(StringUtil.checkNullString(response.getJamEnd()));
        entity.setPelatih(StringUtil.checkNullString(response.getPelatih()));
        entity.setUnggulan(StringUtil.checkNullString(response.getUnggulan()));

        this.upsertToDatabase(entity);
    }

    public EventClubEntity getById(int id) {
        List<EventClubEntity> data = new ArrayList<EventClubEntity>();
        try {
            data = dao.queryBuilder().where().eq(EventClubEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    private void upsertToDatabase(EventClubEntity artikel) {
        try {
            dao.createOrUpdate(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(EventClubEntity artikel) {
        try {
            dao.createIfNotExists(artikel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void safeDelete(int id) {
        EventClubEntity artikel = getById(id);
        artikel.setIsDeleted(true);
        upsertToDatabase(artikel);
    }

    public void softDelete(EventClubEntity artikel) {
        try {
            artikel.setIsDeleted(true);
            dao.update(artikel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventClubEntity getData(int id) {
        List<EventClubEntity> data = new ArrayList<EventClubEntity>();
        try {
            data = dao.queryBuilder().where().eq(EventClubEntity.ID, id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<EventClubEntity> getAll() {
        List<EventClubEntity> data = new ArrayList<EventClubEntity>();
        try {
            QueryBuilder<EventClubEntity, ?> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(EventClubEntity.ID, true);
            data = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getAllData(){
        List<EventClubEntity> data = new ArrayList<EventClubEntity>();
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

    public List<EventClubEntity> getAllDay(String day) {
        List<EventClubEntity> data = new ArrayList<EventClubEntity>();
        List<EventClubEntity> dataAll = new ArrayList<EventClubEntity>();
        try {
            dataAll = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(dataAll != null && dataAll.size()>0){
            for(EventClubEntity entity:dataAll) {
                if(entity.getHari().equals(day)){
                    data.add(entity);
                }
            }
        }
        return data;
    }

    public List<EventClubEntity> getAllByUnggulan(int unggulan) {
        List<EventClubEntity> data = new ArrayList<EventClubEntity>();
        List<EventClubEntity> dataAll = new ArrayList<EventClubEntity>();
        try {
            dataAll = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(dataAll != null && dataAll.size()>0){
            for(EventClubEntity entity:dataAll) {
                if(entity.getIdClass() == unggulan){
                    data.add(entity);
                }
            }
        }
        return data;
    }

    public void deleteData(EventClubEntity entity){
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllReminder(int Id){

        List<EventClubEntity> data = new ArrayList<EventClubEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(data != null && data.size()>0){
            for(EventClubEntity entity:data) {
                if(entity.getId() == Id){
                    deleteData(entity);
                }
            }
        }
    }
}
