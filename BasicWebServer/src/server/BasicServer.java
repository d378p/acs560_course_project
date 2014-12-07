package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.*;

public class BasicServer {
	
	private static final int PORT = 8080;
	private static final String SERVER_ADDRESS = "127.0.0.1";
	ServerSocket initSocket = null;
	ObjectOutputStream outStream;
	ObjectInputStream inStream;
	private String userName = "Jared";
	private String passWord = "aaaa";
	private ArrayList<User> users = new ArrayList<>();
	private ArrayList<Song> songs = new ArrayList<>();
	
	public BasicServer() throws Exception {
		System.out.println("Waiting on connection:");
		initSocket = new ServerSocket(PORT);
		Socket socket = initSocket.accept();
		System.out.println("Connection successful");
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter outToClient = new PrintWriter(socket.getOutputStream());
		generateSongs();
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
			else if(reply.getString("subject").equals("INIT")) {
				response = initDB();
			}
			else if(reply.getString("subject").equals("QUERY")) {
				response = getTopTen();
			}
			else if(reply.getString("subject").equals("ADD_SONG")) {
				response = addSong(reply);
			}
			else if(reply.getString("subject").equals("ADD_FRIEND")) {
				JSONObject json = new JSONObject();
				json.put("verifyAdded", true);
				response = json;
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
	
	private void generateSongs() {
		for(int i = 0; i < 12; i++) {
			Song newSong = new Song("Song" + i, "Artist" + i, "Album" + i);
			songs.add(newSong);
		}	
	}
	
	private JSONObject addSong(JSONObject songToAdd) throws Exception {
		Song song = new Song(songToAdd.getString("songName"), songToAdd.getString("artistName"),
				songToAdd.getString("albumName"));
		songs.add(song);
		JSONObject result = new JSONObject();
		result.put("verifyAdded", true);
		return result;
	}
	
	private JSONObject getTopTen() throws Exception{
		JSONObject topTen = new JSONObject();
		topTen.put("type", "RESPONSE");
		topTen.put("subject", "QUERY");
		topTen.put("queryType", "TOP_TEN");
		JSONArray songlist = new JSONArray();
		for(int i = 0; i < 10; i++) {
			JSONArray songSpecs = new JSONArray();
			songSpecs.put(0, songs.get(i).getSong());
			songSpecs.put(1, songs.get(i).getArtist());
			songSpecs.put(2, songs.get(i).getAlbum());
			songlist.put(i, songSpecs);
		}
		topTen.put("topTenSongs", songlist);
		return topTen;
	}
	
	private JSONObject initDB() throws Exception{
		JSONObject dbToSendToClient = new JSONObject();
		JSONArray friends = new JSONArray();
		for(int i = 0; i < 50; i++) {
			friends.put(i, "Friend  #" + i);
		}
		dbToSendToClient.put("friendsList", friends);
		JSONArray songList = new JSONArray();
		for(int i = 0; i < 50; i++) {
			JSONArray songSpecs = new JSONArray();
			songSpecs.put(0, "Song #" + i);
			songSpecs.put(1, "Artist #" + i);
			songSpecs.put(2, "Album #" + i);
			songList.put(i, songSpecs);
		}
		dbToSendToClient.put("songList", songList);
		return dbToSendToClient;
	}
	

}
