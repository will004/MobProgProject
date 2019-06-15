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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gamecenterHelper.GameHelper;
import com.example.gamecenterHelper.MyGameHelper;
import com.example.model.Game;
import com.example.model.MyGame;

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ActionBar actionBar;

    Toolbar toolbar;

    TextView tvUsername, tvEmail, tvPhone, tvNoGame;

    MyGameHelper myGameHelper;
    GameHelper gameHelper;

    String user_id, user_name, user_email, user_phone;
    ArrayList<MyGame> myGames;
    ArrayList<Game> games;

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
                        Intent intent = new Intent(HomeActivity.this, GameListActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("user_email", user_email);
                        intent.putExtra("user_phone", user_phone);
                        startActivity(intent);
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
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
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
        setContentView(R.layout.activity_home);

        createToolbar();
        createNavigationMenu();

        Utility.listContext.add(HomeActivity.this);


        tvUsername = findViewById(R.id.homeUsername);
        tvEmail = findViewById(R.id.homeEmail);
        tvPhone = findViewById(R.id.homePhone);


        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        user_email = intent.getStringExtra("user_email");
        user_phone = intent.getStringExtra("user_phone");

        tvUsername.setText(user_name);
        tvEmail.setText(user_email);
        tvPhone.setText(user_phone);

        tvNoGame = findViewById(R.id.noGame);
        myGames = new ArrayList<>();
        games = new ArrayList<>();

        //get data from table mygames
        myGameHelper = new MyGameHelper(this);
        myGameHelper.open();
        myGames = myGameHelper.searchMyGame(user_id);
        myGameHelper.close();


        if (myGames.size() == 0) {
            tvNoGame.setText(R.string.strNoGame);
        }
        else {
            tvNoGame.setText("");
            //get game_id from mygame, then show its details here through table game
            gameHelper = new GameHelper(this);
            gameHelper.open();

            for(int i=0; i<myGames.size(); i++){
                games.add(gameHelper.getGameDetails(myGames.get(i).getGame_id()));
            }
            gameHelper.close();

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
            return myGames.size();
        }

        @Override
        public Object getItem(int position) {
            return myGames.get(position);
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


            tvGameTitle.setText(games.get(position).getGameName());
            tvGameGenre.setText(games.get(position).getGameGenre());
            tvGameDesc.setText(games.get(position).getGameDesc());
            tvGameHours.setText(myGames.get(position).getPlayinghour()+"");

            btnPlayGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hours = playGame();
                    int playhoursDB = myGames.get(position).getPlayinghour();
                    playhoursDB += hours;
                    //update to mygames
                    myGameHelper.open();
                    //update
                    myGameHelper.updatePlayingHour(myGames.get(position).getMygame_id(), playhoursDB);
                    myGameHelper.close();
                    tvGameHours.setText(playhoursDB+"");
                }
            });

            return convertView;
        }
    }
}
