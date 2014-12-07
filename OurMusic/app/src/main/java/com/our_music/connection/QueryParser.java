package com.our_music.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.our_music.database.OurMusicDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jared on 11/23/2014.
 *
 * Used to parse JSON data for query requests and results.
 */
public class QueryParser extends AsyncTask<JSONObject, Integer, JSONObject> implements ParseInterface{
    private final String TAG = UserParser.class.getSimpleName();
    private ClientConnection connection = null;
    private MessageType requestType;
    private MessageType queryType;

    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        JSONObject message = jsonObjects[0];
        JSONObject response = null;
        try {
            connection = ClientConnection.getInstance();
            requestType = MessageType.getType(message.getString("subject"));
            queryType = MessageType.getType(message.getString("queryType"));
            response = getRequest(message.toString());
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public JSONObject getRequest(String... params) {
        JSONObject request = null;
        try {
            if (queryType == MessageType.CUSTOM_QUERY) {
                //TODO
            } else {
                request = connection.requestData(params[0]);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return request;
    }
}
