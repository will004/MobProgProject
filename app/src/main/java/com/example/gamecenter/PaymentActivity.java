package com.example.gamecenter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterHelper.GameHelper;
import com.example.gamecenterHelper.MyGameHelper;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    TextView tvPrice;
    EditText etPriceInput;
    Button btnPay;

    String game_id_bought;
    int price;
    String user_id, user_name, user_email, user_phone;

    MyGameHelper myGameHelper;
    GameHelper gameHelper;

    SmsManager smsManager;

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
        price = intent.getIntExtra("game_price", 0);

        tvPrice = findViewById(R.id.paymentPrice);
        etPriceInput = findViewById(R.id.userPriceInput);
        btnPay = findViewById(R.id.btnPay);

        //modify, ambil harga game dari intent
        final int gamePrice = price;
        tvPrice.setText(gamePrice + "");
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
                        int change = input - gamePrice;

                        //kirim SMS kembalian
                        smsManager = SmsManager.getDefault();
                        String sender = "15555215554", receiver = user_phone;
                        Log.i("phone number", receiver);
                        String smstext = "Your Transaction has been completed successfully, your change is " + change +".";
                        smsManager.sendTextMessage(receiver, sender, smstext, null, null);
                        Toast.makeText(PaymentActivity.this, smstext, Toast.LENGTH_SHORT).show();

                        //masukkin ke MyGames via database
                        myGameHelper = new MyGameHelper(PaymentActivity.this);
                        myGameHelper.open();
                        myGameHelper.addMyGame(user_id, game_id_bought);
                        myGameHelper.close();

                        //update stocknya -=1
                        gameHelper = new GameHelper(PaymentActivity.this);
                        gameHelper.open();
                        gameHelper.updateStock(game_id_bought);
                        gameHelper.close();


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
