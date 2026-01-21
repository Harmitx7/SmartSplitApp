package com.example.smartsplitter.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithMembers {
    @Embedded
    public Group group;

    @Relation(
        parentColumn = "group_id",
        entityColumn = "group_id"
    )
    public List<Member> members;
}
