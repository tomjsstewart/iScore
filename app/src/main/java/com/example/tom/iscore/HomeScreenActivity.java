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

        //Find the buttons so they can be edited
        final Button startMatchBtn1 = (Button) findViewById(R.id.startMatchBtn);
        final Button playersListBtn = (Button) findViewById(R.id.profilesBtn);
        final Button historyBtn = (Button) findViewById(R.id.HistoryBtn);

        final DBHandler db = new DBHandler(this);

        //db.viewAll();
        db.deleteForTest();
        /*db.deleteForTest();
        db.addPlayer("Tom Stewart", 17, "Male", "R");
        db.addPlayer("Ben Stewart", 12, "Male", "R");

        PlayerData player1 = new PlayerData();
        PlayerData player2 = new PlayerData();

        Date date = new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);


        player1 = db.getPlayerByID(1);
        player2 = db.getPlayerByID(2);
        player1.setSetsThisMatch(3);
        player1.setTotalSetsPlayed(5);
        player2.setSetsThisMatch(2);
        player2.setTotalSetsPlayed(5);

        String score = Integer.toString(player1.getSetsThisMatch()) + " - " + Integer.toString(player2.getSetsThisMatch());
        Boolean hold;

        hold = db.saveMatch(1, player1.getPlayerID(), player2.getPlayerName(), score, stringDate, player1);
        hold = db.saveMatch(1, player2.getPlayerID(), player1.getPlayerName(), score, stringDate, player2);


        player1.setSetsThisMatch(1);
        player1.setTotalSetsPlayed(5);
        player2.setSetsThisMatch(4);
        player2.setTotalSetsPlayed(5);

        score = Integer.toString(player1.getSetsThisMatch()) + " - " + Integer.toString(player2.getSetsThisMatch());

        hold = db.saveMatch(2, player1.getPlayerID(), player2.getPlayerName(), score, stringDate, player1);
        hold = db.saveMatch(2, player2.getPlayerID(), player1.getPlayerName(), score, stringDate, player2);*/

        startMatchBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Open the AddPlayerOneToMatchActivity if button clicked on
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
                Start the Players profile activity if button clicked on
                 */
                startActivity(new Intent(HomeScreenActivity.this, SelectPlayerActivity.class));
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Start HistoryActivity if button clicked on
                 */
                startActivity(new Intent(HomeScreenActivity.this, HistoryActivity.class));
            }
        });
    }
}
