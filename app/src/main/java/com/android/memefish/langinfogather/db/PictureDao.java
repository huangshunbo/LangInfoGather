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
 * DAO for table "PICTURE".
*/
public class PictureDao extends AbstractDao<Picture, Long> {

    public static final String TABLENAME = "PICTURE";

    /**
     * Properties of entity Picture.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property User = new Property(1, String.class, "user", false, "USER");
        public final static Property Region = new Property(2, String.class, "region", false, "REGION");
        public final static Property Obligee = new Property(3, String.class, "obligee", false, "OBLIGEE");
        public final static Property OneLevel = new Property(4, String.class, "oneLevel", false, "ONE_LEVEL");
        public final static Property TwoLevel = new Property(5, String.class, "twoLevel", false, "TWO_LEVEL");
        public final static Property ThreeLevel = new Property(6, String.class, "threeLevel", false, "THREE_LEVEL");
        public final static Property Path = new Property(7, String.class, "path", false, "PATH");
        public final static Property Name = new Property(8, String.class, "name", false, "NAME");
    }


    public PictureDao(DaoConfig config) {
        super(config);
    }
    
    public PictureDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PICTURE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER\" TEXT NOT NULL ," + // 1: user
                "\"REGION\" TEXT NOT NULL ," + // 2: region
                "\"OBLIGEE\" TEXT NOT NULL ," + // 3: obligee
                "\"ONE_LEVEL\" TEXT NOT NULL ," + // 4: oneLevel
                "\"TWO_LEVEL\" TEXT," + // 5: twoLevel
                "\"THREE_LEVEL\" TEXT," + // 6: threeLevel
                "\"PATH\" TEXT NOT NULL UNIQUE ," + // 7: path
                "\"NAME\" TEXT NOT NULL );"); // 8: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PICTURE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Picture entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUser());
        stmt.bindString(3, entity.getRegion());
        stmt.bindString(4, entity.getObligee());
        stmt.bindString(5, entity.getOneLevel());
 
        String twoLevel = entity.getTwoLevel();
        if (twoLevel != null) {
            stmt.bindString(6, twoLevel);
        }
 
        String threeLevel = entity.getThreeLevel();
        if (threeLevel != null) {
            stmt.bindString(7, threeLevel);
        }
        stmt.bindString(8, entity.getPath());
        stmt.bindString(9, entity.getName());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Picture entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUser());
        stmt.bindString(3, entity.getRegion());
        stmt.bindString(4, entity.getObligee());
        stmt.bindString(5, entity.getOneLevel());
 
        String twoLevel = entity.getTwoLevel();
        if (twoLevel != null) {
            stmt.bindString(6, twoLevel);
        }
 
        String threeLevel = entity.getThreeLevel();
        if (threeLevel != null) {
            stmt.bindString(7, threeLevel);
        }
        stmt.bindString(8, entity.getPath());
        stmt.bindString(9, entity.getName());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Picture readEntity(Cursor cursor, int offset) {
        Picture entity = new Picture( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // user
            cursor.getString(offset + 2), // region
            cursor.getString(offset + 3), // obligee
            cursor.getString(offset + 4), // oneLevel
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // twoLevel
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // threeLevel
            cursor.getString(offset + 7), // path
            cursor.getString(offset + 8) // name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Picture entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUser(cursor.getString(offset + 1));
        entity.setRegion(cursor.getString(offset + 2));
        entity.setObligee(cursor.getString(offset + 3));
        entity.setOneLevel(cursor.getString(offset + 4));
        entity.setTwoLevel(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setThreeLevel(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPath(cursor.getString(offset + 7));
        entity.setName(cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Picture entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Picture entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Picture entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
