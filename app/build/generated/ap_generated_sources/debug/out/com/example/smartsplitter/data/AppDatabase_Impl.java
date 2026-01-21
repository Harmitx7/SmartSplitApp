package com.example.smartsplitter.data;

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
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile GroupDao _groupDao;

  private volatile ExpenseDao _expenseDao;

  private volatile UserDao _userDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `groups` (`group_id` TEXT NOT NULL, `group_name` TEXT NOT NULL, `currency` TEXT NOT NULL, `description` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_archived` INTEGER NOT NULL, `qr_code_data` TEXT NOT NULL, PRIMARY KEY(`group_id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `members` (`member_id` TEXT NOT NULL, `group_id` TEXT NOT NULL, `display_name` TEXT NOT NULL, `username` TEXT, `created_at` INTEGER NOT NULL, PRIMARY KEY(`member_id`), FOREIGN KEY(`group_id`) REFERENCES `groups`(`group_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_members_group_id_display_name` ON `members` (`group_id`, `display_name`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`expense_id` TEXT NOT NULL, `group_id` TEXT NOT NULL, `description` TEXT NOT NULL, `total_amount` REAL NOT NULL, `currency` TEXT NOT NULL, `payer_member_id` TEXT NOT NULL, `category` TEXT NOT NULL, `expense_date` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_settlement` INTEGER NOT NULL, PRIMARY KEY(`expense_id`), FOREIGN KEY(`group_id`) REFERENCES `groups`(`group_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`payer_member_id`) REFERENCES `members`(`member_id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_group_id` ON `expenses` (`group_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_payer_member_id` ON `expenses` (`payer_member_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `expense_splits` (`split_id` TEXT NOT NULL, `expense_id` TEXT NOT NULL, `member_id` TEXT NOT NULL, `amount` REAL NOT NULL, `split_type` TEXT NOT NULL, `created_at` INTEGER NOT NULL, PRIMARY KEY(`split_id`), FOREIGN KEY(`expense_id`) REFERENCES `expenses`(`expense_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`member_id`) REFERENCES `members`(`member_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expense_splits_expense_id` ON `expense_splits` (`expense_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expense_splits_member_id` ON `expense_splits` (`member_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`email` TEXT NOT NULL, `username` TEXT, `name` TEXT, `passwordHash` TEXT, `phoneNumber` TEXT, `profileImageUri` TEXT, PRIMARY KEY(`email`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_users_username` ON `users` (`username`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'bd7c162bee500f414f131224bd347c1c')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `groups`");
        db.execSQL("DROP TABLE IF EXISTS `members`");
        db.execSQL("DROP TABLE IF EXISTS `expenses`");
        db.execSQL("DROP TABLE IF EXISTS `expense_splits`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
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
        final HashMap<String, TableInfo.Column> _columnsGroups = new HashMap<String, TableInfo.Column>(8);
        _columnsGroups.put("group_id", new TableInfo.Column("group_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroups.put("group_name", new TableInfo.Column("group_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroups.put("currency", new TableInfo.Column("currency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroups.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroups.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroups.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroups.put("is_archived", new TableInfo.Column("is_archived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroups.put("qr_code_data", new TableInfo.Column("qr_code_data", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGroups = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGroups = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGroups = new TableInfo("groups", _columnsGroups, _foreignKeysGroups, _indicesGroups);
        final TableInfo _existingGroups = TableInfo.read(db, "groups");
        if (!_infoGroups.equals(_existingGroups)) {
          return new RoomOpenHelper.ValidationResult(false, "groups(com.example.smartsplitter.data.Group).\n"
                  + " Expected:\n" + _infoGroups + "\n"
                  + " Found:\n" + _existingGroups);
        }
        final HashMap<String, TableInfo.Column> _columnsMembers = new HashMap<String, TableInfo.Column>(5);
        _columnsMembers.put("member_id", new TableInfo.Column("member_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("group_id", new TableInfo.Column("group_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("display_name", new TableInfo.Column("display_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMembers = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMembers.add(new TableInfo.ForeignKey("groups", "CASCADE", "NO ACTION", Arrays.asList("group_id"), Arrays.asList("group_id")));
        final HashSet<TableInfo.Index> _indicesMembers = new HashSet<TableInfo.Index>(1);
        _indicesMembers.add(new TableInfo.Index("index_members_group_id_display_name", true, Arrays.asList("group_id", "display_name"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoMembers = new TableInfo("members", _columnsMembers, _foreignKeysMembers, _indicesMembers);
        final TableInfo _existingMembers = TableInfo.read(db, "members");
        if (!_infoMembers.equals(_existingMembers)) {
          return new RoomOpenHelper.ValidationResult(false, "members(com.example.smartsplitter.data.Member).\n"
                  + " Expected:\n" + _infoMembers + "\n"
                  + " Found:\n" + _existingMembers);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenses = new HashMap<String, TableInfo.Column>(11);
        _columnsExpenses.put("expense_id", new TableInfo.Column("expense_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("group_id", new TableInfo.Column("group_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("total_amount", new TableInfo.Column("total_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("currency", new TableInfo.Column("currency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("payer_member_id", new TableInfo.Column("payer_member_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("expense_date", new TableInfo.Column("expense_date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("is_settlement", new TableInfo.Column("is_settlement", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenses = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("groups", "CASCADE", "NO ACTION", Arrays.asList("group_id"), Arrays.asList("group_id")));
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("members", "RESTRICT", "NO ACTION", Arrays.asList("payer_member_id"), Arrays.asList("member_id")));
        final HashSet<TableInfo.Index> _indicesExpenses = new HashSet<TableInfo.Index>(2);
        _indicesExpenses.add(new TableInfo.Index("index_expenses_group_id", false, Arrays.asList("group_id"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_payer_member_id", false, Arrays.asList("payer_member_id"), Arrays.asList("ASC")));
        final TableInfo _infoExpenses = new TableInfo("expenses", _columnsExpenses, _foreignKeysExpenses, _indicesExpenses);
        final TableInfo _existingExpenses = TableInfo.read(db, "expenses");
        if (!_infoExpenses.equals(_existingExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "expenses(com.example.smartsplitter.data.Expense).\n"
                  + " Expected:\n" + _infoExpenses + "\n"
                  + " Found:\n" + _existingExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenseSplits = new HashMap<String, TableInfo.Column>(6);
        _columnsExpenseSplits.put("split_id", new TableInfo.Column("split_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenseSplits.put("expense_id", new TableInfo.Column("expense_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenseSplits.put("member_id", new TableInfo.Column("member_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenseSplits.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenseSplits.put("split_type", new TableInfo.Column("split_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenseSplits.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenseSplits = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysExpenseSplits.add(new TableInfo.ForeignKey("expenses", "CASCADE", "NO ACTION", Arrays.asList("expense_id"), Arrays.asList("expense_id")));
        _foreignKeysExpenseSplits.add(new TableInfo.ForeignKey("members", "CASCADE", "NO ACTION", Arrays.asList("member_id"), Arrays.asList("member_id")));
        final HashSet<TableInfo.Index> _indicesExpenseSplits = new HashSet<TableInfo.Index>(2);
        _indicesExpenseSplits.add(new TableInfo.Index("index_expense_splits_expense_id", false, Arrays.asList("expense_id"), Arrays.asList("ASC")));
        _indicesExpenseSplits.add(new TableInfo.Index("index_expense_splits_member_id", false, Arrays.asList("member_id"), Arrays.asList("ASC")));
        final TableInfo _infoExpenseSplits = new TableInfo("expense_splits", _columnsExpenseSplits, _foreignKeysExpenseSplits, _indicesExpenseSplits);
        final TableInfo _existingExpenseSplits = TableInfo.read(db, "expense_splits");
        if (!_infoExpenseSplits.equals(_existingExpenseSplits)) {
          return new RoomOpenHelper.ValidationResult(false, "expense_splits(com.example.smartsplitter.data.ExpenseSplit).\n"
                  + " Expected:\n" + _infoExpenseSplits + "\n"
                  + " Found:\n" + _existingExpenseSplits);
        }
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(6);
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("passwordHash", new TableInfo.Column("passwordHash", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profileImageUri", new TableInfo.Column("profileImageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(1);
        _indicesUsers.add(new TableInfo.Index("index_users_username", true, Arrays.asList("username"), Arrays.asList("ASC")));
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.smartsplitter.data.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "bd7c162bee500f414f131224bd347c1c", "138041e7e9f3a72d3d36c0aaafe2cbff");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "groups","members","expenses","expense_splits","users");
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
      _db.execSQL("DELETE FROM `groups`");
      _db.execSQL("DELETE FROM `expenses`");
      _db.execSQL("DELETE FROM `members`");
      _db.execSQL("DELETE FROM `expense_splits`");
      _db.execSQL("DELETE FROM `users`");
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
    _typeConvertersMap.put(GroupDao.class, GroupDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDao.class, ExpenseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
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
  public GroupDao groupDao() {
    if (_groupDao != null) {
      return _groupDao;
    } else {
      synchronized(this) {
        if(_groupDao == null) {
          _groupDao = new GroupDao_Impl(this);
        }
        return _groupDao;
      }
    }
  }

  @Override
  public ExpenseDao expenseDao() {
    if (_expenseDao != null) {
      return _expenseDao;
    } else {
      synchronized(this) {
        if(_expenseDao == null) {
          _expenseDao = new ExpenseDao_Impl(this);
        }
        return _expenseDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
