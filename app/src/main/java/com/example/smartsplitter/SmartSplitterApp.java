package com.example.smartsplitter;

import android.app.Application;

import com.example.smartsplitter.core.DummyDataUtil;

public class SmartSplitterApp extends Application {

    public static com.example.smartsplitter.network.P2PManager p2pManager;
    private com.example.smartsplitter.data.AppRepository repository;
    private com.google.gson.Gson gson = new com.google.gson.Gson();

    @Override
    public void onCreate() {
        super.onCreate();

        // =============================================
        // DUMMY DATA - Set DummyDataUtil.ENABLED = false to disable
        // =============================================
        DummyDataUtil.populateIfEnabled(this);

        repository = new com.example.smartsplitter.data.AppRepository(this);

        p2pManager = new com.example.smartsplitter.network.P2PManager(this, (type, payload) -> {
            try {
                switch (type) {
                    case "GROUP":
                        com.example.smartsplitter.data.Group group = gson.fromJson(payload,
                                com.example.smartsplitter.data.Group.class);
                        if (group != null)
                            repository.insertGroupFromSync(group);
                        break;
                    case "MEMBER":
                        com.example.smartsplitter.data.Member member = gson.fromJson(payload,
                                com.example.smartsplitter.data.Member.class);
                        if (member != null)
                            repository.insertMemberFromSync(member);
                        break;
                    case "EXPENSE_FULL":
                        com.example.smartsplitter.data.ExpenseWithSplits expData = gson.fromJson(payload,
                                com.example.smartsplitter.data.ExpenseWithSplits.class);
                        if (expData != null)
                            repository.insertExpenseFromSync(expData);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        p2pManager.start();
    }
}
