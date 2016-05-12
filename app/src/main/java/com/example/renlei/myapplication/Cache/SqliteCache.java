package com.example.renlei.myapplication.Cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.renlei.myapplication.MyApplication;

/**
 * Created by renlei
 * DATE: 16-5-12
 * Time: 下午12:00
 * Email: lei.ren@renren-inc.com
 * 用于数据库缓存
 */
public class SqliteCache {
    private static final String TAG = "SqliteCache";
    private static final String DB_NAME = "renlei";
    private static int DB_VERSION = 4;



    public synchronized void saveIntoSqlite(String content){
        SqlDBHelp sqlDBHelp = new SqlDBHelp(MyApplication.getContext(),DB_NAME,null,DB_VERSION);
        SQLiteDatabase db = sqlDBHelp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",1);
        cv.put("content",content);
//        cv.put("test2","test2");
        db.insert("lei_table",null,cv);
        db.close();
    }

    public synchronized String getFromSqlite(int id){
        SqlDBHelp sqlDBHelp = new SqlDBHelp(MyApplication.getContext(),DB_NAME,null,DB_VERSION);
        SQLiteDatabase db = sqlDBHelp.getReadableDatabase();
        StringBuffer content = new StringBuffer();
        Cursor cursor = db.query(true, "lei_table", new String[]{"id", "content"}, "id=?", new String[]{"1"} ,null, null, null, null, null);
        while (cursor.moveToNext()){
             content.append(cursor.getString(cursor.getColumnIndex("content"))) ;
        }
        return  content.toString();
    }


    class SqlDBHelp extends SQLiteOpenHelper{

        public SqlDBHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DB_NAME, null, DB_VERSION);
            Log.d(TAG,"创建数据库");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "create table lei_table(id int,content varchar)";
            db.execSQL(sql);
            Log.d(TAG,"创建数据表");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG,"oldVersion"+oldVersion+"newVersion"+newVersion);
            for (int v = oldVersion;v<=newVersion;v++){
                switch (v){
                    case 2:
                        updateTo2(db);
                        break;
                }
            }
        }

        private void updateTo2(SQLiteDatabase db){
            String reNameA = "alter table lei_table rename to temp_lei_table";
            db.execSQL(reNameA);
            String createNewA = "create table if not exists lei_table(id int,content varchar,newcl varchar,test text,test2 varchar)";
            db.execSQL(createNewA);
            String copy = "insert into lei_table select *,'' from "+"temp_lei_table";//''是为了给新加的填数据 否则会table lei_table has 4 columns but 3 values were supplied (code 1)
            db.execSQL(copy);
            String delTemp = "drop table if exists temp_lei_table";
            db.execSQL(delTemp);
            Log.d(TAG,"升级成功");
        }
    }

}
