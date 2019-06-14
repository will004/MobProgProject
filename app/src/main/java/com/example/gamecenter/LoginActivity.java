package com.example.gamecenter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamecenterHelper.GameHelper;
import com.example.gamecenterHelper.UserHelper;
import com.example.model.Game;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etEmail, etPass;

    TextView tvHere;

    Toolbar toolbar;

    String email, password;

    UserHelper userHelper;
    GameHelper gameHelper;


    String url = "https://api.myjson.com/bins/15cfg8";
    ArrayList<Game> games;

    void getDataFromJSON(){
        games = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);


                        Game game = new Game(obj.getString("id"), obj.getString("name"), obj.getString("description"), obj.getString("genre"), obj.getDouble("rating"), obj.getInt("stock"), obj.getInt("price"));

                        //insert to database
                        gameHelper = new GameHelper(LoginActivity.this);
                        gameHelper.open();
                        gameHelper.insertGame(game);
                        gameHelper.close();

                    }
                    catch (Exception e){

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }



    private void createHyperlinkHere(){
        tvHere = findViewById(R.id.tvHere);

        SpannableString ss = new SpannableString(getString(R.string.strRegisterHere));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                //ds.setColor(Color.rgb(0,0,0));
            }
        };
        ss.setSpan(clickableSpan, 32,36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView tvHere = findViewById(R.id.tvHere);
        tvHere.setText(ss);
        tvHere.setMovementMethod(LinkMovementMethod.getInstance());
        tvHere.setHighlightColor(Color.TRANSPARENT);
    }

    public int search(String emailCek, String passwordCek){
        
        for(int i=0; i<Utility.data.size(); i++){
            if(emailCek.equals(Utility.data.get(i).email) && passwordCek.equals(Utility.data.get(i).password)){
                return i;
            }
        }
        return -1;
    }

    private void pullDataFromAPI(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);

        createHyperlinkHere();

        getDataFromJSON();


        btnLogin = findViewById(R.id.btnLogIn);
        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etPasswordLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPass.getText().toString();

                if(email.equals("")){
                    Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(LoginActivity.this, "Input your email format correctly", Toast.LENGTH_SHORT).show();
                }
                else{
                    userHelper = new UserHelper(LoginActivity.this);
                    userHelper.open();
                    ArrayList<String> login = userHelper.login(email, password);
                    userHelper.close();

                    if(login.get(0).equals("")){
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                    else if(!login.get(0).isEmpty()){


                        String user_id = login.get(0);
                        String user_name = login.get(1);
                        String user_email = login.get(2);
                        String user_phone = login.get(3);

                        System.out.println(user_id);

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("user_email", user_email);
                        intent.putExtra("user_phone", user_phone);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
