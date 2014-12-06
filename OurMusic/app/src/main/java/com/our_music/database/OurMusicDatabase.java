package com.our_music.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jared on 11/29/2014.
 *
 * Database helper.  Mainly used to store queries received from server.  We only store one query,
 * and as soon as we want another we clear the table and put the new query results in there.
 */
public class OurMusicDatabase extends SQLiteOpenHelper{
    private final String TAG = OurMusicDatabase.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OurMusic.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final int SONG = 0;
    private static final int ARTIST = 1;
    private static final int ALBUM = 2;

    private static String CREATE_USER_FRIENDS_TABLE =
            "CREATE TABLE " + OurMusicContract.UserFriends.TABLE_NAME + " (" +
                    OurMusicContract.UserFriends._ID + " INTEGER PRIMARY KEY," +
                    OurMusicContract.UserFriends.COLUMN_NAME_FRIENDS_ID + TEXT_TYPE + ")";

    private static final String CREATE_USER_SONGS_TABLE = "CREATE TABLE " +
            OurMusicContract.UserSongs.TABLE_NAME + " (" +
            OurMusicContract.UserSongs._ID + " INTEGER PRIMARY KEY," +
            OurMusicContract.UserSongs.COLUMN_NAME_SONG_TITLE + TEXT_TYPE + COMMA_SEP +
            OurMusicContract.UserSongs.COLUMN_NAME_SONG_ARTIST + TEXT_TYPE + COMMA_SEP +
            OurMusicContract.UserSongs.COLUMN_NAME_SONG_ALBUM + TEXT_TYPE + ")";

    private static final String CREATE_QUERY_RESULTS_TABLE = "CREATE TABLE " +
            OurMusicContract.QueryResults.TABLE_NAME + " (" +
            OurMusicContract.QueryResults._ID + " INTEGER PRIMARY KEY," +
            OurMusicContract.QueryResults.COLUMN_NAME_SONG_TITLE + TEXT_TYPE + COMMA_SEP +
            OurMusicContract.QueryResults.COLUMN_NAME_SONG_ARTIST + TEXT_TYPE + COMMA_SEP +
            OurMusicContract.QueryResults.COLUMN_NAME_SONG_ALBUM + TEXT_TYPE + ")";

    public OurMusicDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_FRIENDS_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER_SONGS_TABLE);
        sqLiteDatabase.execSQL(CREATE_QUERY_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + "to " + newVersion + "." +
                "  This will destroy all old data.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OurMusicContract.UserFriends.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OurMusicContract.UserSongs.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OurMusicContract.QueryResults.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long storeQuery(JSONObject json) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = -1;
        try {
            db.execSQL("DELETE FROM " + OurMusicContract.QueryResults.TABLE_NAME);
            JSONArray resultArray = json.getJSONArray("topTenSongs"); //FIXME! May need to change what David names the array, we should make it generic.
            for(int i = 0; i < resultArray.length(); i++) {
                JSONArray songDetails = resultArray.getJSONArray(i);
                values.put(OurMusicContract.QueryResults.COLUMN_NAME_SONG_TITLE, songDetails.getString(SONG));
                values.put(OurMusicContract.QueryResults.COLUMN_NAME_SONG_ARTIST, songDetails.getString(ARTIST));
                values.put(OurMusicContract.QueryResults.COLUMN_NAME_SONG_ALBUM, songDetails.getString(ALBUM));
                id = db.insert(OurMusicContract.QueryResults.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public long addSong(JSONObject song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = -1;
        try {
            values.put(OurMusicContract.UserSongs.COLUMN_NAME_SONG_TITLE, song.getString("songName"));
            values.put(OurMusicContract.UserSongs.COLUMN_NAME_SONG_ARTIST, song.getString("artistName"));
            values.put(OurMusicContract.UserSongs.COLUMN_NAME_SONG_ALBUM, song.getString("albumName"));
            id = db.insert(OurMusicContract.UserSongs.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public long addFriend(JSONObject friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = -1;
        try {
            values.put(OurMusicContract.UserFriends.COLUMN_NAME_FRIENDS_ID, friend.getString("friendUsername"));
            id = db.insert(OurMusicContract.UserFriends.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public List<Query> retrieveQuery() {
        List<Query> results = new ArrayList<Query>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + OurMusicContract.QueryResults.TABLE_NAME;
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()) {
            do {
                Query newQuery = new Query();
                newQuery.setId(c.getInt(c.getColumnIndex(OurMusicContract.QueryResults._ID)));
                newQuery.setSong(c.getString(c.getColumnIndex(OurMusicContract.QueryResults.COLUMN_NAME_SONG_TITLE)));
                newQuery.setArtist(c.getString(c.getColumnIndex(OurMusicContract.QueryResults.COLUMN_NAME_SONG_ARTIST)));
                newQuery.setAlbum(c.getString(c.getColumnIndex(OurMusicContract.QueryResults.COLUMN_NAME_SONG_ALBUM)));
                results.add(newQuery);
            } while (c.moveToNext());
        }
        return results;
    }

    public List<Song> retrieveUserSongs() {
        List<Song> results = new ArrayList<Song>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + OurMusicContract.UserSongs.TABLE_NAME;
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()) {
            int count = 0;
            do {
                Song newSong = new Song();
                newSong.setSong(c.getString(c.getColumnIndex(OurMusicContract.UserSongs.COLUMN_NAME_SONG_TITLE)));
                newSong.setArtist(c.getString(c.getColumnIndex(OurMusicContract.UserSongs.COLUMN_NAME_SONG_ARTIST)));
                newSong.setAlbum(c.getString(c.getColumnIndex(OurMusicContract.UserSongs.COLUMN_NAME_SONG_ALBUM)));
                results.add(newSong);
            } while (count++ < 5 && c.moveToNext());
        }
        return results;
    }

    public List<User> retrieveFriends() {
        List<User> friends = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + OurMusicContract.UserFriends.TABLE_NAME;
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(c.getString(c.getColumnIndex(OurMusicContract.UserFriends.COLUMN_NAME_FRIENDS_ID)));
                friends.add(user);
            } while (c.moveToNext());
        }
        return friends;
    }

    private boolean friendsAlready(String username, List<User> users) {
        for(User el : users) {
            if(el.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
