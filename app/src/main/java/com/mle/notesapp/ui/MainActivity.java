package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.mle.notesapp.R;

public class MainActivity extends AppCompatActivity implements ToolbarHandler {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new NoteListFragment())
                    .commit();
        }


        drawerLayout = findViewById(R.id.drawer_bar);
        NavigationView navigationView = findViewById(R.id.navigation_bar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.notes:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new NoteListFragment())
                                .commit();
                        drawerLayout.close();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new NoteSettingsFragment())
                                .commit();
                        drawerLayout.close();
                        return true;

                    case R.id.add_note:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new NoteAddFragment())
                                .commit();
                        drawerLayout.close();
                        return true;

                }
                return false;
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_notes:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new NoteListFragment())
                                .commit();
                        return true;

                    case R.id.action_info:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new NoteInfoFragment())
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void setToolBar(MaterialToolbar toolbar) {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}