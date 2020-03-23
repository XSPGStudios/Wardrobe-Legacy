package com.ucstudios.wardrobe;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {



    private static final String TABLE_NAME = "categories_table";
    private static final String TABLE_NAME1 = "outfit_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "name";
    public String PINZA;
    public String help;//sendHelppls


    private static final String COL01= "names";


    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);


    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + COL1 +" TEXT,IC INTEGER "+")";
        String createTableOutfit = "CREATE TABLE "+ TABLE_NAME1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";



        db.execSQL(createTableOutfit);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PINZA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }
    public boolean addData(String item){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues cv = new ContentValues();
        contentValues.put(COL1, item);
        db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG,"addData : Adding " + item + " to " + TABLE_NAME);
        db.execSQL("BEGIN TRANSACTION");
        db.execSQL(" CREATE TABLE "+ PINZA +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, names TEXT)");
        Log.d(TAG, "Table "+ PINZA +" created");
        db.execSQL("ALTER TABLE "+TABLE_NAME1+" ADD COLUMN "+PINZA+" TEXT");
        cv.put("IC", 0);
        db.update("categories_table", cv,"ID=(SELECT MAX(ID) FROM categories_table)",null);
        db.execSQL("COMMIT");
        Log.d(TAG, PINZA+" column created in "+ TABLE_NAME1);

        db.close();
        return true;
        }

    public boolean addData1(String sex,String item1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COL01, item1);

        long result1 = db.insert(sex, null, contentValues1);
        Log.d("Message ", item1 +" saved in "+ sex);
        db.close();
        if(result1!=1)return true;
        else return true;
    }

    public boolean addData2(String item1){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1, item1);
        db.update("outfit_table", cv,"ID=(SELECT MAX(ID) FROM outfit_table)",null);
        Log.d(TAG, "ReplaceData : Adding "+item1+" to "+COL1);
        return true;
    }

    public boolean addData3(String item1, String negrone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(negrone, item1);
        long result1 = db.insert(TABLE_NAME1, null, contentValues);
        Log.d(TAG,"addData : Adding " + item1 + " to " + negrone);
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

    public Cursor getData2(){
        SQLiteDatabase db =this.getWritableDatabase();
        String query1 = "SELECT name FROM " + TABLE_NAME1;
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

    public boolean ReplaceItem4(String tabletitle, String item, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", item);
        db.update(tabletitle, cv,"ID="+i,null);
        return true;
    }

    public boolean ReplaceItemOutfit(String item,String negro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(negro, item);
        db.update("outfit_table", cv,"ID=(SELECT MAX(ID) FROM outfit_table)",null);
        Log.d(TAG, "ReplaceData : Adding "+item+" to "+negro);
        return true;
    }


    public boolean delete1(String sex, String edit){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "addData : Deleting "+ edit +" from column "+ sex +" in table "+TABLE_NAME1);
        return db.delete(TABLE_NAME1,sex+"=?",new String[]{edit}) > 0;


    }

    public boolean delete2(String table, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "addData : Deleting "+item+" from column names in table "+ table);
        return db.delete(table,"names=?", new String[]{item}) > 0;


    }

    public boolean delete3(String table, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "addData : Deleting "+item+" from column name in table "+ table);
        return db.delete(table,"name=?", new String[]{item}) > 0;


    }

    public boolean TABLEDROP(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table);
        return true;
    }


    public boolean OutfitColumnDrop(String column,String pietro13){

        Log.i("ecco","sda"+column);
        Log.i("ecco","sda"+pietro13);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("BEGIN TRANSACTION");
        db.execSQL("CREATE TABLE outfit_table_temp("+column+")");
        db.execSQL("INSERT INTO outfit_table_temp SELECT "+pietro13+" FROM outfit_table");
        db.execSQL("DROP TABLE outfit_table");
        db.execSQL("CREATE TABLE outfit_table("+column+")");
        db.execSQL("INSERT INTO outfit_table SELECT "+pietro13+" FROM outfit_table_temp");
        db.execSQL("DROP TABLE outfit_table_temp");
        db.execSQL("COMMIT");

        return true;
    }

    public boolean OutfitChangeColumnName(String column, String pietro13){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("BEGIN TRANSACTION");
        db.execSQL("ALTER TABLE outfit_table RENAME TO outfit_temp");
        db.execSQL("CREATE TABLE outfit_table("+column+")");
        db.execSQL("INSERT INTO outfit_table SELECT "+pietro13+" FROM outfit_temp");
        db.execSQL("DROP TABLE outfit_temp");
        db.execSQL("COMMIT");

        return true;
    }


    public boolean TableRenamer(String tablename, String coto){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("BEGIN TRANSACTION");
        db.execSQL("ALTER TABLE "+tablename+" RENAME TO "+coto);
        db.execSQL("COMMIT");
       return true;

    }




    public boolean ReplaceIcon(int icon, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IC", icon);
        db.update("categories_table", cv,"ID="+i,null);
        return true;
    }






}
