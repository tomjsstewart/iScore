package com.example.tom.iscore;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AddPlayerTwoToMatchActivity extends Activity {

    private SimpleCursorAdapter dataAdapter;
    private int _playerOneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_two_to_match);
        //Force Screen to open Portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Read in the column index of player one
        _playerOneId = getIntent().getIntExtra("PlayerOneId", -1);
        Log.d("player one id ", String.valueOf(_playerOneId));

        displayListView();

    }

    @Override
    public void onResume()
    {
        //If the screen is reopened then the ListView is redisplayed/updated
        super.onResume();
        displayListView();
    }

    private void displayListView()
    {
        DBHandler db = new DBHandler(this);

        //Get a cursor containing all of the players names, except the previously selected one
        Cursor cursor = db.getAllPlayersNamesExceptParmCursor(_playerOneId);

        //Log.d("Show cursor", cursor.toString());

        if (cursor == null)
        {
            Log.d("displayListView", "Cursor is null");
        }

        if (cursor.getCount() == 0)
        {
            Log.d("displayListView", "no players in database");
        }

        //Passed into the SimpleCursorAdapter
        String[] player_names = new String[] {
                DBHandler.KEY_PLAYER_NAME,
        };

        int[] to = new int[]{
                R.id.playername
        };

        //This is used to dynamically edit the ListView
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.display_names,
                cursor,
                player_names,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);


        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                //Open AddPlayerTwoToMatchActivity and pass in Player 1 so they can't be picked twice
                Intent intent = new Intent(AddPlayerTwoToMatchActivity.this,
                        PlayMatchActivity.class);

                //pass the players to the PlayMatchActivity
                intent.putExtra("PlayerOneId", _playerOneId);
                //Turn position in to Id
                position += 1;

                //This is to account for the missing player
                if (_playerOneId <= position)
                {
                    position += 1;
                }
                intent.putExtra("PlayerTwoId", position);
                startActivity(intent);

            }
        });

    }
}
