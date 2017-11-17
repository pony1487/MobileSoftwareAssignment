package com.example.ronan.assignment;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ronan on 14/11/2017.
 * This uses the jsoup-1.11.1.jar
 *
 * 
 */

public class FindTournamentActivity extends ListActivity {

    private ProgressDialog progressDialog;
    private String url = "https://www.pokernews.com/poker-tournaments/";
    MyCustomAdapter myAdapter;

    public ArrayList<String> eventList = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_tournament_activity);



        //myAdapter = new MyCustomAdapter(FindTournamentActivity.this,R.id.event,eventList);
        myAdapter = new MyCustomAdapter(this,R.layout.find_tournament_activity,eventList);
        setListAdapter(myAdapter);

        //this calls the inner class below
        new ParseHTML().execute();


    }//end onCreate()



    //List
    public class MyCustomAdapter extends ArrayAdapter<String>
    {
        public MyCustomAdapter(Context context, int rowLayoutID, ArrayList<String> myData)
        {
            super(context,rowLayoutID,myData);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row;

            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(R.layout.row, parent,false);

            TextView eventView = (TextView)row.findViewById(R.id.event);
            eventView.setText(eventList.get(position));




            return row;
        }//end getView
    }//end customAdapter


    //Jsoup needs it own thread. It causes fatal exception if it runs on main thread. It "Freezes" the UI
    //I used stackoverflow to get this working
    //AsyncTask takes parameters. The last is the return type. Jsoup parses the webpage, fills the arraylist with data and
    //we return that arraylist so we can update the ListActivity
    private class ParseHTML extends AsyncTask<Void, Void, ArrayList<String>>
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
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                //Get Document object after parsing the html from given url.
                doc = Jsoup.connect(url).get();
                //this gets the entire table
                //Elements eventsTable = doc.getElementsByClass("eventsFromXml");

                //this also get the entire table
                Elements eventsTable = doc.select("table[class=eventsFromXml]");

                //
                Elements rows = eventsTable.get(0).select("tr");

                //loop through each row and out the 0th row(event name) into array list
                for (Element row : rows) {
                    if (row.select("td").size() == 6) {
                        eventList.add(row.select("td").get(0).text());
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
        protected void onPostExecute(ArrayList<String> result) {
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
