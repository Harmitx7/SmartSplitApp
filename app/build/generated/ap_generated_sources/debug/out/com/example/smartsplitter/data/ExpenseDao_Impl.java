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
import androidx.room.SharedSQLiteStatement;
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
public final class ExpenseDao_Impl implements ExpenseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Expense> __insertionAdapterOfExpense;

  private final EntityInsertionAdapter<ExpenseSplit> __insertionAdapterOfExpenseSplit;

  private final EntityDeletionOrUpdateAdapter<Expense> __deletionAdapterOfExpense;

  private final EntityDeletionOrUpdateAdapter<Expense> __updateAdapterOfExpense;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSplitsForExpense;

  public ExpenseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExpense = new EntityInsertionAdapter<Expense>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `expenses` (`expense_id`,`group_id`,`description`,`total_amount`,`currency`,`payer_member_id`,`category`,`expense_date`,`created_at`,`updated_at`,`is_settlement`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Expense entity) {
        if (entity.expenseId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.expenseId);
        }
        if (entity.groupId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.groupId);
        }
        if (entity.description == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.description);
        }
        statement.bindDouble(4, entity.totalAmount);
        if (entity.currency == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.currency);
        }
        if (entity.payerMemberId == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.payerMemberId);
        }
        if (entity.category == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.category);
        }
        statement.bindLong(8, entity.expenseDate);
        statement.bindLong(9, entity.createdAt);
        statement.bindLong(10, entity.updatedAt);
        final int _tmp = entity.isSettlement ? 1 : 0;
        statement.bindLong(11, _tmp);
      }
    };
    this.__insertionAdapterOfExpenseSplit = new EntityInsertionAdapter<ExpenseSplit>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `expense_splits` (`split_id`,`expense_id`,`member_id`,`amount`,`split_type`,`created_at`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ExpenseSplit entity) {
        if (entity.splitId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.splitId);
        }
        if (entity.expenseId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.expenseId);
        }
        if (entity.memberId == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.memberId);
        }
        statement.bindDouble(4, entity.amount);
        if (entity.splitType == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.splitType);
        }
        statement.bindLong(6, entity.createdAt);
      }
    };
    this.__deletionAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `expenses` WHERE `expense_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Expense entity) {
        if (entity.expenseId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.expenseId);
        }
      }
    };
    this.__updateAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `expenses` SET `expense_id` = ?,`group_id` = ?,`description` = ?,`total_amount` = ?,`currency` = ?,`payer_member_id` = ?,`category` = ?,`expense_date` = ?,`created_at` = ?,`updated_at` = ?,`is_settlement` = ? WHERE `expense_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Expense entity) {
        if (entity.expenseId == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.expenseId);
        }
        if (entity.groupId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.groupId);
        }
        if (entity.description == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.description);
        }
        statement.bindDouble(4, entity.totalAmount);
        if (entity.currency == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.currency);
        }
        if (entity.payerMemberId == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.payerMemberId);
        }
        if (entity.category == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.category);
        }
        statement.bindLong(8, entity.expenseDate);
        statement.bindLong(9, entity.createdAt);
        statement.bindLong(10, entity.updatedAt);
        final int _tmp = entity.isSettlement ? 1 : 0;
        statement.bindLong(11, _tmp);
        if (entity.expenseId == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.expenseId);
        }
      }
    };
    this.__preparedStmtOfDeleteSplitsForExpense = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM expense_splits WHERE expense_id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insertExpense(final Expense expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfExpense.insertAndReturnId(expense);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertSplits(final List<ExpenseSplit> splits) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExpenseSplit.insert(splits);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteExpense(final Expense expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfExpense.handle(expense);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateExpense(final Expense expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfExpense.handle(expense);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteSplitsForExpense(final String expenseId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSplitsForExpense.acquire();
    int _argIndex = 1;
    if (expenseId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, expenseId);
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
      __preparedStmtOfDeleteSplitsForExpense.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ExpenseWithSplits>> getExpensesForGroup(final String groupId) {
    final String _sql = "SELECT * FROM expenses WHERE group_id = ? ORDER BY expense_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (groupId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, groupId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"expense_splits",
        "expenses"}, true, new Callable<List<ExpenseWithSplits>>() {
      @Override
      @Nullable
      public List<ExpenseWithSplits> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_id");
            final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
            final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
            final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payer_member_id");
            final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
            final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_date");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final int _cursorIndexOfIsSettlement = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settlement");
            final ArrayMap<String, ArrayList<ExpenseSplit>> _collectionSplits = new ArrayMap<String, ArrayList<ExpenseSplit>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              if (_cursor.isNull(_cursorIndexOfExpenseId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getString(_cursorIndexOfExpenseId);
              }
              if (_tmpKey != null) {
                if (!_collectionSplits.containsKey(_tmpKey)) {
                  _collectionSplits.put(_tmpKey, new ArrayList<ExpenseSplit>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipexpenseSplitsAscomExampleSmartsplitterDataExpenseSplit(_collectionSplits);
            final List<ExpenseWithSplits> _result = new ArrayList<ExpenseWithSplits>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final ExpenseWithSplits _item;
              final Expense _tmpExpense;
              if (!(_cursor.isNull(_cursorIndexOfExpenseId) && _cursor.isNull(_cursorIndexOfGroupId) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfTotalAmount) && _cursor.isNull(_cursorIndexOfCurrency) && _cursor.isNull(_cursorIndexOfPayerMemberId) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfExpenseDate) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt) && _cursor.isNull(_cursorIndexOfIsSettlement))) {
                _tmpExpense = new Expense();
                if (_cursor.isNull(_cursorIndexOfExpenseId)) {
                  _tmpExpense.expenseId = null;
                } else {
                  _tmpExpense.expenseId = _cursor.getString(_cursorIndexOfExpenseId);
                }
                if (_cursor.isNull(_cursorIndexOfGroupId)) {
                  _tmpExpense.groupId = null;
                } else {
                  _tmpExpense.groupId = _cursor.getString(_cursorIndexOfGroupId);
                }
                if (_cursor.isNull(_cursorIndexOfDescription)) {
                  _tmpExpense.description = null;
                } else {
                  _tmpExpense.description = _cursor.getString(_cursorIndexOfDescription);
                }
                _tmpExpense.totalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
                if (_cursor.isNull(_cursorIndexOfCurrency)) {
                  _tmpExpense.currency = null;
                } else {
                  _tmpExpense.currency = _cursor.getString(_cursorIndexOfCurrency);
                }
                if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
                  _tmpExpense.payerMemberId = null;
                } else {
                  _tmpExpense.payerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
                }
                if (_cursor.isNull(_cursorIndexOfCategory)) {
                  _tmpExpense.category = null;
                } else {
                  _tmpExpense.category = _cursor.getString(_cursorIndexOfCategory);
                }
                _tmpExpense.expenseDate = _cursor.getLong(_cursorIndexOfExpenseDate);
                _tmpExpense.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpExpense.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfIsSettlement);
                _tmpExpense.isSettlement = _tmp != 0;
              } else {
                _tmpExpense = null;
              }
              final ArrayList<ExpenseSplit> _tmpSplitsCollection;
              final String _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfExpenseId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getString(_cursorIndexOfExpenseId);
              }
              if (_tmpKey_1 != null) {
                _tmpSplitsCollection = _collectionSplits.get(_tmpKey_1);
              } else {
                _tmpSplitsCollection = new ArrayList<ExpenseSplit>();
              }
              _item = new ExpenseWithSplits();
              _item.expense = _tmpExpense;
              _item.splits = _tmpSplitsCollection;
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
  public ExpenseWithSplits getExpenseWithSplits(final String expenseId) {
    final String _sql = "SELECT * FROM expenses WHERE expense_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (expenseId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, expenseId);
    }
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_id");
        final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
        final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
        final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
        final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
        final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payer_member_id");
        final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
        final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_date");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
        final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
        final int _cursorIndexOfIsSettlement = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settlement");
        final ArrayMap<String, ArrayList<ExpenseSplit>> _collectionSplits = new ArrayMap<String, ArrayList<ExpenseSplit>>();
        while (_cursor.moveToNext()) {
          final String _tmpKey;
          if (_cursor.isNull(_cursorIndexOfExpenseId)) {
            _tmpKey = null;
          } else {
            _tmpKey = _cursor.getString(_cursorIndexOfExpenseId);
          }
          if (_tmpKey != null) {
            if (!_collectionSplits.containsKey(_tmpKey)) {
              _collectionSplits.put(_tmpKey, new ArrayList<ExpenseSplit>());
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipexpenseSplitsAscomExampleSmartsplitterDataExpenseSplit(_collectionSplits);
        final ExpenseWithSplits _result;
        if (_cursor.moveToFirst()) {
          final Expense _tmpExpense;
          if (!(_cursor.isNull(_cursorIndexOfExpenseId) && _cursor.isNull(_cursorIndexOfGroupId) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfTotalAmount) && _cursor.isNull(_cursorIndexOfCurrency) && _cursor.isNull(_cursorIndexOfPayerMemberId) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfExpenseDate) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt) && _cursor.isNull(_cursorIndexOfIsSettlement))) {
            _tmpExpense = new Expense();
            if (_cursor.isNull(_cursorIndexOfExpenseId)) {
              _tmpExpense.expenseId = null;
            } else {
              _tmpExpense.expenseId = _cursor.getString(_cursorIndexOfExpenseId);
            }
            if (_cursor.isNull(_cursorIndexOfGroupId)) {
              _tmpExpense.groupId = null;
            } else {
              _tmpExpense.groupId = _cursor.getString(_cursorIndexOfGroupId);
            }
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpExpense.description = null;
            } else {
              _tmpExpense.description = _cursor.getString(_cursorIndexOfDescription);
            }
            _tmpExpense.totalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpExpense.currency = null;
            } else {
              _tmpExpense.currency = _cursor.getString(_cursorIndexOfCurrency);
            }
            if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
              _tmpExpense.payerMemberId = null;
            } else {
              _tmpExpense.payerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
            }
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpExpense.category = null;
            } else {
              _tmpExpense.category = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpExpense.expenseDate = _cursor.getLong(_cursorIndexOfExpenseDate);
            _tmpExpense.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _tmpExpense.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettlement);
            _tmpExpense.isSettlement = _tmp != 0;
          } else {
            _tmpExpense = null;
          }
          final ArrayList<ExpenseSplit> _tmpSplitsCollection;
          final String _tmpKey_1;
          if (_cursor.isNull(_cursorIndexOfExpenseId)) {
            _tmpKey_1 = null;
          } else {
            _tmpKey_1 = _cursor.getString(_cursorIndexOfExpenseId);
          }
          if (_tmpKey_1 != null) {
            _tmpSplitsCollection = _collectionSplits.get(_tmpKey_1);
          } else {
            _tmpSplitsCollection = new ArrayList<ExpenseSplit>();
          }
          _result = new ExpenseWithSplits();
          _result.expense = _tmpExpense;
          _result.splits = _tmpSplitsCollection;
        } else {
          _result = null;
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Expense> getExpensesListForGroup(final String groupId) {
    final String _sql = "SELECT * FROM expenses WHERE group_id = ?";
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
      final int _cursorIndexOfExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_id");
      final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payer_member_id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_date");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfIsSettlement = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settlement");
      final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Expense _item;
        _item = new Expense();
        if (_cursor.isNull(_cursorIndexOfExpenseId)) {
          _item.expenseId = null;
        } else {
          _item.expenseId = _cursor.getString(_cursorIndexOfExpenseId);
        }
        if (_cursor.isNull(_cursorIndexOfGroupId)) {
          _item.groupId = null;
        } else {
          _item.groupId = _cursor.getString(_cursorIndexOfGroupId);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _item.description = null;
        } else {
          _item.description = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.totalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
        if (_cursor.isNull(_cursorIndexOfCurrency)) {
          _item.currency = null;
        } else {
          _item.currency = _cursor.getString(_cursorIndexOfCurrency);
        }
        if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
          _item.payerMemberId = null;
        } else {
          _item.payerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
        }
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _item.category = null;
        } else {
          _item.category = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.expenseDate = _cursor.getLong(_cursorIndexOfExpenseDate);
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsSettlement);
        _item.isSettlement = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ExpenseSplit> getSplitsForExpense(final String expenseId) {
    final String _sql = "SELECT * FROM expense_splits WHERE expense_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (expenseId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, expenseId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfSplitId = CursorUtil.getColumnIndexOrThrow(_cursor, "split_id");
      final int _cursorIndexOfExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_id");
      final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "member_id");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfSplitType = CursorUtil.getColumnIndexOrThrow(_cursor, "split_type");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final List<ExpenseSplit> _result = new ArrayList<ExpenseSplit>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ExpenseSplit _item;
        _item = new ExpenseSplit();
        if (_cursor.isNull(_cursorIndexOfSplitId)) {
          _item.splitId = null;
        } else {
          _item.splitId = _cursor.getString(_cursorIndexOfSplitId);
        }
        if (_cursor.isNull(_cursorIndexOfExpenseId)) {
          _item.expenseId = null;
        } else {
          _item.expenseId = _cursor.getString(_cursorIndexOfExpenseId);
        }
        if (_cursor.isNull(_cursorIndexOfMemberId)) {
          _item.memberId = null;
        } else {
          _item.memberId = _cursor.getString(_cursorIndexOfMemberId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfSplitType)) {
          _item.splitType = null;
        } else {
          _item.splitType = _cursor.getString(_cursorIndexOfSplitType);
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

  @Override
  public LiveData<List<ExpenseWithSplits>> getAllExpenses() {
    final String _sql = "SELECT * FROM expenses ORDER BY expense_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"expense_splits",
        "expenses"}, true, new Callable<List<ExpenseWithSplits>>() {
      @Override
      @Nullable
      public List<ExpenseWithSplits> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_id");
            final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
            final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
            final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payer_member_id");
            final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
            final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_date");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final int _cursorIndexOfIsSettlement = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settlement");
            final ArrayMap<String, ArrayList<ExpenseSplit>> _collectionSplits = new ArrayMap<String, ArrayList<ExpenseSplit>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              if (_cursor.isNull(_cursorIndexOfExpenseId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getString(_cursorIndexOfExpenseId);
              }
              if (_tmpKey != null) {
                if (!_collectionSplits.containsKey(_tmpKey)) {
                  _collectionSplits.put(_tmpKey, new ArrayList<ExpenseSplit>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipexpenseSplitsAscomExampleSmartsplitterDataExpenseSplit(_collectionSplits);
            final List<ExpenseWithSplits> _result = new ArrayList<ExpenseWithSplits>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final ExpenseWithSplits _item;
              final Expense _tmpExpense;
              if (!(_cursor.isNull(_cursorIndexOfExpenseId) && _cursor.isNull(_cursorIndexOfGroupId) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfTotalAmount) && _cursor.isNull(_cursorIndexOfCurrency) && _cursor.isNull(_cursorIndexOfPayerMemberId) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfExpenseDate) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt) && _cursor.isNull(_cursorIndexOfIsSettlement))) {
                _tmpExpense = new Expense();
                if (_cursor.isNull(_cursorIndexOfExpenseId)) {
                  _tmpExpense.expenseId = null;
                } else {
                  _tmpExpense.expenseId = _cursor.getString(_cursorIndexOfExpenseId);
                }
                if (_cursor.isNull(_cursorIndexOfGroupId)) {
                  _tmpExpense.groupId = null;
                } else {
                  _tmpExpense.groupId = _cursor.getString(_cursorIndexOfGroupId);
                }
                if (_cursor.isNull(_cursorIndexOfDescription)) {
                  _tmpExpense.description = null;
                } else {
                  _tmpExpense.description = _cursor.getString(_cursorIndexOfDescription);
                }
                _tmpExpense.totalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
                if (_cursor.isNull(_cursorIndexOfCurrency)) {
                  _tmpExpense.currency = null;
                } else {
                  _tmpExpense.currency = _cursor.getString(_cursorIndexOfCurrency);
                }
                if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
                  _tmpExpense.payerMemberId = null;
                } else {
                  _tmpExpense.payerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
                }
                if (_cursor.isNull(_cursorIndexOfCategory)) {
                  _tmpExpense.category = null;
                } else {
                  _tmpExpense.category = _cursor.getString(_cursorIndexOfCategory);
                }
                _tmpExpense.expenseDate = _cursor.getLong(_cursorIndexOfExpenseDate);
                _tmpExpense.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpExpense.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfIsSettlement);
                _tmpExpense.isSettlement = _tmp != 0;
              } else {
                _tmpExpense = null;
              }
              final ArrayList<ExpenseSplit> _tmpSplitsCollection;
              final String _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfExpenseId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getString(_cursorIndexOfExpenseId);
              }
              if (_tmpKey_1 != null) {
                _tmpSplitsCollection = _collectionSplits.get(_tmpKey_1);
              } else {
                _tmpSplitsCollection = new ArrayList<ExpenseSplit>();
              }
              _item = new ExpenseWithSplits();
              _item.expense = _tmpExpense;
              _item.splits = _tmpSplitsCollection;
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
  public List<ExpenseWithSplits> getExpensesWithSplitsSync(final String groupId) {
    final String _sql = "SELECT * FROM expenses WHERE group_id = ? ORDER BY expense_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (groupId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, groupId);
    }
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_id");
        final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "group_id");
        final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
        final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
        final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
        final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payer_member_id");
        final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
        final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expense_date");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
        final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
        final int _cursorIndexOfIsSettlement = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settlement");
        final ArrayMap<String, ArrayList<ExpenseSplit>> _collectionSplits = new ArrayMap<String, ArrayList<ExpenseSplit>>();
        while (_cursor.moveToNext()) {
          final String _tmpKey;
          if (_cursor.isNull(_cursorIndexOfExpenseId)) {
            _tmpKey = null;
          } else {
            _tmpKey = _cursor.getString(_cursorIndexOfExpenseId);
          }
          if (_tmpKey != null) {
            if (!_collectionSplits.containsKey(_tmpKey)) {
              _collectionSplits.put(_tmpKey, new ArrayList<ExpenseSplit>());
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipexpenseSplitsAscomExampleSmartsplitterDataExpenseSplit(_collectionSplits);
        final List<ExpenseWithSplits> _result = new ArrayList<ExpenseWithSplits>(_cursor.getCount());
        while (_cursor.moveToNext()) {
          final ExpenseWithSplits _item;
          final Expense _tmpExpense;
          if (!(_cursor.isNull(_cursorIndexOfExpenseId) && _cursor.isNull(_cursorIndexOfGroupId) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfTotalAmount) && _cursor.isNull(_cursorIndexOfCurrency) && _cursor.isNull(_cursorIndexOfPayerMemberId) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfExpenseDate) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt) && _cursor.isNull(_cursorIndexOfIsSettlement))) {
            _tmpExpense = new Expense();
            if (_cursor.isNull(_cursorIndexOfExpenseId)) {
              _tmpExpense.expenseId = null;
            } else {
              _tmpExpense.expenseId = _cursor.getString(_cursorIndexOfExpenseId);
            }
            if (_cursor.isNull(_cursorIndexOfGroupId)) {
              _tmpExpense.groupId = null;
            } else {
              _tmpExpense.groupId = _cursor.getString(_cursorIndexOfGroupId);
            }
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpExpense.description = null;
            } else {
              _tmpExpense.description = _cursor.getString(_cursorIndexOfDescription);
            }
            _tmpExpense.totalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpExpense.currency = null;
            } else {
              _tmpExpense.currency = _cursor.getString(_cursorIndexOfCurrency);
            }
            if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
              _tmpExpense.payerMemberId = null;
            } else {
              _tmpExpense.payerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
            }
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpExpense.category = null;
            } else {
              _tmpExpense.category = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpExpense.expenseDate = _cursor.getLong(_cursorIndexOfExpenseDate);
            _tmpExpense.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _tmpExpense.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettlement);
            _tmpExpense.isSettlement = _tmp != 0;
          } else {
            _tmpExpense = null;
          }
          final ArrayList<ExpenseSplit> _tmpSplitsCollection;
          final String _tmpKey_1;
          if (_cursor.isNull(_cursorIndexOfExpenseId)) {
            _tmpKey_1 = null;
          } else {
            _tmpKey_1 = _cursor.getString(_cursorIndexOfExpenseId);
          }
          if (_tmpKey_1 != null) {
            _tmpSplitsCollection = _collectionSplits.get(_tmpKey_1);
          } else {
            _tmpSplitsCollection = new ArrayList<ExpenseSplit>();
          }
          _item = new ExpenseWithSplits();
          _item.expense = _tmpExpense;
          _item.splits = _tmpSplitsCollection;
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipexpenseSplitsAscomExampleSmartsplitterDataExpenseSplit(
      @NonNull final ArrayMap<String, ArrayList<ExpenseSplit>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipexpenseSplitsAscomExampleSmartsplitterDataExpenseSplit(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `split_id`,`expense_id`,`member_id`,`amount`,`split_type`,`created_at` FROM `expense_splits` WHERE `expense_id` IN (");
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
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "expense_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfSplitId = 0;
      final int _cursorIndexOfExpenseId = 1;
      final int _cursorIndexOfMemberId = 2;
      final int _cursorIndexOfAmount = 3;
      final int _cursorIndexOfSplitType = 4;
      final int _cursorIndexOfCreatedAt = 5;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        if (_cursor.isNull(_itemKeyIndex)) {
          _tmpKey = null;
        } else {
          _tmpKey = _cursor.getString(_itemKeyIndex);
        }
        if (_tmpKey != null) {
          final ArrayList<ExpenseSplit> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final ExpenseSplit _item_1;
            _item_1 = new ExpenseSplit();
            if (_cursor.isNull(_cursorIndexOfSplitId)) {
              _item_1.splitId = null;
            } else {
              _item_1.splitId = _cursor.getString(_cursorIndexOfSplitId);
            }
            if (_cursor.isNull(_cursorIndexOfExpenseId)) {
              _item_1.expenseId = null;
            } else {
              _item_1.expenseId = _cursor.getString(_cursorIndexOfExpenseId);
            }
            if (_cursor.isNull(_cursorIndexOfMemberId)) {
              _item_1.memberId = null;
            } else {
              _item_1.memberId = _cursor.getString(_cursorIndexOfMemberId);
            }
            _item_1.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfSplitType)) {
              _item_1.splitType = null;
            } else {
              _item_1.splitType = _cursor.getString(_cursorIndexOfSplitType);
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
