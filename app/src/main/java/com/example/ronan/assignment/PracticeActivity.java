package com.example.ronan.assignment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ronan on 21/11/2017.
 */

public class PracticeActivity extends Activity {


    private Button backButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practive_activity);


        backButton = (Button)findViewById(R.id.back_button);
        //each page should have a way back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

}
