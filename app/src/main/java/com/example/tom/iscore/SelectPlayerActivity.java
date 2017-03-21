package com.example.tom.iscore;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class SelectPlayerActivity extends Activity {

    private SimpleCursorAdapter dataAdapter;
    private int clickedPlayerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_player_activity);
        //Force Screen to open Portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton addPlayerBtn = (ImageButton) findViewById(R.id.addPlayerBtn);

        addPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectPlayerActivity.this, AddPlayerActivity.class));

                //Update the ListView
                displayListView();
            }
        });

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

        /*
        Get a cursor that points to the first position in a cursor containing all players names from
         the database
         */
        Cursor cursor = db.getAllPlayersNamesCursor();

        //Points to the column from the database to display on the screen
        String[] player_names = new String[] {
                DBHandler.KEY_PLAYER_NAME,
        };

        //points to the layout id of the TextView used in the ListView
        //found in ...\layout\display_names.xml this address is also passed to the SimpleCursorAdapter
        int[] to = new int[]{
                R.id.playername
        };

        //Defines the SimpleCursorAdapter, giving the required inputs
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.display_names,
                cursor,
                player_names,
                to,
                0);

        //Find the ListView
        ListView listView = (ListView) findViewById(R.id.listView1);

        //Sets the adapter of the list view to be the cursor adapter
        listView.setAdapter(dataAdapter);

        //Code to handle a click on any item in the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                                //Open the viewPlayerProfileActivity screen and pass the column number of the player selected
                Intent intent = new Intent(SelectPlayerActivity.this, ViewPlayerProfileActivity.class);
                //Pass column index of player clicked
                intent.putExtra("ColumnIndex", position );
                startActivity(intent);
            }
        });

    }
}

