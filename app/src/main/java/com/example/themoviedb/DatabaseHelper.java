package com.example.themoviedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "MovieDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "app_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Profile.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Profile.TABLE_NAME);
        onCreate(db);
    }

    public int updateProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Profile.COLUMN_USERNAME, profile.getUserName());
        values.put(Profile.COLUMN_PASSWORD, profile.getPassword());
        values.put(Profile.COLUMN_PROFILE_PICTURE, profile.getProfilePicture());

        return db.update(Profile.TABLE_NAME, values, Profile.COLUMN_ID + " = ?",
                new String[]{String.valueOf(profile.getId())});
    }

    public Profile getProfile(){
        Log.d(TAG, "Get profile for " + MainActivity.currentUser);
        String[] selectionArgs = new String[]{MainActivity.currentUser};
        String[] projection = new String[]{Profile.COLUMN_ID,
                Profile.COLUMN_USERNAME,
                Profile.COLUMN_PASSWORD,
                Profile.COLUMN_PROFILE_PICTURE};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Profile.TABLE_NAME,
                projection,
                Profile.COLUMN_USERNAME + " =?", selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Profile profile = new Profile(cursor.getInt(cursor.getColumnIndex(Profile.COLUMN_ID)),
                    MainActivity.currentUser,
                    cursor.getString(cursor.getColumnIndex(Profile.COLUMN_PASSWORD)),
                    cursor.getBlob(cursor.getColumnIndex(Profile.COLUMN_PROFILE_PICTURE)));

            cursor.close();
            return profile;
        }

        return null;
    }

    public Boolean login(String username, String password){
        Log.d(TAG, "Logging in: " + username);
        String[] selectionArgs = new String[]{username, password};

        String[] projection = new String[]{Profile.COLUMN_PASSWORD};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Profile.TABLE_NAME,
                projection,
                Profile.COLUMN_USERNAME + " =? AND " + Profile.COLUMN_PASSWORD + "=?", selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }

        return false;
    }

    public void register(Profile profile) {
        Log.d(TAG, "Registering");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Profile.COLUMN_USERNAME, profile.getUserName());
        values.put(Profile.COLUMN_PASSWORD, profile.getPassword());
        values.put(Profile.COLUMN_PROFILE_PICTURE, profile.getProfilePicture());

        long id = db.insert(Profile.TABLE_NAME, null, values);

        db.close();
    }
}
