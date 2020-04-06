package com.mtsealove.github.buslinkerpt.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class AlertOpenHelper extends SQLiteOpenHelper {
    public static String Table = "Alert";

    public AlertOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AlertOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public AlertOpenHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table Alert(" +
                "id integer primary key autoincrement,"+
                "Title varchar(45)," +
                "Message varchar(100)," +
                "Time varchar(15)," +
                "Read boolean," +
                "Act varchar(45))";
        try {
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertAlert(SQLiteDatabase db, String title, String Msg, String Time, String Action) {
        String query = "insert into Alert(Title, Message, Time, Read, Act) values('"+title+"', '"+Msg+"', '"+Time+"', 1, '"+Action+"')";
        db.beginTransaction();
        try {
            db.execSQL(query);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void SetRead(SQLiteDatabase db, int id) {
        String query="update Alert set Read=0 where id="+id;
        db.beginTransaction();
        try {
            db.execSQL(query);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
