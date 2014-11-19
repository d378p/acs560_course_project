package server;

public class RunServer {

	public static void main(String[] args) {
		try{
			BasicServer runServer = new BasicServer();
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
}
