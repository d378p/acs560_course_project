package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jared.ourmusic.R;
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
        topTenRadio = (RadioButton)findViewById(R.id.topTen);
        topThreeRadio = (RadioButton)findViewById(R.id.topThree);
        customRadio = (RadioButton)findViewById(R.id.custom);
        db = new OurMusicDatabase(getApplicationContext());
    }

    public void search(View v) {
        if(topTenRadio.isSelected()) {
            db.storeQuery(getTopTen());
            Intent resultIntent = new Intent(this, ResultsActivity.class);
            startActivity(resultIntent);
            //TODO Pass getTopTen to a class to parse it and add to database  Possibly DONE!
        }
        else if(topThreeRadio.isSelected()) {
            db.storeQuery(getTopThree());
            Intent resultIntent = new Intent(this, ResultsActivity.class);
            startActivity(resultIntent);
            //TODO Pass getTopThree to a class to parse it and add to database.  Possibly DONE!
        }
        else if(customRadio.isSelected()) {
            //TODO
        }
        else {
            Toast.makeText(getApplicationContext(), "Please select a search type",
                    Toast.LENGTH_LONG).show();
        }
    }

    private JSONObject getTopTen() {
        JSONObject search = new JSONObject();
        JSONObject result = null;
        try {
            search.put("type", ParseInterface.MessageType.REQUEST.toString());
            search.put("subject", ParseInterface.MessageType.QUERY.toString());
            search.put("queryType", ParseInterface.MessageType.TOP_TEN.toString());
            AsyncTask searchRequet = new QueryParser().execute();
            result = (JSONObject)searchRequet.get();
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } catch (Exception el) {
            Log.d(TAG, el.getMessage());
            el.printStackTrace();
        }
        return result;
    }

    private JSONObject getTopThree() {
        JSONObject search = new JSONObject();
        JSONObject result = null;
        try {
            search.put("type", ParseInterface.MessageType.REQUEST.toString());
            search.put("subject", ParseInterface.MessageType.QUERY.toString());
            search.put("queryType", ParseInterface.MessageType.TOP_THREE_FRIENDS.toString());
            AsyncTask searchRequest = new QueryParser().execute();
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
