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

        int playerId = getIntent().getIntExtra("ColumnIndex", -1);

        Log.d("Player column index", Integer.toString(playerId));

        PlayerData player = db.getPlayerByID(playerId+1);

        showPlayerName = (TextView) findViewById(R.id.ShowPlayerName);
        showPlayerAge = (TextView) findViewById(R.id.ShowPlayerAge);
        showPlayerGender = (TextView) findViewById(R.id.ShowPlayerGender);
        showPlayerHand = (TextView) findViewById(R.id.ShowPlayerHand);

        //Underline the player's name
        SpannableString content = new SpannableString(player.getPlayerName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        showPlayerName.setText(content);
        showPlayerAge.setText(Integer.toString(player.getPlayerAge()));
        showPlayerGender.setText(player.getPlayerGender());
        showPlayerHand.setText(player.getPlayerHand());


    }
}