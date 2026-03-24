package com.example.registrationapp.data.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.registrationapp.data.entity.MstMark;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MstMarkDao_Impl implements MstMarkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MstMark> __insertionAdapterOfMstMark;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMark;

  public MstMarkDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMstMark = new EntityInsertionAdapter<MstMark>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `mst_marks` (`id`,`subjectId`,`examName`,`marksObtained`,`maxMarks`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final MstMark entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.subjectId);
        if (entity.examName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.examName);
        }
        statement.bindLong(4, entity.marksObtained);
        statement.bindLong(5, entity.maxMarks);
      }
    };
    this.__preparedStmtOfDeleteMark = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM mst_marks WHERE subjectId = ? AND examName = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final MstMark mark) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfMstMark.insertAndReturnId(mark);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteMark(final int subjectId, final String examName) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMark.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, subjectId);
    _argIndex = 2;
    if (examName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, examName);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteMark.release(_stmt);
    }
  }

  @Override
  public MstMark getMarkSync(final int subjectId, final String examName) {
    final String _sql = "SELECT * FROM mst_marks WHERE subjectId = ? AND examName = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    _argIndex = 2;
    if (examName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, examName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
      final int _cursorIndexOfExamName = CursorUtil.getColumnIndexOrThrow(_cursor, "examName");
      final int _cursorIndexOfMarksObtained = CursorUtil.getColumnIndexOrThrow(_cursor, "marksObtained");
      final int _cursorIndexOfMaxMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "maxMarks");
      final MstMark _result;
      if (_cursor.moveToFirst()) {
        final int _tmpSubjectId;
        _tmpSubjectId = _cursor.getInt(_cursorIndexOfSubjectId);
        final String _tmpExamName;
        if (_cursor.isNull(_cursorIndexOfExamName)) {
          _tmpExamName = null;
        } else {
          _tmpExamName = _cursor.getString(_cursorIndexOfExamName);
        }
        final int _tmpMarksObtained;
        _tmpMarksObtained = _cursor.getInt(_cursorIndexOfMarksObtained);
        final int _tmpMaxMarks;
        _tmpMaxMarks = _cursor.getInt(_cursorIndexOfMaxMarks);
        _result = new MstMark(_tmpSubjectId,_tmpExamName,_tmpMarksObtained,_tmpMaxMarks);
        _result.id = _cursor.getInt(_cursorIndexOfId);
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
  public LiveData<List<MstMark>> getMarksBySubject(final int subjectId) {
    final String _sql = "SELECT * FROM mst_marks WHERE subjectId = ? ORDER BY examName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mst_marks"}, false, new Callable<List<MstMark>>() {
      @Override
      @Nullable
      public List<MstMark> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfExamName = CursorUtil.getColumnIndexOrThrow(_cursor, "examName");
          final int _cursorIndexOfMarksObtained = CursorUtil.getColumnIndexOrThrow(_cursor, "marksObtained");
          final int _cursorIndexOfMaxMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "maxMarks");
          final List<MstMark> _result = new ArrayList<MstMark>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MstMark _item;
            final int _tmpSubjectId;
            _tmpSubjectId = _cursor.getInt(_cursorIndexOfSubjectId);
            final String _tmpExamName;
            if (_cursor.isNull(_cursorIndexOfExamName)) {
              _tmpExamName = null;
            } else {
              _tmpExamName = _cursor.getString(_cursorIndexOfExamName);
            }
            final int _tmpMarksObtained;
            _tmpMarksObtained = _cursor.getInt(_cursorIndexOfMarksObtained);
            final int _tmpMaxMarks;
            _tmpMaxMarks = _cursor.getInt(_cursorIndexOfMaxMarks);
            _item = new MstMark(_tmpSubjectId,_tmpExamName,_tmpMarksObtained,_tmpMaxMarks);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MstMark>> getAllMarks() {
    final String _sql = "SELECT * FROM mst_marks ORDER BY subjectId ASC, examName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mst_marks"}, false, new Callable<List<MstMark>>() {
      @Override
      @Nullable
      public List<MstMark> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfExamName = CursorUtil.getColumnIndexOrThrow(_cursor, "examName");
          final int _cursorIndexOfMarksObtained = CursorUtil.getColumnIndexOrThrow(_cursor, "marksObtained");
          final int _cursorIndexOfMaxMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "maxMarks");
          final List<MstMark> _result = new ArrayList<MstMark>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MstMark _item;
            final int _tmpSubjectId;
            _tmpSubjectId = _cursor.getInt(_cursorIndexOfSubjectId);
            final String _tmpExamName;
            if (_cursor.isNull(_cursorIndexOfExamName)) {
              _tmpExamName = null;
            } else {
              _tmpExamName = _cursor.getString(_cursorIndexOfExamName);
            }
            final int _tmpMarksObtained;
            _tmpMarksObtained = _cursor.getInt(_cursorIndexOfMarksObtained);
            final int _tmpMaxMarks;
            _tmpMaxMarks = _cursor.getInt(_cursorIndexOfMaxMarks);
            _item = new MstMark(_tmpSubjectId,_tmpExamName,_tmpMarksObtained,_tmpMaxMarks);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Float> getAveragePercentBySubject(final int subjectId) {
    final String _sql = "SELECT AVG(marksObtained * 100.0 / maxMarks) FROM mst_marks WHERE subjectId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mst_marks"}, false, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
