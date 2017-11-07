package com.example.ronan.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ronan on 07/11/2017.
 */

public class PrelopActivity extends Activity
{
    private String position;
    private int stackSize;
    private double antePercentage;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preflop_activity);

        //buttons
        Button HandRangeActivityButton = (Button)findViewById(R.id.HandRangeActivityButton);
        Button backButton = (Button)findViewById(R.id.back_button);

        //OnClickListeners and intents
        HandRangeActivityButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(PrelopActivity.this, HandRangeActivity.class);
                startActivity(intent);
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
        this.position = position;
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
        return position;
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
        return "Positon: " + position + "StackSize: " + stackSize + "Ante: " + antePercentage;
    }


}
