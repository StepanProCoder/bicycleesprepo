package com.staple.probkaesp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "sensordb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "sensordata";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our sensor type column.
    private static final String SENSOR_TYPE_COL = "sensor_type";

    // below variable is for our sensor data column.
    private static final String SENSOR_DATA_COL = "sensor_data";

    // below variable is for our timestamp column.
    private static final String TIMESTAMP_COL = "timestamp";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SENSOR_TYPE_COL + " TEXT,"
                + SENSOR_DATA_COL + " TEXT,"
                + TIMESTAMP_COL + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new sensor data to our sqlite database.
    public void addNewSensorData(String sensorType, String sensorData, String curTime) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with their key and value pair.
        values.put(SENSOR_TYPE_COL, sensorType);
        values.put(SENSOR_DATA_COL, sensorData);
        values.put(TIMESTAMP_COL, curTime);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @SuppressLint("Range")
    public String getSensorData(String sensorType) {
        String sensorData = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + SENSOR_TYPE_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{sensorType});

        if (cursor.moveToFirst()) {
            sensorData = cursor.getString(cursor.getColumnIndex(SENSOR_DATA_COL));
        }

        cursor.close();
        db.close();

        return sensorData;
    }

    // this method is use to get all data from our database.
    public String getAllSensorData() {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        // on below line we are checking if the cursor is not
        // null and our cursor count is greater than 0.
        if (cursor != null && cursor.getCount() > 0) {
            // moving our cursor to first position.
            cursor.moveToFirst();
            // on below line we are adding all
            // our data to our string builder.
            do {
                @SuppressLint("Range") String sensorType = cursor.getString(cursor.getColumnIndex(SENSOR_TYPE_COL));
                @SuppressLint("Range") String sensorData = cursor.getString(cursor.getColumnIndex(SENSOR_DATA_COL));
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(TIMESTAMP_COL));
                result.append("Sensor Type: ").append(sensorType).append(", Sensor Data: ").append(sensorData)
                        .append(", Timestamp: ").append(timestamp).append("\n");
            } while (cursor.moveToNext());
            // on below line we are closing our cursor.
            cursor.close();
        }

        // on below line we are closing our database.
        db.close();

        // at last we are
        // returning our string builder.
        return result.toString();
    }

    // below is a method for upgrading our database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateDB()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
