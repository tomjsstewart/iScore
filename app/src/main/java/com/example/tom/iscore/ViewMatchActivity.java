package com.example.tom.iscore;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewMatchActivity extends AppCompatActivity {

    //Initialise the TextViews
    TextView Player1Data;
    TextView Player2Data;
    TextView Titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Reference the TextViews in the layout so they can be updated.
        Player1Data = (TextView) findViewById(R.id.Player1Data);
        Player2Data = (TextView) findViewById(R.id.Player2Data);
        Titles = (TextView) findViewById(R.id.Titles);

        DBHandler db = new DBHandler(this);

        int matchID = (getIntent().getIntExtra("MatchID", -1));

        List<PlayerData> players = db.getMatchData(matchID);
        PlayerData Player1 = players.get(0);
        PlayerData Player2 = players.get(1);

        Player1Data.setText(Integer.toString(Player1.getTotalPointsWon())
                + "\n"
                + Integer.toString(Player1.getTotalPointsPlayed()));

        Player2Data.setText(Integer.toString(Player2.getTotalPointsWon())
                + "\n"
                + Integer.toString(Player2.getTotalPointsPlayed()));

        Titles.setText("Total Points Won\nTotalPointsPlayed");




    }

}
