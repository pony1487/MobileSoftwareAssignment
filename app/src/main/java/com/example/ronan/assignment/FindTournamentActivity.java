package com.example.ronan.assignment;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ronan on 14/11/2017.
 * This uses the jsoup-1.11.1.jar
 * data got from this page. Viewed the source to get the tags I needed to parse
 * https://www.pokernews.com/poker-tournaments/
 *
 *
 */

public class FindTournamentActivity extends ListActivity {

    //used while website is being parsed
    private ProgressDialog progressDialog;
    private String url = "https://www.pokernews.com/poker-tournaments/";

    //stores the data from the website
    MyCustomAdapter myAdapter;
    //Event class contains strings for each part of the event. Ie country, date etc
    public ArrayList<Event> eventList = new ArrayList<Event>();

    //database stuff for writing what events user wants to add to their list
    private TournamentDatabaseAccess tournamentDBA;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_tournament_activity);

        //set up database
        tournamentDBA = new TournamentDatabaseAccess(this);

        //open database
        try {
            tournamentDBA.open();
            Log.v("-----------------------","DATBASE OPEN CALL");

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        //set up list
        myAdapter = new MyCustomAdapter(this,R.layout.find_tournament_activity,eventList);
        setListAdapter(myAdapter);
        //this calls the inner class below
        new ParseHTML().execute();





    }//end onCreate()

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Toast.makeText(getApplicationContext(),eventList.get(position).getEvent() + " has been added.",Toast.LENGTH_SHORT).show();

        //Insert event user clicks into database
        //String event, String country, String starts,String end_date,String buyin, String fee


        try {
            tournamentDBA.insertEvent(eventList.get(position).getEvent(),
                    eventList.get(position).getCountry(),
                    eventList.get(position).getDate(),
                    eventList.get(position).getEnd_date(),
                    eventList.get(position).getBuyin(),
                    eventList.get(position).getFee());
        }catch (SQLException e){
            e.printStackTrace();
        }




    }//end onListClickItem


    //Does the databse need to be closed once activity is changed?

    @Override
    public void onStop() {
        super.onStop();
        tournamentDBA.close();

    }




    //List
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
            row = inflater.inflate(R.layout.row, parent,false);


            //Format the string and update Textview
            TextView eventView = (TextView)row.findViewById(R.id.event);
            String eventDetails = "Event Name: \n" + eventList.get(position).getEvent() +"\n"
                    + "Start: " + eventList.get(position).getDate() + "\n"
                    + "End: " + eventList.get(position).getEnd_date()+ "\n"
                    + "Buyin: " + eventList.get(position).getBuyin() + "\n"
                    + "Fee: " + eventList.get(position).getFee() + "\n";
            eventView.setText(eventDetails);

            return row;
        }//end getView


    }//end customAdapter


    //Jsoup needs it own thread. It causes fatal exception if it runs on main thread. It "Freezes" the UI
    //I used stackoverflow to get this working
    //AsyncTask takes parameters. The last is the return type. Jsoup parses the webpage, fills the arraylist with data and
    //we return that arraylist so we can update the ListActivity
    private class ParseHTML extends AsyncTask<Void, Void, ArrayList<Event>>
    {


        private Document doc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this is used becuase it takes time to parse the site
            progressDialog = new ProgressDialog(FindTournamentActivity.this);
            progressDialog.setTitle("Getting Data");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<Event> doInBackground(Void... params) {
            try {
                //Get Document object after parsing the html from given url.
                doc = Jsoup.connect(url).get();
                //this gets the entire table
                //Elements eventsTable = doc.getElementsByClass("eventsFromXml");

                //this also get the entire table
                Elements eventsTable = doc.select("table[class=eventsFromXml]");



                Elements rows = eventsTable.get(0).select("tr");

                //loop through each row and out the 0th row(event name) into array list
                for (Element row : rows) {



                    //For seperating the Event from the tournaments in the event
                    //Try if (row.select("td").size() == 6 && row.select("td").hasClass("title")
                    if (row.select("td").size() == 6 && row.select("td").hasClass("title"))
                    {
                        //The address in the html is in the span tag
                        String address = row.select("span").text();

                        Log.v("------------address",address);

                        Event e = new Event(row.select("td").get(0).text(),row.select("td").get(1).text(),
                                row.select("td").get(2).text(),row.select("td").get(3).text(),
                                row.select("td").get(4).text(),row.select("td").get(5).text());

                        eventList.add(e);

                    }

                }//end for

                //checking array list is filled: it is
                /*
                for (int i = 0; i < eventList.size(); i++)
                {
                    Log.v("-------------ARRAYLIST:",eventList.get(i));
                }
                */


            } catch (Exception e) {
                e.printStackTrace();
            }
            return eventList;
        }//end doInBackground



        @Override
        protected void onPostExecute(ArrayList<Event> result) {
            progressDialog.dismiss();
            //set the list Adapter

            //checking result array list is filled POST_EXECUTE: it is
            /*
            for (int i = 0; i < result.size(); i++)
            {
                Log.v("POST---------ARRAYLIST:",result.get(i));
            }
            */
            myAdapter.notifyDataSetChanged();


        }
    }
}//end class
