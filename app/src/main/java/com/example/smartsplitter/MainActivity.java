package com.example.smartsplitter;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsplitter.ui.AddExpenseFragment;
import com.example.smartsplitter.ui.CreateGroupFragment;
import com.example.smartsplitter.ui.GroupDetailFragment;
import com.example.smartsplitter.ui.GroupListFragment;
import com.example.smartsplitter.ui.HistoryFragment;
import com.example.smartsplitter.ui.JoinGroupFragment;
import com.example.smartsplitter.ui.SettingsFragment;
import com.example.smartsplitter.ui.StatsFragment;

import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

        private View navHome, navStats, navHistory, navSettings, navCenterFab;
        private ImageView navHomeIcon, navStatsIcon, navHistoryIcon, navSettingsIcon;
        private TextView navHomeLabel, navStatsLabel, navHistoryLabel, navSettingsLabel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                // Check Session
                com.example.smartsplitter.utils.SessionManager session = new com.example.smartsplitter.utils.SessionManager(
                                this);
                if (!session.isLoggedIn()) {
                        Intent intent = new Intent(this, com.example.smartsplitter.auth.AuthActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                }

                setContentView(R.layout.activity_main);

                setupBottomNav();

                if (savedInstanceState == null) {
                        getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, new GroupListFragment())
                                        .commit();
                        setActiveTab(0);
                }
        }

        private void setupBottomNav() {
                navHome = findViewById(R.id.nav_home);
                navStats = findViewById(R.id.nav_stats);
                navHistory = findViewById(R.id.nav_history);
                navSettings = findViewById(R.id.nav_settings);
                navCenterFab = findViewById(R.id.nav_center_fab);

                navHomeIcon = findViewById(R.id.nav_home_icon);
                navStatsIcon = findViewById(R.id.nav_stats_icon);
                navHistoryIcon = findViewById(R.id.nav_history_icon);
                navSettingsIcon = findViewById(R.id.nav_settings_icon);

                navHomeLabel = findViewById(R.id.nav_home_label);
                navStatsLabel = findViewById(R.id.nav_stats_label);
                navHistoryLabel = findViewById(R.id.nav_history_label);
                navSettingsLabel = findViewById(R.id.nav_settings_label);

                navHome.setOnClickListener(v -> {
                        getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple)
                                        .replace(R.id.fragment_container, new GroupListFragment())
                                        .commit();
                        setActiveTab(0);
                });

                navStats.setOnClickListener(v -> {
                        // Stats Tab
                        getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple)
                                        .replace(R.id.fragment_container, new StatsFragment())
                                        .commit();
                        setActiveTab(1);
                });

                navCenterFab.setOnClickListener(v -> {
                        // Center FAB = Create new group
                        showCreateGroupFragment();
                });

                navHistory.setOnClickListener(v -> {
                        // History Tab
                        getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple)
                                        .replace(R.id.fragment_container, new HistoryFragment())
                                        .commit();
                        setActiveTab(2);
                });

                navSettings.setOnClickListener(v -> {
                        // Settings
                        getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple)
                                        .replace(R.id.fragment_container, new SettingsFragment())
                                        .commit();
                        setActiveTab(3);
                });
        }

        private void setActiveTab(int index) {
                int activeColor = getResources().getColor(R.color.accent_lime);
                int inactiveColor = getResources().getColor(R.color.text_muted);

                navHomeLabel.setTextColor(index == 0 ? activeColor : inactiveColor);
                navStatsLabel.setTextColor(index == 1 ? activeColor : inactiveColor);
                navHistoryLabel.setTextColor(index == 2 ? activeColor : inactiveColor);
                navSettingsLabel.setTextColor(index == 3 ? activeColor : inactiveColor);

                navHomeIcon.setColorFilter(index == 0 ? activeColor : inactiveColor);
                navStatsIcon.setColorFilter(index == 1 ? activeColor : inactiveColor);
                navHistoryIcon.setColorFilter(index == 2 ? activeColor : inactiveColor);
                navSettingsIcon.setColorFilter(index == 3 ? activeColor : inactiveColor);
        }

        public void showCreateGroupFragment() {
                getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple,
                                                R.anim.slide_up_enter,
                                                R.anim.fade_out_simple)
                                .replace(R.id.fragment_container, new CreateGroupFragment())
                                .addToBackStack(null)
                                .commit();
        }

        public void navigateToGroupDetail(String groupId) {
                getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple,
                                                R.anim.slide_up_enter,
                                                R.anim.fade_out_simple)
                                .replace(R.id.fragment_container, GroupDetailFragment.newInstance(groupId))
                                .addToBackStack(null)
                                .commit();
        }

        public void showAddExpenseFragment(String groupId) {
                getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple,
                                                R.anim.slide_up_enter,
                                                R.anim.fade_out_simple)
                                .replace(R.id.fragment_container, AddExpenseFragment.newInstance(groupId))
                                .addToBackStack(null)
                                .commit();
        }

        public void showJoinGroupFragment() {
                getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple,
                                                R.anim.slide_up_enter,
                                                R.anim.fade_out_simple)
                                .replace(R.id.fragment_container, new JoinGroupFragment())
                                .addToBackStack(null)
                                .commit();
        }

        public void showStatsFragment() {
                getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple,
                                                R.anim.slide_up_enter,
                                                R.anim.fade_out_simple)
                                .replace(R.id.fragment_container, new StatsFragment())
                                .addToBackStack(null)
                                .commit();
        }

        public void showSettingsFragment() {
                getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_up_enter, R.anim.fade_out_simple,
                                                R.anim.slide_up_enter,
                                                R.anim.fade_out_simple)
                                .replace(R.id.fragment_container, new SettingsFragment())
                                .addToBackStack(null)
                                .commit();
        }
}
