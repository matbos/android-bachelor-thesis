package pl.mbos.bachelor_thesis.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import pl.mbos.bachelor_thesis.dao.UserDao;
import pl.mbos.bachelor_thesis.dao.AttentionDao;
import pl.mbos.bachelor_thesis.dao.MeditationDao;
import pl.mbos.bachelor_thesis.dao.PoorSignalDao;
import pl.mbos.bachelor_thesis.dao.BlinkDao;
import pl.mbos.bachelor_thesis.dao.PowerEEGDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 3): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 3;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        UserDao.createTable(db, ifNotExists);
        AttentionDao.createTable(db, ifNotExists);
        MeditationDao.createTable(db, ifNotExists);
        PoorSignalDao.createTable(db, ifNotExists);
        BlinkDao.createTable(db, ifNotExists);
        PowerEEGDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        UserDao.dropTable(db, ifExists);
        AttentionDao.dropTable(db, ifExists);
        MeditationDao.dropTable(db, ifExists);
        PoorSignalDao.dropTable(db, ifExists);
        BlinkDao.dropTable(db, ifExists);
        PowerEEGDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(UserDao.class);
        registerDaoClass(AttentionDao.class);
        registerDaoClass(MeditationDao.class);
        registerDaoClass(PoorSignalDao.class);
        registerDaoClass(BlinkDao.class);
        registerDaoClass(PowerEEGDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
