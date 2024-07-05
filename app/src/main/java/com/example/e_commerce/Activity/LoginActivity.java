package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText txt_usrename, txt_password;
    private TextView textView_forgot_password, textView_signup;
    private CheckBox checkBox_remember_me;
    private Boolean remember_me = false;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        db = new Database(this);

        checkBox_remember_me = findViewById(R.id.login_checkBox_remember_me);
        txt_usrename = findViewById(R.id.login_txt_username);
        txt_password = findViewById(R.id.login_txt_password);
        btn_login = findViewById(R.id.login_btn_login);

        // Create a proxy object for the login button click listener
        View.OnClickListener loginClickListener = new LoginClickListenerProxy(new LoginClickListener());
        btn_login.setOnClickListener(loginClickListener);

        textView_signup = findViewById(R.id.login_tv_signup);
        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        textView_forgot_password = findViewById(R.id.login_tv_forgot_password);
        textView_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    // Proxy class for the login button click listener
    //Real Subject (LoginClickListener):
    //Proxy (LoginClickListenerProxy):
    //Client (LoginActivity):
    //
    private class LoginClickListenerProxy implements View.OnClickListener {
        private LoginClickListener realClickListener;

        public LoginClickListenerProxy(LoginClickListener realClickListener) {
            this.realClickListener = realClickListener;
        }

        @Override
        public void onClick(View view) {
            if (checkBox_remember_me.isChecked()) {
                remember_me = true;
            } else {
                remember_me = false;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("remember_me", remember_me);
            myEdit.apply();

            // Perform any additional operations or checks before delegating to the real click listener
            String username = txt_usrename.getText().toString();
            String password = txt_password.getText().toString();

            if (username.isEmpty()) {
                txt_usrename.setError("Please Enter Email");
                txt_usrename.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                txt_password.setError("Please Enter Password");
                txt_password.requestFocus();
                return;
            }

            // Delegate the remaining logic to the real click listener
            realClickListener.onClick(view);
        }
    }

    // Real class for the login button click listener
    private class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String username = txt_usrename.getText().toString();
            String password = txt_password.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                if (username.equals("admin") && password.equals("admin")) {
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                } else {
                    Cursor cursor = db.user_login(username, password);
                    if (cursor.getCount() > 0) {
                        User user = User.getInstance();
                        user.setId(cursor.getInt(0));
                        user.setName(cursor.getString(1));
                        user.setEmail(cursor.getString(2));
                        user.setPassword(cursor.getString(3));
                        user.setGender(cursor.getString(4));
                        user.setBirthdate(cursor.getString(5));
                        user.setJob(cursor.getString(6));

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putInt("id", cursor.getInt(0));
                        myEdit.putString("name", cursor.getString(1));
                        myEdit.putString("email", cursor.getString(2));
                        myEdit.putString("password", cursor.getString(3));
                        myEdit.putString("gender", cursor.getString(4));
                        myEdit.putString("birthdate", cursor.getString(5));
                        myEdit.putString("job", cursor.getString(6));
                        myEdit.apply();

                        finish();
                        startActivity(new Intent(LoginActivity.this, UserActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Make sure your data is correct or sign up if you haven't registered", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please fill inyour data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}