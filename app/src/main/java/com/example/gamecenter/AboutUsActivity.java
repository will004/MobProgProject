package com.example.gamecenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutUsActivity extends AppCompatActivity implements OnMapReadyCallback {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar toolbar;
    ActionBar actionBar;

    GoogleMap map;

    String user_id, user_name, user_email, user_phone;

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
                        Intent intent = new Intent(AboutUsActivity.this, HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("user_email", user_email);
                        intent.putExtra("user_phone", user_phone);
                        startActivity(intent);
                        return true;

                    case R.id.gamesMenu:
                        Intent intent1 = new Intent(AboutUsActivity.this, GameListActivity.class);
                        intent1.putExtra("user_id", user_id);
                        intent1.putExtra("user_name", user_name);
                        intent1.putExtra("user_email", user_email);
                        intent1.putExtra("user_phone", user_phone);
                        startActivity(intent1);
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

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        user_email = intent.getStringExtra("user_email");
        user_phone = intent.getStringExtra("user_phone");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //Add marker
        LatLng office = new LatLng(-6.2178652, 106.7335872);

        map.addMarker(new MarkerOptions().position(office).title("Game Center Office"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(office, 15));
    }
}