package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;

public class BasicServer {
	
	private static final int PORT = 8080;
	private static final String SERVER_ADDRESS = "127.0.0.1";
	ServerSocket initSocket = null;
	ObjectOutputStream outStream;
	ObjectInputStream inStream;
	private String userName = "Jared";
	private String passWord = "aaaa";
	private ArrayList<User> users = new ArrayList<>();
	
	public BasicServer() throws Exception {
		System.out.println("Waiting on connection:");
		initSocket = new ServerSocket(PORT);
		Socket socket = initSocket.accept();
		System.out.println("Connection successful");
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter outToClient = new PrintWriter(socket.getOutputStream());
		while(true){
			String test = inFromClient.readLine();
			System.out.println(test);
			JSONObject reply = new JSONObject(test);
			JSONObject response = null;
			if(reply.getString("subject").equals("NEW_USER")) {
				response = createUser(test);
			}
			else if(reply.getString("subject").equals("LOGIN")) {
				response = checkCredentials(test);
			}
			System.out.println("REPLY: " +response.toString());
			outToClient.write(response.toString()+"\n");
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
		} else if (userExists(loginRequest.getString("username"), loginRequest.getString("password"))) {
			return createJson();
		}
		else {
			JSONObject badLogin = new JSONObject();
			badLogin.put("validity", false);
			return badLogin;
		}	
	}
	
	private JSONObject createJson() throws Exception{
		JSONObject newJson = new JSONObject();
		newJson.put("validity", true);
		return newJson;
	}
	
	private JSONObject createUser(String json) throws Exception {
		JSONObject createUserRequest = new JSONObject(json);
		String user = createUserRequest.getString("username");
		String pass = createUserRequest.getString("password");
		String email = createUserRequest.getString("email");
		boolean exists = userExists(user, pass, email);
		if(!exists) {
			User newUser = new User(user, pass, email);
			users.add(newUser);
			exists = true;
		} else if(exists) {
			exists = false;
		}
		JSONObject response = new JSONObject();
		response.put("type", "RESPONSE");
		response.put("subject", "NEW_USER");
		response.put("validity", exists);
		return response;
	}
	
	private boolean userExists(String username, String password, String email) {
		for(User el: users) {
			if(el.verify(username, password, email)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean userExists(String username, String password) {
		for(User el : users) {
			if(el.verify(username, password)) {
				return true;
			}
		}
		return false;
	}
	

}
