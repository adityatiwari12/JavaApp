package com.example.registrationapp.data.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.registrationapp.data.entity.TimetableEntry;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TimetableDao_Impl implements TimetableDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TimetableEntry> __insertionAdapterOfTimetableEntry;

  public TimetableDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTimetableEntry = new EntityInsertionAdapter<TimetableEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `timetable_entries` (`id`,`subjectId`,`dayOfWeek`,`startTime`,`endTime`,`room`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TimetableEntry entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.subjectId);
        statement.bindLong(3, entity.dayOfWeek);
        if (entity.startTime == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.startTime);
        }
        if (entity.endTime == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.endTime);
        }
        if (entity.room == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.room);
        }
      }
    };
  }

  @Override
  public long insert(final TimetableEntry entry) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTimetableEntry.insertAndReturnId(entry);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<TimetableEntry>> getEntriesByDay(final int day) {
    final String _sql = "SELECT * FROM timetable_entries WHERE dayOfWeek = ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, day);
    return __db.getInvalidationTracker().createLiveData(new String[] {"timetable_entries"}, false, new Callable<List<TimetableEntry>>() {
      @Override
      @Nullable
      public List<TimetableEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final List<TimetableEntry> _result = new ArrayList<TimetableEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TimetableEntry _item;
            final int _tmpSubjectId;
            _tmpSubjectId = _cursor.getInt(_cursorIndexOfSubjectId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            final String _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            }
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item = new TimetableEntry(_tmpSubjectId,_tmpDayOfWeek,_tmpStartTime,_tmpEndTime,_tmpRoom);
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
  public LiveData<List<TimetableEntry>> getAllEntries() {
    final String _sql = "SELECT * FROM timetable_entries ORDER BY dayOfWeek ASC, startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"timetable_entries"}, false, new Callable<List<TimetableEntry>>() {
      @Override
      @Nullable
      public List<TimetableEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final List<TimetableEntry> _result = new ArrayList<TimetableEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TimetableEntry _item;
            final int _tmpSubjectId;
            _tmpSubjectId = _cursor.getInt(_cursorIndexOfSubjectId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            final String _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            }
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item = new TimetableEntry(_tmpSubjectId,_tmpDayOfWeek,_tmpStartTime,_tmpEndTime,_tmpRoom);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
