package com.example.ronan.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ronan on 14/11/2017.
 * Stores the events that the user clicks on to a database
 * Database table will be in
 * EVENT	COUNTRY	STARTS	BUY-IN	FEE
 *
 */

public class TournamentDatabaseAccess {
    //row id will be the big blind amount ie the users stack size
    private static final String KEY_ROWID 	    = "_id";

    //COLUMNS
    private static final String KEY_TASK_EVENT = "Event";
    private static final String KEY_TASK_COUNTRY = "COUNTRY";
    private static final String KEY_TASK_STARTS = "STARTS";
    private static final String KEY_TASK_BUYIN = "BUYIN";
    private static final String KEY_TASK_FEE = "FEE";


    private static final String DATABASE_TABLE 	= "Events";
    private static final String DATABASE_NAME 	= "EventDatabase";
    private static final int DATABASE_VERSION 	= 1; // since it is the first version of the dB


    // SQL statement to create the database
    //private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TASK_NAME + "text NOT NULL, " + KEY_TASK_DESCRIPTION + "text NOT NULL);";
    private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TASK_EVENT + " text NOT NULL," +
            KEY_TASK_COUNTRY + " text, " +
            KEY_TASK_STARTS + " text NOT NULL, " +
            KEY_TASK_BUYIN + " text, " +
            KEY_TASK_FEE + " text;";

    private final Context context;
    private TournamentDatabaseAccess.DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Constructor
    public TournamentDatabaseAccess(Context ctx)
    {
        //
        this.context = ctx;
        DBHelper = new TournamentDatabaseAccess.DatabaseHelper(context);

    }

    public TournamentDatabaseAccess open()
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
    public long insertEvent(String event, String country, String starts,String buyin, String fee)
    {
        ContentValues initialValues = new ContentValues();
        // put your own column/ values onto the Context Values object
        //initialValues.put(KEY_TASK_SMALLBLIND, smallBlind);



        return db.insert(DATABASE_TABLE, null, initialValues);
    }//end insert

    public void deleteEvent(int id)
    {
        db.delete(DATABASE_TABLE, "_id=?", new String[]{String.valueOf(id)});
    }

    // an example of a custom method to query a database.


    public Cursor getAllTasks()
    {
        Cursor mCursor =  db.query(DATABASE_TABLE, new String[]{KEY_ROWID,
                KEY_TASK_EVENT,
                KEY_TASK_COUNTRY,
                KEY_TASK_STARTS,
                KEY_TASK_BUYIN,
                KEY_TASK_FEE},null,null,null,null,null);


        return mCursor;
    }

    public Cursor getEventByID(long rowId)
    {
        Cursor mCursor =   db.query(true, DATABASE_TABLE, new String[]
                        {
                                KEY_ROWID,
                                KEY_TASK_EVENT,
                                KEY_TASK_COUNTRY,
                                KEY_TASK_STARTS,
                                KEY_TASK_BUYIN,
                                KEY_TASK_FEE},
                KEY_ROWID + "=" + rowId,  null, null, null, null, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;

    }//end getEventByID

    public void dropTable()
    {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }





}
