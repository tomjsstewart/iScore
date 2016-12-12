package com.example.tom.iscore;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
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

        _playerOneId = getIntent().getIntExtra("PlayerOneId", -1);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        displayListView();
    }

    private void displayListView()
    {
        DBHandler db = new DBHandler(this);

        Cursor cursor = db.getAllPlayersNamesExceptParmCursor(_playerOneId);

        Log.d("Show cursor", cursor.toString());

        if (cursor == null)
        {
            Log.d("displayListView", "Cursor is null");
        }

        if (cursor.getCount() == 0)
        {
            Log.d("displayListView", "no players in database");
        }


        String[] player_names = new String[] {
                DBHandler.KEY_PLAYER_NAME,
        };

        int[] to = new int[]{
                R.id.playername
        };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.display_names,
                cursor,
                player_names,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);

        Log.d("List view", listView.toString());



        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                //Get cursor position of clicked on player
                Log.d("position", String.valueOf(position));
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);


                //Open AddPlayerTwoToMatchActivity and pass in Player 1 so they can't be picked twice
                Intent intent = new Intent(AddPlayerTwoToMatchActivity.this,
                        PlayMatchActivity.class);

                //pass the players to the PlayMatchActivity
                intent.putExtra("PlayerOneId", _playerOneId);
                //position += 1;

                Log.d("playerOne id", String.valueOf(_playerOneId));
                Log.d("playerTwo pos", String.valueOf(position));


                if (_playerOneId == position)
                {
                    position += 1;
                }
                else if(position < _playerOneId)
                {
                    position += 2;
                }

                intent.putExtra("PlayerTwoId", position + 1);
                startActivity(intent);

            }
        });

    }
}
