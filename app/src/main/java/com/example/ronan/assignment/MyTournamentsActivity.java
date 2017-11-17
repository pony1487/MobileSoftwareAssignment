package com.example.ronan.assignment;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_tournament_activity);

        //Event test = new Event("Event","Ireland","1-4-1987","1-4-1988","$1000","$20");
        //userEventList.add(test);

        //set up database
        tournamentDBA = new TournamentDatabaseAccess(this);

        //open database
        try {
            tournamentDBA.open();

            //fills arrayList with events from database
            getEventsFromDatabase();
            Log.v("-----------------------","DATBASE OPEN CALL");

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        setListAdapter(new MyCustomAdapter(this,R.layout.my_tournaments_activity,userEventList));


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
                    Event e = new Event(c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6));

                    userEventList.add(e);

                } while (c.moveToNext());
            }//end if

        }catch(SQLException e)
        {
            e.printStackTrace();
        }

    }//end getEventsFromDatabase


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
            userEventView.setText(userEventList.get(position).getEvent());

            return row;
        }

    }//end MyCustomAdapter



}
