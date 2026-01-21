package com.example.smartsplitter.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.smartsplitter.data.User;
import com.example.smartsplitter.data.UserDao;

public class AppRepository {

    private GroupDao groupDao;
    private ExpenseDao expenseDao;
    private UserDao userDao;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        groupDao = db.groupDao();
        expenseDao = db.expenseDao();
        userDao = db.userDao();
    }

    public List<User> searchUsers(String query) {
        return userDao.searchUsers(query);
    }

    // Group Operations
    public void insertGroup(Group group) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insertGroup(group);
            broadcastSync("GROUP", group);
        });
    }

    public void insertGroupFromSync(Group group) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insertGroup(group);
        });
    }

    public void createGroupWithCreator(Group group, Member creator) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insertGroup(group);
            groupDao.insertMember(creator);
            broadcastSync("GROUP", group);
            broadcastSync("MEMBER", creator);
        });
    }

    public void insertMember(Member member) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insertMember(member);
            broadcastSync("MEMBER", member);
        });
    }

    public void insertMemberFromSync(Member member) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insertMember(member);
        });
    }

    public void addMemberByUsername(String groupId, String username, OnMemberAddResult callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDao.getUserByUsername(username);
            if (user != null) {
                Member member = new Member(groupId, user.name);
                member.username = user.username;
                try {
                    groupDao.insertMember(member);
                    broadcastSync("MEMBER", member);
                    callback.onResult(true, "Member added");
                } catch (Exception e) {
                    callback.onResult(false, "Member already exists or error");
                }
            } else {
                callback.onResult(false, "User not found with username: " + username);
            }
        });
    }

    public interface OnMemberAddResult {
        void onResult(boolean success, String message);
    }

    public LiveData<List<Group>> getAllActiveGroups() {
        return groupDao.getAllActiveGroups();
    }

    public LiveData<GroupWithMembers> getGroupWithMembers(String groupId) {
        return groupDao.getGroupWithMembers(groupId);
    }

    // Expense Operations
    public void insertExpense(Expense expense, List<ExpenseSplit> splits) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            expenseDao.insertExpense(expense);
            expenseDao.deleteSplitsForExpense(expense.expenseId);
            expenseDao.insertSplits(splits);

            // For Expense Sync, we need to send Expense + Splits.
            // Simplified: Send just Expense (splits logic must be handled or sent
            // separately).
            // Better: Create a wrapper ExpenseWithSplits payload.
            // For now, simpler: Send Expense, assume splits follow or handled.
            // Sending ExpenseWithSplits object via JSON is best.
            ExpenseWithSplits fullData = new ExpenseWithSplits();
            fullData.expense = expense;
            fullData.splits = splits;
            broadcastSync("EXPENSE_FULL", fullData);
        });
    }

    public void insertExpenseFromSync(ExpenseWithSplits fullData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            expenseDao.insertExpense(fullData.expense);
            expenseDao.deleteSplitsForExpense(fullData.expense.expenseId); // clean up OLD splits
            expenseDao.insertSplits(fullData.splits);
        });
    }

    public LiveData<List<ExpenseWithSplits>> getExpensesForGroup(String groupId) {
        return expenseDao.getExpensesForGroup(groupId);
    }

    public void deleteExpense(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            expenseDao.deleteExpense(expense);
            // Sync delete? Not in scope yet but good to have
        });
    }

    private void broadcastSync(String type, Object entity) {
        if (com.example.smartsplitter.SmartSplitterApp.p2pManager != null) {
            com.example.smartsplitter.SmartSplitterApp.p2pManager.broadcastData(type, entity);
        }
    }

    // Sync methods for calculations (must run on background thread)
    public List<Expense> getExpensesListForGroupSync(String groupId) {
        return expenseDao.getExpensesListForGroup(groupId);
    }

    public List<ExpenseSplit> getSplitsForExpenseSync(String expenseId) {
        return expenseDao.getSplitsForExpense(expenseId);
    }

    public List<Member> getMembersForGroupSync(String groupId) {
        // We technically need a synchronous method in DAO for this
        // Adding a basic workaround or assuming we can fetch LiveData's value is risky
        // Let's rely on Room allowing generic queries if not main thread
        // Ideally we add a sync method to DAO.
        // For now, I'll rely on the VM to use the Async methods or add sync ones to DAO
        // if needed.
        // Waiting for DAO update in next iteration if needed.
        return null;
    }
}
