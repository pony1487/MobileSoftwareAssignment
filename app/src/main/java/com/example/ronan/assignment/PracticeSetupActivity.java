package com.example.ronan.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ronan on 21/11/2017.
 */

public class PracticeSetupActivity extends Activity
{
    private Button backButton;
    private Button practiceButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_setup_activity);


        backButton = (Button)findViewById(R.id.back_button);
        practiceButton = (Button)findViewById(R.id.practiceButton);

        //move to the next activity
        practiceButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(PracticeSetupActivity.this, PracticeActivity.class);

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
}
