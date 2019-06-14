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

import com.example.gamecenterHelper.UserHelper;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etEmail, etPass;

    TextView tvHere;

    Toolbar toolbar;

    String email, password;

    UserHelper userHelper;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);

        createHyperlinkHere();

        //delete soon
        /*
        if(Utility.adminLogin){
            Utility.data.add(new UserData("US000","admin", "admin123", "admin@admin.com","1234567890", "Student", "Male","02/03/1999"));
            Utility.adminLogin = false;
        }
        */
        //end

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
                    //delete soon
                    int idx =  search(email, password);

                    if(idx > -1){
                        Utility.idxUser = idx;
                    }
                    else if(idx == -1){
                        Toast.makeText(LoginActivity.this, "idx -1", Toast.LENGTH_SHORT).show();
                    }
                    //end

                    userHelper = new UserHelper(LoginActivity.this);
                    userHelper.open();

                    String user_id = userHelper.login(email, password);
                    userHelper.close();

                    if(user_id.equals("")){
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                    else if(!user_id.isEmpty()){

                        Toast.makeText(LoginActivity.this, user_id, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                    }

                }
            }
        });
    }
}
