package com.example.smartsplitter.data;

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
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`email`,`username`,`name`,`passwordHash`,`phoneNumber`,`profileImageUri`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.email == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.email);
        }
        if (entity.username == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.username);
        }
        if (entity.name == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.name);
        }
        if (entity.passwordHash == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.passwordHash);
        }
        if (entity.phoneNumber == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.phoneNumber);
        }
        if (entity.profileImageUri == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.profileImageUri);
        }
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `email` = ?,`username` = ?,`name` = ?,`passwordHash` = ?,`phoneNumber` = ?,`profileImageUri` = ? WHERE `email` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.email == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.email);
        }
        if (entity.username == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.username);
        }
        if (entity.name == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.name);
        }
        if (entity.passwordHash == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.passwordHash);
        }
        if (entity.phoneNumber == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.phoneNumber);
        }
        if (entity.profileImageUri == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.profileImageUri);
        }
        if (entity.email == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.email);
        }
      }
    };
  }

  @Override
  public void insertUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public User getUserByEmail(final String email) {
    final String _sql = "SELECT * FROM users WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
      final int _cursorIndexOfProfileImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUri");
      final User _result;
      if (_cursor.moveToFirst()) {
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _result = new User(_tmpEmail,_tmpName,_tmpPasswordHash,_tmpUsername);
        if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
          _result.phoneNumber = null;
        } else {
          _result.phoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
        }
        if (_cursor.isNull(_cursorIndexOfProfileImageUri)) {
          _result.profileImageUri = null;
        } else {
          _result.profileImageUri = _cursor.getString(_cursorIndexOfProfileImageUri);
        }
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
  public User login(final String email, final String passwordHash) {
    final String _sql = "SELECT * FROM users WHERE email = ? AND passwordHash = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (passwordHash == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, passwordHash);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
      final int _cursorIndexOfProfileImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUri");
      final User _result;
      if (_cursor.moveToFirst()) {
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _result = new User(_tmpEmail,_tmpName,_tmpPasswordHash,_tmpUsername);
        if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
          _result.phoneNumber = null;
        } else {
          _result.phoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
        }
        if (_cursor.isNull(_cursorIndexOfProfileImageUri)) {
          _result.profileImageUri = null;
        } else {
          _result.profileImageUri = _cursor.getString(_cursorIndexOfProfileImageUri);
        }
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
  public User getUserByUsername(final String username) {
    final String _sql = "SELECT * FROM users WHERE username = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
      final int _cursorIndexOfProfileImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUri");
      final User _result;
      if (_cursor.moveToFirst()) {
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _result = new User(_tmpEmail,_tmpName,_tmpPasswordHash,_tmpUsername);
        if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
          _result.phoneNumber = null;
        } else {
          _result.phoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
        }
        if (_cursor.isNull(_cursorIndexOfProfileImageUri)) {
          _result.profileImageUri = null;
        } else {
          _result.profileImageUri = _cursor.getString(_cursorIndexOfProfileImageUri);
        }
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
  public List<User> searchUsers(final String query) {
    final String _sql = "SELECT * FROM users WHERE username LIKE ? || '%' LIMIT 20";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
      final int _cursorIndexOfProfileImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUri");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final User _item;
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _item = new User(_tmpEmail,_tmpName,_tmpPasswordHash,_tmpUsername);
        if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
          _item.phoneNumber = null;
        } else {
          _item.phoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
        }
        if (_cursor.isNull(_cursorIndexOfProfileImageUri)) {
          _item.profileImageUri = null;
        } else {
          _item.profileImageUri = _cursor.getString(_cursorIndexOfProfileImageUri);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<User> getUserLiveData(final String email) {
    final String _sql = "SELECT * FROM users WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfProfileImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUri");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPasswordHash;
            if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
              _tmpPasswordHash = null;
            } else {
              _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            }
            _result = new User(_tmpEmail,_tmpName,_tmpPasswordHash,_tmpUsername);
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _result.phoneNumber = null;
            } else {
              _result.phoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            if (_cursor.isNull(_cursorIndexOfProfileImageUri)) {
              _result.profileImageUri = null;
            } else {
              _result.profileImageUri = _cursor.getString(_cursorIndexOfProfileImageUri);
            }
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
