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
 */
public class OurMusicDatabase extends SQLiteOpenHelper{
    private final String TAG = OurMusicDatabase.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OurMusic.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static String CREATE_USER_FRIENDS_TABLE =
            "CREATE TABLE " + OurMusicContract.UserFriends.TABLE_NAME + " (" +
                    OurMusicContract.UserFriends.COLUMN_NAME_FRIENDS_ID + " PRIMARY KEY)";

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
            JSONArray resultArray = json.getJSONArray("resultsArray"); //FIXME! May need to change what David names the array, we should make it generic.
            values.put(OurMusicContract.QueryResults.COLUMN_NAME_SONG_TITLE, "FIX");
            values.put(OurMusicContract.QueryResults.COLUMN_NAME_SONG_ARTIST, "FIX");
            values.put(OurMusicContract.QueryResults.COLUMN_NAME_SONG_ALBUM, "FIX");
            id = db.insert(OurMusicContract.QueryResults.TABLE_NAME, null, values);
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

}
