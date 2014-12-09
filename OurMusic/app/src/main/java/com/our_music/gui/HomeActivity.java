package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jared.ourmusic.R;
import com.our_music.connection.ClientConnection;
import com.our_music.database.OurMusicDatabase;
import com.our_music.database.Song;
import com.our_music.database.User;

import java.util.List;

/**
 * Created by Jared at some point in time
 *
 * Activity for main page of app.  Displays a current song list and friend list of the user.
 */

public class HomeActivity extends Activity {

    private TextView songList;
    private TextView friendList;
    private OurMusicDatabase db;
    private List<Song> songs;
    private List<User> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        songList = (TextView)findViewById(R.id.recentSongList);
        friendList = (TextView)findViewById(R.id.friendsList);
        db = new OurMusicDatabase(getApplicationContext());
        updateLists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        ClientConnection.getInstance().resetConnection();
        Intent toLoginScreen = new Intent(this, LoginActivity.class);
        toLoginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toLoginScreen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLists();
    }

    private void fillSongList() {
        for(int i = 0; i < songs.size() && i < 5; i++)
            songList.append(songs.get(i).toString() + "\n");
    }

    private void fillFriends() {
        for(int i = 0; i < friends.size() && i < 5; i++)
            friendList.append(friends.get(i).toString() + "\n");
    }

    public void goToSearch(View v) {
        Intent toSearch = new Intent(this, SearchActivity.class);
        startActivity(toSearch);
    }

    public void launchAddSong(View v) {
        Intent addSong = new Intent(this, AddSongActivity.class);
        startActivity(addSong);
    }

    public void launchAddFriend(View v) {
        Intent addFriend = new Intent(this, AddFriendActivity.class);
        startActivity(addFriend);
    }

    private void updateLists() {
        songList.setText("");
        songs = db.retrieveUserSongs();
        fillSongList();
        friendList.setText("");
        friends = db.retrieveFriends();
        fillFriends();
    }
}
