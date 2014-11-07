package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.jared.ourmusic.R;


/**
 * Created by Jared on 10/5/2014.
 * Login activity that is loaded when the app is launched, or if the user requests to be logged out.
 */
public class LoginActivity extends Activity{

    private static final String TAG = "LoginActivity";
    private EditText username;
    private EditText password;
    private TextView loginText;
    private TextView passwordText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.loginBox);
        password = (EditText)findViewById(R.id.passwordBox);
        loginText = (TextView)findViewById(R.id.loginTextView);
        passwordText = (TextView)findViewById(R.id.passwordText);
        loginButton = (Button)findViewById(R.id.loginButton);
    }

    public void login(View v) {
        //Temp code
        if(username.getText().equals("Jared") && password.getText().equals("aaaa")){
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }
        else {
            Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
        }

        //TODO
        //Call to parser with username & password
        //if match then create home activity and launch
        //else toast prompt & error that credentials are incorrect
    }





}
