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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gamecenterHelper.GameHelper;
import com.example.model.Game;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ListView lvGamesGameList;

    GameHelper gameHelper;
    ArrayList<Game> games;

    String user_id, user_name, user_email, user_phone;

    private void createToolbar() {
        toolbar = findViewById(R.id.toolbarGame);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void createNavigationMenu() {
        drawerLayout = findViewById(R.id.drawer_layout_game);
        navigationView = findViewById(R.id.nav_view_game);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
//                    case R.id.userProfileMenu:
//                        Toast.makeText(GameListActivity.this, "User Profile", Toast.LENGTH_SHORT).show();
//                        return true;

                    case R.id.homeMenu:
                        Intent intent = new Intent(GameListActivity.this, HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("user_email", user_email);
                        intent.putExtra("user_phone", user_phone);
                        startActivity(intent);
                        return true;

                    case R.id.gamesMenu:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.logoutMenu:
                        Utility.finishActivities();
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
                Intent intent = new Intent(GameListActivity.this, AboutUsActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_phone", user_phone);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        createToolbar();
        createNavigationMenu();

        Utility.listContext.add(GameListActivity.this);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        user_email = intent.getStringExtra("user_email");
        user_phone = intent.getStringExtra("user_phone");

        //get games from database
        gameHelper = new GameHelper(this);
        gameHelper.open();
        games = gameHelper.getGames();
        gameHelper.close();


        lvGamesGameList = findViewById(R.id.gameGameList);

        GamesAdapter adapter = new GamesAdapter();
        lvGamesGameList.setAdapter(adapter);


        lvGamesGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GameListActivity.this, GameDetailActivity.class);

                intent.putExtra("game_id", games.get(position).getGameID());
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_phone", user_phone);

                startActivity(intent);
            }
        });
    }

    class GamesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return games.size();
        }

        @Override
        public Object getItem(int position) {
            return games.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.row_games_gamelist, null);

            TextView tvGamesTitle = convertView.findViewById(R.id.gamesTitle);
            TextView tvGamesDesc = convertView.findViewById(R.id.gamesDesc);
            RatingBar rbGamesRating = convertView.findViewById(R.id.gamesRating);

            tvGamesTitle.setText(games.get(position).getGameName());
            tvGamesDesc.setText(games.get(position).getGameDesc());
            rbGamesRating.setRating((float) games.get(position).getGameRating());

            return convertView;
        }
    }
}
