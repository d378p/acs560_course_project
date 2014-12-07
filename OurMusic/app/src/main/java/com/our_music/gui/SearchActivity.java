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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jared.ourmusic.R;
import com.our_music.connection.ClientConnection;
import com.our_music.connection.ParseInterface;
import com.our_music.connection.QueryParser;
import com.our_music.database.OurMusicDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jared on 11/22/2014.
 *
 * Activity for selecting the type of search we want to conduct.  Search results will be displayed
 * on a new activity.
 */
public class SearchActivity extends Activity{
    private static final String TAG = SearchActivity.class.getSimpleName();
    private RadioButton topTenRadio;
    private RadioButton topThreeRadio;
    private RadioButton customRadio;
    private OurMusicDatabase db;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_search);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        topTenRadio = (RadioButton)findViewById(R.id.topTen);
        topThreeRadio = (RadioButton)findViewById(R.id.topThree);
        customRadio = (RadioButton)findViewById(R.id.custom);
        db = new OurMusicDatabase(getApplicationContext());
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
     * Will request results for selected query from the remote server and store results in local database
     * @param v
     */
    public void search(View v) {
        if(topTenRadio.isChecked()) {
            db.storeQuery(getTopTen());
            Intent resultIntent = new Intent(this, ResultsActivity.class);
            startActivity(resultIntent);
        }
        else if(topThreeRadio.isChecked()) {
            db.storeQuery(getTopThree());
            Intent resultIntent = new Intent(this, ResultsActivity.class);
            startActivity(resultIntent);
        }
        else if(customRadio.isChecked()) {
            //TODO
        }
        else {
            Toast.makeText(getApplicationContext(), "Please select a search type",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Issues request for Top Ten songs for all users
     * @return JSONArray containg JSONArrays of song title, artist, and album
     */
    private JSONObject getTopTen() {
        JSONObject search = new JSONObject();
        JSONObject result = null;
        try {
            search.put("type", ParseInterface.MessageType.REQUEST.toString());
            search.put("subject", ParseInterface.MessageType.QUERY.toString());
            search.put("queryType", ParseInterface.MessageType.TOP_TEN.toString());
            AsyncTask searchRequest = new QueryParser().execute(search);
            result = (JSONObject)searchRequest.get();
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } catch (Exception el) {
            Log.d(TAG, el.getMessage());
            el.printStackTrace();
        }
        return result;
    }

    /**
     * Issues request for Top Three songs amongst the user's friends
     * @return JSONArray containg JSONArrays of song title, artist, and album
     */
    private JSONObject getTopThree() {
        JSONObject search = new JSONObject();
        JSONObject result = null;
        try {
            search.put("type", ParseInterface.MessageType.REQUEST.toString());
            search.put("subject", ParseInterface.MessageType.QUERY.toString());
            search.put("queryType", ParseInterface.MessageType.TOP_THREE_FRIENDS.toString());
            AsyncTask searchRequest = new QueryParser().execute(search);
            result = (JSONObject) searchRequest.get();
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } catch (Exception el) {
            Log.d(TAG, el.getMessage());
            el.printStackTrace();
        }
        return result;
    }

    private String getCustom() {
        //TODO
        return "";
    }

}
