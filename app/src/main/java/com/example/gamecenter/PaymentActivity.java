package com.example.gamecenter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    TextView tvPrice;
    EditText etPriceInput;
    Button btnPay;

    String game_id_bought;

    String user_id, user_name, user_email, user_phone;

    private void createToolbar() {
        toolbar = findViewById(R.id.toolbarPayment);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PaymentActivity.this, GameListActivity.class);
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
        setContentView(R.layout.activity_payment);

        Utility.listContext.add(PaymentActivity.this);

        createToolbar();

        Intent intent = getIntent();
        game_id_bought = intent.getStringExtra("game_id");
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        user_email = intent.getStringExtra("user_email");
        user_phone = intent.getStringExtra("user_phone");

        //modify
        tvPrice = findViewById(R.id.paymentPrice);
        etPriceInput = findViewById(R.id.userPriceInput);
        btnPay = findViewById(R.id.btnPay);
        final int gamePrice = Utility.games.get(game_id_bought).gamePrice;
        tvPrice.setText(Utility.games.get(game_id_bought).gamePrice + "");
        //end

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceInput = etPriceInput.getText().toString();

                if (priceInput.equals("")) {
                    Toast.makeText(PaymentActivity.this, "You must input your payment", Toast.LENGTH_SHORT).show();
                }

                else {
                    int input = Integer.parseInt(priceInput);
                    if (input < gamePrice) {
                        Toast.makeText(PaymentActivity.this, "Your input money is less than the price", Toast.LENGTH_SHORT).show();
                    } else {
                        //masukkin ke MyGames via database

                        //update stocknya -=1

                        //delete
                        Utility.data.get(Utility.idxUser).myGames.add(new MyGame(Utility.games.get(game_id_bought).gameID, Utility.games.get(game_id_bought).gameTitle, Utility.games.get(game_id_bought).gameGenre, Utility.games.get(game_id_bought).gameDesc, 0));

                        Utility.games.get(game_id_bought).gameStock -= 1;
                        //end

                        int change = input - gamePrice;

                        //kirim SMS kembalian, bukan toast
                        Toast.makeText(PaymentActivity.this, "Your change is " + change, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("user_email", user_email);
                        intent.putExtra("user_phone", user_phone);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
}
