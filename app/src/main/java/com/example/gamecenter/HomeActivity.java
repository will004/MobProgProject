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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ActionBar actionBar;

    Toolbar toolbar;

    TextView tvUsername, tvEmail, tvPhone, tvNoGame;

    int idx;

    ListView lvGameList;

    private void createToolbar() {
        toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void createNavigationMenu() {
        drawerLayout = findViewById(R.id.drawer_layout_home);

        navigationView = findViewById(R.id.nav_view_home);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                switch (id) {
//                    case R.id.userProfileMenu:
//                        Toast.makeText(HomeActivity.this, "User Profile", Toast.LENGTH_SHORT).show();
//                        return true;

                    case R.id.homeMenu:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.gamesMenu:
                        startActivity(new Intent(HomeActivity.this, GameListActivity.class));
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.aboutUs:
                startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        createToolbar();
        createNavigationMenu();

        Utility.listContext.add(HomeActivity.this);


        tvUsername = findViewById(R.id.homeUsername);
        tvEmail = findViewById(R.id.homeEmail);
        tvPhone = findViewById(R.id.homePhone);

        idx = Utility.idxUser;

        tvUsername.setText(Utility.data.get(idx).name);
        tvEmail.setText(Utility.data.get(idx).email);
        tvPhone.setText(Utility.data.get(idx).phone);

        tvNoGame = findViewById(R.id.noGame);


        if (Utility.data.get(idx).myGames.size() == 0) {
            tvNoGame.setText(R.string.strNoGame);
        } else {
            tvNoGame.setText("");

            //buat listView
            lvGameList = findViewById(R.id.homeGameList);
            HomeAdapter adapter = new HomeAdapter();
            lvGameList.setAdapter(adapter);
        }
    }

    private int playGame() {
        Random rand = new Random();
        return rand.nextInt(10-1)+1;
    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Utility.data.get(idx).myGames.size();
        }

        @Override
        public Object getItem(int position) {
            return Utility.data.get(idx).myGames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.row_home_gamelist, null);

            TextView tvGameTitle = convertView.findViewById(R.id.homeGameTitle);
            TextView tvGameGenre = convertView.findViewById(R.id.homeGameGenre);
            TextView tvGameDesc = convertView.findViewById(R.id.homeGameDesc);
            final TextView tvGameHours = convertView.findViewById(R.id.homeGameHours);
            Button btnPlayGame = convertView.findViewById(R.id.btnPlay);

            tvGameTitle.setText(Utility.data.get(idx).myGames.get(position).gameTitle);
            tvGameGenre.setText(Utility.data.get(idx).myGames.get(position).gameGenre);
            tvGameDesc.setText(Utility.data.get(idx).myGames.get(position).gameDesc);
            tvGameHours.setText(Utility.data.get(idx).myGames.get(position).gameHourPlay+"");

            btnPlayGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hours = playGame();
                    Utility.data.get(idx).myGames.get(position).gameHourPlay += hours;
                    tvGameHours.setText(Utility.data.get(idx).myGames.get(position).gameHourPlay+"");
                }
            });

            return convertView;
        }
    }
}
