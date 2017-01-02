package com.example.tom.iscore;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Tom on 02/01/2017.
 */

public class HistoryCursorAdapter extends CursorAdapter{
    public HistoryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.history_list_info, parent, false);
    }


    /*
    This function is used to bind the data to its specified TextView.
     */
    @Override
    public void bindView (View view, Context context, Cursor cursor)
    {
        //Find the views to populate
        TextView player1TextView = (TextView) view.findViewById(R.id.player1TextView);
        TextView player2TextView = (TextView) view.findViewById(R.id.player2TextView);
        TextView scoreTextView = (TextView) view.findViewById(R.id.scoreTextView);
        TextView dateTextView = (TextView) view.findViewById(R.id.dateTextView);

        //Get the properties to display from the cursor
        String player1 = cursor.getString(0);
        String player2 = cursor.getString(1);
        String score = cursor.getString(2);
        String date = cursor.getString(3);

        //Populate the TextViews.
        player1TextView.setText("Player 1: " + player1);
        player2TextView.setText("Player 2: " + player2);
        scoreTextView.setText("Score: " + score);
        dateTextView.setText("Date: " + date);

    }
}
