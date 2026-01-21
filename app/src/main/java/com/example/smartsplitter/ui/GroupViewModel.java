package com.example.smartsplitter.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smartsplitter.data.AppRepository;
import com.example.smartsplitter.data.Group;
import com.example.smartsplitter.data.Member;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Group>> allGroups;

    public GroupViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allGroups = repository.getAllActiveGroups();
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }

    public void createGroup(String name, String currency, String description, String creatorName,
            String creatorUsername) {
        Group newGroup = new Group(name, currency, description);
        Member creator = new Member(newGroup.groupId, creatorName);
        if (creatorUsername != null && !creatorUsername.isEmpty()) {
            creator.username = creatorUsername;
        }
        repository.createGroupWithCreator(newGroup, creator);
    }

    public void joinGroup(Group group, String memberName) {
        // Assume group object is parsed from QR/Manual entry
        // We need to check if group exists locally first?
        // For simplicity, we just insert. OnConflict REPLACES which might be bad if we
        // overwrite local data?
        // Actually, if we join a group we already have, we should probably just ensure
        // we are a member.
        // But for "offline first" without sync, "joining" is effectively just importing
        // the group definition.

        repository.insertGroup(group);
        Member newMember = new Member(group.groupId, memberName);
        repository.insertMember(newMember);
    }
}
