package com.our_music.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.jared.ourmusic.R;
import com.our_music.connection.ParseInterface;

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

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_search);
        topTenRadio = (RadioButton)findViewById(R.id.topTen);
        topThreeRadio = (RadioButton)findViewById(R.id.topThree);
        customRadio = (RadioButton)findViewById(R.id.custom);
    }

    public void search(View v) {
        if(topTenRadio.isSelected()) {
            //TODO
        }
        else if(topThreeRadio.isSelected()) {
            //TODO
        }
        else if(customRadio.isSelected()) {
            //TODO
        }
    }

    private String getTopTen() {
        ParseInterface.MessageType messageSubject = ParseInterface.MessageType.QUERY;
        return "";
    }

    private String getTopThree() {
        //TODO
        return "";
    }

    private String getCustom() {
        //TODO
        return "";
    }

}
