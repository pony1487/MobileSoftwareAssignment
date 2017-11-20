package com.example.ronan.assignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        //There is a bug here. It will only delete the entry after clicking dialog twice it twice..
        //if you get rid of the dialog box that asks user if they want to delete it works fine..
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete?").setTitle("Delete from database?");

        inflater = getLayoutInflater();

        View customView = inflater.inflate(R.layout.my_tournaments_dialog, null);
        TextView messageView = (TextView)customView.findViewById(R.id.eventName);

        builder.setView(customView);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                isClicked = true;
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        dialog = builder.create();
        dialog.show();
        if(isClicked) {
            Toast.makeText(getApplicationContext(), "Yes clicked", Toast.LENGTH_SHORT).show();

            try {
                tournamentDBA.deleteEvent(userEventList.get(position).getEventID());

                userEventList.remove(position);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            isClicked = false;

        }

        mAdapter.notifyDataSetChanged();

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

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row;

            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(R.layout.tournament_row, parent,false);

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
