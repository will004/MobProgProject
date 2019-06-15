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

public class GameDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    TextView tvGameTitle, tvStock, tvPrice, tvGenre, tvDescription;
    RatingBar rbGame;
    Button btnBuy;

    int idxLv;

    String user_id, user_name, user_email, user_phone;

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

        for(int i=0; i<Utility.data.get(Utility.idxUser).myGames.size(); i++){
            if(gameID.equals(Utility.data.get(Utility.idxUser).myGames.get(i).gameID)){
                return true;
            }
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
        idxLv = intent.getIntExtra("IDX_GAMES_LISTVIEW",0);

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

        tvGameTitle.setText(Utility.games.get(idxLv).gameTitle);
        tvDescription.setText(Utility.games.get(idxLv).gameDesc);
        tvGenre.setText(Utility.games.get(idxLv).gameGenre);
        tvPrice.setText(Utility.games.get(idxLv).gamePrice+"");
        tvStock.setText(Utility.games.get(idxLv).gameStock+"");
        rbGame.setRating(Utility.games.get(idxLv).gameRating);

        //harus cek dia punya game atau engga
        if(isBought(Utility.games.get(idxLv).gameID)){
            btnBuy.setEnabled(false);
        }
        else btnBuy.setEnabled(true);


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utility.games.get(idxLv).gameStock -= 1;
//                if(Utility.games.get(idxLv).gameStock < 0){
//                    Utility.games.get(idxLv).gameStock = 0;
//                }
                Intent paymentConfirmation = new Intent(GameDetailActivity.this, PaymentActivity.class);
                paymentConfirmation.putExtra("IDX_BOUGHT", idxLv);
                startActivity(paymentConfirmation);
            }
        });
    }
}
