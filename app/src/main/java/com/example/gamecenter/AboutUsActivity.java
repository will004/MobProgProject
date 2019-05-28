package com.example.gamecenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AboutUsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar toolbar;
    ActionBar actionBar;

    private void createToolbar(){
        toolbar = findViewById(R.id.toolbarAboutUs);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void createNavigationMenu(){
        drawerLayout = findViewById(R.id.drawer_layout_aboutUs);

        navigationView = findViewById(R.id.nav_view_aboutUs);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                switch (id) {
//                    case R.id.userProfileMenu:
//                        Toast.makeText(AboutUsActivity.this, "User Profile", Toast.LENGTH_SHORT).show();
//                        return true;

                    case R.id.homeMenu:
                        startActivity(new Intent(AboutUsActivity.this, HomeActivity.class));
                        return true;

                    case R.id.gamesMenu:
                        startActivity(new Intent(AboutUsActivity.this, GameListActivity.class));
                        return true;

                    case R.id.logoutMenu:
                        Utility.finishActivities();
                        Utility.idxUser = -1;
                        return true;

                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.aboutUs:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        createToolbar();
        createNavigationMenu();

        Utility.listContext.add(AboutUsActivity.this);
    }
}