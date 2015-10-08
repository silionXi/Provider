package com.silion.providersample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.silion.providersample.MainContract.Tables;
import com.silion.providersample.MainContract.User;

/**
 * Created by silion on 2015/10/8.
 */
public class MainProvider extends ContentProvider {
    private static final String AUTHORITY = "com.silion.sampleprovider";
    private static final String DB_NAME = "sample";

    private static final int USER = 1;
    private static final int USER_ID = 2;
    private MainDatabaseHelper mDatabaseHelper = null;

    public static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        mUriMatcher.addURI(AUTHORITY, Tables.USER, USER);
        mUriMatcher.addURI(AUTHORITY, Tables.USER + "/#", USER_ID);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new MainDatabaseHelper(getContext(), DB_NAME);
        mDatabaseHelper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int match = mUriMatcher.match(uri);
        switch (match) {
            case USER: {
                qb.setTables(Tables.USER);
                //qb.setProjectionMap(null); 不使用看有没有问题
                if(TextUtils.isEmpty(sortOrder)) {
                    sortOrder = MainContract.SORT_OERDER;
                }
                break;
            }
            case USER_ID: {
                qb.setTables(Tables.USER);
                //qb.setProjectionMap(null); 不使用看有没有问题
                qb.appendWhere(MainContract._ID + " = " + uri.getLastPathSegment()); //????
                break;
            }
            default: {
                break;
            }
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null , null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case USER:
                return User.DIR;
            case USER_ID:
                return User.ITEM;
            default:
                throw new IllegalArgumentException("Unknow uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        long id = -1;
        int match = mUriMatcher.match(uri);
        switch(match) {
            case USER:
                id = db.insert(Tables.USER, User.NAME, values);
                break;
            default:
                break;
        }
        if(id > 0) {
            uri = ContentUris.withAppendedId(uri, id);
            android.util.Log.v("slong.liang", "SampleProvider.insert uri = " + uri.toString());
            getContext().getContentResolver().notifyChange(uri, null);
            return uri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        int count = -1;
        switch(match) {
            case USER:
                count = db.delete(Tables.USER, selection, selectionArgs);
                break;
            case USER_ID:
                String userId = uri.getPathSegments().get(1);
                if(selection != null) {
                    selection = MainContract._ID + " = " + userId + " AND " + selection;
                }
                count = db.delete(Tables.USER, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        int count = -1;
        switch(match) {
            case USER:
                count = db.update(Tables.USER, values, selection, selectionArgs);
                break;
            case USER_ID:
                String userId = uri.getPathSegments().get(1);
                if(selection != null) {
                    selection = MainContract._ID + " = " + userId + " AND " + selection;
                }
                count = db.update(Tables.USER, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
        return count;
    }
}

