package com.example.gamecenter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spRole;
    EditText etName, etEmail, etPass, etConfirmPass, etPhone;
    TextView birthDate;
    RadioButton rdMale, rdFemale;
    Button btnBirthdate, btnRegister;

    ActionBar actionBar;

    DatePickerDialog picker;
    Calendar calendar;

    Toolbar toolbar;

    String date = "";

    private String generateId(){
        int digit1 = (int) (Math.random()*(9-0)+1);
        int digit2 = (int) (Math.random()*(9-0)+1);
        int digit3 = (int) (Math.random()*(9-0)+1);

        return "US"+digit1+""+digit2+""+digit3;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbarReg);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        spRole = findViewById(R.id.spRole);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.strArrRole, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRole.setAdapter(adapter);
        
        btnBirthdate = findViewById(R.id.btnBirthDate);
        btnRegister = findViewById(R.id.btnRegister);

        etName = findViewById(R.id.etName);
        etPass = findViewById(R.id.etPasswordReg);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        etEmail = findViewById(R.id.etEmailReg);
        etPhone = findViewById(R.id.etPhone);
        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);

        birthDate = findViewById(R.id.tvBirthDate);

        btnBirthdate.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnRegister){
            String name, pass, confpass, email, phone, gender, role;

            name = etName.getText().toString();
            pass = etPass.getText().toString();
            confpass = etConfirmPass.getText().toString();
            email = etEmail.getText().toString();
            phone = etPhone.getText().toString();
            role = spRole.getSelectedItem().toString();
            gender = "";

            if(name.equals("")){
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else if(pass.equals("")){
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else if(confpass.equals("")){
                Toast.makeText(this, "Must confirm your password", Toast.LENGTH_SHORT).show();
            }
            else if(email.equals("")){
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else if(phone.equals("")){
                Toast.makeText(this, "Phone cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else if(name.length() < 3 || name.length() > 25){
                    Toast.makeText(this, "Name must be between 3-25 characters", Toast.LENGTH_SHORT).show();
            }
            else if(pass.length() <= 6){
                Toast.makeText(this, "Password must more than 6 characters", Toast.LENGTH_SHORT).show();
            }
            else if(!confpass.equals(pass)){
                Toast.makeText(this, "Please re-input your password correctly", Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Input your email format correctly", Toast.LENGTH_SHORT).show();
            }
            else if(phone.length() < 10 || phone.length() > 12){
                Toast.makeText(this, "Phone must be between 10-12 characters", Toast.LENGTH_SHORT).show();
            }
            else if(!rdMale.isChecked() && !rdFemale.isChecked()){
                Toast.makeText(this, "Please choose your gender", Toast.LENGTH_SHORT).show();
            }
            else if(role.equals("Please choose your role")){
                Toast.makeText(this, "You must choose your role", Toast.LENGTH_SHORT).show();
            }
            else if(date.equals("")){
                Toast.makeText(this, "Please input your birth date", Toast.LENGTH_SHORT).show();
            }
            else{
                String id = generateId();

                if(rdFemale.isChecked()) gender = rdFemale.getText().toString();
                else gender = rdMale.getText().toString();

                //add to database
                //delete soon
                Utility.data.add(new UserData(id, name, pass, email, phone, role, gender, date));
                //delete end



                Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        }

        else if(v == btnBirthdate){
            calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = String.format(" %02d/%02d/%d", dayOfMonth, month+1, year);
                    birthDate.setText(getString(R.string.strBirthday) + date);
                }
            }, year, month, day);

            picker.show();
        }
    }
}