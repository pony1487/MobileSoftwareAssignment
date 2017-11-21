package com.example.ronan.assignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ronan on 17/11/2017.
 * Gets tournament event details from the tournament database and
 * displays them to the user in a listActivity
 */

public class MyTournamentsActivity extends ListActivity {

    private ArrayList<Event> userEventList = new ArrayList<Event>();
    private TournamentDatabaseAccess tournamentDBA;
    private Cursor c;
    private MyCustomAdapter mAdapter;

    //stuff for dialog box which will show more info on events
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    LayoutInflater inflater;
    private Boolean isClicked;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_tournament_activity);

        isClicked = false;

        //set up database
        tournamentDBA = new TournamentDatabaseAccess(this);

        //open database
        try {
            tournamentDBA.open();
            //tournamentDBA.dropTable();//reset everything for testing

            //fills arrayList with events from database
            getEventsFromDatabase();
            Log.v("-----------------------", "DATBASE OPEN CALL");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //setListAdapter(new MyCustomAdapter(this, R.layout.my_tournaments_activity, userEventList));
        mAdapter = new MyCustomAdapter(this, R.layout.my_tournaments_activity, userEventList);
        setListAdapter(mAdapter);



    }//end onCreate

    public void getEventsFromDatabase()
    {

        try
        {
            c = tournamentDBA.getAllEvents();

            if (c.moveToFirst()) {
                do {

                    //Column Numbers
                    // 0: rowID
                    // 1 : Event,
                    // 2: Country,
                    // 3: Date ,
                    // 4: End_date
                    // 5: Buyin
                    // 6: Fee

                    //Make a new event object with the data from each row from cursor
                    Event e = new Event(
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6));
                    //give the event the rowID
                    //this will be used for deleting events. This isnt in the constructor of the event becuase it isnt needed when parsing
                    //the webpage(this is messy..Change if you get time)
                    e.setEventID(c.getInt(0));

                    userEventList.add(e);

                } while (c.moveToNext());
            }//end if

        }catch(SQLException e)
        {
            e.printStackTrace();
        }

    }//end getEventsFromDatabase

    //Not used Delete
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {

    }//end onListClickItem


    @Override
    public void onStop() {
        super.onStop();
        tournamentDBA.close();

    }


    public class MyCustomAdapter extends ArrayAdapter<Event>
    {
        public MyCustomAdapter(Context context, int rowLayoutID, ArrayList<Event> myData)
        {
            super(context,rowLayoutID,myData);
        }

        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View row;

            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(R.layout.tournament_row, parent,false);

            Button deleteButton = (Button)row.findViewById(R.id.deleteFromDatabaseButton);
            Button eventMap = (Button)row.findViewById(R.id.eventMap);

            eventMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyTournamentsActivity.this, EventMapActivity.class);
                    intent.putExtra("event_location",userEventList.get(position).getEventID());
                    startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),userEventList.get(position).getEventID() + " has been deleted.",Toast.LENGTH_SHORT).show();

                    //Doing this required position to made final as a parameter above
                    try {
                        tournamentDBA.deleteEvent(userEventList.get(position).getEventID());

                        userEventList.remove(position);

                        mAdapter.notifyDataSetChanged();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            TextView userEventView = (TextView)row.findViewById(R.id.user_event);
            String eventDetails = "Event Name: \n" + userEventList.get(position).getEvent() +"\n"
                    + "Start: " + userEventList.get(position).getDate() + "\n"
                    + "End: " + userEventList.get(position).getEnd_date()+ "\n"
                    + "Buyin: " + userEventList.get(position).getBuyin() + "\n"
                    + "Fee: " + userEventList.get(position).getFee() + "\n";
            userEventView.setText(eventDetails);

            return row;
        }


    }//end MyCustomAdapter



}
