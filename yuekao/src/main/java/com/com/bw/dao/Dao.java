package com.com.bw.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bw.util.Sqlate;

public class Dao {
    private Sqlate sqlate;
    private SQLiteDatabase db;
    public Dao(Context context) {
      sqlate=new Sqlate(context);
      db=sqlate.getReadableDatabase();
    }
    /**
     * 添加方法
     */
    public void add(String name){
        ContentValues values=new ContentValues();
        values.put("name",name);
        db.insert("news",null,values);
    }
}
