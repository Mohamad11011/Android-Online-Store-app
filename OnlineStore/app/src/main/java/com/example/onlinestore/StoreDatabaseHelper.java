package com.example.onlinestore;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDatabaseHelper extends SQLiteOpenHelper
{
    public Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StoreManager2.db";
    private static final String TABLE_STORE = "store2";

    private static final String COLUMN_STORE_ID = "store_id";
    private static final String COLUMN_STORE_NAME = "store_name";
    private static final String COLUMN_STORE_PRICE = "store_price";
    private static final String COLUMN_STORE_IMAGE = "store_image";

    private static String DB_PATH = "/data/data/package_name/databases/";
    static SQLiteDatabase sqliteDataBase;


    private String CREATE_STORE_TABLE = "CREATE TABLE " + TABLE_STORE + "("
            + COLUMN_STORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_STORE_NAME + " TEXT,"
             + COLUMN_STORE_PRICE + " INTEGER," + COLUMN_STORE_IMAGE + " BLOB  )";


    private String DROP_STORE_TABLE = "DROP TABLE IF EXISTS " + TABLE_STORE;


    public StoreDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    /**
     * Copies your database from assets-folder to the just created empty database in the
     * system folder.
     * This is done by transferring byte stream.*/

    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     *opens data base connection.
     * First it create the path
     * Then create connection with data base.*/

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { db.execSQL(DROP_STORE_TABLE);
        onCreate(db);
    }

    /*Methods below to add store item or remove or update ...*/

    public void addStore(Storeitem store)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STORE_NAME, store.getName());
        values.put(COLUMN_STORE_PRICE, store.getPrice());
        if(store.getImage() != null)
        {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            store.getImage().compress(Bitmap.CompressFormat.PNG, 100, b);
            byte[] photo = b.toByteArray();
            values.put(COLUMN_STORE_IMAGE, photo);
        }

        db.insert(TABLE_STORE, null, values);
        db.close();
    }


    @SuppressLint("Range")
    public List<Storeitem> getAllStore() {

        String[] columns = {COLUMN_STORE_ID,COLUMN_STORE_NAME, COLUMN_STORE_PRICE,COLUMN_STORE_IMAGE };

        String sortOrder =COLUMN_STORE_NAME + " ASC";

        List<Storeitem> StoreList = new ArrayList<Storeitem>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STORE, columns,null, null,null,null,sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Storeitem item = new Storeitem();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STORE_ID))));
                item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_STORE_NAME)));
                item.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_STORE_PRICE)));

                byte[] photo = cursor.getBlob(cursor.getColumnIndex(COLUMN_STORE_IMAGE));
                if(photo != null)
                {
                    ByteArrayInputStream b = new ByteArrayInputStream(photo);
                    Bitmap picture = BitmapFactory.decodeStream(b);
                    item.setImage(picture);
                }

                StoreList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return StoreList;
    }


    public void updateStore(Storeitem item ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STORE_NAME, item.getName());
        values.put(COLUMN_STORE_PRICE, item.getPrice());

        db.update(TABLE_STORE, values, COLUMN_STORE_NAME + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }


    public void deleteStore(Storeitem item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STORE, COLUMN_STORE_NAME + " = ?",
        new String[]{String.valueOf(item.getName())});
        db.close();
    }


    public boolean checkStore(String Name, String Price) {

        String[] columns = { COLUMN_STORE_ID};


        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_STORE_NAME + " = ?" + " AND " + COLUMN_STORE_PRICE + " = ?";

        String[] selectionArgs = {Name, Price};

        Cursor cursor = db.query(TABLE_STORE, columns,selection, selectionArgs,null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0)
        {
            return true;
        }
        return false;
    }
}
