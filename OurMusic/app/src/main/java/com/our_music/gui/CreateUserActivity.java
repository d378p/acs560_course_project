package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jared.ourmusic.R;
import com.our_music.connection.ParseInterface;
import com.our_music.connection.Parser;
import com.our_music.connection.UserParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Jared on 11/17/2014.
 *
 * Activity for creating a new user for the app.  User is taken back to login screen after user
 * creation is successful.
 */
public class CreateUserActivity extends Activity{
    private static final String TAG = CreateUserActivity.class.getSimpleName();
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private EditText confirmEmail;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        username = (EditText)findViewById(R.id.loginBoxNewUser);
        password = (EditText)findViewById(R.id.passwordBoxNewUser);
        confirmPassword = (EditText)findViewById(R.id.confirmPasswordBoxNewUser);
        email = (EditText)findViewById(R.id.emailBoxNewUser);
        confirmEmail = (EditText)findViewById(R.id.confirmEmailBoxNewUser);
    }

    public void submitNewUserRequest(View v) {
        String sendUsername = String.valueOf(username.getText());
        String sendPassword = String.valueOf(password.getText());
        String passwordCopy = String.valueOf(confirmPassword.getText());
        String emailCopy = String.valueOf(confirmEmail.getText());
        String sendEmail = String.valueOf(email.getText());
        if(!sendPassword.equals(passwordCopy)) {
            //TODO Highlight password box, to have user fix
        } else if(!sendEmail.equals(emailCopy)) {
            //TODO Highlight email box, to have user fix
        } else {
            JSONObject newUserRequest = new JSONObject();
            try {
                newUserRequest.put("type", ParseInterface.MessageType.REQUEST.toString());
                newUserRequest.put("subject", ParseInterface.MessageType.NEW_USER.toString());
                newUserRequest.put("username", sendUsername);
                newUserRequest.put("password", sendPassword);
                newUserRequest.put("email", sendEmail);
                AsyncTask newUserParser = new UserParser().execute(newUserRequest);
                boolean result = (Boolean) newUserParser.get();
                if (result) {
                    //FIXME! Change to send back true to let us know server stored user information
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            } catch (JSONException e) {
                Log.i(TAG, e.getMessage());
                e.printStackTrace();
            } catch (Exception el) {
                Log.d(TAG, el.getMessage());
                el.printStackTrace();
            }
        }
    }
}
