package com.liu.contentproviderdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.liu.contentproviderdemo.db.DbOpenHelper;
import com.liu.contentproviderdemo.util.L;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class BookProvider extends ContentProvider {

    private static final String TAG = "vivi";

    private static final String AUTHORITY ="com.liu.contentproviderdemo.provider";


    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/user");


    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    public static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    private Context mContent;
    private SQLiteDatabase mDb;

    static {

        mUriMatcher.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        mUriMatcher.addURI(AUTHORITY,"user",USER_URI_CODE);

    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (mUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        L.d(TAG, "onCreate");

        mContent = getContext();

        inittProviderData();

        return true;
    }

    private void inittProviderData() {
        mDb = new DbOpenHelper(mContent).getWritableDatabase();

        mDb.execSQL("delete from "+ DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from "+ DbOpenHelper.USER_TABLE_NAME);

        mDb.execSQL("insert into book values (3,'Android'); ");
        mDb.execSQL("insert into book values (4,'Ios'); ");
        mDb.execSQL("insert into book values (5,'Html5'); ");
        mDb.execSQL("insert into book values (1,'Ios'); ");
        mDb.execSQL("insert into book values (2,'Ios'); ");
        mDb.execSQL("insert into user values (7,'LiuNan',0); ");
        mDb.execSQL("insert into user values (8,'AmyLee',1); ");
        mDb.execSQL("insert into user values (9,'ChangFang',1); ");

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        L.d(TAG, "query current thread : "+Thread.currentThread().getName());

       String table = getTableName(uri);
        if(table==null){
            throw  new IllegalArgumentException("不支持的URI "+ uri);
        }

        return mDb.query(table,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        L.d(TAG, "getType");

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        L.d(TAG, "insert");
        String table = getTableName(uri);
        if(table==null){
            throw  new IllegalArgumentException("不支持的URI "+ uri);
        }
         mDb.insert(table,null,values);

        mContent.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        L.d(TAG, "delete");
        String table = getTableName(uri);
        if(table==null){
            throw  new IllegalArgumentException("不支持的URI "+ uri);
        }
        int count = mDb.delete(table,selection,selectionArgs);
        if(count > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        L.d(TAG, "update");
        String table = getTableName(uri);
        if(table==null){
            throw  new IllegalArgumentException("不支持的URI "+ uri);
        }

        int row = mDb.update(table,values,selection,selectionArgs);

        if(row > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return row;
    }
}
