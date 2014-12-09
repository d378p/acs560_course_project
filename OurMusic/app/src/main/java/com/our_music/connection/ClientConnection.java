package com.our_music.connection;

import android.util.Log;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Jared on 11/15/2014.
 *
 * Singleton class to handle the connection.  Designed to be called by a parser class.  All
 * methods are protected, so only a parsing class has the ability to manage the connection.
 */
public class ClientConnection {
    private static ClientConnection instance = null;
    private static final String TAG = ClientConnection.class.getSimpleName();
    private final String SERVER_ADDRESS = "10.0.2.2";
    private final int PORT = 8080;
    private final int MAX_RECONNECTS = 15;
    private final int RECONNECT_MULTIPLIER = 500;
    private int reconnectAttempts = 0;
    private Socket socket;
    private DataOutputStream outStream;
    private DataInputStream inStream;
    private PrintWriter pw;
    private BufferedReader reader;
    protected boolean connectionActive = false;

    protected ClientConnection(){
        setupConnection();
    }

    public static ClientConnection getInstance() {
        if(instance == null) {
            instance = new ClientConnection();
        }
        return instance;
    }

    protected void setupConnection() {
        if(reconnectAttempts < MAX_RECONNECTS){
            try{
                socket = new Socket(SERVER_ADDRESS, PORT);
                outStream = new DataOutputStream(socket.getOutputStream());
                inStream = new DataInputStream(socket.getInputStream());
                pw = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(inStream));
                reconnectAttempts = 0;
                connectionActive = true;
            }
            catch (Exception e){
                reconnectAttempts++;
                Log.d(TAG, e.toString());
                try{
                    Log.i(TAG, "Unable to connect to server, waiting " +
                            RECONNECT_MULTIPLIER * reconnectAttempts + "ms");
                    wait(RECONNECT_MULTIPLIER*reconnectAttempts);
                }
                catch(Exception el) {
                    Log.d(TAG, el.getMessage());
                }
                setupConnection();
            }
        }
        else{
            Log.d(TAG, "Attempted connection to server " +MAX_RECONNECTS+ "times and was unable to connect.");
            //TODO Need to create a method to notify the current activity that a connection cannot be made.
        }
    }

    protected void closeConnection() {
        try{
            if(!socket.isClosed()){
                reader.close();
                inStream.close();
                outStream.close();
                socket.close();
                connectionActive = false;
            }
        }
        catch(IOException e) {
            Log.i(TAG, "Unable to close connection to server.");
        }
    }

    public void resetConnection() {
        closeConnection();
        setupConnection();
    }

    protected JSONObject requestData(String parsedJson) {
        JSONObject returnObject = null;
        try{
            pw.println(parsedJson);
            pw.flush();
            returnObject = new JSONObject(reader.readLine());
        }
        catch (IOException e){
            Log.i(TAG, e.getMessage());
            e.printStackTrace();
        }
        catch (JSONException el) {
            Log.d(TAG, el.getMessage());
            Log.d(TAG, "Malformed JSON String");
            el.printStackTrace();
        }
        return returnObject;
    }
}
