package com.example.tom.iscore;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AddPlayerOneToMatchActivity extends Activity {

    private ListView listView;

    private SimpleCursorAdapter dataAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_one_to_match);
        //Force Screen to open Portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        //Get a cursor containing all of the players names
        Cursor cursor = db.getAllPlayersNamesCursor();

        //Passed into the SimpleCursorAdapter
        String[] player_names = new String[] {
                DBHandler.KEY_PLAYER_NAME,
        };

        int[] to = new int[]{
                R.id.playername
        };

        //This is used to dynamically edit the ListView
        dataAdapter2 = new SimpleCursorAdapter(
                this, R.layout.display_names,
                cursor,
                player_names,
                to,
                0);

        //find the ListView so that it can be edited
        listView = (ListView) findViewById(R.id.listView1);


        listView.setAdapter(dataAdapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                //Open AddPlayerTwoToMatchActivity and pass in Player 1 so they can't be picked twice
                Intent intent = new Intent(AddPlayerOneToMatchActivity.this,
                        AddPlayerTwoToMatchActivity.class);
                //position is incremented to ensure that it is the ID
                intent.putExtra("PlayerOneId", position + 1 );
                startActivity(intent);

            }
        });
    }
}
