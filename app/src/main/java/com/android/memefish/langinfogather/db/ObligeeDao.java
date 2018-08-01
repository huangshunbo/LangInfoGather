package com.android.memefish.langinfogather.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OBLIGEE".
*/
public class ObligeeDao extends AbstractDao<Obligee, Long> {

    public static final String TABLENAME = "OBLIGEE";

    /**
     * Properties of entity Obligee.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Region = new Property(1, String.class, "region", false, "REGION");
        public final static Property User = new Property(2, String.class, "user", false, "USER");
        public final static Property Num = new Property(3, String.class, "num", false, "NUM");
        public final static Property HouseNumber = new Property(4, String.class, "houseNumber", false, "HOUSE_NUMBER");
        public final static Property Status = new Property(5, String.class, "status", false, "STATUS");
        public final static Property Time = new Property(6, String.class, "time", false, "TIME");
        public final static Property Names = new Property(7, String.class, "names", false, "NAMES");
        public final static Property Propertys = new Property(8, String.class, "propertys", false, "PROPERTYS");
    }


    public ObligeeDao(DaoConfig config) {
        super(config);
    }
    
    public ObligeeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OBLIGEE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"REGION\" TEXT NOT NULL ," + // 1: region
                "\"USER\" TEXT NOT NULL ," + // 2: user
                "\"NUM\" TEXT NOT NULL UNIQUE ," + // 3: num
                "\"HOUSE_NUMBER\" TEXT NOT NULL UNIQUE ," + // 4: houseNumber
                "\"STATUS\" TEXT," + // 5: status
                "\"TIME\" TEXT," + // 6: time
                "\"NAMES\" TEXT," + // 7: names
                "\"PROPERTYS\" TEXT);"); // 8: propertys
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OBLIGEE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Obligee entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getRegion());
        stmt.bindString(3, entity.getUser());
        stmt.bindString(4, entity.getNum());
        stmt.bindString(5, entity.getHouseNumber());
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(6, status);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(7, time);
        }
 
        String names = entity.getNames();
        if (names != null) {
            stmt.bindString(8, names);
        }
 
        String propertys = entity.getPropertys();
        if (propertys != null) {
            stmt.bindString(9, propertys);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Obligee entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getRegion());
        stmt.bindString(3, entity.getUser());
        stmt.bindString(4, entity.getNum());
        stmt.bindString(5, entity.getHouseNumber());
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(6, status);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(7, time);
        }
 
        String names = entity.getNames();
        if (names != null) {
            stmt.bindString(8, names);
        }
 
        String propertys = entity.getPropertys();
        if (propertys != null) {
            stmt.bindString(9, propertys);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Obligee readEntity(Cursor cursor, int offset) {
        Obligee entity = new Obligee( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // region
            cursor.getString(offset + 2), // user
            cursor.getString(offset + 3), // num
            cursor.getString(offset + 4), // houseNumber
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // status
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // time
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // names
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // propertys
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Obligee entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRegion(cursor.getString(offset + 1));
        entity.setUser(cursor.getString(offset + 2));
        entity.setNum(cursor.getString(offset + 3));
        entity.setHouseNumber(cursor.getString(offset + 4));
        entity.setStatus(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNames(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPropertys(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Obligee entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Obligee entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Obligee entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
