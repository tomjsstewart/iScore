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
        final Button historyBtn = (Button) findViewById(R.id.HistoryBtn);

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

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteForTest();
                db.addPlayer("Tom Stewart", 17, "Male", "R");
                db.addPlayer("Ben Stewart", 12, "Male", "R");

                PlayerData player1 = new PlayerData();
                PlayerData player2 = new PlayerData();

                player1 = db.getPlayerByID(1);
                player2 = db.getPlayerByID(2);
                player1.setSetsThisMatch(3);
                player1.setTotalSetsPlayed(5);
                player2.setSetsThisMatch(2);
                player2.setTotalSetsPlayed(5);

                String score = Integer.toString(player1.getSetsThisMatch()) + " - " + Integer.toString(player2.getSetsThisMatch());

                db.saveMatch(1, player1.getPlayerID(), player2.getPlayerID(), score, player1);
                db.saveMatch(1, player2.getPlayerID(), player1.getPlayerID(), score, player2);


                player1.setSetsThisMatch(1);
                player1.setTotalSetsPlayed(5);
                player2.setSetsThisMatch(4);
                player2.setTotalSetsPlayed(5);

                score = Integer.toString(player1.getSetsThisMatch()) + " - " + Integer.toString(player2.getSetsThisMatch());

                db.saveMatch(2, player1.getPlayerID(), player2.getPlayerID(), score, player1);
                db.saveMatch(2, player2.getPlayerID(), player1.getPlayerID(), score, player2);



                /*
                Open HistoryActivity
                 */
                startActivity(new Intent(HomeScreenActivity.this, HistoryActivity.class));
            }
        });
    }
}
