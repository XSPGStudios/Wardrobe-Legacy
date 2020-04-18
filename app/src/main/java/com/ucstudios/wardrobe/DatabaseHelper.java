package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {



    private static final String TABLE_NAME = "categories_table";
    private static final String TABLE_NAME1 = "outfit_table";
    private static final String COL0 = "ROWID";
    private static final String COL1 = "name";
    public String PINZA;



    private static final String COL01= "names";


    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);


    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + " ("+ COL1 +" TEXT,IC INTEGER "+")";
        String createTableOutfit = "CREATE TABLE "+ TABLE_NAME1 + "(name TEXT)";



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
    public boolean addData(String item,int icon){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        ContentValues a = new ContentValues();
        contentValues.put(COL1, item);
        contentValues.put("IC",icon);
        db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG,"addData : Adding " + item + " to " + TABLE_NAME);
        db.execSQL("BEGIN TRANSACTION");
        db.execSQL(" CREATE TABLE "+ PINZA +"(names TEXT,IC2 INTEGER,POS INTEGER,size TEXT,brand TEXT,value INTEGER,currency INTEGER,image BLOB)");
        Log.d(TAG, "Table "+ PINZA +" created");
        db.execSQL("ALTER TABLE "+TABLE_NAME1+" ADD COLUMN "+PINZA+" TEXT");
        a.put("POS",0);
        db.update(PINZA,a,"ROWID=(SELECT MAX(ROWID) FROM "+ PINZA +")", null);
        db.execSQL("COMMIT");

        Log.d(TAG, PINZA+" column created in "+ TABLE_NAME1);

        db.close();
        return true;
        }

    public boolean addData1(String table,String name,String size,Integer icon,String brand,int value,int currency,byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COL01, name);
        contentValues1.put("IC2",icon);
        contentValues1.put("size",size);
        contentValues1.put("brand",brand);
        contentValues1.put("value",value);
        contentValues1.put("currency",currency);
        contentValues1.put("image",image);
        long result1 = db.insert(table, null, contentValues1);
        Log.d("Message ", name +" saved in "+ table);
        db.close();
        if(result1!=1)return true;
        else return true;
    }

    public boolean addData2(String item1){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1, item1);
        db.update("outfit_table", cv,"ROWID=(SELECT MAX(ROWID) FROM outfit_table)",null);
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

    public boolean ReplaceItem(String tabletitle, String item,String size,String brand, Integer value,Integer currency,Integer icon,byte[] image, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("names", item);
        cv.put("IC2",icon);
        cv.put("size",size);
        cv.put("brand",brand);
        cv.put("value",value);
        cv.put("currency",currency);
        cv.put("image",image);
        db.update(tabletitle, cv,"ROWID="+i,null);
        return true;
    }

    public boolean ReplaceItemnoPic(String tabletitle, String item,String size,String brand,Integer value,Integer currency,Integer icon,int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("names",item);
        cv.put("IC2",icon);
        cv.put("size",size);
        cv.put("brand",brand);
        cv.put("value",value);
        cv.put("currency",currency);
        db.update(tabletitle,cv,"ROWID="+i,null);
        return true;
    }

    public boolean ReplaceOutfit(String nome,Integer pos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",nome);
        db.update(TABLE_NAME1,cv,"ROWID="+pos,null);
        return true;
    }

    public boolean ReplaceItem4(String tabletitle, String item, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", item);
        db.update(tabletitle, cv,"ROWID="+i,null);
        return true;
    }

    public boolean ReplaceItemOutfit(String item,String negro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(negro, item);
        db.update("outfit_table", cv,"ROWID=(SELECT MAX(ROWID) FROM outfit_table)",null);
        Log.d(TAG, "ReplaceData : Adding "+item+" to "+negro);
        return true;
    }

    public boolean ReplaceItemOutfitatId(String item,String negro,Integer position){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(negro, item);
        db.update("outfit_table", cv,"ROWID="+position,null);
        Log.d(TAG, "ReplaceData : Adding "+item+" to "+negro+" at "+position);
        return true;
    }

    public boolean ReplaceIteminOutfitTable(String item,String column,String itemvecchio){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column, item);
        db.update("outfit_table", cv,column+"='"+itemvecchio+"'",null);
        Log.d(TAG, "ReplaceData : Adding "+item+" to "+column);
        return true;
    }

    public boolean DeleteIteminOutfitafteredit(String column,String itemvecchio){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column, (byte[]) null);
        db.update("outfit_table",cv,column+"='"+itemvecchio+"'",null);
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
        db.delete(table,"name=?", new String[]{item});
        db.execSQL("VACUUM");
        return true;

    }

    public  boolean delete4(String table, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "addData : Deleting "+item+" from column name in table "+ table);
        db.delete(table,"names=?", new String[]{item});
        db.execSQL("VACUUM");
        return true;

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




    public void ReplaceIcon(int icon, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IC", icon);
        db.update("categories_table",cv,"ROWID="+i,null);

    }

    public Cursor GetBasket(String tablename){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT * FROM "+tablename+" WHERE POS = 1";
        Cursor data1 = db.rawQuery(query1, null);
        return data1;
    }

    public boolean toBasket(String tablename, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("POS", 1);
        db.update(tablename, cv,"ROWID="+i,null);
        return true;
    }

    public boolean toWardrobemodif(String tablename, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("POS", (byte[]) null);
        db.update(tablename, cv,"ROWID="+i,null);
        return true;
    }
    public boolean toLaundry(String tablename, String SpecificItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("POS", 2);
        db.update(tablename, cv,"names='"+SpecificItem+"'",null);
        return true;
    }
    public boolean toWardrobe(String tablename, String SpecificItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("POS", (byte[]) null);
        db.update(tablename, cv,"names='"+SpecificItem+"'",null);
        return true;
    }


    public boolean WashingMachineActivacted(String tablename, String SpecificItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("POS", 3);
        db.update(tablename, cv,"names='"+SpecificItem+"'",null);
        return true;
    }




    public boolean toBasketmodif(String tablename, String SpecificItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("POS", 1);
        db.update(tablename, cv,"names='"+SpecificItem+"'",null);
        return true;
    }

    public Cursor GetItemData(Integer pos,String tablename){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+tablename+" WHERE ROWID="+pos+"";
        Cursor data= db.rawQuery(query,null);
        return data;
    }



    public void AddPictureItem(String tablename,byte[] image,int i) throws SQLException{

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("image",image);
        db.update(tablename,cv,"ROWID="+i,null);
    }

    public Cursor GetBasketSpecific(String tablename,String itemname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT * FROM "+tablename+" WHERE POS = 1 AND names = '"+itemname+"'";
        Cursor data1 = db.rawQuery(query1, null);
        return data1;
    }

    public Cursor GetWardrobeSpecific(String tablename,String itemname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT * FROM "+tablename+" WHERE POS = 1 AND names = '"+itemname+"'";
        Cursor data1 = db.rawQuery(query1, null);
        return data1;
    }

    public Cursor GetWMSpecific(String tablename,String itemname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT * FROM "+tablename+" WHERE POS = 2 AND names = '"+itemname+"'";
        Cursor data1 = db.rawQuery(query1, null);
        return data1;
    }

    public Cursor GetWM(String tablename){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT * FROM "+tablename+" WHERE POS = 2";
        Cursor data1 = db.rawQuery(query1, null);
        return data1;
    }

    public Cursor GetItemOutfit(String columname,String outfitname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+columname+" FROM outfit_table WHERE name ='"+outfitname+"'";
        Cursor data = db.rawQuery(query,null);
        return data;

    }

    public Cursor GetByteOutfit(String tablename,String itemname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT image FROM "+tablename+" WHERE names=+'"+itemname+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor GetSpecificIdItem(String tablename, String Itemname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ROWID FROM "+tablename+" WHERE names=+'"+Itemname+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void GetNullOutfitName(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME1+" WHERE name IS NULL");

    }

    public void DeleteOutfit(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME1+" WHERE name='"+name+"'");
    }

    public void SwapRows(int position1,int position2,String table){
       SQLiteDatabase db = this.getWritableDatabase();

       String query = "SELECT * FROM "+table+" WHERE ROWID="+position1;
       String query2 = "SELECT * FROM "+table+" WHERE ROWID="+position2;
       @SuppressLint("Recycle") Cursor datafirst = db.rawQuery(query,null);
        @SuppressLint("Recycle") Cursor datasecond = db.rawQuery(query2,null);
        ContentValues cv2 = new ContentValues();
        ContentValues cv = new ContentValues();
       while(datafirst.moveToNext()){
           cv.put("names",datafirst.getString(0));
           cv.put("IC2",datafirst.getString(1));
           cv.put("POS",datafirst.getString(2));
           cv.put("size",datafirst.getString(3));
           cv.put("brand",datafirst.getString(4));
           cv.put("value",datafirst.getInt(5));
           cv.put("currency",datafirst.getInt(6));
           cv.put("image",datafirst.getBlob(7));
       }



       while(datasecond.moveToNext()){
           cv2.put("names",datasecond.getString(0));
           cv2.put("IC2",datasecond.getString(1));
           cv2.put("POS",datasecond.getString(2));
           cv2.put("size",datasecond.getString(3));
           cv2.put("brand",datasecond.getString(4));
           cv2.put("value",datasecond.getInt(5));
           cv2.put("currency",datasecond.getInt(6));
           cv2.put("image",datasecond.getBlob(7));

       }

       db.update(table,cv,"ROWID="+position2,null);
       db.update(table,cv2,"ROWID="+position1,null);
       db.execSQL("VACUUM");


    }

    public void SwapRowsOutfit(int position1,int position2){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM outfit_table WHERE ROWID="+position1;
        String query2 = "SELECT * FROM outfit_table WHERE ROWID="+position2;
        @SuppressLint("Recycle") Cursor datafirst  = db.rawQuery(query,null);
        @SuppressLint("Recycle") Cursor datasecond  = db.rawQuery(query2,null);
        Cursor c = getData();
        ContentValues cv = new ContentValues();
        ContentValues cv2 = new ContentValues();
        final ArrayList<String> columns = new ArrayList<>();
        while (c.moveToNext()){
             columns.add(c.getString(0));
        }
        while(datafirst.moveToNext()){
        for(int i=0;(i<columns.size()+1);i++){
            if(i==0){
                cv.put("name",datafirst.getString(i));
            }
            else{
                cv.put(columns.get(i-1),datafirst.getString(i));
            }
        }
        while(datasecond.moveToNext()){
                for(int i=0;(i<columns.size()+1);i++){
                    if(i==0){
                        cv2.put("name",datasecond.getString(i));
                    }
                    else{
                        cv2.put(columns.get(i-1),datasecond.getString(i));
                    }
                }

        }


    }

        db.update("outfit_table",cv,"ROWID="+position2,null);
        db.update("outfit_table",cv2,"ROWID="+position1,null);
        db.execSQL("VACUUM");
    }

}











