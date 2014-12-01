package com.our_music.database;

import android.provider.BaseColumns;

/**
 * Created by Jared on 11/29/2014.
 *
 * Schema of local database for storing user's information, friends, songs, and latest query results
 */
public final class OurMusicContract {

    public OurMusicContract() {}

    public static abstract class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USER_ID = "userId";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }

    public static abstract class UserFriends implements BaseColumns {
        public static final String TABLE_NAME = "userFriends";
        public static final String COLUMN_NAME_FRIENDS_ID = "friendsId";
    }

    public static abstract class UserSongs implements BaseColumns {
        public static final String TABLE_NAME = "userSongs";
        public static final String COLUMN_NAME_SONG_TITLE = "title";
        public static final String COLUMN_NAME_SONG_ARTIST = "artist";
        public static final String COLUMN_NAME_SONG_ALBUM = "album";
    }

    public static abstract class QueryResults implements BaseColumns {
        public static final String TABLE_NAME = "query";
        public static final String COLUMN_NAME_SONG_TITLE = "title";
        public static final String COLUMN_NAME_SONG_ARTIST = "artist";
        public static final String COLUMN_NAME_SONG_ALBUM = "album";
    }
}
