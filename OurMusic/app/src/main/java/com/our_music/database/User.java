package com.our_music.database;

/**
 * Created by Jared on 12/6/2014.
 */
public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public String toString() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
