package com.beingknow.eatit2020.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beingknow.eatit2020.ModelResponse.OrderDetailResponse;
import com.beingknow.eatit2020.Models.Item1;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CartDB2.db";
    public static final String TABLE_NAME = "cart";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_ITEMID = "item_id";
    private long Sum = 0;
    private HashMap hp;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table cart " +
                        "(id integer primary key, name text, quantity text, price text, amount text, item_id integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    public boolean insertCart (String name, String quantity, String price, String amount, int item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);
        contentValues.put("amount", amount);
        contentValues.put("item_id", item_id);
        db.insert("cart", null, contentValues);
        return true;
    }

    public boolean insertAmount(String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        db.insert("cart", null, contentValues);
        return true;
    }

    public void getallAmount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select amount from cart";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        cursor.close();
    }

    public boolean updateCart (Integer id, String quantity, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", quantity);
        contentValues.put("amount", amount);
        db.update("cart", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from cart";
        Cursor res =  db.rawQuery(query, null);
        return res;
    }

    public boolean getAllreadyItem(int item_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select name,quantity,amount from cart where item_id = " + item_id;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        cursor.close();
       return true;
    }

    public boolean getItem(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from cart where id = " + id;
        Cursor cursor = db.rawQuery(query,null);
        return true;
    }

    public ArrayList<Item1> getCartData() {
        ArrayList<Item1> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        cursor.moveToFirst();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item1 item = new Item1();
                item.setName(cursor.getString(1));
                item.setQuantity(cursor.getString(2));
                item.setPrice(cursor.getDouble(3));
                arrayList.add(item);
            }while (cursor.moveToNext());
        }
       // System.out.println(arrayList);
        cursor.close();
        return arrayList;
    }

    public ArrayList<Item1> getCartData1() {
        ArrayList<Item1> arrayList1 = new ArrayList<>();

        // select all query
        String select_query1= "SELECT name,quantity,amount FROM " + TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor1 = db.rawQuery(select_query1, null);
        cursor1.moveToFirst();
        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {
                Item1 item = new Item1();
                item.setName(cursor1.getString(0));
                item.setQuantity(cursor1.getString(1));
                item.setPrice(cursor1.getDouble(2));
                arrayList1.add(item);
            }while (cursor1.moveToNext());
        }
        // System.out.println(arrayList);
        cursor1.close();
        return arrayList1;
    }

    public int getItemId()
    {
        String select_query1= "SELECT item_id FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(select_query1, null);
        cursor1.moveToFirst();
        int i_id = cursor1.getInt(0);
        System.out.println("Item_id = " + i_id);
        cursor1.close();
        return i_id;
    }
    public String getQuantity()
    {
        String select_query1= "SELECT quantity FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(select_query1, null);
        cursor1.moveToFirst();
        String qty = cursor1.getString(0);
        cursor1.close();
        return qty;
    }
    public float getAmount()
    {
        String select_query1= "SELECT amount FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(select_query1, null);
        cursor1.moveToFirst();
        float amt = cursor1.getFloat(0);
        cursor1.close();
        return amt;
    }

    public ArrayList<OrderDetailResponse> addOrderDetail()
    {
        ArrayList<OrderDetailResponse> arrayList = new ArrayList<>();
        String select_query1= "SELECT item_id,quantity,amount FROM " + TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor1 = db.rawQuery(select_query1, null);
        cursor1.moveToFirst();

        if (cursor1.moveToFirst()) {
            do {
                OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
                orderDetailResponse.setItem_id(cursor1.getInt(0));
                orderDetailResponse.setQuantity(cursor1.getString(1));
                orderDetailResponse.setAmount(cursor1.getFloat(2));
                arrayList.add(orderDetailResponse);
            }while (cursor1.moveToNext());
        }
        // System.out.println(arrayList);
        cursor1.close();
        return arrayList;

    }

    public boolean getCartData2(int item_id) {
        ArrayList<Item1> arrayList2 = new ArrayList<>();

        // select all query
        String select_query2= "SELECT name,quantity,amount FROM " + TABLE_NAME + " where item_id = " + item_id;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor2 = db.rawQuery(select_query2, null);
        cursor2.moveToFirst();
        // looping through all rows and adding to list
        if (cursor2.moveToFirst()) {
            do {
                Item1 item = new Item1();
                item.setName(cursor2.getString(0));
                item.setQuantity(cursor2.getString(1));
                item.setPrice(cursor2.getDouble(2));
                arrayList2.add(item);
            }while (cursor2.moveToNext());
        }
        // System.out.println(arrayList);
        cursor2.close();
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }



    public Integer deleteCartItem (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cart",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public boolean deleteAllItems()
    {
        String query= "DELETE FROM " + TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query, null);
        cursor1.moveToFirst();
        cursor1.close();
        return true;
    }

    public ArrayList<String> getAllCartItem() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from cart", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getAllCartItem1() {
        ArrayList array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from cart", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }


    public long sum_Of_Price()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM (" + COLUMN_PRICE + ") FROM " + TABLE_NAME , null);
        cursor.moveToFirst();
        int sum = cursor.getInt(0);
        cursor.close();
        System.out.println(sum);
        return sum;

    }

    public long sum_Of_Amount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM (" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME , null);
        cursor.moveToFirst();
        int sum = cursor.getInt(0);
        cursor.close();
        System.out.println(sum);
        return sum;

    }


}
