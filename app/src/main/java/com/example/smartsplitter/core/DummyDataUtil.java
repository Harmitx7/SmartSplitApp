package com.example.smartsplitter.core;

import android.content.Context;

import com.example.smartsplitter.data.AppDatabase;
import com.example.smartsplitter.data.Expense;
import com.example.smartsplitter.data.ExpenseSplit;
import com.example.smartsplitter.data.Group;
import com.example.smartsplitter.data.Member;
import com.example.smartsplitter.data.User;

import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * ======================================
 * DUMMY DATA UTILITY - REMOVE BEFORE PRODUCTION
 * ======================================
 * 
 * Toggle ENABLED to true to populate sample data on app start.
 * Set to false or delete this file entirely for production.
 */
public class DummyDataUtil {

        // =============================================
        // TOGGLE THIS TO ENABLE/DISABLE DUMMY DATA
        // =============================================
        public static final boolean ENABLED = true;
        // =============================================

        public static void populateIfEnabled(Context context) {
                if (!ENABLED)
                        return;

                Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase db = AppDatabase.getDatabase(context);

                        // Check if updated data already exists (using v2 ID)
                        Group existingGroup = db.groupDao().getGroupById("dummy-trip-goa-v2");
                        if (existingGroup != null) {
                                return;
                        }

                        // We intentionally use new IDs to force populate if old dummy data exists
                        // Ideally we would clear old dummy data, but simple addition is safer.

                        long now = System.currentTimeMillis();

                        // 1. Create OUR User (Jenil Soni)
                        User me = new User("j@j.j", "Jenil Soni", "qwerty", "jenil"); // PASSWORD HASH logic ignored for
                                                                                      // dummy
                        db.userDao().insertUser(me);

                        // 2. Create Groups
                        Group trip = new Group("Trip to Goa", "INR", "Weekend getaway");
                        trip.groupId = "dummy-trip-goa-v2";
                        Group flatmates = new Group("Flatmates", "INR", "Monthly expenses");
                        flatmates.groupId = "dummy-flatmates-v2";

                        db.groupDao().insertGroup(trip);
                        db.groupDao().insertGroup(flatmates);

                        // 3. Create Other Users
                        User uArav = new User("arav@example.com", "Arav Sharma", "hash", "arav");
                        User uVihaan = new User("vihaan@example.com", "Vihaan Patel", "hash", "vihaan");
                        db.userDao().insertUser(uArav);
                        db.userDao().insertUser(uVihaan);

                        // 4. Add Members (Jenil in EVERY group)

                        // Trip Members: Jenil, Arav, Vihaan
                        Member mJenilTrip = new Member(trip.groupId, me.name);
                        mJenilTrip.username = me.username;

                        Member mAravTrip = new Member(trip.groupId, uArav.name);
                        mAravTrip.username = uArav.username;

                        Member mVihaanTrip = new Member(trip.groupId, uVihaan.name);
                        mVihaanTrip.username = uVihaan.username;

                        db.groupDao().insertMember(mJenilTrip);
                        db.groupDao().insertMember(mAravTrip);
                        db.groupDao().insertMember(mVihaanTrip);

                        // Flatmates Members: Jenil, Arav
                        Member mJenilFlat = new Member(flatmates.groupId, me.name);
                        mJenilFlat.username = me.username;

                        Member mAravFlat = new Member(flatmates.groupId, uArav.name);
                        mAravFlat.username = uArav.username;

                        db.groupDao().insertMember(mJenilFlat);
                        db.groupDao().insertMember(mAravFlat);

                        // 5. Create Expenses

                        // Dinner (Jenil Paid, split 3 ways) - I should be able to see others owe me
                        Expense dinner = new Expense(trip.groupId, "Dinner at Fisherman's Wharf", 3000.00, "INR",
                                        mJenilTrip.memberId, "Food", now - 86400000, false);
                        db.expenseDao().insertExpense(dinner);

                        db.expenseDao().insertSplits(Arrays.asList(
                                        new ExpenseSplit(dinner.expenseId, mJenilTrip.memberId, 1000.00, "equal"),
                                        new ExpenseSplit(dinner.expenseId, mAravTrip.memberId, 1000.00, "equal"),
                                        new ExpenseSplit(dinner.expenseId, mVihaanTrip.memberId, 1000.00, "equal")));

                        // Hotel (Arav Paid, split 3 ways) - I owe Arav
                        Expense hotel = new Expense(trip.groupId, "Hotel Booking", 6000.00, "INR",
                                        mAravTrip.memberId, "Accommodation", now - 172800000, false);
                        db.expenseDao().insertExpense(hotel);

                        db.expenseDao().insertSplits(Arrays.asList(
                                        new ExpenseSplit(hotel.expenseId, mJenilTrip.memberId, 2000.00, "equal"),
                                        new ExpenseSplit(hotel.expenseId, mAravTrip.memberId, 2000.00, "equal"),
                                        new ExpenseSplit(hotel.expenseId, mVihaanTrip.memberId, 2000.00, "equal")));

                        // Rent (Jenil Paid, Arav owes)
                        Expense rent = new Expense(flatmates.groupId, "Rent", 20000.00, "INR",
                                        mJenilFlat.memberId, "Rent", now, false);
                        db.expenseDao().insertExpense(rent);

                        db.expenseDao().insertSplits(Arrays.asList(
                                        new ExpenseSplit(rent.expenseId, mJenilFlat.memberId, 10000.00, "equal"),
                                        new ExpenseSplit(rent.expenseId, mAravFlat.memberId, 10000.00, "equal")));

                });
        }
}
