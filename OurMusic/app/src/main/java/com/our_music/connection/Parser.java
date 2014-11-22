package com.our_music.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jared on 10/25/2014.
 *
 * Parser class to prepare requests for the server, and parse responses from the server.  Relies
 * on the ClientConnection singleton to communicate with the server.
 */
public class Parser extends AsyncTask<JSONObject, Integer, String>{
    public enum MessageType {
        REQUEST, RESPONSE, INIT, LOGIN, QUERY, DB_UPDATE, ADD_FRIEND, ADD_SONG, NEW_USER,
        GET_SONGS, GET_FRIENDS, TOP_TEN, TOP_THREE_FRIENDS, CUSTOM_QUERY;

        private static final Map<String, MessageType> enumMap;
        static {
            Map<String, MessageType> map = new HashMap<String, MessageType>();
            for(MessageType el: MessageType.values()) {
                map.put(el.toString(), el);
            }
            enumMap = Collections.unmodifiableMap(map);
        }

        public static MessageType getType(String type){
            MessageType typeOfMessage = null;
            try{
                typeOfMessage = enumMap.get(type);
            }
            catch (Exception e) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }
            return typeOfMessage;
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
