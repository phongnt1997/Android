package vn.phongandfriends.motorwashing.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDBAdapter {
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BIRTHDATE = "birthdate";
    private static final String KEY_CAR_TYPE = "cartype";

    private static final String TABLE_NAME = "Account";
    private static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_PHONE + " varchar(20) primary key, " +
            KEY_PASSWORD + " varchar(30) not null, " +
            KEY_FULLNAME + " text not null, " +
            KEY_BIRTHDATE + " varchar(15), " +
            KEY_EMAIL + " varchar(255), " +
            KEY_CAR_TYPE + " text "
            + ");";
    private static final String DROP_STATEMENT = "DROP TABLE IF EXISTS Account;";

    private static int DB_VERSION = 1;
    private static final String DB_NAME = "AccountDB";

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_STATEMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_STATEMENT);
            onCreate(sqLiteDatabase);
        }
    }

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public AccountDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);

    }

    public SQLiteDatabase open() {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    public void close() {
        if (db != null) db.close();
    }

    public boolean addAccount(String phone, String password, String fullname, String email, String birthDate, String carType) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PHONE, phone);
        contentValues.put(KEY_PASSWORD, password);
        contentValues.put(KEY_FULLNAME, fullname);
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_BIRTHDATE, birthDate);
        contentValues.put(KEY_CAR_TYPE, carType);
        return db.insert(TABLE_NAME, null, contentValues) > 0;
    }

    public boolean updateAccount(String phone, String fullname, String email, String birthDate, String carType) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FULLNAME, fullname);
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_BIRTHDATE, birthDate);
        contentValues.put(KEY_CAR_TYPE, carType);
        return db.update(TABLE_NAME, contentValues, KEY_PHONE + "= ?", new String[]{phone}) > 0;
    }

    public boolean isExisted(String phone) {
        open();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_FULLNAME},
                KEY_PHONE + " = ?",
                new String[]{phone},
                null, null, null);
        return cursor.moveToFirst();
    }

    public Cursor findByPhone(String phone) {
        open();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_PHONE, KEY_PASSWORD, KEY_FULLNAME, KEY_EMAIL, KEY_BIRTHDATE, KEY_CAR_TYPE}, KEY_PHONE + " = ?",
                new String[]{phone}, null, null, null, null);
        return cursor;
    }

    public boolean checkLogin(String phone, String password) {
        open();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_FULLNAME},
                KEY_PHONE + " = ? and " + KEY_PASSWORD + " = ?",
                new String[]{phone, password},
                null, null, null);
        return cursor.moveToFirst();
    }

    public boolean updatePassword(String phone, String password) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PASSWORD, password);
        return db.update(TABLE_NAME, contentValues, KEY_PHONE + " = ?", new String[]{phone}) > 0;
    }

    public boolean isCorrectPassword(String phone, String password) {
        open();
        return db.query(TABLE_NAME, new String[] {phone}, KEY_PHONE + " = ? AND " + KEY_PASSWORD + " = ?",
                new String[] {phone, password}, null, null, null , null).moveToFirst();
    }

    public String findFullnameByPhone(String phone) {
        open();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_FULLNAME},
                KEY_PHONE + " = ?",
                new String[]{phone},
                null, null, null);
        if (!cursor.moveToFirst()) return null;
        int nFullname = cursor.getColumnIndex(KEY_FULLNAME);
        return cursor.getString(nFullname);
    }
}
