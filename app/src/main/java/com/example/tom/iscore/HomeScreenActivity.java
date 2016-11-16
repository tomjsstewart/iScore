package com.example.tom.iscore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        final Button startMatchBtn1 = (Button) findViewById(R.id.startMatchBtn);

        startMatchBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Open the PlayMatchActivity
                 */
                startActivity(new Intent(HomeScreenActivity.this, PlayMatchActivity.class));
            }
        });
    }
}
