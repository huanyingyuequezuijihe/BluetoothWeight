package com.zt.bw.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class MyDatabase_Impl extends MyDatabase {
  private volatile GoodsDao _goodsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `goods` (`id` INTEGER NOT NULL, `goodsNo` TEXT, `goodsName` TEXT, `unit` TEXT, `keyword` TEXT, `price` REAL NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"6531415577bf0691b7f88054cd27ae57\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `goods`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsGoods = new HashMap<String, TableInfo.Column>(6);
        _columnsGoods.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsGoods.put("goodsNo", new TableInfo.Column("goodsNo", "TEXT", false, 0));
        _columnsGoods.put("goodsName", new TableInfo.Column("goodsName", "TEXT", false, 0));
        _columnsGoods.put("unit", new TableInfo.Column("unit", "TEXT", false, 0));
        _columnsGoods.put("keyword", new TableInfo.Column("keyword", "TEXT", false, 0));
        _columnsGoods.put("price", new TableInfo.Column("price", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGoods = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGoods = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGoods = new TableInfo("goods", _columnsGoods, _foreignKeysGoods, _indicesGoods);
        final TableInfo _existingGoods = TableInfo.read(_db, "goods");
        if (! _infoGoods.equals(_existingGoods)) {
          throw new IllegalStateException("Migration didn't properly handle goods(com.zt.bw.bean.GoodsBean).\n"
                  + " Expected:\n" + _infoGoods + "\n"
                  + " Found:\n" + _existingGoods);
        }
      }
    }, "6531415577bf0691b7f88054cd27ae57", "8f45468efade4a37854eb6b95a46f56f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "goods");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `goods`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public GoodsDao goodsDao() {
    if (_goodsDao != null) {
      return _goodsDao;
    } else {
      synchronized(this) {
        if(_goodsDao == null) {
          _goodsDao = new GoodsDao_Impl(this);
        }
        return _goodsDao;
      }
    }
  }
}
