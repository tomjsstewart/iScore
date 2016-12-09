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

public class AddPlayerOneToMatchActivity extends Activity {

    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_one_to_match);
        //Force Screen to open Portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
                Intent intent = new Intent(AddPlayerOneToMatchActivity.this,
                        AddPlayerTwoToMatchActivity.class);
                intent.putExtra("PlayerOneId", position );
                startActivity(intent);

            }
        });

    }
}
