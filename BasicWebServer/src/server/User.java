package server;

public class User {
	private String username;
	private String password;
	private String email;
	
	public User(String user, String pass, String email) {
		this.username = user;
		this.password = pass;
		this.email = email;
	}
	
	public boolean verify(String user, String pass, String em) {
		return (username.equals(user) && password.equals(pass) &&
				email.equals(em));
	}
}
