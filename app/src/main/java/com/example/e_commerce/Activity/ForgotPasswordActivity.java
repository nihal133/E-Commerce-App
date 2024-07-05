package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.R;

public class ForgotPasswordActivity extends AppCompatActivity {
     //initialization
    EditText txt_email;
    Button btn_get_password;
    TextView textView_password;
    TextView forgot_password_tv_password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        //new object of database
        Database db = new Database(this);
        //set title
        setTitle("Forgot Password");
        // get item by id
        txt_email = (EditText) findViewById(R.id.forgot_password_txt_email);
        textView_password = (TextView) findViewById(R.id.login_tv_forgot_password);
        forgot_password_tv_password = findViewById(R.id.forgot_password_tv_password);
        btn_get_password = (Button) findViewById(R.id.forgot_password_btn_get_password);
        btn_get_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Forgot password code
                String email = txt_email.getText().toString();
                if(!email.isEmpty()){
                    //call function forget password in database
                    String password = db.frgot_password(email);
                    //check if password is not null (email in database and password)
                    if(password != null)
                        //return password
                        forgot_password_tv_password.setText(password);
                    else
                        //message if not find mail
                        Toast.makeText(getApplicationContext(), "Make sure from your email", Toast.LENGTH_SHORT).show();
                }
                // if email is empty error message
                else{
                    Toast.makeText(getApplicationContext(), "Enter your mail first", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}