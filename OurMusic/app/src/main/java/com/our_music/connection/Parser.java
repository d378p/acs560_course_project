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
        try {
            connection = ClientConnection.getInstance();
            if(!connection.connectionActive){
                connection.setupConnection();
            }
            JSONObject message = params[0];
            MessageType requestType = MessageType.getType(message.getString("type"));
            switch (requestType) {
                case LOGIN:
                    String username = message.getString("username");
                    String password = message.getString("password");
                    return getLogin(username, password);

            }
            //TODO Add functionality to use correct parsing method
            return "";
        }
        catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        finally {
            //TODO Fix return statement if exception thrown
            return "Failed to Parse JSON for message";
        }
    }

    public String getLogin(String username, String password) {
        try{
            JSONObject sendMessage = new JSONObject();
            sendMessage.put("type", MessageType.REQUEST);
            sendMessage.put("messageType", MessageType.LOGIN);
            JSONObject response = connection.requestData(sendMessage.toString());
            if(response.getString("username").equals(username) &&
                    response.getString("password").equals(password)){
                return VALID;
            }
            else {
                return INVALID;
            }
        }
        catch (JSONException el) {
            //TODO Add way to make another attempt to get information from server.  Close and create a new connection if we are unable to reach the server.
            Log.i(TAG, el.getMessage());
        }
        finally {
            return INVALID;
        }
    }

//    private JSONObject requestData(String parsedJson) {
//        try{
//            pw.println(parsedJson);
//            pw.flush();
//            JSONObject serverReply = new JSONObject(reader.readLine());
//            return serverReply;
//        }
//        catch (IOException e){
//            Log.i(TAG, e.getMessage());
//        }
//        catch (JSONException el) {
//            Log.d(TAG, el.getMessage());
//            Log.d(TAG, "Malformed JSON String");
//        }
//        finally {
//            return null;
//        }
//    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        setupConnection();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        closeConnection();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent, flags, startId);
//        Log.d(TAG, "onStarted");
//        if(socket.isClosed()){
//            Log.d(TAG, "Connection closed.  Reconnecting...");
//            setupConnection();
//        }
//        else{
//            //TODO Place a generic method to issue a request to server
//        }
//        return START_STICKY;
//    }
//
//    private void setupConnection() {
//        if(reconnectAttempts < MAX_RECONNECTS){
//            try{
//                InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
//                socket = new Socket(serverAddress, PORT);
//                outStream = new DataOutputStream(socket.getOutputStream());
//                inStream = new DataInputStream(socket.getInputStream());
//                pw = new PrintWriter(outStream);
//                reader = new BufferedReader(new InputStreamReader(inStream));
//                reconnectAttempts = 0;
//            }
//            catch (Exception e){
//                reconnectAttempts++;
//                try{
//                    Log.i(TAG, "Unable to connect to server, waiting " +
//                            RECONNECT_MULTIPLIER*reconnectAttempts + "ms");
//                    wait(RECONNECT_MULTIPLIER*reconnectAttempts);
//                }
//                catch(Exception el) {
//                    Log.d(TAG, el.getMessage());
//                }
//                setupConnection();
//            }
//        }
//        else{
//            Log.d(TAG, "Attempted connection to server " +MAX_RECONNECTS+ "times and was unable to connect.");
//            //TODO Need to create a method to notify the current activity that a connection cannot be made.
//        }
//    }
//
//    private void closeConnection() {
//        try{
//            if(!socket.isClosed()){
//                reader.close();
//                inStream.close();
//                outStream.close();
//                socket.close();
//            }
//        }
//        catch(IOException e) {
//            Log.i(TAG, "Unable to close connection to server.");
//        }
//    }
}
