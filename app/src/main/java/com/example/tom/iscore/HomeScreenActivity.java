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
        final Button playersListBtn = (Button) findViewById(R.id.profilesBtn);

        final DBHandler db = new DBHandler(this);
        //db.deleteForTest();

        startMatchBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Open the AddPlayerOneToMatchActivity
                 */
                startActivity(new Intent(HomeScreenActivity.this, AddPlayerOneToMatchActivity.class));
            }
        });

        playersListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //db.addPlayer("Tom Stewart", 17, "Male", "R");
                //db.addPlayer("Ben Stewart", 12, "Male", "R");
                /*
                Open the Players profile activity
                 */
                startActivity(new Intent(HomeScreenActivity.this, SelectPlayerActivity.class));
            }
        });
    }
}
