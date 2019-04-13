package com.example.classman.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Build.ID;

public class UserDao extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "ClassM";
    private final static int DATABASE_VERSION = 2;
    public final static String LOGIN_TABLE_NAME = "users";
    public static final String LOGIN_ID = "_id";
    public final static String LOGIN_USER = "username";
    public final static String LOGIN_PWD = "password";
    public final static String LOGIN_TEL= "tel";
    public final static String LOGIN_CLASS= "cla";
    public final static String LOGIN_POWER = "power";



    public UserDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + LOGIN_TABLE_NAME + " ("
                + LOGIN_ID + " integer primary key autoincrement,"
                + LOGIN_USER + " text unique not null, "
                + LOGIN_PWD + " text, "
                + LOGIN_TEL + " number,"
                +LOGIN_CLASS+" text,"
                +LOGIN_POWER+" text);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

        String sql = "drop table if exists " + LOGIN_TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
    public long insert(String[] strArray){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN_USER, strArray[0]);
        cv.put(LOGIN_PWD, strArray[1]);
        cv.put(LOGIN_TEL, strArray[2]);
        cv.put(LOGIN_CLASS, strArray[3]);
        cv.put(LOGIN_POWER, strArray[4]);
        return db.insert(LOGIN_TABLE_NAME, null, cv);
    }
    public Cursor login(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from users where username=? and password=?";
        Cursor cursor=db.rawQuery(sql, new String[]{username,password});
        return cursor;
    }
    public Cursor select(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(LOGIN_TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
    public Cursor selectBy(String cla, String power){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from users where cla=? and power=?";
        Cursor cursor=db.rawQuery(sql, new String[]{cla,power});
        return cursor;
    }
    public Cursor selectBycla(String cla){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from users where cla=? ";
        Cursor cursor=db.rawQuery(sql, new String[]{cla});
        return cursor;
    }
    public Cursor selectBypower(String power){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from users where power=? ";
        Cursor cursor=db.rawQuery(sql, new String[]{power});
        return cursor;
    }
    public Cursor selectById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from users where _id=? ";
        Cursor cursor=db.rawQuery(sql, new String[]{id});
        return cursor;
    }
    public void delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = LOGIN_ID+"=?";
        String[] whereValues = {id};
        db.delete(LOGIN_TABLE_NAME, where, whereValues);
    }
    public int update(String id,String[] strArray){
        SQLiteDatabase db = this.getWritableDatabase();
        String where =LOGIN_ID+"=?";
        String[] whereValues = {id};
        ContentValues cv = new ContentValues();
        cv.put(LOGIN_USER, strArray[0]);
        cv.put(LOGIN_PWD, strArray[1]);
        cv.put(LOGIN_TEL,strArray[2]);
        cv.put(LOGIN_CLASS, strArray[3]);
        cv.put(LOGIN_POWER, strArray[4]);
        return db.update(LOGIN_TABLE_NAME, cv, where, whereValues);
    }
}
