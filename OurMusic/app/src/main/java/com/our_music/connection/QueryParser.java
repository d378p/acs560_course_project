package com.our_music.connection;

import android.os.AsyncTask;
import android.util.Log;

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


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject getRequest(String... params) {
        JSONObject json = new JSONObject();
        JSONObject request = null;
        try {
            json.put("type", MessageType.REQUEST.toString());
            json.put("subject", MessageType.QUERY.toString());
            json.put("queryType", queryType);
            if (queryType == MessageType.CUSTOM_QUERY) {
                //TODO
            } else {
                request = connection.requestData(json.toString());
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return request;
    }
}
