package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class BasicServer {
	
	private static final int PORT = 8080;
	private static final String SERVER_ADDRESS = "127.0.0.1";
	ServerSocket initSocket = null;
	ObjectOutputStream outStream;
	ObjectInputStream inStream;
	private String userName = "Jared";
	private String passWord = "aaaa";
	
	public BasicServer() throws Exception {
		while(true) {
			System.out.println("Waiting on connection:");
			initSocket = new ServerSocket(PORT);
			Socket socket = initSocket.accept();
			System.out.println("Connection successful");
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outToClient = new PrintWriter(socket.getOutputStream());
			String test = inFromClient.readLine();
			System.out.println(test);
			JSONObject reply = checkCredentials(test);
			//System.out.println("REPLY: " +reply.toString());
			outToClient.write(reply.toString());
			outToClient.flush();
		}
	}
	
	private JSONObject checkCredentials(String jsonString) throws Exception{
		JSONObject loginRequest = new JSONObject(jsonString);
		System.out.println("username: "+ loginRequest.getString("username"));
		System.out.println("password: "+ loginRequest.getString("password"));
		if(loginRequest.getString("username").equals(userName) &&
				loginRequest.getString("password").equals(passWord)) {
			return createJson();
		}
		else {
			JSONObject badLogin = new JSONObject();
			badLogin.put("valid", "FALSE");
			return badLogin;
		}	
	}
	
	private JSONObject createJson() throws Exception{
		JSONObject newJson = new JSONObject();
		newJson.put("valid", "TRUE");
		return newJson;
	}
	

}
