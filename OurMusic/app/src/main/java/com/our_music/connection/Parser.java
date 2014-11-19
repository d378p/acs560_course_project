package com.our_music.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jared on 10/25/2014.
 *
 * Parser class to prepare requests for the server, and parse responses from the server.  Relies
 * on the ClientConnection singleton to communicate with the server.
 */
public class Parser extends AsyncTask<JSONObject, Integer, String>{
    public enum MessageType {
        REQUEST, RESPONSE, LOGIN, SEARCH, NEW_USER, SONGS, FRIENDS;

        public static MessageType getType(String type){
            //FIXME Need to return correct type
            return LOGIN;
        }
    }
    private static final String TAG = Parser.class.getSimpleName();
    private ClientConnection connection;
    private final String VALID = "TRUE";
    private final String INVALID = "FALSE";

    protected String doInBackground(JSONObject... params) {
        String receivedMessage = "";
        try {
            connection = ClientConnection.getInstance();
            JSONObject message = params[0];
            MessageType requestType = MessageType.getType(message.getString("type"));
            switch (requestType) {
                case LOGIN:
                    String username = message.getString("username");
                    String password = message.getString("password");
                    Log.d(TAG, "RIGHT BEFORE GetLogin");
                    receivedMessage = getLogin(username, password);
            }
            //TODO Add functionality to use correct parsing method
        }
        catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return receivedMessage;
    }

    public String getLogin(String username, String password) {
        String returnResponse = "";
        try{
            JSONObject sendMessage = new JSONObject();
            sendMessage.put("type", MessageType.REQUEST);
            sendMessage.put("messageType", MessageType.LOGIN);
            sendMessage.put("username", username);
            sendMessage.put("password", password);
            Log.d(TAG, "RIGHT BEFORE REQUEST DATA FROM SERVER");
            JSONObject response = connection.requestData(sendMessage.toString());
            Log.d("RESULT", response.toString());
            if(response.getString("valid").equals(VALID)){
                returnResponse = VALID;
            }
            else {
                returnResponse = INVALID;
            }
        }
        catch (JSONException el) {
            //TODO Add way to make another attempt to get information from server.  Close and create a new connection if we are unable to reach the server.
            Log.i(TAG, el.getMessage());
        }
        return returnResponse;
    }
}
