package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.jared.ourmusic.R;
import com.our_music.connection.ParseInterface;
import com.our_music.connection.Parser;
import com.our_music.connection.UserParser;

import org.json.JSONObject;


/**
 * Created by Jared on 10/5/2014.
 * Login activity that is loaded when the app is launched, or if the user requests to be logged out.
 */
public class LoginActivity extends Activity{

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText username;
    private EditText password;
    private TextView loginText;
    private TextView passwordText;
    private Button loginButton;
    private final String LOGIN_VALID = "TRUE";
    private final String LOGIN_INVALID = "FALSE";

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

    public void login(View v) throws Exception{
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        JSONObject login = new JSONObject();
        login.put("type", ParseInterface.MessageType.REQUEST.toString());
        login.put("subject", ParseInterface.MessageType.LOGIN.toString());
        login.put("username", user);
        login.put("password", pass);
        AsyncTask loginParser = new UserParser().execute(login);
        boolean result = (Boolean)loginParser.get();
        //Log.d(TAG, result);
        if(result){
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        } else {
            Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
        }
    }

    public void createUser(View v) {
        Intent createUser = new Intent(this, CreateUserActivity.class);
        startActivity(createUser);
    }





}
