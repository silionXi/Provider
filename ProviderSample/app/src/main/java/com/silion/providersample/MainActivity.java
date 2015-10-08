package com.silion.providersample;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.silion.providersample.MainContract.User;

public class MainActivity extends Activity {

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.insert: {
                    ContentValues values = new ContentValues();
                    values.put(User.NAME, "silion");
                    values.put(User.AGE, 28);
                    values.put(User.PHONE, "10086");
                    ContentResolver cr = getContentResolver();
                    Uri uri = cr.insert(User.URI, values);
                    android.util.Log.v("slong.liang", "insert uri = " + uri);
                    break;
                }
                case R.id.query: {
                    String[] projection = new String[] {User.NAME, User.AGE, User.PHONE};
                    String selection = User.NAME + " =? OR " + User.NAME + " =?";
                    String[] selectionArgs = new String[] {"silion", "xixi"};
                    ContentResolver cr = getContentResolver();
                    Cursor cursor = cr.query(User.URI, projection, selection, selectionArgs, null);
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(User.NAME));
                        int age = cursor.getInt(cursor.getColumnIndex(User.AGE));
                        String phone = cursor.getString(cursor.getColumnIndex(User.PHONE));
                        android.util.Log.v("slong.liang", "query name = " + name +
                                ", age = " + age + ", phone = " + phone);
                    }
                    break;
                }
                case R.id.update: {
                    ContentValues values = new ContentValues();
                    values.put(User.NAME, "xixi");
                    values.put(User.PHONE, "10086");
                    String where = User.NAME + " =?";
                    String[] selectionArgs = new String[] {"silion"};
                    ContentResolver cr = getContentResolver();
                    int id = cr.update(User.URI, values, where, selectionArgs);
                    Uri uri = ContentUris.withAppendedId(User.URI, id);
                    android.util.Log.v("slong.liang", "update uri = " + uri);
                    break;
                }
                case R.id.delete: {
                    String where = User.NAME + " =? OR " + User.NAME + " =?";
                    String[] selectionArgs = new String[] {"xixi", "silion"};
                    ContentResolver cr = getContentResolver();
                    int rowNum = cr.delete(User.URI, where, selectionArgs);
                    android.util.Log.v("slong.liang", "insert rowNum = " + rowNum);
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button insertButton = (Button) findViewById(R.id.insert);
        insertButton.setOnClickListener(mClickListener);
        Button queryButton = (Button) findViewById(R.id.query);
        queryButton.setOnClickListener(mClickListener);
        Button updateButton = (Button) findViewById(R.id.update);
        updateButton.setOnClickListener(mClickListener);
        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(mClickListener);
    }

}

