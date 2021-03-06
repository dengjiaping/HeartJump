package com.cucr.myapplication.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.model.setting.LocationData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/4/28.
 */

public class CityDao {

    //查询所有的省
    public static List<LocationData> queryProvience() {
        List<LocationData> prviences = null;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(Constans.LOCATION_PATH, null, SQLiteDatabase.OPEN_READONLY);
        if (db.isOpen()) {
            prviences = new ArrayList<>();
            LocationData locationData = null;
            Cursor cursor = db.query(Constans.TABLENAME_PROVINCE, null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                try {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String code = cursor.getString(cursor.getColumnIndex("code"));
                    byte[] names = cursor.getBlob(cursor.getColumnIndex("name"));
                    String name = new String(names, "gbk");
                    locationData = new LocationData(id, code, name, null);
                    prviences.add(locationData);
                    locationData = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
            db.close();
        }
        return prviences;
    }


    //根据pcode查市 下一级
    public static List<LocationData> queryCityByPcode(String pCode) {
        List<LocationData> citys = null;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(Constans.LOCATION_PATH, null, SQLiteDatabase.OPEN_READONLY);
        if (db.isOpen()) {
            Cursor cursor = db.query(Constans.TABLENAME_CITY, null, "pcode = ?", new String[]{pCode}, null, null, null);
            LocationData locationData = null;
            citys = new ArrayList<>();

            while (cursor.moveToNext()) {
                try {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String code = cursor.getString(cursor.getColumnIndex("code"));
                    String pcode = cursor.getString(cursor.getColumnIndex("pcode"));
                    byte[] names = cursor.getBlob(cursor.getColumnIndex("name"));
                    String name = new String(names, "gbk");


                    locationData = new LocationData(id, code, name, pcode);
                    citys.add(locationData);
                    locationData = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            cursor.close();
            db.close();
        }
        return citys;
    }

    //根据pcode查区 下一级
    public static List<LocationData> queryDistrictByPcode(String pCode) {
        List<LocationData> citys = null;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(Constans.LOCATION_PATH, null, SQLiteDatabase.OPEN_READONLY);
        if (db.isOpen()) {
            Cursor cursor = db.query(Constans.TABLENAME_DISTRICT, null, "pcode = ?", new String[]{pCode}, null, null, null);
            LocationData locationData = null;
            citys = new ArrayList<>();
            try {
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String code = cursor.getString(cursor.getColumnIndex("code"));
                    String pcode = cursor.getString(cursor.getColumnIndex("pcode"));
                    byte[] names = cursor.getBlob(cursor.getColumnIndex("name"));
                    String name = new String(names, "gbk");
                    locationData = new LocationData(id, code, name, pcode);
                    citys.add(locationData);
                    locationData = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
                db.close();
            }

        }
        return citys;
    }

    //根据pcode查省 上一级
    public static LocationData queryPrivnceBycode(String codes) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(Constans.LOCATION_PATH, null, SQLiteDatabase.OPEN_READONLY);
        LocationData locationData = null;
        if (db.isOpen()) {
            Cursor cursor = db.query(Constans.TABLENAME_PROVINCE, null, "code = ?", new String[]{codes}, null, null, null);

            while (cursor.moveToNext()) {
                try {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String code = cursor.getString(cursor.getColumnIndex("code"));
                    byte[] names = cursor.getBlob(cursor.getColumnIndex("name"));
                    String name = new String(names, "gbk");


                    locationData = new LocationData(id, code, name, null);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                    db.close();
                }
            }

        }
        return locationData;
    }

    //根据pcode查市 上一级
    public static LocationData queryCityBycode(String codes) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(Constans.LOCATION_PATH, null, SQLiteDatabase.OPEN_READONLY);
        LocationData locationData = null;
        if (db.isOpen()) {
            Cursor cursor = db.query(Constans.TABLENAME_CITY, null, "code = ?", new String[]{codes}, null, null, null);

            while (cursor.moveToNext()) {
                try {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String code = cursor.getString(cursor.getColumnIndex("code"));
                    String pcode = cursor.getString(cursor.getColumnIndex("pcode"));
                    byte[] names = cursor.getBlob(cursor.getColumnIndex("name"));
                    String name = new String(names, "gbk");


                    locationData = new LocationData(id, code, name, pcode);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                    db.close();
                }
            }

        }
        return locationData;
    }
}
