package com.example.savss.expensetracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private int selectedID = -1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_transaction:
                    return setFragment(new AddTransactionFragment(), R.id.navigation_add_transaction);
                case R.id.navigation_dashboard:
                    return setFragment(new DashboardFragment(), R.id.navigation_dashboard);
                case R.id.navigation_settings:
                    // return setFragment(new SettingsFragment(), R.id.navigation_settings);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean setFragment(Fragment fragment, int id) {
        if (selectedID == id) {
            return false;
        }
        selectedID = id;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
        return true;
    }

}
