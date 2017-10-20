package ru.key_next.savemoney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    DBHelper(Context context) {
        super(context, "SaMo_db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PeriodColumns.STRING_CREATE);
        db.execSQL(DayColumns.STRING_CREATE);
        db.execSQL(ExpenseColumns.STRING_CREATE);
        db.execSQL(ExpenseDictionaryColumns.STRING_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 2) {
//            db.execSQL(DayColumns.STRING_CREATE);
//        }
    }

    private String test(){
        return "eee";
    }

    public static final class PeriodColumns implements BaseColumns {
        public static final String TABLE = "period";
        public static final String START_DATE_TIME_COLUMN = "start_date_time";
        public static final String END_DATE_TIME_COLUMN = "end_date_time";
        public static final String PLAN_COLUMN = "plan";
        public static final String STRING_CREATE =
                "CREATE TABLE " + TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        START_DATE_TIME_COLUMN + " INTEGER, " +
                        END_DATE_TIME_COLUMN + " INTEGER, " +
                        PLAN_COLUMN + " BIGINT);";

//        private PeriodColumns() {
//        }

//        public static String test() {
//            return DBHelper.test();
//        }
    }

    public static final class DayColumns implements BaseColumns {
        public static final String TABLE = "day";
        public static final String ID_PERIOD_COLUMN = "id_period";
        public static final String DATE_TIME_COLUMN = "date_time";
        public static final String PLAN_COLUMN = "plan";
        public static final String STRING_CREATE =
                "CREATE TABLE " + TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ID_PERIOD_COLUMN + " INTEGER, " +
                        DATE_TIME_COLUMN + " INTEGER, " +
                        PLAN_COLUMN + " BIGINT, " +
                        "FOREIGN KEY (" +
                        ID_PERIOD_COLUMN +
                        ") REFERENCES " +
                        PeriodColumns.TABLE +
                        " (_ID));";

//        private DayColumns() {
//        }
    }

    public static final class ExpenseColumns implements BaseColumns {
        public static final String TABLE = "expense";
        public static final String ID_EXPENSE_DICTIONARY_COLUMN = "id_expense_dictionary";
        public static final String COST_COLUMN = "cost";
        public static final String STRING_CREATE =
                "CREATE TABLE " + TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ID_EXPENSE_DICTIONARY_COLUMN + " INTEGER, " +
                        COST_COLUMN + " BIGINT, " +
                        "FOREIGN KEY (" +
                        ID_EXPENSE_DICTIONARY_COLUMN +
                        ") REFERENCES " +
                        ExpenseDictionaryColumns.TABLE +
                        " (_ID));";

//        private ExpenseColumns() {
//        }
    }

    public static final class ExpenseDictionaryColumns implements BaseColumns {
        public static final String TABLE = "expense_dictionary";
        public static final String NAME_COLUMN = "name";
        public static final String STRING_CREATE =
                "CREATE TABLE " + TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NAME_COLUMN + " VARCHAR(255));";

//        private ExpenseDictionaryColumns() {
//        }
    }
}
