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

    int idxBought;

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
                startActivity(new Intent(PaymentActivity.this, GameListActivity.class));
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
        idxBought = intent.getIntExtra("IDX_BOUGHT", 0);

        tvPrice = findViewById(R.id.paymentPrice);
        etPriceInput = findViewById(R.id.userPriceInput);
        btnPay = findViewById(R.id.btnPay);

        final int gamePrice = Utility.games.get(idxBought).gamePrice;

        tvPrice.setText(Utility.games.get(idxBought).gamePrice + "");

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
                        //masukkin ke MyGames
                        Utility.data.get(Utility.idxUser).myGames.add(new MyGame(Utility.games.get(idxBought).gameID, Utility.games.get(idxBought).gameTitle, Utility.games.get(idxBought).gameGenre, Utility.games.get(idxBought).gameDesc, 0));

                        Utility.games.get(idxBought).gameStock -= 1;

                        int change = input - gamePrice;
                        Toast.makeText(PaymentActivity.this, "Your change is " + change, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
                        finish();
                    }
                }
            }
        });

    }
}
