package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.jared.ourmusic.R;
import com.our_music.connection.AddParser;
import com.our_music.connection.ClientConnection;
import com.our_music.connection.ParseInterface;
import com.our_music.database.OurMusicDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jared on 11/30/2014.
 *
 * Class responsible for adding a song to a users listening list.
 */
public class AddSongActivity extends Activity {
    private static final String TAG = AddSongActivity.class.getSimpleName();
    private EditText songName;
    private EditText artistName;
    private EditText albumName;
    private OurMusicDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        db = new OurMusicDatabase(getApplicationContext());
        songName = (EditText) findViewById(R.id.song_title_field);
        artistName = (EditText) findViewById(R.id.song_artist_field);
        albumName = (EditText) findViewById(R.id.song_album_field);
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

    /**
     * Adds new song to local database and then sends to remote database for permanent storage
     * @param v
     */
    public void addSong(View v) {
        String song = String.valueOf(songName.getText());
        String artist = String.valueOf(artistName.getText());
        String album = String.valueOf(albumName.getText());
        if(song.isEmpty() || artist.isEmpty() || album.isEmpty()) {
            //TODO Prompt user to enter missing information
        } else {
            JSONObject songJson = new JSONObject();
            try {
                songJson.put("type", ParseInterface.MessageType.REQUEST.toString());
                songJson.put("subject", ParseInterface.MessageType.ADD_SONG.toString());
                songJson.put("songName", song);
                songJson.put("albumName", album);
                songJson.put("artistName", artist);
                db.addSong(songJson);
                AsyncTask addSong = new AddParser().execute(songJson);
                Intent returnToLast = new Intent(this, HomeActivity.class);
                returnToLast.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(returnToLast);
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
