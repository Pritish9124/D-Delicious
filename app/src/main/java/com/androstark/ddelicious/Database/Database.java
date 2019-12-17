package com.androstark.ddelicious.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.androstark.ddelicious.Model.AddressData;
import com.androstark.ddelicious.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;



public class Database extends SQLiteAssetHelper
{
    private static final String DB_NAME = "DDeliciousDB.db";
    private static final int DB_VER = 2;

     private Context mycontext;

    public Database(Context context)
    {
        super(context,DB_NAME,null,DB_VER);
        mycontext = context;
    }
    public List<Order> getCarts()
    {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        String[] sqlselect = {"ProductName","ProductId","Quantity","Price","ProductImage"};
        String sqlTable = "OrderDetail";
        sqLiteQueryBuilder.setTables(sqlTable);
        Cursor c = sqLiteQueryBuilder.query(database,sqlselect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst())
        {
            do {
                result.add(new Order
                        (c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                                c.getString(c.getColumnIndex("ProductImage"))
                        ));

            }
            while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order)
    {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,ProductImage) VALUES ('%s','%s','%s','%s','%s');",
                order.getProductId(),order.getProductName(),order.getQuantity(),order.getPrice(),order.getProductImage());
        database.execSQL(query);


    }


    public void clean_from_cart(String id)
    {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail WHERE ProductId='%s'",id);
        database.execSQL(query);
    }
    public void cleanCart()
    {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        database.execSQL(query);
    }



    public List<AddressData> getAddress()
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        String [] addressselect = {"id","customername","customersreetaddress","shippping_adress2","customercity","customerstate","customerpostalcode","shippping_phone","shippping_country"};
        String addresstable = "AddressDetails";
        sqLiteQueryBuilder.setTables(addresstable);
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase,addressselect,null,null,null,null,null);
        final List<AddressData> result = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            do
            {
                result.add(new AddressData(
                        cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("customername")),
                        cursor.getString(cursor.getColumnIndex("customersreetaddress")),
                        cursor.getString(cursor.getColumnIndex("shippping_adress2")),
                        cursor.getString(cursor.getColumnIndex("customercity")),
                        cursor.getString(cursor.getColumnIndex("customerstate")),
                        cursor.getString(cursor.getColumnIndex("customerpostalcode")),
                        cursor.getString(cursor.getColumnIndex("shippping_phone")),
                        cursor.getString(cursor.getColumnIndex("shippping_country"))



                ));
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return result;
    }
    public void addAddress(AddressData addressData)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("INSERT INTO AddressDetails(customername,customersreetaddress,shippping_adress2,customercity,customerstate,customerpostalcode,shippping_phone,shippping_country)values('%s','%s','%s','%s','%s','%s','%s','%s');",
                addressData.getCustomername(),addressData.getCustomersreetaddress(),addressData.getShippping_adress2(),addressData.getCustomercity(),addressData.getCustomerstate(),addressData.getCustomerpostalcode(),addressData.getShippping_phone(),addressData.getShippping_country());
        sqLiteDatabase.execSQL(query);

    }
    public void clean_from_address(String id)
    {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM AddressDetails WHERE id='%s'",id);
        database.execSQL(query);
    }

    public void cleanAddress() {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM AddressDetails");
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TAG", "Database Version: OLD: "+ oldVersion + " = NEW: "+newVersion);


        if (oldVersion<2)
        {
            mycontext.deleteDatabase(DB_NAME);
        }


    }


}
