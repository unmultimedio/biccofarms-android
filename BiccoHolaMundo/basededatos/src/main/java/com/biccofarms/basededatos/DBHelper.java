package com.biccofarms.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 10/19/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "biccofarms.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + DBContract.Users.TABLE_NAME + " (" +
                    DBContract.Users._ID + " INTEGER PRIMARY KEY, " +
                    DBContract.Users.COLUMN_NAME_NAME + " TEXT, " +
                    DBContract.Users.COLUMN_NAME_DRINK + " TEXT, " +
                    DBContract.Users.COLUMN_NAME_SPORT + " TEXT);";

    public static final String DELETE_USERS_TABLE =
            "DROP TABLE IF EXISTS " + DBContract.Users.TABLE_NAME + ";";

    public static final String TRUNCATE_USERS_TABLE =
            "TRUNCATE TABLE IF EXISTS " + DBContract.Users.TABLE_NAME + ";";

    SQLiteDatabase reader, writer;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        reader = getReadableDatabase();
        writer = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(TRUNCATE_USERS_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }

    public long insertUser(User u){
        ContentValues map = new ContentValues();

        map.put(DBContract.Users.COLUMN_NAME_NAME, u.getName());
        map.put(DBContract.Users.COLUMN_NAME_DRINK, u.getDrink());
        map.put(DBContract.Users.COLUMN_NAME_SPORT, u.getSport());

        return writer.insert(DBContract.Users.TABLE_NAME,
                DBContract.Users.COLUMN_NAME_NAME,
                map);
    }

    public List<User> queryUsers(){
        String[] columns = {DBContract.Users._ID,
                DBContract.Users.COLUMN_NAME_NAME,
                DBContract.Users.COLUMN_NAME_DRINK,
                DBContract.Users.COLUMN_NAME_SPORT};

        String selection = null;//DBContract.Users.COLUMN_NAME_NAME + " LIKE ?";
        String selectionArgs[] = null;//{"Jul%"};
        String groupBy = null;
        String having = null;
        String orderBy = null;//DBContract.Users.COLUMN_NAME_NAME;
        String limit = null;//"20";

        Cursor cursor = reader.query(DBContract.Users.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy,
                limit);

        List<User> users = new ArrayList<>();

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int columnId = cursor.getColumnIndex(DBContract.Users._ID);
                int columnName = cursor.getColumnIndex(DBContract.Users.COLUMN_NAME_NAME);
                int columnDrink = cursor.getColumnIndex(DBContract.Users.COLUMN_NAME_DRINK);
                int columnSport = cursor.getColumnIndex(DBContract.Users.COLUMN_NAME_SPORT);
                long id = cursor.getLong(columnId);
                String name = cursor.getString(columnName);
                String drink = cursor.getString(columnDrink);
                String sport = cursor.getString(columnSport);
                users.add(new User(id, name, drink, sport));
                //users.add(new User(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                cursor.moveToNext();
            }
        }


        return users;
    }

    public int deleteUser(long id){
        String where = DBContract.Users._ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        return writer.delete(DBContract.Users.TABLE_NAME,
                where,
                whereArgs);
    }
}
