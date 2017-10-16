package ru.key_next.savemoney;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    private static final String PERIOD_TABLE_NAME = "period";
    private static final String PERIOD_TABLE_CREATE =
            "CREATE TABLE " + PERIOD_TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "start_date_time  INTEGER, " +
                    "end_date_time INTEGER);";

    private static final String DAY_TABLE_NAME = "day";
    private static final String DAY_TABLE_CREATE =
            "CREATE TABLE " + DAY_TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_period  INTEGER, " +
                    "date_time  INTEGER);";

    public DBHelper(Context context) {
        super(context, "SaMo_db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PERIOD_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ((newVersion == 2) && (oldVersion == 1)) {
            db.execSQL(DAY_TABLE_CREATE);
        }
    }
}
