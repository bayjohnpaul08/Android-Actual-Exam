package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import javax.xml.transform.Result;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Product.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_product";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_UNIT = "product_unit";
    private static final String COLUMN_PRICE = "product_price";
    private static final String COLUMN_DATE_EXPIRY = "product_date_expiry";
    private static final String COLUMN_INVENTORY = "product_inventory";
    private static final String COLUMN_INVENTORY_COST = "product_inventory_cost";
    private static final String COLUMN_IMAGE = "product_image";

    public DBHelper(@Nullable  Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_UNIT + " TEXT, " +
                COLUMN_PRICE + " REAL," +
                COLUMN_DATE_EXPIRY + " TEXT," +
                COLUMN_INVENTORY + " INTEGER," +
                COLUMN_INVENTORY_COST + " REAL," +
                COLUMN_IMAGE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addProduct(String product_name, String unit, String price, String date_expiry, String inventory, String inventory_cost, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCT_NAME, product_name);
        cv.put(COLUMN_UNIT, unit);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_DATE_EXPIRY, date_expiry);
        cv.put(COLUMN_INVENTORY, inventory);
        cv.put(COLUMN_INVENTORY_COST, inventory_cost);
        cv.put(COLUMN_IMAGE, image);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String product_name, String unit, String price, String date_expiry, String inventory, String inventory_cost, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT_NAME, product_name);
        cv.put(COLUMN_UNIT, unit);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_DATE_EXPIRY, date_expiry);
        cv.put(COLUMN_INVENTORY, inventory);
        cv.put(COLUMN_INVENTORY_COST, inventory_cost);
        cv.put(COLUMN_IMAGE, image);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
