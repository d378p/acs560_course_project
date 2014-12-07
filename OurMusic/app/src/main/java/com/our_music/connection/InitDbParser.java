package com.our_music.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Jared on 12/6/2014.
 *
 * Class for retrieving initial state of songs and friends from the remote database upon login.
 */
public class InitDbParser extends AsyncTask<JSONObject, Integer, JSONObject> implements ParseInterface {
    private static final String TAG = InitDbParser.class.getSimpleName();
    private ClientConnection connection = null;

    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        JSONObject message = jsonObjects[0];
        JSONObject response = null;
        try {
            connection = ClientConnection.getInstance();
            response = getRequest(message.toString());
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public JSONObject getRequest(String... params) {
        JSONObject request = null;
        try {
            request = connection.requestData(params[0]);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return request;
    }
}
