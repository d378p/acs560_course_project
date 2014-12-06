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
import com.our_music.connection.AddParser;
import com.our_music.connection.ParseInterface;
import com.our_music.database.OurMusicDatabase;

import org.json.JSONObject;

/**
 * Created by Jared on 12/6/2014.
 */
public class AddFriendActivity extends Activity {
    private final String TAG = AddFriendActivity.class.getSimpleName();
    private EditText friendUserName;
    private OurMusicDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        db = new OurMusicDatabase(getApplicationContext());
        friendUserName = (EditText) findViewById(R.id.friend_username_box);
    }

    public void addFriend(View v) {
        String friendUser = String.valueOf(friendUserName.getText());
        if(friendUser.isEmpty()) {
            //TODO Toast notification to enter text into text box
        }
        else {
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("type", ParseInterface.MessageType.REQUEST.toString());
                jsonObject.put("subject", ParseInterface.MessageType.ADD_FRIEND.toString());
                jsonObject.put("friendUsername", friendUser);
                AsyncTask addFriend = new AddParser().execute(jsonObject);
                boolean result = (Boolean) addFriend.get();
                if(result) {
                    db.addFriend(jsonObject);
                    Intent returnToLast = new Intent(this, HomeActivity.class);
                    returnToLast.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(returnToLast);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Such User Exists", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
