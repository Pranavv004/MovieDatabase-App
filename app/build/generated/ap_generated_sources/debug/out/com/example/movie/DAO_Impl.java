package com.example.movie;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DAO_Impl implements DAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DataClass> __insertionAdapterOfDataClass;

  private final EntityInsertionAdapter<DataClass> __insertionAdapterOfDataClass_1;

  private final EntityDeletionOrUpdateAdapter<DataClass> __deletionAdapterOfDataClass;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDataById;

  public DAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDataClass = new EntityInsertionAdapter<DataClass>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `data` (`key`,`url`,`herourl`,`title`,`description`,`score`,`added`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DataClass value) {
        stmt.bindLong(1, value.key);
        if (value.url == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.url);
        }
        if (value.herourl == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.herourl);
        }
        if (value.title == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.title);
        }
        if (value.description == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.description);
        }
        if (value.score == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.score);
        }
        final int _tmp = value.added ? 1 : 0;
        stmt.bindLong(7, _tmp);
      }
    };
    this.__insertionAdapterOfDataClass_1 = new EntityInsertionAdapter<DataClass>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `data` (`key`,`url`,`herourl`,`title`,`description`,`score`,`added`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DataClass value) {
        stmt.bindLong(1, value.key);
        if (value.url == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.url);
        }
        if (value.herourl == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.herourl);
        }
        if (value.title == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.title);
        }
        if (value.description == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.description);
        }
        if (value.score == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.score);
        }
        final int _tmp = value.added ? 1 : 0;
        stmt.bindLong(7, _tmp);
      }
    };
    this.__deletionAdapterOfDataClass = new EntityDeletionOrUpdateAdapter<DataClass>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `data` WHERE `key` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DataClass value) {
        stmt.bindLong(1, value.key);
      }
    };
    this.__preparedStmtOfDeleteDataById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM data WHERE key = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final DataClass... dataClasses) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDataClass.insert(dataClasses);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertOrUpdate(final DataClass dataClass) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDataClass_1.insert(dataClass);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final DataClass dataClass) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfDataClass.handle(dataClass);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteDataById(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDataById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteDataById.release(_stmt);
    }
  }

  @Override
  public List<DataClass> getAll() {
    final String _sql = "SELECT * FROM data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfKey = CursorUtil.getColumnIndexOrThrow(_cursor, "key");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfHerourl = CursorUtil.getColumnIndexOrThrow(_cursor, "herourl");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
      final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
      final List<DataClass> _result = new ArrayList<DataClass>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DataClass _item;
        final int _tmpKey;
        _tmpKey = _cursor.getInt(_cursorIndexOfKey);
        final String _tmpUrl;
        if (_cursor.isNull(_cursorIndexOfUrl)) {
          _tmpUrl = null;
        } else {
          _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        }
        final String _tmpHerourl;
        if (_cursor.isNull(_cursorIndexOfHerourl)) {
          _tmpHerourl = null;
        } else {
          _tmpHerourl = _cursor.getString(_cursorIndexOfHerourl);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmpScore;
        if (_cursor.isNull(_cursorIndexOfScore)) {
          _tmpScore = null;
        } else {
          _tmpScore = _cursor.getString(_cursorIndexOfScore);
        }
        final boolean _tmpAdded;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAdded);
        _tmpAdded = _tmp != 0;
        _item = new DataClass(_tmpKey,_tmpUrl,_tmpHerourl,_tmpTitle,_tmpScore,_tmpDescription,_tmpAdded);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean getDataById(final int id) {
    final String _sql = "SELECT added FROM data WHERE key = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public DataClass checkduplicateById(final int id) {
    final String _sql = "SELECT * FROM data WHERE key = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfKey = CursorUtil.getColumnIndexOrThrow(_cursor, "key");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfHerourl = CursorUtil.getColumnIndexOrThrow(_cursor, "herourl");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
      final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
      final DataClass _result;
      if(_cursor.moveToFirst()) {
        final int _tmpKey;
        _tmpKey = _cursor.getInt(_cursorIndexOfKey);
        final String _tmpUrl;
        if (_cursor.isNull(_cursorIndexOfUrl)) {
          _tmpUrl = null;
        } else {
          _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        }
        final String _tmpHerourl;
        if (_cursor.isNull(_cursorIndexOfHerourl)) {
          _tmpHerourl = null;
        } else {
          _tmpHerourl = _cursor.getString(_cursorIndexOfHerourl);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmpScore;
        if (_cursor.isNull(_cursorIndexOfScore)) {
          _tmpScore = null;
        } else {
          _tmpScore = _cursor.getString(_cursorIndexOfScore);
        }
        final boolean _tmpAdded;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAdded);
        _tmpAdded = _tmp != 0;
        _result = new DataClass(_tmpKey,_tmpUrl,_tmpHerourl,_tmpTitle,_tmpScore,_tmpDescription,_tmpAdded);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
