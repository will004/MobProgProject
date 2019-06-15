package com.example.gamecenter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gamecenterHelper.GameHelper;
import com.example.gamecenterHelper.MyGameHelper;
import com.example.model.Game;
import com.example.model.MyGame;

import java.util.ArrayList;

public class GameDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    TextView tvGameTitle, tvStock, tvPrice, tvGenre, tvDescription;
    RatingBar rbGame;
    Button btnBuy;

    String game_id_selected;
    String user_id, user_name, user_email, user_phone;

    GameHelper gameHelper;
    MyGameHelper myGameHelper;

    Game selected_game;

    private void createToolbar() {
        toolbar = findViewById(R.id.toolbarGameDetail);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(GameDetailActivity.this, GameListActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_phone", user_phone);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isBought(String gameID){

        //cek di table MyGames, apakah user udh beli game ini atau belom
        //liat dari user_id dan game_id
        myGameHelper = new MyGameHelper(GameDetailActivity.this);
        myGameHelper.open();
        ArrayList<MyGame> myGames = myGameHelper.searchMyGame(user_id);
        myGameHelper.close();

        for(int i=0; i<myGames.size(); i++){
            if(gameID.equals(myGames.get(i).getGame_id())) return true;
        }

        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        createToolbar();
        Utility.listContext.add(GameDetailActivity.this);

        Intent intent = getIntent();
        game_id_selected = intent.getStringExtra("game_id");
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        user_email = intent.getStringExtra("user_email");
        user_phone = intent.getStringExtra("user_phone");

        tvGameTitle = findViewById(R.id.gameDetailsTitle);
        tvDescription = findViewById(R.id.gameDetailsDesc);
        tvGenre = findViewById(R.id.gameDetailsGenre);
        tvPrice = findViewById(R.id.gameDetailsPrice);
        tvStock = findViewById(R.id.gameDetailsStock);
        rbGame = findViewById(R.id.gameDetailRating);
        btnBuy = findViewById(R.id.btnBuy);

        //ambil dari database
        gameHelper = new GameHelper(this);
        gameHelper.open();
        selected_game = gameHelper.getGameDetails(game_id_selected);
        gameHelper.close();

        tvGameTitle.setText(selected_game.getGameName());
        tvDescription.setText(selected_game.getGameDesc());
        tvGenre.setText(selected_game.getGameGenre());
        tvPrice.setText(selected_game.getGamePrice()+"");
        tvStock.setText(selected_game.getGameStock()+"");
        rbGame.setRating((float) selected_game.getGameRating());


        //harus cek dia punya game atau engga
        //modify soon
        if(isBought(game_id_selected)){
            btnBuy.setEnabled(false);
        }
        else btnBuy.setEnabled(true);
        //end

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paymentConfirmation = new Intent(GameDetailActivity.this, PaymentActivity.class);
                paymentConfirmation.putExtra("game_id", game_id_selected);
                paymentConfirmation.putExtra("user_id", user_id);
                paymentConfirmation.putExtra("user_name", user_name);
                paymentConfirmation.putExtra("user_email", user_email);
                paymentConfirmation.putExtra("user_phone", user_phone);
                startActivity(paymentConfirmation);
            }
        });
    }
}
