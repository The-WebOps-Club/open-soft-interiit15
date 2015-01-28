package opensoft.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager";

    // annotationions table name
    private static final String TABLE_USER = "User";

    // annotationions Table Columns names
    public static final String KEY_LOCAL_ID = "l_id";
    public static final String KEY_SERVER_ID = "s_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_OCCUPATION = "occupation";
    public static final String KEY_PLACE = "place";
    public static final String KEY_CREATED_TIME = "created_time";
    public static final String KEY_LAST_SYNCED = "last_synced";
    public static final String KEY_DATA_SIZE = "data_size";
    public static final String KEY_PRIORITY = "priority_list";
    private static final String TAG = "UserDatabaseHandler";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_SERVER_ID + " INTEGER,"
                + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_OCCUPATION + " TEXT,"
                + KEY_PLACE + " TEXT,"
                + KEY_CREATED_TIME + " TEXT,"
                + KEY_LAST_SYNCED + " TEXT,"
                + KEY_DATA_SIZE + " TEXT,"
                + KEY_PRIORITY + " TEXT" +")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // Annotation primary key

        values.put(KEY_LOCAL_ID, user.getiLocalId());
        values.put(KEY_SERVER_ID, user.getiServerId());
        values.put(KEY_NAME, user.getsName());
        values.put(KEY_PHONE, user.getsPhone());
        values.put(KEY_EMAIL, user.getsEmail());
        values.put(KEY_OCCUPATION, user.getsOccupation());
        values.put(KEY_PLACE, user.getsPlace());
        values.put(KEY_LAST_SYNCED, user.getsCreatedTime());
        values.put(KEY_DATA_SIZE, user.getsDataSize());
        values.put(KEY_PRIORITY,user.getSaPriority().toString());

        // Inserting Row
        if(db.insert(TABLE_USER, null, values)==-1)
            Log.e(TAG, "error in inserting in user");
        db.close(); // Closing database connection
    }

    // Getting single annotation
    public User getUser(int l_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] {
                        KEY_LOCAL_ID,
                        KEY_SERVER_ID,
                        KEY_NAME,
                        KEY_PHONE,
                        KEY_EMAIL,
                        KEY_OCCUPATION,
                        KEY_PLACE,
                        KEY_CREATED_TIME,
                        KEY_LAST_SYNCED,
                        KEY_DATA_SIZE,
                        KEY_PRIORITY
                    }, KEY_LOCAL_ID + "=?",
                new String[] { String.valueOf(l_id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User();
        user.setiLocalId(cursor.getInt(0));
        user.setiServerId(cursor.getInt(1));
        user.setsName(cursor.getString(2));
        user.setsPhone(cursor.getString(3));
        user.setsEmail(cursor.getString(4));
        user.setsOccupation(cursor.getString(5));
        user.setsPlace(cursor.getString(6));
        user.setsCreatedTime(cursor.getString(7));
        user.setsLastSyncedTime(cursor.getString(8));
        user.setsDataSize(cursor.getString(9));

        JSONArray jsonArray=null;
        try {
            jsonArray = new JSONArray(cursor.getString(10));
        }catch (JSONException e) {
            e.printStackTrace();
        }

        user.setSaPriority(jsonArray);

        db.close();
        return user;
    }


    // Getting single annotation
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<User>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setiLocalId(cursor.getInt(0));
                user.setiServerId(cursor.getInt(1));
                user.setsName(cursor.getString(2));
                user.setsPhone(cursor.getString(3));
                user.setsEmail(cursor.getString(4));
                user.setsOccupation(cursor.getString(5));
                user.setsPlace(cursor.getString(6));
                user.setsCreatedTime(cursor.getString(7));
                user.setsLastSyncedTime(cursor.getString(8));
                user.setsDataSize(cursor.getString(9));

                JSONArray jsonArray=null;
                try {
                    jsonArray = new JSONArray(cursor.getString(10));
                }catch (JSONException e) {
                    e.printStackTrace();
                }

                user.setSaPriority(jsonArray);

                // Adding to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        db.close();
        return userList;
    }

}

