package ru.key_next.savemoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import java.util.ArrayList;

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
        db.execSQL(ExpenseCategory.STRING_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 2) {
//            db.execSQL(DayColumns.STRING_CREATE);
//        }
    }

    public static final class PeriodColumns implements BaseColumns {
        static final String TABLE = "period";
        static final String START_DATE_TIME_COLUMN = "start_date_time";
        static final String END_DATE_TIME_COLUMN = "end_date_time";
        static final String PLAN_COLUMN = "plan";
        static final String STRING_CREATE =
                "CREATE TABLE " + TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        START_DATE_TIME_COLUMN + " INTEGER, " +
                        END_DATE_TIME_COLUMN + " INTEGER, " +
                        PLAN_COLUMN + " BIGINT);";

//        private PeriodColumns() {
//        }

//        public static Cursor getPeriodCursor(SQLiteDatabase db) {
//            return db.query("CAT",
//                    new String[]{"NAME", "DESCRIPTION"},
//                    null, null, null, null, null);
//        }
    }

    public static final class DayColumns implements BaseColumns {
        static final String TABLE = "day";
        static final String ID_PERIOD_COLUMN = "id_period";
        static final String DATE_TIME_COLUMN = "date_time";
        static final String PLAN_COLUMN = "plan";
        static final String STRING_CREATE =
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
        static final String TABLE = "expense";
        static final String ID_EXPENSE_DICTIONARY_COLUMN = "id_expense_dictionary";
        static final String COST_COLUMN = "cost";
        static final String STRING_CREATE =
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
        static final String TABLE = "expense_dictionary";
        static final String ID_EXPENSE_CATEGORY_COLUMN = "id_parent_category";
        static final String NAME_COLUMN = "name";
        static final String STRING_CREATE =
                "CREATE TABLE " + TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ID_EXPENSE_CATEGORY_COLUMN + " INTEGER, " +
                        NAME_COLUMN + " VARCHAR(255));";

//        private ExpenseDictionaryColumns() {
//        }
    }

    public static final class ExpenseCategory implements BaseColumns {
        static final String TABLE = "expense_category";
        static final String ID_EXPENSE_PARENT_CATEGORY_COLUMN = "id_parent_category";
        static final Integer TOP_LEVEL_CATEGORY = 0;
        static final String NAME_COLUMN = "name";
        static final String IS_FOLDER_COLUMN = "is_folder";
        static final String STRING_CREATE =
                "CREATE TABLE " + TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ID_EXPENSE_PARENT_CATEGORY_COLUMN + " INTEGER NOT NULL DEFAULT " + TOP_LEVEL_CATEGORY + ", " +
                        IS_FOLDER_COLUMN + " BOOLEAN NOT NULL DEFAULT FALSE , " +
                        NAME_COLUMN + " VARCHAR(255));";

        public static ArrayList<Category> getAll(SQLiteDatabase db) {
            Cursor c = db.query(TABLE,
                    new String[]{_ID, ID_EXPENSE_PARENT_CATEGORY_COLUMN, NAME_COLUMN},
                    ID_EXPENSE_PARENT_CATEGORY_COLUMN + " = ?",
                    new String[]{ExpenseCategory.TOP_LEVEL_CATEGORY.toString()},
                    null, null, null);

            ArrayList<Category> categoryList = new ArrayList<Category>();

            int columnIndexID = c.getColumnIndex(ExpenseCategory._ID);
            int columnIndexParent = c.getColumnIndex(ExpenseCategory.ID_EXPENSE_PARENT_CATEGORY_COLUMN);
            int columnIndexName = c.getColumnIndex(ExpenseCategory.NAME_COLUMN);

            while (c.moveToNext()) {
                categoryList.add(new Category(
                        c.getString(columnIndexID),
                        c.getString(columnIndexParent),
                        c.getString(columnIndexName),
                        ExpenseCategory.TOP_LEVEL_CATEGORY
                ));

                categoryList.addAll(getCategories(db, c.getString(columnIndexID), 0));
            }

            c.close();

            return categoryList;
        }

        public static ArrayList<Category> getCategories(SQLiteDatabase db, String idParent, int nestedLevel) {
            nestedLevel++;
            Cursor c = db.query(TABLE,
                    new String[]{_ID, ID_EXPENSE_PARENT_CATEGORY_COLUMN, NAME_COLUMN},
                    ID_EXPENSE_PARENT_CATEGORY_COLUMN + " = ?",
                    new String[]{idParent},
                    null, null, null);

            ArrayList<Category> categoryList = new ArrayList<Category>();

            int columnIndexID = c.getColumnIndex(ExpenseCategory._ID);
            int columnIndexParent = c.getColumnIndex(ExpenseCategory.ID_EXPENSE_PARENT_CATEGORY_COLUMN);
            int columnIndexName = c.getColumnIndex(ExpenseCategory.NAME_COLUMN);

            while (c.moveToNext()) {
                categoryList.add(new Category(
                        c.getString(columnIndexID),
                        c.getString(columnIndexParent),
                        c.getString(columnIndexName),
                        nestedLevel
                ));

                categoryList.addAll(getCategories(db, c.getString(columnIndexID), nestedLevel));
            }

            c.close();

            return categoryList;
        }

        public static void set(SQLiteDatabase db, String idParent, String categoryName) {
            ContentValues cv = new ContentValues();
            cv.put(ID_EXPENSE_PARENT_CATEGORY_COLUMN, idParent);
            cv.put(NAME_COLUMN, categoryName);
            cv.put(IS_FOLDER_COLUMN, "TRUE");

            db.insert(TABLE, null, cv);
        }

        static final class Category implements Parcelable {
            String _id;
            String id_parent_category;
            String name;
            Integer nesting_level;

            Category(String _id, String id_parent_category, String name, Integer nesting_level) {
                this._id = _id;
                this.id_parent_category = id_parent_category;
                this.name = name;
                this.nesting_level = nesting_level;
            }

            private Category(Parcel parcel) {
                _id = parcel.readString();
                id_parent_category = parcel.readString();
                name = parcel.readString();
                nesting_level = parcel.readInt();
            }

            public static final Creator<Category> CREATOR = new Creator<Category>() {
                @Override
                public Category createFromParcel(Parcel in) {
                    return new Category(in);
                }

                @Override
                public Category[] newArray(int size) {
                    return new Category[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(_id);
                dest.writeString(id_parent_category);
                dest.writeString(name);
                dest.writeInt(nesting_level);
            }
        }
    }
}
