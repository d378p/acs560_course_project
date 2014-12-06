package server;

public class Song {
	private String song;
	private String artist;
	private String album;
	
	public Song(String song, String artist, String album) {
		this.song = song;
		this.artist = artist;
		this.album = album;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public String getSong() {
		return song;
	}
	
	public String getArtist() {
		return artist;
	}

	public void setSong(String song) {
		this.song = song;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
}
