package com.fitness.aplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fitness.database.BaseEntity;
import com.fitness.entities.ClassEntity;
import com.fitness.entities.ClubEntity;
import com.fitness.entities.EventClubEntity;
import com.fitness.entities.EventEntity;
import com.fitness.entities.LanguageEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 20;
    private Context context;

    public DBHelper(Context context) {
        super(context, Config.DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        resetDatabase(oldVersion);
        createTable();

    }

    private void createTable() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, BaseEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, LanguageEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, EventEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, EventClubEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ClubEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ClassEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetDatabase(int oldVersion) {
        try {
            if(oldVersion<1){
                TableUtils.dropTable(connectionSource, BaseEntity.class, true);
                APP.removePreference(context, Preference.TOKEN);
            }

            TableUtils.dropTable(connectionSource, LanguageEntity.class, true);
            TableUtils.dropTable(connectionSource, EventEntity.class, true);
            TableUtils.dropTable(connectionSource, EventClubEntity.class, true);
            TableUtils.dropTable(connectionSource, ClubEntity.class, true);
            TableUtils.dropTable(connectionSource, ClassEntity.class, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getDatabaseVersion(){
        return DATABASE_VERSION;
    }

    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz)
            throws SQLException {
        return super.getDao(clazz);
    }

    @Override
    public void close() {
        super.close();
    }

    public void resetDatabase() {
        try {
            TableUtils.dropTable(connectionSource, BaseEntity.class, true);
            TableUtils.dropTable(connectionSource, LanguageEntity.class, true);
            TableUtils.dropTable(connectionSource, EventEntity.class, true);
            TableUtils.dropTable(connectionSource, EventClubEntity.class, true);
            TableUtils.dropTable(connectionSource, ClubEntity.class, true);
            TableUtils.dropTable(connectionSource, ClassEntity.class, true);

            APP.removePreference(context, Preference.TOKEN);
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
