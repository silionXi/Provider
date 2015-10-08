package com.silion.providersample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.silion.providersample.MainContract.Tables;
import com.silion.providersample.MainContract.User;

/**
 * Created by silion on 2015/10/8.
 */
public class MainDatabaseHelper extends SQLiteOpenHelper {

    public MainDatabaseHelper(Context context, String name) {
        this(context, name, 1);
    }

    public MainDatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }
    public MainDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        android.util.Log.v("slong.liang", "MainDatabaseHelper : onCreate");
        db.execSQL("CREATE TABLE " + Tables.USER + "(" +
                MainContract._ID + " INTEGER PRIMARY KEY," +
                User.NAME + " TEXT," +
                User.AGE + " INTEGER," +
                User.PHONE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.v("slong.liang", "MainDatabaseHelper : onUpgrade oldVersion = " +
                oldVersion + ", newVersion = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.USER);
        onCreate(db);
    }
}
