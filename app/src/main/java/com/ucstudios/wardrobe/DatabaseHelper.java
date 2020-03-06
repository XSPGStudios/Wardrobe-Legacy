package com.ucstudios.wardrobe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "categories_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "name";
    public String PINZA;
    private static final String COL01= "names";


    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);


    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + COL1 +" TEXT"+")";



        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PINZA);
        onCreate(db);
    }
    public boolean addData(String item){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG,"addData : Adding " + item + " to " + TABLE_NAME);
        db.execSQL(" CREATE TABLE "+ PINZA +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, names TEXT)");
        Log.d(TAG, "Table "+ PINZA +" created");


        if (result!=1){
            return true;


        }
        else return false;




        }

    public boolean addData1(String sex,String item1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COL01, item1);

        long result1 = db.insert(sex, null, contentValues1);
        Log.d("Message ", item1 +" saved in "+ sex);
        if(result1!=1)return true;
        else return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return  data;

    }
    public Cursor getData1(String sat){
        SQLiteDatabase db =this.getWritableDatabase();
        String query1 = "SELECT * FROM " + sat;
        Cursor data1 = db.rawQuery(query1, null);
        return data1;
    }

    public boolean ReplaceItem(String tabletitle, String item, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("names", item);
        db.update(tabletitle, cv,"ID="+i,null);
        return true;
    }

}
