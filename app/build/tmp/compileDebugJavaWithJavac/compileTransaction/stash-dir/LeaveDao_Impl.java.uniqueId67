package com.example.registrationapp.data.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.registrationapp.data.entity.LeaveRequest;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class LeaveDao_Impl implements LeaveDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LeaveRequest> __insertionAdapterOfLeaveRequest;

  private final EntityDeletionOrUpdateAdapter<LeaveRequest> __updateAdapterOfLeaveRequest;

  public LeaveDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLeaveRequest = new EntityInsertionAdapter<LeaveRequest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `leave_requests` (`id`,`fromDate`,`toDate`,`reason`,`status`,`appliedDate`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LeaveRequest entity) {
        statement.bindLong(1, entity.id);
        if (entity.fromDate == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.fromDate);
        }
        if (entity.toDate == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.toDate);
        }
        if (entity.reason == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.reason);
        }
        if (entity.status == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.status);
        }
        if (entity.appliedDate == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.appliedDate);
        }
      }
    };
    this.__updateAdapterOfLeaveRequest = new EntityDeletionOrUpdateAdapter<LeaveRequest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `leave_requests` SET `id` = ?,`fromDate` = ?,`toDate` = ?,`reason` = ?,`status` = ?,`appliedDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LeaveRequest entity) {
        statement.bindLong(1, entity.id);
        if (entity.fromDate == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.fromDate);
        }
        if (entity.toDate == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.toDate);
        }
        if (entity.reason == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.reason);
        }
        if (entity.status == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.status);
        }
        if (entity.appliedDate == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.appliedDate);
        }
        statement.bindLong(7, entity.id);
      }
    };
  }

  @Override
  public long insert(final LeaveRequest leave) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfLeaveRequest.insertAndReturnId(leave);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final LeaveRequest leave) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLeaveRequest.handle(leave);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<LeaveRequest>> getAllLeaves() {
    final String _sql = "SELECT * FROM leave_requests ORDER BY appliedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"leave_requests"}, false, new Callable<List<LeaveRequest>>() {
      @Override
      @Nullable
      public List<LeaveRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFromDate = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDate");
          final int _cursorIndexOfToDate = CursorUtil.getColumnIndexOrThrow(_cursor, "toDate");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAppliedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "appliedDate");
          final List<LeaveRequest> _result = new ArrayList<LeaveRequest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LeaveRequest _item;
            final String _tmpFromDate;
            if (_cursor.isNull(_cursorIndexOfFromDate)) {
              _tmpFromDate = null;
            } else {
              _tmpFromDate = _cursor.getString(_cursorIndexOfFromDate);
            }
            final String _tmpToDate;
            if (_cursor.isNull(_cursorIndexOfToDate)) {
              _tmpToDate = null;
            } else {
              _tmpToDate = _cursor.getString(_cursorIndexOfToDate);
            }
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpAppliedDate;
            if (_cursor.isNull(_cursorIndexOfAppliedDate)) {
              _tmpAppliedDate = null;
            } else {
              _tmpAppliedDate = _cursor.getString(_cursorIndexOfAppliedDate);
            }
            _item = new LeaveRequest(_tmpFromDate,_tmpToDate,_tmpReason,_tmpStatus,_tmpAppliedDate);
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
  public LiveData<List<LeaveRequest>> getLeavesByStatus(final String status) {
    final String _sql = "SELECT * FROM leave_requests WHERE status = ? ORDER BY appliedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"leave_requests"}, false, new Callable<List<LeaveRequest>>() {
      @Override
      @Nullable
      public List<LeaveRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFromDate = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDate");
          final int _cursorIndexOfToDate = CursorUtil.getColumnIndexOrThrow(_cursor, "toDate");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAppliedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "appliedDate");
          final List<LeaveRequest> _result = new ArrayList<LeaveRequest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LeaveRequest _item;
            final String _tmpFromDate;
            if (_cursor.isNull(_cursorIndexOfFromDate)) {
              _tmpFromDate = null;
            } else {
              _tmpFromDate = _cursor.getString(_cursorIndexOfFromDate);
            }
            final String _tmpToDate;
            if (_cursor.isNull(_cursorIndexOfToDate)) {
              _tmpToDate = null;
            } else {
              _tmpToDate = _cursor.getString(_cursorIndexOfToDate);
            }
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpAppliedDate;
            if (_cursor.isNull(_cursorIndexOfAppliedDate)) {
              _tmpAppliedDate = null;
            } else {
              _tmpAppliedDate = _cursor.getString(_cursorIndexOfAppliedDate);
            }
            _item = new LeaveRequest(_tmpFromDate,_tmpToDate,_tmpReason,_tmpStatus,_tmpAppliedDate);
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
  public LiveData<Integer> getPendingCount() {
    final String _sql = "SELECT COUNT(*) FROM leave_requests WHERE status = 'PENDING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"leave_requests"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
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
