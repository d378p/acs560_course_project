package com.our_music.connection;

import org.json.JSONObject;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Jared on 10/25/2014.
 */
public class Parser {
    public enum MessageType {
        REQUEST, RESPONSE, LOGIN, SEARCH, NEW_USER, SONGS, FRIENDS
    }
    private static final String TAG = "ClientParser";
    private final String SERVER_ADDRESS = "192.168.1.208";
    private final int PORT = 56789;
    private final int MAX_RECONNECTS = 15;
    private int reconnectAttempts = 0;
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;


    public Parser() {
        setupConnection();
    }

    private void setupConnection() {
        try{
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            socket = new Socket(serverAddress, PORT);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            reconnectAttempts++;
            //TODO Include a slowly growing sleep time to wait on recconnects
            setupConnection();
        }
    }

    public String getLogin(String username, String password) {
        try{
            JSONObject sendMessage = new JSONObject();
            sendMessage.put("type", MessageType.REQUEST);
            sendMessage.put("messageType", MessageType.LOGIN);
            sendMessage.put("login", username);
            sendMessage.put("password", password);
            outStream.writeObject(sendMessage);

            //FIXME!  I need to be the value returned from the server, not the original json string
            return "";
        }
        catch (Exception e) {
            System.err.print(e);
            System.err.print("Malformed JSON String");
        }
        finally {
            //FIXME! Need to figure out how we are going to recover from not getting a response
            return "";
        }
    }
}
