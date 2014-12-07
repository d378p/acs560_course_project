package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.example.jared.ourmusic.R;
import com.our_music.connection.InitDbParser;
import com.our_music.connection.ParseInterface;
import com.our_music.connection.UserParser;
import com.our_music.database.OurMusicDatabase;

import org.json.JSONObject;


/**
 * Created by Jared on 10/5/2014.
 * Login activity that is loaded when the app is launched, or if the user requests to be logged out.
 */
public class LoginActivity extends Activity{

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText username;
    private EditText password;
    private OurMusicDatabase db;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.loginBox);
        password = (EditText)findViewById(R.id.passwordBox);
        db = new OurMusicDatabase(getApplicationContext());
    }

    /**
     * When login button is clicked, verifies if username and password exist on remote server and
     * lets user continue if so.  Otherwise the user can attempt to login again.
     * @param v
     * @throws Exception
     */
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
        if(result) {
            Log.i(TAG, user + "logged in successfully");
            JSONObject init = new JSONObject();
            init.put("type", ParseInterface.MessageType.REQUEST.toString());
            init.put("subject", ParseInterface.MessageType.INIT.toString());
            AsyncTask initDb = new InitDbParser().execute(init);
            JSONObject copyOfRemoteDb = (JSONObject) initDb.get();
            db.initializeDatabse(copyOfRemoteDb);
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        } else {
            Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Login failed for user: " + user);
        }
    }

    /**
     * Launches a new activity to create a user when Create User button is clicked
     * @param v
     */
    public void createUser(View v) {
        Intent createUser = new Intent(this, CreateUserActivity.class);
        startActivity(createUser);
    }





}
