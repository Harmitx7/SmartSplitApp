package com.example.smartsplitter.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GroupDao_Impl implements GroupDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Group> __insertionAdapterOfGroup;

  private final EntityInsertionAdapter<Member> __insertionAdapterOfMember;

  private final EntityDeletionOrUpdateAdapter<Group> __deletionAdapterOfGroup;

  private final EntityDeletionOrUpdateAdapter<Group> __updateAdapterOfGroup;

  public GroupDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGroup = new EntityInsertionAdapter<Group>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `groups` (`group_id`,`group_name`,`currency`,`description`,`created_at`,`updated_at`,`is_archived`,`qr_code_data`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Group entity) {
        if (entity.groupId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.groupId);
        }
        if (entity.groupName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.groupName);
        }
        if (entity.currency == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.currency);
        }
        if (entity.description == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.description);
        }
        statement.bindLong(5, entity.createdAt);
        statement.bindLong(6, entity.updatedAt);
        final int _tmp = entity.isArchived ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.qrCodeData == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.qrCodeData);
        }
      }
    };
    this.__insertionAdapterOfMember = new EntityInsertionAdapter<Member>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `members` (`member_id`,`group_id`,`display_name`,`username`,`created_at`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Member entity) {
        if (entity.memberId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.memberId);
        }
        if (entity.groupId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.groupId);
        }
        if (entity.displayName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.displayName);
        }
        if (entity.username == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.username);
        }
        statement.bindLong(5, entity.createdAt);
      }
    };
    this.__deletionAdapterOfGroup = new EntityDeletionOrUpdateAdapter<Group>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `groups` WHERE `group_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Group entity) {
        if (entity.groupId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.groupId);
        }
      }
    };
    this.__updateAdapterOfGroup = new EntityDeletionOrUpdateAdapter<Group>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `groups` SET `group_id` = ?,`group_name` = ?,`currency` = ?,`description` = ?,`created_at` = ?,`updated_at` = ?,`is_archived` = ?,`qr_code_data` = ? WHERE `group_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Group entity) {
        if (entity.groupId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.groupId);
        }
        if (entity.groupName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.groupName);
        }
        if (entity.currency == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.currency);
        }
        if (entity.description == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.description);
        }
        statement.bindLong(5, entity.createdAt);
        statement.bindLong(6, entity.updatedAt);
        final int _tmp = entity.isArchived ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.qrCodeData == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.qrCodeData);
        }
        if (entity.groupId == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.groupId);
        }
      }
    };
  }

  @Override
  public long insertGroup(final Group group) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfGroup.insertAndReturnId(group);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertMember(final Member member) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfMember.insertAndReturnId(member);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteGroup(final Group group) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfGroup.handle(group);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateGroup(final Group group) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfGroup.handle(group);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Group>> getAllActiveGroups() {
    final String _sql = "SELECT * FROM groups WHERE is_archived = 0 ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"groups"}, false, new Callable<List<Group>>() {
      @Override
      @Nullable
      public List<Group> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
          final int _cursorIndexOfGroupName = CursorUtil.getColumnIndexOrThrow(_cursor, "group_name");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfQrCodeData = CursorUtil.getColumnIndexOrThrow(_cursor, "qr_code_data");
          final List<Group> _result = new ArrayList<Group>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Group _item;
            _item = new Group();
            if (_cursor.isNull(_cursorIndexOfGroupId)) {
              _item.groupId = null;
            } else {
              _item.groupId = _cursor.getString(_cursorIndexOfGroupId);
            }
            if (_cursor.isNull(_cursorIndexOfGroupName)) {
              _item.groupName = null;
            } else {
              _item.groupName = _cursor.getString(_cursorIndexOfGroupName);
            }
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _item.currency = null;
            } else {
              _item.currency = _cursor.getString(_cursorIndexOfCurrency);
            }
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _item.description = null;
            } else {
              _item.description = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _item.isArchived = _tmp != 0;
            if (_cursor.isNull(_cursorIndexOfQrCodeData)) {
              _item.qrCodeData = null;
            } else {
              _item.qrCodeData = _cursor.getString(_cursorIndexOfQrCodeData);
            }
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
  public LiveData<List<GroupWithMembers>> getAllActiveGroupsWithMembers() {
    final String _sql = "SELECT * FROM groups WHERE is_archived = 0 ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"members",
        "groups"}, true, new Callable<List<GroupWithMembers>>() {
      @Override
      @Nullable
      public List<GroupWithMembers> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
            final int _cursorIndexOfGroupName = CursorUtil.getColumnIndexOrThrow(_cursor, "group_name");
            final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
            final int _cursorIndexOfQrCodeData = CursorUtil.getColumnIndexOrThrow(_cursor, "qr_code_data");
            final ArrayMap<String, ArrayList<Member>> _collectionMembers = new ArrayMap<String, ArrayList<Member>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              if (_cursor.isNull(_cursorIndexOfGroupId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getString(_cursorIndexOfGroupId);
              }
              if (_tmpKey != null) {
                if (!_collectionMembers.containsKey(_tmpKey)) {
                  _collectionMembers.put(_tmpKey, new ArrayList<Member>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipmembersAscomExampleSmartsplitterDataMember(_collectionMembers);
            final List<GroupWithMembers> _result = new ArrayList<GroupWithMembers>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final GroupWithMembers _item;
              final Group _tmpGroup;
              if (!(_cursor.isNull(_cursorIndexOfGroupId) && _cursor.isNull(_cursorIndexOfGroupName) && _cursor.isNull(_cursorIndexOfCurrency) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt) && _cursor.isNull(_cursorIndexOfIsArchived) && _cursor.isNull(_cursorIndexOfQrCodeData))) {
                _tmpGroup = new Group();
                if (_cursor.isNull(_cursorIndexOfGroupId)) {
                  _tmpGroup.groupId = null;
                } else {
                  _tmpGroup.groupId = _cursor.getString(_cursorIndexOfGroupId);
                }
                if (_cursor.isNull(_cursorIndexOfGroupName)) {
                  _tmpGroup.groupName = null;
                } else {
                  _tmpGroup.groupName = _cursor.getString(_cursorIndexOfGroupName);
                }
                if (_cursor.isNull(_cursorIndexOfCurrency)) {
                  _tmpGroup.currency = null;
                } else {
                  _tmpGroup.currency = _cursor.getString(_cursorIndexOfCurrency);
                }
                if (_cursor.isNull(_cursorIndexOfDescription)) {
                  _tmpGroup.description = null;
                } else {
                  _tmpGroup.description = _cursor.getString(_cursorIndexOfDescription);
                }
                _tmpGroup.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpGroup.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
                _tmpGroup.isArchived = _tmp != 0;
                if (_cursor.isNull(_cursorIndexOfQrCodeData)) {
                  _tmpGroup.qrCodeData = null;
                } else {
                  _tmpGroup.qrCodeData = _cursor.getString(_cursorIndexOfQrCodeData);
                }
              } else {
                _tmpGroup = null;
              }
              final ArrayList<Member> _tmpMembersCollection;
              final String _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfGroupId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getString(_cursorIndexOfGroupId);
              }
              if (_tmpKey_1 != null) {
                _tmpMembersCollection = _collectionMembers.get(_tmpKey_1);
              } else {
                _tmpMembersCollection = new ArrayList<Member>();
              }
              _item = new GroupWithMembers();
              _item.group = _tmpGroup;
              _item.members = _tmpMembersCollection;
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<GroupWithMembers> getGroupWithMembers(final String groupId) {
    final String _sql = "SELECT * FROM groups WHERE group_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (groupId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, groupId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"members",
        "groups"}, true, new Callable<GroupWithMembers>() {
      @Override
      @Nullable
      public GroupWithMembers call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
            final int _cursorIndexOfGroupName = CursorUtil.getColumnIndexOrThrow(_cursor, "group_name");
            final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
            final int _cursorIndexOfQrCodeData = CursorUtil.getColumnIndexOrThrow(_cursor, "qr_code_data");
            final ArrayMap<String, ArrayList<Member>> _collectionMembers = new ArrayMap<String, ArrayList<Member>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              if (_cursor.isNull(_cursorIndexOfGroupId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getString(_cursorIndexOfGroupId);
              }
              if (_tmpKey != null) {
                if (!_collectionMembers.containsKey(_tmpKey)) {
                  _collectionMembers.put(_tmpKey, new ArrayList<Member>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipmembersAscomExampleSmartsplitterDataMember(_collectionMembers);
            final GroupWithMembers _result;
            if (_cursor.moveToFirst()) {
              final Group _tmpGroup;
              if (!(_cursor.isNull(_cursorIndexOfGroupId) && _cursor.isNull(_cursorIndexOfGroupName) && _cursor.isNull(_cursorIndexOfCurrency) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt) && _cursor.isNull(_cursorIndexOfIsArchived) && _cursor.isNull(_cursorIndexOfQrCodeData))) {
                _tmpGroup = new Group();
                if (_cursor.isNull(_cursorIndexOfGroupId)) {
                  _tmpGroup.groupId = null;
                } else {
                  _tmpGroup.groupId = _cursor.getString(_cursorIndexOfGroupId);
                }
                if (_cursor.isNull(_cursorIndexOfGroupName)) {
                  _tmpGroup.groupName = null;
                } else {
                  _tmpGroup.groupName = _cursor.getString(_cursorIndexOfGroupName);
                }
                if (_cursor.isNull(_cursorIndexOfCurrency)) {
                  _tmpGroup.currency = null;
                } else {
                  _tmpGroup.currency = _cursor.getString(_cursorIndexOfCurrency);
                }
                if (_cursor.isNull(_cursorIndexOfDescription)) {
                  _tmpGroup.description = null;
                } else {
                  _tmpGroup.description = _cursor.getString(_cursorIndexOfDescription);
                }
                _tmpGroup.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpGroup.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
                _tmpGroup.isArchived = _tmp != 0;
                if (_cursor.isNull(_cursorIndexOfQrCodeData)) {
                  _tmpGroup.qrCodeData = null;
                } else {
                  _tmpGroup.qrCodeData = _cursor.getString(_cursorIndexOfQrCodeData);
                }
              } else {
                _tmpGroup = null;
              }
              final ArrayList<Member> _tmpMembersCollection;
              final String _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfGroupId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getString(_cursorIndexOfGroupId);
              }
              if (_tmpKey_1 != null) {
                _tmpMembersCollection = _collectionMembers.get(_tmpKey_1);
              } else {
                _tmpMembersCollection = new ArrayList<Member>();
              }
              _result = new GroupWithMembers();
              _result.group = _tmpGroup;
              _result.members = _tmpMembersCollection;
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Member>> getGroupMembers(final String groupId) {
    final String _sql = "SELECT * FROM members WHERE group_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (groupId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, groupId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"members"}, false, new Callable<List<Member>>() {
      @Override
      @Nullable
      public List<Member> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "member_id");
          final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "display_name");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<Member> _result = new ArrayList<Member>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Member _item;
            _item = new Member();
            if (_cursor.isNull(_cursorIndexOfMemberId)) {
              _item.memberId = null;
            } else {
              _item.memberId = _cursor.getString(_cursorIndexOfMemberId);
            }
            if (_cursor.isNull(_cursorIndexOfGroupId)) {
              _item.groupId = null;
            } else {
              _item.groupId = _cursor.getString(_cursorIndexOfGroupId);
            }
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _item.displayName = null;
            } else {
              _item.displayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _item.username = null;
            } else {
              _item.username = _cursor.getString(_cursorIndexOfUsername);
            }
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
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
  public Group getGroupById(final String groupId) {
    final String _sql = "SELECT * FROM groups WHERE group_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (groupId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, groupId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
      final int _cursorIndexOfGroupName = CursorUtil.getColumnIndexOrThrow(_cursor, "group_name");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
      final int _cursorIndexOfQrCodeData = CursorUtil.getColumnIndexOrThrow(_cursor, "qr_code_data");
      final Group _result;
      if (_cursor.moveToFirst()) {
        _result = new Group();
        if (_cursor.isNull(_cursorIndexOfGroupId)) {
          _result.groupId = null;
        } else {
          _result.groupId = _cursor.getString(_cursorIndexOfGroupId);
        }
        if (_cursor.isNull(_cursorIndexOfGroupName)) {
          _result.groupName = null;
        } else {
          _result.groupName = _cursor.getString(_cursorIndexOfGroupName);
        }
        if (_cursor.isNull(_cursorIndexOfCurrency)) {
          _result.currency = null;
        } else {
          _result.currency = _cursor.getString(_cursorIndexOfCurrency);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _result.description = null;
        } else {
          _result.description = _cursor.getString(_cursorIndexOfDescription);
        }
        _result.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
        _result.isArchived = _tmp != 0;
        if (_cursor.isNull(_cursorIndexOfQrCodeData)) {
          _result.qrCodeData = null;
        } else {
          _result.qrCodeData = _cursor.getString(_cursorIndexOfQrCodeData);
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
  public List<Group> getAllActiveGroupsSync() {
    final String _sql = "SELECT * FROM groups WHERE is_archived = 0 ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
      final int _cursorIndexOfGroupName = CursorUtil.getColumnIndexOrThrow(_cursor, "group_name");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
      final int _cursorIndexOfQrCodeData = CursorUtil.getColumnIndexOrThrow(_cursor, "qr_code_data");
      final List<Group> _result = new ArrayList<Group>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Group _item;
        _item = new Group();
        if (_cursor.isNull(_cursorIndexOfGroupId)) {
          _item.groupId = null;
        } else {
          _item.groupId = _cursor.getString(_cursorIndexOfGroupId);
        }
        if (_cursor.isNull(_cursorIndexOfGroupName)) {
          _item.groupName = null;
        } else {
          _item.groupName = _cursor.getString(_cursorIndexOfGroupName);
        }
        if (_cursor.isNull(_cursorIndexOfCurrency)) {
          _item.currency = null;
        } else {
          _item.currency = _cursor.getString(_cursorIndexOfCurrency);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _item.description = null;
        } else {
          _item.description = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
        _item.isArchived = _tmp != 0;
        if (_cursor.isNull(_cursorIndexOfQrCodeData)) {
          _item.qrCodeData = null;
        } else {
          _item.qrCodeData = _cursor.getString(_cursorIndexOfQrCodeData);
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
  public List<Member> getGroupMembersSync(final String groupId) {
    final String _sql = "SELECT * FROM members WHERE group_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (groupId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, groupId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "member_id");
      final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "display_name");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final List<Member> _result = new ArrayList<Member>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Member _item;
        _item = new Member();
        if (_cursor.isNull(_cursorIndexOfMemberId)) {
          _item.memberId = null;
        } else {
          _item.memberId = _cursor.getString(_cursorIndexOfMemberId);
        }
        if (_cursor.isNull(_cursorIndexOfGroupId)) {
          _item.groupId = null;
        } else {
          _item.groupId = _cursor.getString(_cursorIndexOfGroupId);
        }
        if (_cursor.isNull(_cursorIndexOfDisplayName)) {
          _item.displayName = null;
        } else {
          _item.displayName = _cursor.getString(_cursorIndexOfDisplayName);
        }
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _item.username = null;
        } else {
          _item.username = _cursor.getString(_cursorIndexOfUsername);
        }
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipmembersAscomExampleSmartsplitterDataMember(
      @NonNull final ArrayMap<String, ArrayList<Member>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipmembersAscomExampleSmartsplitterDataMember(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `member_id`,`group_id`,`display_name`,`username`,`created_at` FROM `members` WHERE `group_id` IN (");
    final int _inputSize = __mapKeySet == null ? 1 : __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    if (__mapKeySet == null) {
      _stmt.bindNull(_argIndex);
    } else {
      for (String _item : __mapKeySet) {
        if (_item == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _item);
        }
        _argIndex++;
      }
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "group_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfMemberId = 0;
      final int _cursorIndexOfGroupId = 1;
      final int _cursorIndexOfDisplayName = 2;
      final int _cursorIndexOfUsername = 3;
      final int _cursorIndexOfCreatedAt = 4;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        if (_cursor.isNull(_itemKeyIndex)) {
          _tmpKey = null;
        } else {
          _tmpKey = _cursor.getString(_itemKeyIndex);
        }
        if (_tmpKey != null) {
          final ArrayList<Member> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Member _item_1;
            _item_1 = new Member();
            if (_cursor.isNull(_cursorIndexOfMemberId)) {
              _item_1.memberId = null;
            } else {
              _item_1.memberId = _cursor.getString(_cursorIndexOfMemberId);
            }
            if (_cursor.isNull(_cursorIndexOfGroupId)) {
              _item_1.groupId = null;
            } else {
              _item_1.groupId = _cursor.getString(_cursorIndexOfGroupId);
            }
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _item_1.displayName = null;
            } else {
              _item_1.displayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _item_1.username = null;
            } else {
              _item_1.username = _cursor.getString(_cursorIndexOfUsername);
            }
            _item_1.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
