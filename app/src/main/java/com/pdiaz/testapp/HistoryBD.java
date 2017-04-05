package com.pdiaz.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class HistoryBD extends SQLiteOpenHelper {

    static final String name = "Liverpool";
    Context ctx;
    static final int version = 1;

    public HistoryBD(Context ctx, String name, SQLiteDatabase.CursorFactory factory, int version){
       super(ctx,name,factory,version);
    }

    public HistoryBD(Context ctx){
        super(ctx,name,null,version);
        this.ctx = ctx;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Histories (query TEXT, PRIMARY KEY(query))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addSearch(String query){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            if (db != null){
                ContentValues values = new ContentValues();
                values.put("query",query);
                db.insertOrThrow("Histories",null,values);
            }
            db.close();

        }catch (SQLiteConstraintException ex){
            Log.e("ERROR constraints",ex.toString());
        }catch (SQLException ex2){
            Log.e("ERROR BD",ex2.toString());
        }catch (Exception ex3){
            Log.e("ERROR GLOBAL",ex3.toString());
        }

    }
    public ArrayList getHistory(){
        ArrayList<String> searches = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null){
            Cursor c = db.rawQuery("SELECT * FROM Histories",null);
            if (c.moveToFirst()){
                do {
                    searches.add(c.getString(0));
                }while(c.moveToNext());
            }
        }
        db.close();
        return searches;
    }
}
