package com.our_music.database;

/**
 * Created by Jared on 12/4/2014.
 *
 * Song object to store
 */
public class Song {
    private int id;
    private String song;
    private String artist;
    private String album;

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return song + " by " + artist + " from the album " + album;
    }
}
