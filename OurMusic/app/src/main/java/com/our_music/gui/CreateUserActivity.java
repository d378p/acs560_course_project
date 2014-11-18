package com.our_music.gui;

import android.app.Activity;
import android.os.Bundle;

import com.example.jared.ourmusic.R;

/**
 * Created by Jared on 11/17/2014.
 */
public class CreateUserActivity extends Activity{
    private static final String TAG = CreateUserActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
    }
}
