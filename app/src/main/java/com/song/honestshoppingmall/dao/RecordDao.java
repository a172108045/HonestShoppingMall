package com.song.honestshoppingmall.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.song.honestshoppingmall.db.RecordDBHelper;
import com.song.honestshoppingmall.util.Constants;

import java.util.Date;

/**
 * Created by Judy on 2017/1/13.
 */

public class RecordDao {

    private RecordDBHelper mHelper;
    private Date date = new Date();

    public RecordDao(Context context) {
        mHelper = new RecordDBHelper(context);
    }

    public void insert(String id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("writeTime", date.getTime());
        db.insert(Constants.TABLE_RECORD, null, values);
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Constants.TABLE_RECORD, "where id = ?", new String[] {id});
        db.close();
    }

    public void queryAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_RECORD, null, null, null, null, null, null);
        //没写完
        db.close();
    }

}
