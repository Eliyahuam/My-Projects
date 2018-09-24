package com.example.eliyahugalfinal.picapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliba on 31/03/2017.
 */

public class PictureSql {

    final static String IMAGE_TABLE = "IMAGE_TABLE";
    final static String IMG_DECRIPTION = "IMG_DECRIPTION";
    final static String USERID = "USERID";
    final static String IMG_URL = "IMG_URL";
    final static String IMG_NAME = "IMG_NAME";



    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + IMAGE_TABLE + " (" +
                IMG_DECRIPTION + " TEXT PRIMARY KEY," +
                USERID + " TEXT," +
                IMG_URL + " TEXT," +
                IMG_NAME + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + IMAGE_TABLE + ";");
    }

    public static List<Picture> getAllPicsByUid(SQLiteDatabase db,String uid) {
        String where = USERID + " = ?";
        String[] args = {uid};
        Cursor cursor = db.query(IMAGE_TABLE, null, where , args, null, null, null);
        List<Picture> picList = new LinkedList<Picture>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(IMG_DECRIPTION);
            int useridIndex = cursor.getColumnIndex(USERID);
            int imageUrlIndex = cursor.getColumnIndex(IMG_URL);
            int imageNameIndex = cursor.getColumnIndex(IMG_NAME);

            do {
                String id = cursor.getString(idIndex);
                String userid = cursor.getString(useridIndex);
                String imageUrl = cursor.getString(imageUrlIndex);
                String imageName = cursor.getString(imageNameIndex);
                Picture pic = new Picture();
                pic.descreption = id;
                pic.imageName = imageName;
                pic.url = imageUrl;



                picList.add(pic);
            } while (cursor.moveToNext());
        }
        return picList;
    }


    public static void add(SQLiteDatabase db, Picture pic,String uid) {
        ContentValues values = new ContentValues();
        values.put(IMG_DECRIPTION, pic.descreption);
        values.put(IMG_URL, pic.url);
        values.put(IMG_NAME, pic.imageName);
        values.put(USERID,uid);


        db.insertWithOnConflict(IMAGE_TABLE, IMG_DECRIPTION, values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static double getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db,IMAGE_TABLE);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, double date){
        LastUpdateSql.setLastUpdate(db,IMAGE_TABLE, date);
    }
}
