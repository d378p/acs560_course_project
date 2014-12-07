package com.our_music.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jared.ourmusic.R;
import com.our_music.database.OurMusicDatabase;
import com.our_music.database.Song;

import java.util.List;

/**
 * Created by Jared on 11/29/2014.
 *
 * Activity for displaying results from a query the user requested.
 */
public class ResultsActivity extends Activity {
    private static final String TAG = ResultsActivity.class.getSimpleName();
    private TextView queryType;
    private TextView queryList;
    private OurMusicDatabase db;
    private List<Song> queries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        queryType = (TextView)findViewById(R.id.result_type);
        queryList = (TextView)findViewById(R.id.query_list);
        db = new OurMusicDatabase(getApplicationContext());
        queries = db.retrieveQuery();
        showQuery();
    }

    /**
     * Displays query results on activity page
     */
    private void showQuery() {
        try{
            for(Song el: queries) {
                queryList.append(el.toString() + "\n");
            }
        } catch (NullPointerException e) {
            Log.d(TAG, e.getMessage());
            Log.d(TAG, "List of queries is null!");
            e.printStackTrace();
        }
    }

    /**
     * Takes user back to search page so another query can be performed
     * @param v
     */
    public void returnToSearch(View v) {
        finish();
    }

    /**
     * Takes user back to home page
     * @param v
     */
    public void returnHome(View v) {
        Intent goHome = new Intent(this, HomeActivity.class);
        goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goHome);
    }




}
