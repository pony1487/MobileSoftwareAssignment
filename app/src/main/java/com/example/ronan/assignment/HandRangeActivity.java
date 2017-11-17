package com.example.ronan.assignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Ronan on 07/11/2017.
 * Uses the handrange that is filled with the string of hands from from database. Prints them to screen
 * using custom rows
 */

public class HandRangeActivity extends Activity {

    private HandRange handRange;
    private String percentage;
    private TextView handRangeView;
    private DatabaseAccess db;
    private Cursor c;

    //put these into handRange class. Ie have handRange.button etc. This is just for testing
    private String BB = "";
    private String sb = "";
    private String btn = "";
    private String co = "";
    private String hj = "";
    private String lj = "";
    private String utg2 = "";
    private String utg1 = "";
    private String utg = "";

    private String resultFromCursor = "";

    //data passed from Preflop Activity
    Intent HandRangeIntent;
    private int stackSize = 0;
    private int position = 0;




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_range_activity);

        //get the data from the Preflop Activity
        HandRangeIntent = getIntent();
        position = HandRangeIntent.getIntExtra("player_position",0);
        stackSize = HandRangeIntent.getIntExtra("player_stack_size",0);

        //make new database helper. I tried to do this in the DAO class but I could not get it working
        db = new DatabaseAccess(this);

        //open
        try {
            db.open();
            //db.dropTable();
            fillDataBase();
            displayHandRangeToScreen();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        //buttons
        Button backButton = (Button)findViewById(R.id.back_button);

        //each page should have a way back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //close database
        db.close();
    }//end onCreate()

    @Override
    public void onStop() {
        super.onStop();
        db.close();

    }


    //get the preflop details from the intent.putExtra() in PreflopActivity
    //Once you have that info call
    /*
    handRange.getHandRange();
     */

    public void displayHandRangeToScreen()
    {
        //you will need row adapter/inflater thing
        try
        {
            //Use the column name against the user input to find right position. It just prints everything for 1 bb ALL positions at the minute
            c = db.getHandRangeByID(stackSize);


            //Used for testing
            handRangeView = (TextView)findViewById(R.id.testHandRangeView);

            String test = "";
            if (c.moveToFirst()) {
                do {
                    //index is the column NOT the row
                    /*
                    BB = c.getString(0);//BIG BLINS
                    sb = c.getString(1);//SB
                    btn = c.getString(2);//BTN
                    co = c.getString(3);
                    hj = c.getString(4);
                    lj = c.getString(5);
                    utg2 = c.getString(6);
                    utg1 = c.getString(7);
                    utg = c.getString(8);
                    */
                    test = c.getString(position);


                } while (c.moveToNext());
            }

            //resultFromCursor = "\tSTACK SIZE: " + BB + "BB's \n" + " " + sb + " " + btn + " " + co + " " + hj + "\n" + lj + " " + utg2 + " " + utg1 + " " + utg;
            handRangeView.setText(test);

            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(c));


        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void fillDataBase()
    {
        try{
            //1BB 10% ante by default
            db.insertHandRange("100.0%, Any two","100.0%, Any two","100.0%, Any two",
                    "100.0%, Any two","100.0%, Any two","100.0%, Any two","100.0%, Any two","100.0%, Any two");

            //2bb
            db.insertHandRange("100.0%, Any two,","84.6%, 22+ Tx+ 92s+ 95o+ 82s+ 85o+ 73s+ 75o+ 62s+ 64o+ 52s+ 54o 43s",
                    "85.2%, 22+ Jx+ T2s+ T3o+ 92s+ 95o+ 82s+ 85o+ 72s+ 74o+ 62s+ 64o+ 52s+ 54o 42s+","78.3%, 22+ Qx+ J2s+ J3o+ T2s+ T6o+ 92s+ 96o+ 82s+ 85o+ 73s+ 75o+ 63s+ 65o 52s+ 54o 42s+",
                    "71.6%, 22+ Kx+ Q2s+ Q3o+ J2s+ J5o+ T2s+ T6o+ 92s+ 96o+ 84s+ 86o+ 73s+ 76o 63s+ 65o 53s+ 43s","71.3%, 22+ Kx+ Q2s+ Q3o+ J2s+ J6o+ T2s+ T6o+ 92s+ 96o+ 83s+ 86o+ 73s+ 76o 63s+ 65o 52s+ 43s",
                    "65.6%, 22+ Kx+ Q2s+ Q5o+ J2s+ J7o+ T2s+ T7o+ 93s+ 97o+ 84s+ 86o+ 74s+ 76o 63s+ 65o 53s+ 43s","65.6%, 22+ Kx+ Q2s+ Q5o+ J2s+ J7o+ T2s+ T7o+ 93s+ 97o+ 84s+ 86o+ 74s+ 76o 63s+ 65o 53s+ 43s");
            //3bb
            db.insertHandRange("94.6%, 22+ 8x+ 72s+ 73o+ 62s+ 63o+ 52s+ 53o+ 42s+ 32s",
                    "65.6%, 22+ Qx+ J2s+ J5o+ T3s+ T6o+ 95s+ 97o+ 85s+ 87o 75s+ 65s 54s",
                    "56.3%, 22+ Kx+ Q2s+ Q5o+ J3s+ J7o+ T5s+ T8o+ 95s+ 98o 85s+ 75s+ 65s",
                    "50.5%, 22+ Ax+ K2s+ K4o+ Q2s+ Q7o+ J4s+ J8o+ T6s+ T8o+ 96s+ 98o 86s+ 75s+ 65s",
                    "50.5%, 22+ Ax+ K2s+ K4o+ Q2s+ Q8o+ J3s+ J8o+ T6s+ T8o+ 96s+ 98o 85s+ 75s+ 65s 54s",
                    "45.1%, 22+ Ax+ K2s+ K6o+ Q3s+ Q8o+ J5s+ J8o+ T6s+ T9o 96s+ 86s+ 76s 65s",
                    "39.7%, 22+ Ax+ K2s+ K8o+ Q4s+ Q9o+ J7s+ J9o+ T7s+ T9o 97s+ 86s+ 76s",
                    "38.2%, 22+ Ax+ K2s+ K9o+ Q5s+ Q9o+ J7s+ J9o+ T7s+ T9o 97s+ 87s 76s");

            //4bb
            db.insertHandRange("90.3%, 22+ 9x+ 82s+ 83o+ 72s+ 74o+ 62s+ 64o+ 52s+ 54o 43s","54.4%, 22+ Kx+ Q2s+ Q5o+ J4s+ J7o+ T6s+ T8o+ 96s+ 98o 86s+ 76s",
                    "50.5%, 22+ Ax+ K2s+ K3o+ Q2s+ Q7o+ J5s+ J8o+ T6s+ T8o+ 96s+ 98o 86s+ 76s","42.7%, 22+ Ax+ K2s+ K5o+ Q4s+ Q8o+ J7s+ J9o+ T7s+ T9o 97s+ 87s",
                    "38.8%, 22+ Ax+ K2s+ K7o+ Q5s+ Q9o+ J7s+ J9o+ T7s+ 97s+ 87s","33.0%, 22+ Ax+ K4s+ K9o+ Q6s+ QTo+ J8s+ JTo T8s+ 98s",
                    "33.3%, 22+ Ax+ K3s+ K9o+ Q6s+ QTo+ J8s+ JTo T8s+ 98s","31.5%, 22+ A2s+ A3o+ K4s+ K9o+ Q8s+ QTo+ J8s+ JTo T8s+ 98s");

            //5bb
            db.insertHandRange("84.9%, 22+ Tx+ 92s+ 93o+ 82s+ 84o+ 73s+ 75o+ 63s+ 65o 53s+ 43s","50.5%, 22+ Kx+ Q2s+ Q6o+ J5s+ J8o+ T6s+ T8o+ 97s+ 87s",
                    "45.4%, 22+ Ax+ K2s+ K4o+ Q3s+ Q8o+ J6s+ J8o+ T7s+ T9o 97s+ 87s 76s","36.7%, 22+ Ax+ K2s+ K7o+ Q6s+ Q9o+ J8s+ JTo T8s+ 98s 87s",
                    "33.0%, 22+ Ax+ K3s+ K9o+ Q7s+ QTo+ J8s+ JTo T8s+ 98s","31.5%, 22+ Ax+ K5s+ K9o+ Q8s+ QTo+ J9s+ JTo T9s 98s",
                    "28.5%, 22+ A2s+ A4o+ K5s+ KTo+ Q8s+ QTo+ J9s+ JTo T9s","25.5%, 22+ A2s+ A7o+ A5o K6s+ KTo+ Q8s+ QTo+ J9s+ T9s");

            //6bb
            db.insertHandRange("84.6%, 22+ Tx+ 92s+ 94o+ 82s+ 85o+ 73s+ 75o+ 62s+ 65o 52s+ 54o 43s","50.8%, 22+ Kx+ Q2s+ Q6o+ J5s+ J8o+ T7s+ T8o+ 97s+ 86s+ 76s",
                    "40.0%, 22+ Ax+ K2s+ K6o+ Q5s+ Q9o+ J7s+ J9o+ T7s+ 97s+ 87s 76s","33.9%, 22+ Ax+ K3s+ K9o+ Q7s+ QTo+ J8s+ JTo T8s+ 97s+ 87s 76s",
                    "33.0%, 22+ Ax+ K4s+ K9o+ Q8s+ QTo+ J8s+ JTo T8s+ 98s 87s 76s","29.1%, 22+ A2s+ A4o+ K6s+ KTo+ Q9s+ QTo+ J8s+ JTo T8s+ 98s 87s",
                    "25.2%, 22+ A2s+ A7o+ A5o K6s+ KTo+ Q9s+ QJo J9s+ T8s+ 98s 87s","22.5%, 22+ A2s+ A8o+ K8s+ KTo+ Q9s+ QJo J9s+ T8s+ 98s");

            //7bb
            db.insertHandRange("83.7%, 22+ Tx+ 92s+ 95o+ 82s+ 85o+ 73s+ 75o+ 63s+ 65o 52s+ 54o 42s+","46.6%, 22+ Kx+ Q3s+ Q8o+ J7s+ J9o+ T7s+ T9o 97s+ 86s+ 76s 65s",
                    "39.4%, 22+ Ax+ K2s+ K7o+ Q6s+ Q9o+ J7s+ JTo T7s+ T9o 97s+ 86s+ 76s 65s","33.0%, 22+ Ax+ K5s+ K9o+ Q8s+ QTo+ J8s+ JTo T8s+ 97s+ 87s 76s",
                    "30.9%, 22+ Ax+ K6s+ KTo+ Q9s+ QTo+ J8s+ JTo T8s+ 98s 87s","25.2%, 22+ A2s+ A7o+ A5o K7s+ KTo+ Q9s+ QJo J8s+ T8s+ 98s 87s",
                    "22.5%, 22+ A2s+ A8o+ K8s+ KTo+ Q9s+ QJo J9s+ T8s+ 98s","19.8%, 22+ A3s+ A9o+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s");
            //8bb
            db.insertHandRange("78.3%, 22+ Jx+ T2s+ T4o+ 92s+ 96o+ 83s+ 86o+ 73s+ 75o+ 63s+ 65o 53s+ 43s","45.7%, 22+ Ax+ K2s+ K3o+ Q4s+ Q8o+ J7s+ J9o+ T7s+ T9o 96s+ 86s+ 76s 65s",
                    "34.2%, 22+ Ax+ K4s+ K9o+ Q8s+ QTo+ J8s+ JTo T7s+ 97s+ 86s+ 76s 65s","31.5%, 22+ Ax+ K6s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 98s 87s 76s",
                    "27.0%, 22+ A2s+ A4o+ K7s+ KTo+ Q9s+ QJo J8s+ T8s+ 98s 87s","23.4%, 22+ A2s+ A8o+ K7s+ KTo+ Q9s+ QJo J8s+ T8s+ 98s 87s",
                    "19.8%, 22+ A3s+ A9o+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s","18.3%, 22+ A7s+ A5s-A4s ATo+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s");
            //9bb
            db.insertHandRange("75.0%, 22+ Jx+ T2s+ T6o+ 92s+ 96o+ 84s+ 86o+ 74s+ 76o 63s+ 65o 53s+ 43s","43.0%, 22+ Ax+ K2s+ K5o+ Q5s+ Q9o+ J7s+ J9o+ T7s+ T9o 96s+ 86s+ 76s 65s 54s",
                    "34.2%, 22+ Ax+ K4s+ K9o+ Q8s+ QTo+ J8s+ JTo T7s+ 97s+ 86s+ 76s 65s","30.0%, 22+ Ax+ K6s+ KTo+ Q9s+ QJo J8s+ JTo T8s+ 98s 87s",
                    "25.2%, 22+ A2s+ A7o+ K7s+ KTo+ Q9s+ QJo J8s+ JTo T8s+ 98s 87s","20.7%, 22+ A2s+ A9o+ K9s+ KJo+ Q9s+ QJo J8s+ T8s+ 98s",
                    "19.2%, 22+ A7s+ A5s-A3s ATo+ K8s+ KJo+ Q9s+ QJo J9s+ T8s+ 98s","16.4%, 22+ A8s+ A5s ATo+ K9s+ KJo+ Q9s+ J9s+ T9s");
            //10bb
            db.insertHandRange("71.9%, 22+ Qx+ J2s+ J4o+ T2s+ T6o+ 93s+ 96o+ 84s+ 86o+ 74s+ 76o 63s+ 53s+ 43s",
                    "42.7%, 22+ Ax+ K2s+ K6o+ Q5s+ Q9o+ J7s+ J9o+ T6s+ T9o 96s+86s+ 75s+ 65s 54s",
                    "33.0%, 22+ Ax+ K5s+ KTo+ Q8s+ QTo+ J8s+ JTo T7s+ 97s+ 86s+ 76s 65s",
                    "29.7%, 22+ A2s+ A4o+ K7s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 97s+ 87s 76s",
                    "29.7%, 22+ A2s+ A4o+ K7s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 97s+ 87s 76s",
                    "19.8%, 22+ A7s+ A5s-A3s A9o+ K9s+ KJo+ Q9s+ QJo J9s+ T8s+ 98s",
                    "18.3%, 22+ A7s+ A5s-A4s ATo+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s",
                    "14.6%, 22+ A9s+ ATo+ K9s+ KQo Q9s+ JTs T9s");
            //11bb
            db.insertHandRange("71.9%, 22+ Qx+ J2s+ J5o+ T2s+ T6o+ 93s+ 96o+ 84s+ 86o+ 74s+ 76o 63s+ 65o 53s+ 43s","39.7%, 22+ Ax+ K2s+ K8o+ Q5s+ Q9o+ J7s+ JTo T7s+ T9o 96s+ 86s+ 75s+ 65s 54s",
                    "32.1%, 22+ Ax+ K5s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 97s+ 87s 76s","27.3%, 22+ A2s+ A4o+ K9s+ KTo+ Q9s+ QJo J8s+ JTo T8s+ 98s 87s",
                    "22.5%, 22+ A2s+ A9o+ K9s+ KTo+ Q9s+ QJo J8s+ JTo T8s+ 98s","18.6%, 22+ A7s+ A5s-A3s ATo+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s",
                    "16.4%, 22+ A8s+ A5s ATo+ K9s+ KJo+ Q9s+ J9s+ T9s","13.7%, 22+ A9s+ A5s AJo+ K9s+ KQo QTs+ JTs T9s");
            //12bb
            db.insertHandRange("65.6%, 22+ Qx+ J2s+ J7o+ T3s+ T7o+ 95s+ 97o+ 84s+ 87o 74s+ 76o 63s+ 53s+ 43s","39.7%, 22+ Ax+ K2s+ K8o+ Q6s+ QTo+ J7s+ J9o+ T6s+ T9o 96s+ 86s+ 75s+ 65s 54s",
                    "31.8%, 22+ Ax+ K6s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 97s+ 87s 76s","26.4%, 22+ A2s+ A7o+ A5o K7s+ KTo+ Q8s+ QJo J8s+ JTo T8s+ 98s 87s",
                    "19.8%, 22+ A3s+ A9o+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s","17.6%, 22+ A8s+ A5s ATo+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s",
                    "13.7%, 22+ A9s+ AJo+ K9s+ KQo Q9s+ JTs T9s","13.3%, 33+ A9s+ A5s AJo+ K9s+ KQo QTs+ JTs T9s");
            //13bb
            db.insertHandRange("65.6%, 22+ Kx+ Q2s+ Q3o+ J2s+ J7o+ T3s+ T7o+ 95s+ 97o+ 84s+ 86o+ 74s+ 76o 63s+ 53s+","36.7%, 22+ Ax+ K3s+ K9o+ Q6s+ QTo+ J7s+ JTo T7s+ T9o 96s+ 86s+ 76s 65s",
                    "31.2%, 22+ Ax+ K7s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 97s+ 87s","25.2%, 22+ A2s+ A8o+ K7s+ KTo+ Q9s+ QTo+ J8s+ JTo T8s+ 98s 87s",
                    "19.8%, 22+ A7s+ A5s-A3s ATo+ K7s+ KJo+ Q9s+ QJo J8s+ T8s+ 98s","16.4%, 22+ A8s+ A5s ATo+ K9s+ KJo+ Q9s+ J9s+ T9s",
                    "13.7%, 22+ A9s+ A5s AJo+ K9s+ KQo QTs+ JTs T9s","11.8%, 55+ A9s+ A5s AJo+ KTs+ KQo QTs+ JTs");
            //14bb
            db.insertHandRange("63.5%, 22+ Kx+ Q2s+ Q4o+ J2s+ J7o+ T3s+ T7o+ 95s+ 97o+ 84s+ 87o 74s+ 76o 64s+ 53s+ 43s","36.3%, 22+ Ax+ K4s+ K9o+ Q6s+ QTo+ J7s+ JTo T7s+ T9o 96s+ 86s+ 76s 65s",
                    "30.0%, 22+ A2s+ A3o+ K7s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 98s 87s","23.1%, 22+ A2s+ A9o+ K7s+ KTo+ Q9s+ QJo J8s+ JTo T8s+ 98s",
                    "18.6%, 22+ A7s+ A5s-A4s ATo+ K8s+ KJo+ Q9s+ QJo J9s+ T9s 98s","15.2%, 22+ A9s+ A5s ATo+ K9s+ KQo Q9s+ J9s+ T9s",
                    "13.3%, 33+ A9s+ A5s AJo+ K9s+ KQo QTs+ JTs T9s","10.3%, 55+ ATs+ AJo+ KTs+ QTs+ JTs");
            //15bb
            db.insertHandRange("61.1%, 22+ Kx+ Q2s+ Q5o+ J2s+ J8o+ T4s+ T7o+ 95s+ 97o+ 85s+ 87o 74s+ 76o 64s+ 53s+ 43s","36.3%, 22+ Ax+ K4s+ K9o+ Q6s+ QTo+ J7s+ JTo T7s+ T9o 96s+ 86s+ 76s 65s",
                    "30.9%, 22+ A2s+ A3o+ K6s+ KTo+ Q8s+ QTo+ J8s+ JTo T8s+ 97s+ 87s 76s","21.9%, 22+ A2s+ A9o+ K8s+ KJo+ Q9s+ QJo J8s+ JTo T8s+ 98s",
                    "17.6%, 22+ A8s+ A5s ATo+ K9s+ KJo+ Q9s+ QJo J9s+ T9s 98s","14.9%, 22+ A9s+ A5s ATo+ K9s+ KQo QTs+ J9s+ T9s",
                    "12.1%, 55+ A9s+ A5s AJo+ K9s+ KQo QTs+ JTs","10.4%, 88+ A9s+ A5s AJo+ KTs+ KQo QTs+ JTs");





        }catch (SQLException e){
            e.printStackTrace();
        }
    }





}
