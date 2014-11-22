package com.our_music.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jared.ourmusic.R;

/**
 * Created by Jared at some point in time
 *
 * Activity for main page of app.  Displays a current song list and friend list of the user.
 */

public class HomeActivity extends Activity {

    private TextView songList;
    private TextView friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        songList = (TextView)findViewById(R.id.recentSongList);
        friendList = (TextView)findViewById(R.id.friendsList);
        fillSongList();
        fillFriends();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO - Change to getSongList and request songs from server/local db
    private void fillSongList() {
        for(int i = 1; i < 6; i++) {
            songList.append(String.format("Song# %d\n", i));
        }
    }

    //TODO - Change to getFriends and request friends from server/local db
    private void fillFriends() {
        for(int i = 1; i <6; i++) {
            friendList.append(String.format("Fname Lname#%d\n", i));
        }
    }
}
