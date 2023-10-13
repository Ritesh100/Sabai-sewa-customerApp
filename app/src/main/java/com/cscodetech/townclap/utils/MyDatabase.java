package com.cscodetech.townclap.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cscodetech.townclap.activity.ItemListActivity;
import com.cscodetech.townclap.model.ServiceListItem;

import java.util.ArrayList;
import java.util.List;


public class MyDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_NAME = "items";
    public static final String ICOL_1 = "ID";
    public static final String ICOL_2 = "SID";
    public static final String ICOL_3 = "service_title";
    public static final String ICOL_4 = "service_price";
    public static final String ICOL_5 = "service_subcat_id";
    public static final String ICOL_6 = "service_sdesc";
    public static final String ICOL_7 = "service_childcat_id";
    public static final String ICOL_8 = "service_cat_id";
    public static final String ICOL_9 = "service_discount";
    public static final String ICOL_10 = "service_ttken";
    public static final String ICOL_11 = "service_mqty";
    public static final String ICOL_12 = "service_qty";
    SessionManager sessionManager;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sessionManager = new SessionManager(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SID TEXT , service_title TEXT ,service_price Double , service_subcat_id TEXT , service_sdesc TEXT, service_childcat_id TEXT , service_cat_id TEXT, service_discount Double , service_ttken TEXT , service_mqty TEXT , service_qty int )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(ServiceListItem rModel) {
        if (getID(rModel.getServiceId()) == -1) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ICOL_2, rModel.getServiceId());
            contentValues.put(ICOL_3, rModel.getServiceTitle());
            contentValues.put(ICOL_4, rModel.getServicePrice());
            contentValues.put(ICOL_5, rModel.getServiceSubcatId());
            contentValues.put(ICOL_6, rModel.getServiceSdesc());
            contentValues.put(ICOL_7, rModel.getServiceChildcatId());
            contentValues.put(ICOL_8, rModel.getServiceCatId());
            contentValues.put(ICOL_9, rModel.getServiceDiscount());
            contentValues.put(ICOL_10, rModel.getServiceTtken());
            contentValues.put(ICOL_11, rModel.getServiceMqty());
            contentValues.put(ICOL_12, rModel.getServiceQty());
            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                List<ServiceListItem> list = getCData(rModel.getServiceCatId());
                if (ItemListActivity.getInstance() != null) {
                    ItemListActivity.getInstance().cardData(list);
                }
                return true;
            }
        } else {
            return updateData(rModel.getServiceCatId(), rModel.getServiceId(), rModel.getServiceQty());
        }
    }

    public List<ServiceListItem> getCData(String cid) {
        List<ServiceListItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor c = db.rawQuery("select * from items where service_cat_id= " + cid + "", null);
            if (c.getCount() != -1) { //if the row exist then return the id
                while (c.moveToNext()) {
                    ServiceListItem item = new ServiceListItem();
                    item.setServiceId(c.getString(1));
                    item.setServiceTitle(c.getString(2));
                    item.setServicePrice(c.getDouble(3));
                    item.setServiceSubcatId(c.getString(4));
                    item.setServiceSdesc(c.getString(5));
                    item.setServiceChildcatId(c.getString(6));
                    item.setServiceCatId(c.getString(7));
                    item.setServiceDiscount(c.getDouble(8));
                    item.setServiceTtken(c.getString(9));
                    item.setServiceMqty(c.getString(10));
                    item.setServiceQty(c.getString(11));
                    list.add(item);
                }

            }
        } catch (Exception e) {
            Log.e("Error", "-->" + e.toString());

        }
        return list;
    }

    private int getID(String sid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{"SID"}, "SID =? ", new String[]{sid}, null, null, null, null);
        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("SID"));
        return -1;
    }

    public int getCard(String aid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{"service_qty"}, "SID =? ", new String[]{aid}, null, null, null, null);
        if (c.moveToFirst()) { //if the row exist then return the id
            return c.getInt(c.getColumnIndex("service_qty"));
        } else {
            return -1;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String cid, String sid, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ICOL_12, qty);
        db.update(TABLE_NAME, contentValues, "SID = ? ", new String[]{sid});
        List<ServiceListItem> list = getCData(cid);
        if (ItemListActivity.getInstance() != null) {
            ItemListActivity.getInstance().cardData(list);
        }
        return true;
    }

    public void deleteCard(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "service_cat_id = ? ", new String[]{cid});
    }

    public Integer deleteRData(String cid, String sid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer a = db.delete(TABLE_NAME, "SID = ? ", new String[]{sid});
        List<ServiceListItem> list = getCData(cid);
        if (ItemListActivity.getInstance() != null) {
            ItemListActivity.getInstance().cardData(list);
        }
        return a;
    }
}