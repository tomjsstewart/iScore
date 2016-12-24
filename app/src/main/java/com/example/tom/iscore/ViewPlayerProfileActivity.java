package com.example.tom.iscore;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewPlayerProfileActivity extends AppCompatActivity {

    TextView showPlayerName;
    TextView showPlayerAge;
    TextView showPlayerGender;
    TextView showPlayerHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_player_profile);
        //Force Screen to open Portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DBHandler db = new DBHandler(this);

        /*
        Get the player ID from the position passed in by the SelectPlayer screen.
        The +1 is needed because the ListView is zero-indexed, but the SQLite database is one-indexed.
        This means that to turn any position in a list view to an ID one must be added to it
         */
        int playerId = (getIntent().getIntExtra("ColumnIndex", -1)) + 1;

        Log.d("Player column index", Integer.toString(playerId));

        //Select that player and get a PlayerData instance that contains all of their data.
        PlayerData player = db.getPlayerByID(playerId);

        //Get the TextViews so that they can be updated.
        showPlayerName = (TextView) findViewById(R.id.ShowPlayerName);
        showPlayerAge = (TextView) findViewById(R.id.ShowPlayerAge);
        showPlayerGender = (TextView) findViewById(R.id.ShowPlayerGender);
        showPlayerHand = (TextView) findViewById(R.id.ShowPlayerHand);

        //Underline the player's name
        SpannableString content = new SpannableString(player.getPlayerName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        //Update the screen to show this information.
        showPlayerName.setText(content);
        showPlayerAge.setText(Integer.toString(player.getPlayerAge()));
        showPlayerGender.setText(player.getPlayerGender());
        showPlayerHand.setText(player.getPlayerHand());


    }
}