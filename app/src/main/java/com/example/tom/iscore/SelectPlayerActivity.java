package com.example.tom.iscore;

import android.app.ListActivity;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Tom on 21/11/2016.
 */

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


                displayListView();
            }
        });

        displayListView();
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

        /*
        Get a cursor that points to the first position in a cursor containing all players names from
         the database
         */
        Cursor cursor = db.getAllPlayersNamesCursor();

        Log.d("Show cursor", cursor.toString());

        if (cursor == null)
        {
            Log.d("displayListView", "Cursor is null");
        }

        if (cursor.getCount() == 0)
        {
            Log.d("displayListView", "no players in database");
        }

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

        Log.d("List view", listView.toString());

        //Sets the adapter of the list view to be the cursor adapter
        listView.setAdapter(dataAdapter);

        //Code to handle a click on any item in the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                //Get cursor position of clicked on player
                Log.d("position", String.valueOf(position));

                //Open the viewPlayerProfileActivity screen and pass the column number of the player selected
                Intent intent = new Intent(SelectPlayerActivity.this, ViewPlayerProfileActivity.class);
                //Pass column index of player clicked
                intent.putExtra("ColumnIndex", position );
                startActivity(intent);

            }
        });

    }
}

