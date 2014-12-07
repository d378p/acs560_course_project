package com.our_music.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Jared on 12/6/2014.
 *
 * Parsing class that allows us to send songs or friends to add to user's settings.
 */
public class AddParser extends AsyncTask <JSONObject, Integer, Boolean> implements ParseInterface {
    private final String TAG = AddParser.class.getSimpleName();
    private ClientConnection connection = null;
    private MessageType requestType;

    @Override
    protected Boolean doInBackground(JSONObject... jsonObjects) {
        JSONObject message = jsonObjects[0];
        JSONObject response = null;
        boolean valid = false;
        try {
            connection = ClientConnection.getInstance();
            requestType = MessageType.getType(message.getString("subject"));
            response = getRequest(message.toString());
            valid = response.getBoolean("verifyAdded");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return valid;
    }

    @Override
    public JSONObject getRequest(String... params) {
        JSONObject request = null;
        try{
            request = connection.requestData(params[0]);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return request;
    }
}
