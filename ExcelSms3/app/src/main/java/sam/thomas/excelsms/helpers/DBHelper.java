package sam.thomas.excelsms.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MyDB1.db";
    private static final int DB_VERSION = 4;

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
                    + Price + " TEXT,"
                    + "Eng" + " TEXT,"
                    + "Kis" + " TEXT,"
                    + "Mat" + " TEXT,"
                    + "Bio" + " TEXT,"
                    + "Phy" + " TEXT,"
                    + "Che" + " TEXT,"
                    + "His" + " TEXT,"
                    + "Geo" + " TEXT,"
                    + "Cre" + " TEXT,"
                    + "Agr" + " TEXT,"
                    + "Com" + " TEXT,"
                    + "Fre" + " TEXT,"
                    + "Mus" + " TEXT,"
                    + "Bst" + " TEXT,"
                    + "Sbj" + " TEXT,"
                    + "Vap" + " TEXT,"
                    + "Tmks" + " TEXT,"
                    + "Ttpts" + " TEXT,"
                    + "Gr" + " TEXT,"
                    + "SPos" + " TEXT,"
                    + "OPos" + " TEXT"
                    + ")";
            db.execSQL(createSql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN His TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Geo TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Cre TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Agr TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Com TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Fre TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Mus TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Bst TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Sbj TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Vap TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Tmks TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Ttpts TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN Gr TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN SPos TEXT");
            db.execSQL("ALTER TABLE " + Tablename + " ADD COLUMN OPos TEXT");

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
                map.put("Eng", cursor.getString(4));
                map.put("Kis", cursor.getString(5));
                map.put("Mat", cursor.getString(6));
                map.put("Bio", cursor.getString(7));
                map.put("Phy", cursor.getString(8));
                map.put("Che", cursor.getString(9));
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
                map.put("Eng", cursor.getString(4));
                map.put("Kis", cursor.getString(5));
                map.put("Mat", cursor.getString(6));
                map.put("Bio", cursor.getString(7));
                map.put("Phy", cursor.getString(8));
                map.put("Che", cursor.getString(9));
                map.put("His", cursor.getString(10));
                map.put("Geo", cursor.getString(11));
                map.put("Cre", cursor.getString(12));
                map.put("Agr", cursor.getString(13));
                map.put("Com", cursor.getString(14));
                map.put("Fre", cursor.getString(15));
                map.put("Mus", cursor.getString(16));
                map.put("Bst", cursor.getString(17));
                map.put("Sbj", cursor.getString(18));
                map.put("Vap", cursor.getString(19));
                map.put("Tmks", cursor.getString(20));
                map.put("Ttpts", cursor.getString(21));
                map.put("Gr", cursor.getString(22));
                map.put("SPos", cursor.getString(23));
                map.put("OPos", cursor.getString(24));
                searchlist.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return searchlist;
    }


}