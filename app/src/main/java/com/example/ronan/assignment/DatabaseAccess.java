package com.example.ronan.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ronan on 13/11/2017.
 *
 * Should there be a percentage table?????
 */

public class DatabaseAccess
{

    //row id will be the big blind amount ie the users stack size
    private static final String KEY_ROWID 	    = "_id";

    //COLUMNS
    private static final String KEY_TASK_SMALLBLIND = "SmallBlind";
    private static final String KEY_TASK_BUTTON = "Button";
    private static final String KEY_TASK_CUTOFF = "CutOff";
    private static final String KEY_TASK_HIJACK = "Hijack";
    private static final String KEY_TASK_LOJACK = "Lojack";
    private static final String KEY_TASK_UTG2 = "UTG2";
    private static final String KEY_TASK_UTG1 = "UTG1";
    private static final String KEY_TASK_UTG = "UTG";




    private static final String DATABASE_TABLE 	= "HandRanges";
    private static final String DATABASE_NAME 	= "HandDataBase";
    private static final int DATABASE_VERSION 	= 1; // since it is the first version of the dB


    /*
    create table HandRange(
  BigBlinds int NOT NULL,
  SmallBlind VARCHAR2(100),
  Button VARCHAR2(100),
  Cutoff VARCHAR2(100),
  Hijack VARCHAR2(100),
  Lojack VARCHAR2(100),
  UnderTheGun2 VARCHAR2(100),
  UnderTheGun1 VARCHAR2(100),
  UnderTheGun VARCHAR2(100),
  PRIMARY KEY(BigBlinds)
);
    */

    // SQL statement to create the database
    //private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TASK_NAME + "text NOT NULL, " + KEY_TASK_DESCRIPTION + "text NOT NULL);";
    private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TASK_SMALLBLIND + " text NOT NULL," +
            KEY_TASK_BUTTON + " text NOT NULL, " +
            KEY_TASK_CUTOFF + " text NOT NULL, " +
            KEY_TASK_HIJACK + " text NOT NULL, " +
            KEY_TASK_LOJACK + " text NOT NULL, " +
            KEY_TASK_UTG2 + " text NOT NULL, " +
            KEY_TASK_UTG1 + " text NOT NULL, " +
            KEY_TASK_UTG + " text NOT NULL);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Constructor
    public DatabaseAccess(Context ctx)
    {
        //
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }

    public DatabaseAccess open()
    {
        db     = DBHelper.getWritableDatabase();
        return this;

    }

    // nested dB helper class
    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        //
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        //
        public void onCreate(SQLiteDatabase db)
        {

            // Execute SQL to create your tables (call the execSQL method of the SQLLiteDatabase class, passing in your create table(s) SQL)
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        //
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            // dB structure change..

        }
    }   // end nested class



    // remainder of the Database Example methods to "use" the database
    public void close()

    {
        DBHelper.close();
    }

    // an example of a database insert.  This is for a particular database that has three columns
    public long insertHandRange(String smallBlind, String button, String cutoff, String hijack, String lojack, String utg2, String utg1, String utg)
    {
        ContentValues initialValues = new ContentValues();
        // put your own column/ values onto the Context Values object
        initialValues.put(KEY_TASK_SMALLBLIND, smallBlind);
        initialValues.put(KEY_TASK_BUTTON, button);
        initialValues.put(KEY_TASK_CUTOFF, cutoff);
        initialValues.put(KEY_TASK_HIJACK, hijack);
        initialValues.put(KEY_TASK_LOJACK, lojack);
        initialValues.put(KEY_TASK_UTG2, utg2);
        initialValues.put(KEY_TASK_UTG1, utg1);
        initialValues.put(KEY_TASK_UTG, utg);


        return db.insert(DATABASE_TABLE, null, initialValues);
    }//end insert

    public void deleteHandRange(int id)
    {
        db.delete(DATABASE_TABLE, "_id=?", new String[]{String.valueOf(id)});
    }

    // an example of a custom method to query a database.

    public Cursor getAllTasks()
    {
        Cursor mCursor =  db.query(DATABASE_TABLE, new String[]{KEY_ROWID,
                KEY_TASK_SMALLBLIND,
                KEY_TASK_BUTTON,
                KEY_TASK_CUTOFF,
                KEY_TASK_HIJACK,
                KEY_TASK_LOJACK,
                KEY_TASK_UTG2,
                KEY_TASK_UTG1,
                KEY_TASK_UTG},null,null,null,null,null);


        return mCursor;
    }

    public Cursor getHandRangeByID(long rowId)
    {
        Cursor mCursor =   db.query(true, DATABASE_TABLE, new String[]
                        {
                                KEY_ROWID,
                                KEY_TASK_SMALLBLIND,
                                KEY_TASK_BUTTON,
                                KEY_TASK_CUTOFF,
                                KEY_TASK_HIJACK,
                                KEY_TASK_LOJACK,
                                KEY_TASK_UTG2,
                                KEY_TASK_UTG1,
                                KEY_TASK_UTG
                        },
                KEY_ROWID + "=" + rowId,  null, null, null, null, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;

    }//end getHandRangeByID

    public void dropTable()
    {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }





}//end class

