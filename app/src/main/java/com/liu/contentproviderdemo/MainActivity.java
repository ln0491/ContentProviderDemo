package com.liu.contentproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liu.contentproviderdemo.bean.Book;
import com.liu.contentproviderdemo.bean.User;
import com.liu.contentproviderdemo.util.L;

public class MainActivity extends AppCompatActivity {

    private ContentResolver mCResolver;
    private static final String AUTHORITY ="com.liu.contentproviderdemo.provider";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initUser();
    }



    private void initData() {

        mCResolver = getContentResolver();

        Uri uri  = Uri.parse("content://"+AUTHORITY+"/book");

        ContentValues contentValues = new ContentValues();
        contentValues.put("_id",10);
        contentValues.put("name","Android 开发者艺术");

        mCResolver.insert(uri,contentValues);


        Cursor cursor = mCResolver.query(uri, new String[]{"_id", "name"}, null,null,"_id DESC");//_id ASC

        while (cursor.moveToNext()){

            Book book = new Book();

            book._id=cursor.getInt(cursor.getColumnIndex("_id"));
            book.name=cursor.getString(cursor.getColumnIndex("name"));
            L.d("vivi","query book : "+book.toString());

        }
        cursor.close();


    }

    private void initUser() {

        Uri uri = Uri.parse("content://"+AUTHORITY+"/user");

        Cursor cursor = mCResolver.query(uri, new String[]{"_id", "name", "sex"}, null, null, "_id DESC");

        while (cursor.moveToNext()){

            User user = new User();
            user._id=cursor.getInt(cursor.getColumnIndex("_id"));
            user.name=cursor.getString(cursor.getColumnIndex("name"));
            user.sex=cursor.getInt(cursor.getColumnIndex("sex"));

            L.d("vivi","query user :  "+user.toString());
        }
        cursor.close();


    }
}
