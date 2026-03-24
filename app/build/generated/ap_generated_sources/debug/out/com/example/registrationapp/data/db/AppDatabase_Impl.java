package com.example.registrationapp.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile SubjectDao _subjectDao;

  private volatile AttendanceDao _attendanceDao;

  private volatile LeaveDao _leaveDao;

  private volatile MstMarkDao _mstMarkDao;

  private volatile AnnouncementDao _announcementDao;

  private volatile TimetableDao _timetableDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `subjects` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `code` TEXT, `color` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `attendance_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subjectId` INTEGER NOT NULL, `date` TEXT, `status` TEXT, FOREIGN KEY(`subjectId`) REFERENCES `subjects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_attendance_records_subjectId` ON `attendance_records` (`subjectId`)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_attendance_records_subjectId_date` ON `attendance_records` (`subjectId`, `date`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `leave_requests` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fromDate` TEXT, `toDate` TEXT, `reason` TEXT, `status` TEXT, `appliedDate` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `mst_marks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subjectId` INTEGER NOT NULL, `examName` TEXT, `marksObtained` INTEGER NOT NULL, `maxMarks` INTEGER NOT NULL, FOREIGN KEY(`subjectId`) REFERENCES `subjects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_mst_marks_subjectId` ON `mst_marks` (`subjectId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `announcements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `body` TEXT, `date` TEXT, `priority` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `timetable_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subjectId` INTEGER NOT NULL, `dayOfWeek` INTEGER NOT NULL, `startTime` TEXT, `endTime` TEXT, `room` TEXT, FOREIGN KEY(`subjectId`) REFERENCES `subjects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_timetable_entries_subjectId` ON `timetable_entries` (`subjectId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '348922bfd2e9de886ba3a274b70a04d0')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `subjects`");
        db.execSQL("DROP TABLE IF EXISTS `attendance_records`");
        db.execSQL("DROP TABLE IF EXISTS `leave_requests`");
        db.execSQL("DROP TABLE IF EXISTS `mst_marks`");
        db.execSQL("DROP TABLE IF EXISTS `announcements`");
        db.execSQL("DROP TABLE IF EXISTS `timetable_entries`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSubjects = new HashMap<String, TableInfo.Column>(4);
        _columnsSubjects.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("code", new TableInfo.Column("code", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubjects = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubjects = new TableInfo("subjects", _columnsSubjects, _foreignKeysSubjects, _indicesSubjects);
        final TableInfo _existingSubjects = TableInfo.read(db, "subjects");
        if (!_infoSubjects.equals(_existingSubjects)) {
          return new RoomOpenHelper.ValidationResult(false, "subjects(com.example.registrationapp.data.entity.Subject).\n"
                  + " Expected:\n" + _infoSubjects + "\n"
                  + " Found:\n" + _existingSubjects);
        }
        final HashMap<String, TableInfo.Column> _columnsAttendanceRecords = new HashMap<String, TableInfo.Column>(4);
        _columnsAttendanceRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceRecords.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceRecords.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceRecords.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAttendanceRecords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAttendanceRecords.add(new TableInfo.ForeignKey("subjects", "CASCADE", "NO ACTION", Arrays.asList("subjectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAttendanceRecords = new HashSet<TableInfo.Index>(2);
        _indicesAttendanceRecords.add(new TableInfo.Index("index_attendance_records_subjectId", false, Arrays.asList("subjectId"), Arrays.asList("ASC")));
        _indicesAttendanceRecords.add(new TableInfo.Index("index_attendance_records_subjectId_date", true, Arrays.asList("subjectId", "date"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoAttendanceRecords = new TableInfo("attendance_records", _columnsAttendanceRecords, _foreignKeysAttendanceRecords, _indicesAttendanceRecords);
        final TableInfo _existingAttendanceRecords = TableInfo.read(db, "attendance_records");
        if (!_infoAttendanceRecords.equals(_existingAttendanceRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "attendance_records(com.example.registrationapp.data.entity.AttendanceRecord).\n"
                  + " Expected:\n" + _infoAttendanceRecords + "\n"
                  + " Found:\n" + _existingAttendanceRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsLeaveRequests = new HashMap<String, TableInfo.Column>(6);
        _columnsLeaveRequests.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaveRequests.put("fromDate", new TableInfo.Column("fromDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaveRequests.put("toDate", new TableInfo.Column("toDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaveRequests.put("reason", new TableInfo.Column("reason", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaveRequests.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaveRequests.put("appliedDate", new TableInfo.Column("appliedDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLeaveRequests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLeaveRequests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLeaveRequests = new TableInfo("leave_requests", _columnsLeaveRequests, _foreignKeysLeaveRequests, _indicesLeaveRequests);
        final TableInfo _existingLeaveRequests = TableInfo.read(db, "leave_requests");
        if (!_infoLeaveRequests.equals(_existingLeaveRequests)) {
          return new RoomOpenHelper.ValidationResult(false, "leave_requests(com.example.registrationapp.data.entity.LeaveRequest).\n"
                  + " Expected:\n" + _infoLeaveRequests + "\n"
                  + " Found:\n" + _existingLeaveRequests);
        }
        final HashMap<String, TableInfo.Column> _columnsMstMarks = new HashMap<String, TableInfo.Column>(5);
        _columnsMstMarks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMstMarks.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMstMarks.put("examName", new TableInfo.Column("examName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMstMarks.put("marksObtained", new TableInfo.Column("marksObtained", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMstMarks.put("maxMarks", new TableInfo.Column("maxMarks", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMstMarks = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMstMarks.add(new TableInfo.ForeignKey("subjects", "CASCADE", "NO ACTION", Arrays.asList("subjectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMstMarks = new HashSet<TableInfo.Index>(1);
        _indicesMstMarks.add(new TableInfo.Index("index_mst_marks_subjectId", false, Arrays.asList("subjectId"), Arrays.asList("ASC")));
        final TableInfo _infoMstMarks = new TableInfo("mst_marks", _columnsMstMarks, _foreignKeysMstMarks, _indicesMstMarks);
        final TableInfo _existingMstMarks = TableInfo.read(db, "mst_marks");
        if (!_infoMstMarks.equals(_existingMstMarks)) {
          return new RoomOpenHelper.ValidationResult(false, "mst_marks(com.example.registrationapp.data.entity.MstMark).\n"
                  + " Expected:\n" + _infoMstMarks + "\n"
                  + " Found:\n" + _existingMstMarks);
        }
        final HashMap<String, TableInfo.Column> _columnsAnnouncements = new HashMap<String, TableInfo.Column>(5);
        _columnsAnnouncements.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("body", new TableInfo.Column("body", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAnnouncements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAnnouncements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAnnouncements = new TableInfo("announcements", _columnsAnnouncements, _foreignKeysAnnouncements, _indicesAnnouncements);
        final TableInfo _existingAnnouncements = TableInfo.read(db, "announcements");
        if (!_infoAnnouncements.equals(_existingAnnouncements)) {
          return new RoomOpenHelper.ValidationResult(false, "announcements(com.example.registrationapp.data.entity.Announcement).\n"
                  + " Expected:\n" + _infoAnnouncements + "\n"
                  + " Found:\n" + _existingAnnouncements);
        }
        final HashMap<String, TableInfo.Column> _columnsTimetableEntries = new HashMap<String, TableInfo.Column>(6);
        _columnsTimetableEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableEntries.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableEntries.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableEntries.put("startTime", new TableInfo.Column("startTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableEntries.put("endTime", new TableInfo.Column("endTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableEntries.put("room", new TableInfo.Column("room", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimetableEntries = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTimetableEntries.add(new TableInfo.ForeignKey("subjects", "CASCADE", "NO ACTION", Arrays.asList("subjectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTimetableEntries = new HashSet<TableInfo.Index>(1);
        _indicesTimetableEntries.add(new TableInfo.Index("index_timetable_entries_subjectId", false, Arrays.asList("subjectId"), Arrays.asList("ASC")));
        final TableInfo _infoTimetableEntries = new TableInfo("timetable_entries", _columnsTimetableEntries, _foreignKeysTimetableEntries, _indicesTimetableEntries);
        final TableInfo _existingTimetableEntries = TableInfo.read(db, "timetable_entries");
        if (!_infoTimetableEntries.equals(_existingTimetableEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "timetable_entries(com.example.registrationapp.data.entity.TimetableEntry).\n"
                  + " Expected:\n" + _infoTimetableEntries + "\n"
                  + " Found:\n" + _existingTimetableEntries);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "348922bfd2e9de886ba3a274b70a04d0", "6fcf4d5f9be8ea15ec4d423b7cbd823b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "subjects","attendance_records","leave_requests","mst_marks","announcements","timetable_entries");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `subjects`");
      _db.execSQL("DELETE FROM `attendance_records`");
      _db.execSQL("DELETE FROM `leave_requests`");
      _db.execSQL("DELETE FROM `mst_marks`");
      _db.execSQL("DELETE FROM `announcements`");
      _db.execSQL("DELETE FROM `timetable_entries`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SubjectDao.class, SubjectDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AttendanceDao.class, AttendanceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LeaveDao.class, LeaveDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MstMarkDao.class, MstMarkDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AnnouncementDao.class, AnnouncementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TimetableDao.class, TimetableDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SubjectDao subjectDao() {
    if (_subjectDao != null) {
      return _subjectDao;
    } else {
      synchronized(this) {
        if(_subjectDao == null) {
          _subjectDao = new SubjectDao_Impl(this);
        }
        return _subjectDao;
      }
    }
  }

  @Override
  public AttendanceDao attendanceDao() {
    if (_attendanceDao != null) {
      return _attendanceDao;
    } else {
      synchronized(this) {
        if(_attendanceDao == null) {
          _attendanceDao = new AttendanceDao_Impl(this);
        }
        return _attendanceDao;
      }
    }
  }

  @Override
  public LeaveDao leaveDao() {
    if (_leaveDao != null) {
      return _leaveDao;
    } else {
      synchronized(this) {
        if(_leaveDao == null) {
          _leaveDao = new LeaveDao_Impl(this);
        }
        return _leaveDao;
      }
    }
  }

  @Override
  public MstMarkDao mstMarkDao() {
    if (_mstMarkDao != null) {
      return _mstMarkDao;
    } else {
      synchronized(this) {
        if(_mstMarkDao == null) {
          _mstMarkDao = new MstMarkDao_Impl(this);
        }
        return _mstMarkDao;
      }
    }
  }

  @Override
  public AnnouncementDao announcementDao() {
    if (_announcementDao != null) {
      return _announcementDao;
    } else {
      synchronized(this) {
        if(_announcementDao == null) {
          _announcementDao = new AnnouncementDao_Impl(this);
        }
        return _announcementDao;
      }
    }
  }

  @Override
  public TimetableDao timetableDao() {
    if (_timetableDao != null) {
      return _timetableDao;
    } else {
      synchronized(this) {
        if(_timetableDao == null) {
          _timetableDao = new TimetableDao_Impl(this);
        }
        return _timetableDao;
      }
    }
  }
}
