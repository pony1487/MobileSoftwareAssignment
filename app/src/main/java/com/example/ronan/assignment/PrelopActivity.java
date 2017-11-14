package com.example.ronan.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ronan on 07/11/2017.
 *
 */

public class PrelopActivity extends Activity
{
    private String positionString;
    //this makes it easier to get the column from the DB
    // UTG = 8; UTG1 = 7: UTG2 = 6: LOJACK = 5: HIJACK = 4: CO = 3: BTN = 2: SB = 1;
    private int positionInt;
    private int stackSize;
    private double antePercentage;





    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preflop_activity);


        //buttons for picking the players positon
        Button utgButton = (Button)findViewById(R.id.utgButton);
        Button utg1Button = (Button)findViewById(R.id.utg1Button);
        Button utg2Button = (Button)findViewById(R.id.utg2Button);
        Button lojackButton = (Button)findViewById(R.id.lojackButton);
        Button hijackButton = (Button)findViewById(R.id.hijackButton);
        Button cuttOffButton = (Button)findViewById(R.id.cuttoffButton);
        Button buttonButton = (Button)findViewById(R.id.buttonButton);
        Button smallBlindButton = (Button)findViewById(R.id.smallBlinfButton);


        //buttons for next intent and going back
        Button HandRangeActivityButton = (Button)findViewById(R.id.HandRangeActivityButton);
        Button backButton = (Button)findViewById(R.id.back_button);

        //number picker for stack size
        NumberPicker blindNumberPicker = (NumberPicker)findViewById(R.id.blindNumberPicker);

        blindNumberPicker.setMinValue(1);
        blindNumberPicker.setMaxValue(15);
        //set default value(seems to be a bug) Wont work unless you select another number and then go back to 1
        blindNumberPicker.setValue(1);
        blindNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                stackSize = newVal;
                //TESTING to see if it saved into stackSize. It lags in App
                //Toast.makeText(getApplicationContext(),"Your stack size: " + String.valueOf(stackSize),Toast.LENGTH_SHORT).show();
            }
        });

        //OnClickListeners and intents
        //moves to next screen; NEEDS ERROR CHECKING SO THAT THE USER HAS ENTERED SOMETHING
        HandRangeActivityButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(PrelopActivity.this, HandRangeActivity.class);

                //send the data user entered to the HandRangeActivity
                intent.putExtra("player_position",positionInt);
                intent.putExtra("player_stack_size",stackSize);
                startActivity(intent);
            }
        });

        utgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "UTG";//used to show user what they clicked with a Toast
                positionInt = 8;//this is sent to the HandRangeActivity class
                Toast.makeText(getApplicationContext(),String.valueOf(positionInt)  ,Toast.LENGTH_SHORT).show();
            }
        });

        utg1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "UTG1";
                positionInt = 7;
                Toast.makeText(getApplicationContext(),positionString,Toast.LENGTH_SHORT).show();
            }
        });

        utg2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "UTG2";
                positionInt = 6;
                Toast.makeText(getApplicationContext(),positionString,Toast.LENGTH_SHORT).show();
            }
        });

        lojackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "LOJACK";
                positionInt = 5;
                Toast.makeText(getApplicationContext(),positionString,Toast.LENGTH_SHORT).show();
            }
        });

        hijackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "HIJACK";
                positionInt = 4;
                Toast.makeText(getApplicationContext(),positionString,Toast.LENGTH_SHORT).show();
            }
        });

        cuttOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "CUTOFF";
                positionInt = 3;
                Toast.makeText(getApplicationContext(),positionString,Toast.LENGTH_SHORT).show();
            }
        });
        buttonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "BUTTON";
                positionInt = 2;
                Toast.makeText(getApplicationContext(),positionString,Toast.LENGTH_SHORT).show();
            }
        });

        smallBlindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionString = "SMALLBLIND";
                positionInt = 1;
                Toast.makeText(getApplicationContext(),positionString,Toast.LENGTH_SHORT).show();
            }
        });
        //each page should have a way back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //setters
    public void setPosition(String position) {
        this.positionString = position;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public void setAntePercentage(double antePercentage) {
        this.antePercentage = antePercentage;
    }

    //getters
    public String getPosition()
    {
        return positionString;
    }

    public int getStackSize()
    {
        return stackSize;
    }

    public double getAntePercentage()
    {
        return antePercentage;
    }

    public String toString()
    {
        return "Positon: " + positionString + "StackSize: " + stackSize + "Ante: " + antePercentage;
    }



}
