package com.example.myfirstapp4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "db";
	
	private static final String TITLE = "title";	

    public DictionaryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //create table
      db.execSQL("CREATE TABLE stringValues (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT);");
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.w("stringValues", "Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS stringValues");
		onCreate(db);		
	}
}
