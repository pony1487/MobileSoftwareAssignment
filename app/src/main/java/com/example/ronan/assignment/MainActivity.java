package com.example.ronan.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menu Buttons
        Button calculateShoveButton = (Button)findViewById(R.id.calulateShoveButton);
        //Button practiceButton = (Button)findViewById(R.id.practiceButton);
        //Button FindTournamentsButton = (Button)findViewById(R.id.FindTournamentsButton);
        //Button myTouramentsButton = (Button)findViewById(R.id.myTouramentsButton);

        //OnClickListeners and intents
        calculateShoveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, PrelopActivity.class);
                startActivity(intent);
            }
        });

    }//end onCreate()


    //This functions will use intents to change to the appropriate activity
    public void calulateShovingRange()
    {
    }//end calculateShovingRange()

    public void findTournaments()
    {
    }//end findTournaments()

    public void displayMyTournaments()
    {
    }//end displayMyTournaments()

}