package com.our_music.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jared on 11/22/2014.
 *
 * Used to parse JSON data for logging into app and for creating a new user.
 */
public class UserParser extends AsyncTask<JSONObject, Integer, Boolean> implements ParseInterface{
    private final String TAG = UserParser.class.getSimpleName();
    private ClientConnection connection = null;
    private MessageType requestType;

    @Override
    protected Boolean doInBackground(JSONObject... jsonObjects) {
        JSONObject message = jsonObjects[0];
        JSONObject response = null;
        boolean valid = false;
        try{
            connection = ClientConnection.getInstance();
            requestType = MessageType.getType(message.getString("subject"));
            String user = message.getString("username");
            String pass = message.getString("password");
            String email = "";
            if(message.has("email")) {
                email = message.getString("email");
            }
            response = (!email.isEmpty()) ? getRequest(user, pass, email) : getRequest(user, pass);
            valid = response.getBoolean("validity");
        }

        catch (Exception e){
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return valid;
    }

    @Override
    public JSONObject getRequest(String...params) {
        JSONObject request = null;
        switch (requestType) {
            case LOGIN: request = getLoginRequest(params[0], params[1]);
                break;
            case NEW_USER:  request = newUserRequest(params[0], params[1], params[2]);
                break;
            default: Log.d(TAG, "Invalid request to parse");
        }
        return connection.requestData(request.toString());
    }

    private JSONObject getLoginRequest(String username, String password) {
        JSONObject json = new JSONObject();
        try{
            json.put("type", MessageType.REQUEST.toString());
            json.put("subject", MessageType.LOGIN.toString());
            json.put("username", username);
            json.put("password", password);
        }
        catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return json;
    }

    private JSONObject newUserRequest(String username, String password, String email) {
        JSONObject json = new JSONObject();
        try{
            json.put("type", MessageType.REQUEST.toString());
            json.put("subject", MessageType.NEW_USER.toString());
            json.put("username", username);
            json.put("password", password);
            json.put("email", email);
        }
        catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return json;
    }
}
