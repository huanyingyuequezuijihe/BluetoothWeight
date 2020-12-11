package com.zt.bw.database;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.zt.bw.bean.GoodsBean;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class GoodsDao_Impl implements GoodsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGoodsBean;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public GoodsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGoodsBean = new EntityInsertionAdapter<GoodsBean>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `goods`(`id`,`goodsNo`,`goodsName`,`unit`,`keyword`,`price`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GoodsBean value) {
        stmt.bindLong(1, value.id);
        if (value.goodsNo == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.goodsNo);
        }
        if (value.goodsName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.goodsName);
        }
        if (value.unit == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.unit);
        }
        if (value.keyword == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.keyword);
        }
        stmt.bindDouble(6, value.price);
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM goods";
        return _query;
      }
    };
  }

  @Override
  public long insert(GoodsBean bean) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfGoodsBean.insertAndReturnId(bean);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int clear() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfClear.release(_stmt);
    }
  }

  @Override
  public int getCount() {
    final String _sql = "SELECT count(*) FROM goods";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public GoodsBean findGoodsById(int id) {
    final String _sql = "SELECT * FROM goods where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfGoodsNo = _cursor.getColumnIndexOrThrow("goodsNo");
      final int _cursorIndexOfGoodsName = _cursor.getColumnIndexOrThrow("goodsName");
      final int _cursorIndexOfUnit = _cursor.getColumnIndexOrThrow("unit");
      final int _cursorIndexOfKeyword = _cursor.getColumnIndexOrThrow("keyword");
      final int _cursorIndexOfPrice = _cursor.getColumnIndexOrThrow("price");
      final GoodsBean _result;
      if(_cursor.moveToFirst()) {
        _result = new GoodsBean();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.goodsNo = _cursor.getString(_cursorIndexOfGoodsNo);
        _result.goodsName = _cursor.getString(_cursorIndexOfGoodsName);
        _result.unit = _cursor.getString(_cursorIndexOfUnit);
        _result.keyword = _cursor.getString(_cursorIndexOfKeyword);
        _result.price = _cursor.getDouble(_cursorIndexOfPrice);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<GoodsBean> getAllDoods() {
    final String _sql = "SELECT * FROM goods";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfGoodsNo = _cursor.getColumnIndexOrThrow("goodsNo");
      final int _cursorIndexOfGoodsName = _cursor.getColumnIndexOrThrow("goodsName");
      final int _cursorIndexOfUnit = _cursor.getColumnIndexOrThrow("unit");
      final int _cursorIndexOfKeyword = _cursor.getColumnIndexOrThrow("keyword");
      final int _cursorIndexOfPrice = _cursor.getColumnIndexOrThrow("price");
      final List<GoodsBean> _result = new ArrayList<GoodsBean>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final GoodsBean _item;
        _item = new GoodsBean();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.goodsNo = _cursor.getString(_cursorIndexOfGoodsNo);
        _item.goodsName = _cursor.getString(_cursorIndexOfGoodsName);
        _item.unit = _cursor.getString(_cursorIndexOfUnit);
        _item.keyword = _cursor.getString(_cursorIndexOfKeyword);
        _item.price = _cursor.getDouble(_cursorIndexOfPrice);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
