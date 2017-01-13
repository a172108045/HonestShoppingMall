package com.song.honestshoppingmall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.song.honestshoppingmall.util.Constants;

/**
 * Created by Judy on 2017/1/13.
 */

public class RecordDBHelper extends SQLiteOpenHelper {

    public RecordDBHelper(Context context) {
        super(context, Constants.DB_NAME, null, 1);
    }

    public RecordDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("", new String[] {});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
