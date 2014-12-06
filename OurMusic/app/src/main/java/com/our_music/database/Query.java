package com.our_music.database;

/**
 * Created by Jared on 11/29/2014.
 */
public class Query {
    private int id;
    private String song;
    private String artist;
    private String album;

    public Query() {}

    public Query(String song, String artist, String album) {
        this.song = song;
        this.artist = artist;
        this.album = album;
    }

    public Query(int id, String song, String artist, String album) {
        this.id = id;
        this.song = song;
        this.artist = artist;
        this.album = album;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return song + " by " + artist + " from the album " + album;
    }
}
