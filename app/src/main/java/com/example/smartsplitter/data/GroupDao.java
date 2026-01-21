package com.example.smartsplitter.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGroup(Group group);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMember(Member member);

    @Update
    void updateGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("SELECT * FROM groups WHERE is_archived = 0 ORDER BY updated_at DESC")
    LiveData<List<Group>> getAllActiveGroups();

    @Transaction
    @Query("SELECT * FROM groups WHERE is_archived = 0 ORDER BY updated_at DESC")
    LiveData<List<GroupWithMembers>> getAllActiveGroupsWithMembers();

    @Transaction
    @Query("SELECT * FROM groups WHERE group_id = :groupId")
    LiveData<GroupWithMembers> getGroupWithMembers(String groupId);

    @Query("SELECT * FROM members WHERE group_id = :groupId")
    LiveData<List<Member>> getGroupMembers(String groupId);

    @Query("SELECT * FROM groups WHERE group_id = :groupId")
    Group getGroupById(String groupId);

    @Query("SELECT * FROM groups WHERE is_archived = 0 ORDER BY updated_at DESC")
    List<Group> getAllActiveGroupsSync();

    @Query("SELECT * FROM members WHERE group_id = :groupId")
    List<Member> getGroupMembersSync(String groupId);
}
