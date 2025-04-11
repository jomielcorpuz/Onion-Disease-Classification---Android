package com.example.onionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBhelper";

    // Table name and columns for history
    public static final String TABLE_NAME_IMAGES = "images_table";
    public static final String ID_COL = "id";
    public static final String IMAGENAME_COL ="imageName";
    public static final String IMAGEPATH_COL = "imagePath";
    public static final String IMAGES_COL = "images";
    public static final String DATETAKEN_COL = "dateTaken";;
    // Database name and version
    private static final String DATABASE_NAME = "recommendations.db";
    private static final int DATABASE_VERSION = 3;

    // Table name and columns for recommendation
    public static final String TABLE_NAME = "recommendations_table";
    public static final String COLUMN_ID = "rec_Id";
    public static final String COLUMN_NAME = "recomValue";
    public static final String COLUMN_VALUE = "someText";
    public static final String COLUMN_CHEMICAL_RECOMMENDATION = "chemicalRecommendation";
    public static final String COLUMN_CAREFULTIPS = "carefultips";
    public static final String COLUMN_REFERENCES = "referenc";


    public static Bitmap objectBitmap;
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInByte;

    // SQL query to create the table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_VALUE + " TEXT, " +
                    COLUMN_CHEMICAL_RECOMMENDATION + " TEXT, " +
                    COLUMN_CAREFULTIPS + " TEXT, " +
                    COLUMN_REFERENCES + " TEXT);";

    // SQL query to create the new table for history
    private static final String TABLE_CREATE_IMAGES =
            "CREATE TABLE " + TABLE_NAME_IMAGES + " (" +
                    ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMAGENAME_COL + " TEXT, " +
                    IMAGEPATH_COL + " TEXT, " +
                    IMAGES_COL + " BLOB, " +
                    DATETAKEN_COL + " TEXT);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table when the database is first created
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades (if needed)
        // This method is called when DATABASE_VERSION is increased

        if (oldVersion < 2) {
            // Add the new column in case of upgrade from version 1 to 2
            db.execSQL("ALTER TABLE " + TABLE_NAME +
                    " ADD COLUMN " + COLUMN_CHEMICAL_RECOMMENDATION + " TEXT;"
            );
        }

        if (oldVersion < 3) {
            // Add the new columns in case of upgrade from version 2 to 3
            db.execSQL("ALTER TABLE " + TABLE_NAME +
                    " ADD COLUMN " + COLUMN_CAREFULTIPS + " TEXT;"
            );

            db.execSQL("ALTER TABLE " + TABLE_NAME +
                    " ADD COLUMN " + COLUMN_REFERENCES + " TEXT;"
            );
        }
    }



    public void StoreImage (ModelClass objectmodelclass) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = objectmodelclass.getImage();

        if (imageToStoreBitmap != null) {
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInByte = objectByteArrayOutputStream.toByteArray();
            ContentValues values = new ContentValues();
            values.put(IMAGENAME_COL, objectmodelclass.getDiseaseName());
            values.put(IMAGES_COL, imageInByte);
            values.put(IMAGEPATH_COL, objectmodelclass.getImagePath());
            values.put(DATETAKEN_COL, objectmodelclass.getDateTaken());


            DB.insert(TABLE_NAME_IMAGES, null, values);
            DB.close();
        }else {
            // Handle the case when the bitmap is null
            Log.e(TAG, "StoreImage: Image bitmap is null");
        }

    }

    public ArrayList<ModelClass> getAllImageData() {
        try
        {
            SQLiteDatabase objectSqLiteDatabase = this.getReadableDatabase();
            ArrayList<ModelClass> objectModelClassList = new ArrayList<>();

            Cursor objectCursor = objectSqLiteDatabase.rawQuery("Select * from "+ TABLE_NAME_IMAGES, null);
            if (objectCursor.getCount() !=0 )
            {
                while (objectCursor.moveToNext())
                {
                    String ID = objectCursor.getString(0);
                    String diseaseName = objectCursor.getString(1);
                    String image_Path = objectCursor.getString(2);
                    byte[] imageByte = objectCursor.getBlob(3);
                    String date_Taken = objectCursor.getString(4);


                    objectBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    objectModelClassList.add(new ModelClass(ID, diseaseName, objectBitmap, image_Path,date_Taken));

                }
                objectSqLiteDatabase.close();
                return objectModelClassList;
            }else
            {
                return null;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Cursor ReadData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from "+ TABLE_NAME_IMAGES, null);
    }
}

