package com.example.tom.iscore;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class HistoryActivity extends Activity {

    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Force Screen to open Portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        displayHistoryList();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        displayHistoryList();
    }

    private void displayHistoryList()
    {
        DBHandler db = new DBHandler(this);

        //Get a cursor containing all matches
        Cursor cursor = db.getAllMatches();

        //Find the ListView
        ListView listView = (ListView) findViewById(R.id.HistoryListView);

        //Generate the cursor adapter
        HistoryCursorAdapter historyCursorAdapter = new HistoryCursorAdapter(this, cursor);

        //Sets the adapter of the list view to be the cursor adapter
        listView.setAdapter(historyCursorAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HistoryActivity.this, ViewMatchActivity.class);

                //Is this correct?
                intent.putExtra("MatchID", (position/2) + 1);

                startActivity(intent);
            }
        });
    }

}
