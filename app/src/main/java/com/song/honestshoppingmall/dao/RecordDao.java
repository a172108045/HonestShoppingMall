package com.song.honestshoppingmall.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.song.honestshoppingmall.db.RecordDBHelper;
import com.song.honestshoppingmall.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Judy on 2017/1/13.
 */

public class RecordDao {

    private RecordDBHelper mHelper;
    private Date date = new Date();
    private static List<String> mData = new ArrayList<>();

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
        db.delete(Constants.TABLE_RECORD, "where id = ?", new String[]{id});
        db.close();
    }

    public List<String> queryAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id from record order by writeTime asc;", null);
        if (cursor != null) {
            mData.clear();
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                mData.add(id);
            }
        }

        cursor.close();
        db.close();
        return mData;
    }

}
