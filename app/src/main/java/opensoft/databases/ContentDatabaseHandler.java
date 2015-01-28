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
import java.util.List;

public class ContentDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ContentManager";

    // annotationions table name
    private static final String TABLE_CONTENT = "Content";

    // annotationions Table Columns names
    public static final String KEY_LOCAL_ID = "l_id";
    public static final String KEY_SERVER_ID = "s_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_REV_ID = "rev_id";
    public static final String KEY_PAGE_ID = "page_id";
    public static final String KEY_SERVER_TIMESTAMP = "s_timestamp";
    public static final String KEY_LOCAL_TIMESTAMP = "l_timestamp";
    public static final String KEY_IMAGES = "images";
    private static final String TAG = "ContentDatabaseHandler";

    public ContentDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTENT_TABLE = "CREATE TABLE " + TABLE_CONTENT + "("
                + KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_SERVER_ID + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_SUMMARY + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_REV_ID + " TEXT,"
                + KEY_PAGE_ID + " TEXT,"
                + KEY_SERVER_TIMESTAMP + " TEXT,"
                + KEY_LOCAL_TIMESTAMP + " TEXT,"
                + KEY_IMAGES + " TEXT" +")";
        db.execSQL(CREATE_CONTENT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENT);

        // Create tables again
        onCreate(db);
    }

    public void addContent(Content content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // primary key

        values.put(KEY_LOCAL_ID, content.getiLocalId());
        values.put(KEY_SERVER_ID, content.getiServerId());
        values.put(KEY_TITLE, content.getsTitle());
        values.put(KEY_SUMMARY, content.getsSummary());
        values.put(KEY_CONTENT, content.getsContent());
        values.put(KEY_REV_ID, content.getsRevId());
        values.put(KEY_PAGE_ID, content.getsPageId());
        values.put(KEY_SERVER_TIMESTAMP, content.getsServerTimeStamp());
        values.put(KEY_LOCAL_TIMESTAMP, content.getsLocalTimeStamp());
        values.put(KEY_IMAGES,content.getSaImagePath().toString());

        // Inserting Row
        if(db.insert(TABLE_CONTENT, null, values)==-1)
            Log.e(TAG, "error in inserting in user");
        db.close(); // Closing database connection
    }

    // Getting single
    public Content getContent(int l_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTENT, new String[] {
                        KEY_LOCAL_ID,
                        KEY_SERVER_ID,
                        KEY_TITLE,
                        KEY_SUMMARY,
                        KEY_CONTENT,
                        KEY_REV_ID,
                        KEY_PAGE_ID,
                        KEY_SERVER_TIMESTAMP,
                        KEY_LOCAL_TIMESTAMP,
                        KEY_IMAGES
                    }, KEY_LOCAL_ID + "=?",
                new String[] { String.valueOf(l_id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Content content = new Content();
        content.setiLocalId(cursor.getInt(0));
        content.setiServerId(cursor.getInt(1));
        content.setsTitle(cursor.getString(2));
        content.setsSummary(cursor.getString(3));
        content.setsContent(cursor.getString(4));
        content.setsRevId(cursor.getString(5));
        content.setsPageId(cursor.getString(6));
        content.setsServerTimeStamp(cursor.getString(7));
        content.setsLocalTimeStamp(cursor.getString(8));

        JSONArray jsonArray=null;
        try {
            jsonArray = new JSONArray(cursor.getString(9));
        }catch (JSONException e) {
            e.printStackTrace();
        }

        content.setSaImagePath(jsonArray);

        db.close();
        return content;
    }

    // Getting single
    public ArrayList<Content> getAllContent() {
        ArrayList<Content> contentList = new ArrayList<Content>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Content content = new Content();
                content.setiLocalId(cursor.getInt(0));
                content.setiServerId(cursor.getInt(1));
                content.setsTitle(cursor.getString(2));
                content.setsSummary(cursor.getString(3));
                content.setsContent(cursor.getString(4));
                content.setsRevId(cursor.getString(5));
                content.setsPageId(cursor.getString(6));
                content.setsServerTimeStamp(cursor.getString(7));
                content.setsLocalTimeStamp(cursor.getString(8));

                JSONArray jsonArray=null;
                try {
                    jsonArray = new JSONArray(cursor.getString(9));
                }catch (JSONException e) {
                    e.printStackTrace();
                }

                content.setSaImagePath(jsonArray);

                // Adding to list
                contentList.add(content);
            } while (cursor.moveToNext());
        }
        db.close();
        return contentList;
    }

}

