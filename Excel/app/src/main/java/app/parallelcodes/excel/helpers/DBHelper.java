package app.parallelcodes.excel.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MyDB1.db";
    private static final int DB_VERSION = 1;

    public static final String Tablename = "MyTable1";
    public static final String id = "_id";
    public static final String Company = "Company";
    public static final String Product = "Product";
    public static final String Price = "Price";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = "CREATE TABLE IF NOT EXISTS " + Tablename + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Company + " TEXT,"
                + Product + " TEXT,"
                + Price + " TEXT"
                + ")";
        db.execSQL(createSql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tablename);
        onCreate(db);
    }

    public void open() {
        SQLiteDatabase db = getWritableDatabase();
    }

    public void close() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public int insert(String table, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        int y = (int) db.insert(table, null, values);
        db.close();
        Log.e("Data Inserted", "Data Inserted");
        Log.e("y", y + "");
        return y;
    }

    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Tablename);
        db.close();
    }

    public Cursor getAllRow(String table) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(table, null, null, null, null, null, id);
    }

    public ArrayList<HashMap<String, String>> getProducts() {
        ArrayList<HashMap<String, String>> prolist = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Tablename;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(Company, cursor.getString(1));
                map.put(Product, cursor.getString(2));
                map.put(Price, cursor.getString(3));
                prolist.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return prolist;
    }

    public ArrayList<HashMap<String, String>> getCompany(String searchValue) {
        ArrayList<HashMap<String, String>> searchlist = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Tablename + " WHERE " + Company + " = ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{searchValue});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(Company, cursor.getString(1));
                map.put(Product, cursor.getString(2));
                map.put(Price, cursor.getString(3));
                searchlist.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return searchlist;
    }

}
