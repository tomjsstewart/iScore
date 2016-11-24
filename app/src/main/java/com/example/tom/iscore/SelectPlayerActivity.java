package com.example.tom.iscore;

import android.app.ListActivity;
import java.util.ArrayList;
import java.util.List;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

/**
 * Created by Tom on 21/11/2016.
 */

public class SelectPlayerActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_list_item);
        //Force Screen to open Portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DBHandler db = new DBHandler(this);
        List<PlayerData> values = db.getAllPlayers();

        List<String> names = new ArrayList<String>();
        for(PlayerData value: values)
        {
            names.add(value.getPlayerName() );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);

    }


    public void onClick(View view)
    {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();

        //What is this doing??


    }


}