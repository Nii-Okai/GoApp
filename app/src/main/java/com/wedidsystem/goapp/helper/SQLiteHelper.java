package com.wedidsystem.goapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "keywordApp";

    // Table Names
    private static final String TABLE_KEYWORD = "keyword_table";
    private static final String TABLE_HISTORY = "history_table";
//    private static final String TABLE_TODO_TAG = "todo_tags";

    // Column names
    private static final String KEY_ID = "id";
    private static final String KEY_KEYWORD = "keyword";
    private static final String KEY_PERIOD = "period";
    private static final String KEY_TIMER = "timer";

    private static final String HIS_ID = "id";
    private static final String HIS_KEYWORD = "keyword";
    private static final String HIS_PERIOD = "period";

    // Table Create Statements
    // keyword table create statement
    private static final String CREATE_TABLE_KEYWORD = "CREATE TABLE "
            + TABLE_KEYWORD + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_KEYWORD
            + " TEXT," + KEY_PERIOD + " TEXT," + KEY_TIMER
            + " LONG" + ")";

    // history table create statement
    private static final String CREATE_TABLE_HISTORY = "CREATE TABLE "
            + TABLE_HISTORY + "(" + HIS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + HIS_KEYWORD
            + " TEXT," + HIS_PERIOD + " TEXT " + ")";


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create
    public boolean insertKeyword(String keyword, String period, long timer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KEYWORD, keyword);
        values.put(KEY_PERIOD, period);
        values.put(KEY_TIMER, timer);

        // insert row
        long result = db.insert(TABLE_KEYWORD, null, values);

        //return -1 if data is inserted incorrectly
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertHistory(String keyword, String period) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HIS_KEYWORD, keyword);
        values.put(HIS_PERIOD, period);

        // insert row
        long result = db.insert(TABLE_HISTORY, null, values);

        //return -1 if data is inserted incorrectly
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getKeywords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_KEYWORD, null);
        return data;
    }

    public Cursor getHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_HISTORY, null);
        return data;
    }

    public boolean deleteKeyword(String id){
        long longId = Long.parseLong(id);
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_KEYWORD, KEY_ID + " =" + longId, null) > 0;
    }

    public boolean updateKeyword(String id, String keyword, String period){
        long longId = Long.parseLong(id);
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KEYWORD, keyword);
        values.put(KEY_PERIOD, period);

        return database.update(TABLE_KEYWORD, values, KEY_ID + " =" + longId, null) > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_KEYWORD);
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEYWORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // create new tables
        onCreate(db);
    }
}
