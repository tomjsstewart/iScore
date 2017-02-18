package com.example.tom.iscore;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tom.iscore.PlayerData;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

public class PlayMatchActivity extends AppCompatActivity {

    //Add all the text views so that I can edit them
    TextView player1PointsTextView;
    TextView player2PointsTextView;
    TextView player1GamesTextView;
    TextView player2GamesTextView;
    TextView player1SetsTextView;
    TextView player2SetsTextView;

    TextView player1NameTextView;
    TextView player2NameTextView;

//    ImageButton saveMatchBtn;

    //Lookup for the values of different points
    private String[] pointsValues = {"0", "15", "30", "40", "game", "40", "ad", "game"};

    //Instantiate an object of PlayerData for player 1 and player 2
    PlayerData player1Data = new PlayerData();
    PlayerData player2Data = new PlayerData();

    //Initialise database
    DBHandler db = new DBHandler(this);

    private int playerClicked = 0;

    public void checkWinningGameSetOrMatch()
    {
        /*
        function to check if either player has won a game, set or the match and adjust scores
        accordingly, if match has been won, call endMatch method to stop further inputs and save
        match data.
         */


        /*
         If either player has won a game then reset all points to 0;
         */
        if (
                player1Data.getPointsThisGame() == 4 ||
                        player2Data.getPointsThisGame() == 4 ||
                        player1Data.getPointsThisGame() == 7 ||
                        player2Data.getPointsThisGame() == 7)
        {
            player1Data.resetPoints();
            player2Data.resetPoints();
        }

        /*
         If either player has won a set reset games
         */
        if (
                ((player1Data.getGamesThisSet() >=6) && ((player2Data.getGamesThisSet()+2) <= player1Data.getGamesThisSet())) ||
                        ((player2Data.getGamesThisSet() >=6) && ((player1Data.getGamesThisSet()+2) <= player2Data.getGamesThisSet()))

                )
        {
            player1Data.resetGames();
            player2Data.resetGames();
        }

        /*
        If either player has won the match disable buttons.
         */
        if (player1Data.getSetsThisMatch() == 3 || player2Data.getSetsThisMatch() == 3)
        {
           endMatch();
        }

        updateScreen();
    }


    public void updateScreen()
    {
        /*
        Function to update the values of the TextView on the screen.
         */
        player1PointsTextView.setText(pointsValues[player1Data.getPointsThisGame()]);
        player1GamesTextView.setText(String.format("%d", player1Data.getGamesThisSet()));
        player1SetsTextView.setText(String.format("%d", player1Data.getSetsThisMatch()));

        player2PointsTextView.setText(pointsValues[player2Data.getPointsThisGame()]);
        player2GamesTextView.setText(String.format("%d", player2Data.getGamesThisSet()));
        player2SetsTextView.setText(String.format("%d", player2Data.getSetsThisMatch()));

    }

    public void player1LosesPoint()
    {
        /*
        Function to call required code when player 1 loses a point
         */
        player1Data.setOpponentsPointsThisGame(
                player2Data.getPointsThisGame());
        player1Data.setOpponentsGamesThisSet(
                player2Data.getGamesThisSet());
        player1Data.setOpponentsSetsThisMatch(
                player2Data.getSetsThisMatch());

        player1Data.losePoint();

        checkWinningGameSetOrMatch();
    }

    public void player2LosesPoint()
    {
         /*
        Function to call required code when player 2 loses a point
         */
        player2Data.setOpponentsPointsThisGame(
                player1Data.getPointsThisGame());
        player2Data.setOpponentsGamesThisSet(
                player1Data.getGamesThisSet());
        player2Data.setOpponentsSetsThisMatch(
                player1Data.getSetsThisMatch());

        player2Data.losePoint();

        checkWinningGameSetOrMatch();
    }

    public void endMatch()
    {
        /*
        Function to handle the end of a match.
         */
        player1NameTextView.setEnabled(false);
        player2NameTextView.setEnabled(false);

        //Generate matchID
        Integer matchId = db.generateMatchID();

        //Get date of match
        Date date = new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);

        Log.d("Match ID", String.valueOf(matchId));

        //Create a string of the score to save to the database
        String score = Integer.toString(player1Data.getSetsThisMatch()) + " - " + Integer.toString(player2Data.getSetsThisMatch());
        //If there hasn't been a full set played include the number of games won by each player
        if (player1Data.getSetsThisMatch() == 0 && player2Data.getSetsThisMatch() == 0)
        {
            score += " (" + Integer.toString(player1Data.getGamesThisSet()) + " - " + Integer.toString(player2Data.getGamesThisSet()) +")";
        }

        //Save player 1
        Boolean success1 = db.saveMatch(matchId,
                player1Data.getPlayerID(),
                player2Data.getPlayerName(),
                score,
                stringDate,
                player1Data);

        //Save player 2
        Boolean success2 = db.saveMatch(matchId,
                player2Data.getPlayerID(),
                player1Data.getPlayerName(),
                score,
                stringDate,
                player2Data);

        //generate a small message to tell user if save worked or not.
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        CharSequence msg;

        if (success1 && success2)
        {
            /*
            Display a success message if save worked.
             */
            msg = "Match saved.";
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show(); //Display message
        }
        else
        {
            /*
            Display an error message if something went wrong with the save
             */
            msg = "Something went wrong.";
            duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show(); //Display message
        }
    }


    public void showPointPopup(final View v)
    {
        /*
        Function to handle the point Popup menu.
        This will call the appropriate functions within the PlayerData class,
        or another popup menu.
         */

        //Define a new popup menu.
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();

        //Set a listener on the buttons and define a function to be called if a button is clicked
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                /*
                Function to call the appropriate function in the correct players instance of
                PlayerData, or another popup window, playerClicked is updated to show the players name that was clicked on.
                */

                switch (item.getItemId())
                {
                    case R.id.Ace: //Ace
                        if (playerClicked == 1)
                        {
                            player1Data.scoreAce();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreAce();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Winner:
                        //Call the winner popup menu
                        showWinnerPopup(v);

                    case R.id.Fault: //Fault
                        if (playerClicked == 1)
                        {
                            player1Data.recordFault();
                        }
                        else
                        {
                            player2Data.recordFault();
                        }
                        return true;

                    case R.id.Double_Fault: //Double fault
                        if (playerClicked == 1)
                        {
                            player1Data.recordDoubleFault();
                            player2Data.scorePoint();
                            player1LosesPoint();
                        }
                        else
                        {
                            player2Data.recordDoubleFault();
                            player1Data.scorePoint();
                            player2LosesPoint();
                        }
                        return true;

                    case R.id.Unforced_Error: //Unforced error
                        if (playerClicked == 1)
                        {
                            player1Data.recordUnforcedError();
                            player2Data.scorePoint();
                            player1LosesPoint();
                        }
                        else
                        {
                            player2Data.recordUnforcedError();
                            player1Data.scorePoint();
                            player2LosesPoint();
                        }
                        return true;

                    case R.id.Forced_Error: //Forced error
                        if (playerClicked == 1)
                        {
                            player1Data.recordForcedError();
                            player2Data.scorePoint();
                            player1LosesPoint();
                        }
                        else
                        {
                            player2Data.recordForcedError();
                            player1Data.scorePoint();
                            player2LosesPoint();
                        }
                        return true;

                    default:
                        updateScreen();
                        return false;
                }
            }
        });
        //Inflate and display the menu.
        inflater.inflate(R.menu.points_popup_menu, popup.getMenu());
        popup.show();
    }


    public void showWinnerPopup(final View v)
    {
        /*
        Function to handle the winner Popup menu.
        This will call the appropriate functions within the PlayerData class.
        */

        //Define a new popup menu.
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                /*
                Function to call the appropriate function in the correct players instance of
                PlayerData, or another popup window, playerClicked is updated to show the players name that was clicked on.
                */
                switch (item.getItemId())
                {
                    case R.id.Forehand: //Forehand winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreForehandWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreForehandWinner();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Backhand: //Backhand winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreBackhandWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreBackhandWinner();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Volley: //Volley
                        //Call volley popup menu.
                        showVolleyPopup(v);
                        return true;

                    case R.id.Smash: //Smash winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreSmashWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreSmashWinner();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Dropshot: //Dropshot winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreDropShotWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreDropShotWinner();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Lob: //Lob winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreLobWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreLobWinner();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Returner_Serve: //returner serve winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreReturnerServeWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreReturnerServeWinner();
                            player1LosesPoint();
                        }
                        return true;

                    default:
                        updateScreen();
                        return false;
                }
            }
        });
        //Inflate and display the menu.
        inflater.inflate(R.menu.winners_popup_menu, popup.getMenu());
        popup.show();
    }

    public void showVolleyPopup(final View v)
    {
        /*
        Function to handle the volley Popup menu.
        This will call the appropriate functions within the PlayerData class.
         */

        //Define a new popup menu.
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();

        //Set a listener on the buttons and define a function to be called if a button is clicked
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                /*
                Function to call the appropriate function in the correct players instance of
                PlayerData, playerClicked is updated to show the players name that was clicked on.
                 */
                switch (item.getItemId())
                {
                    case R.id.Volley2: //Volley winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreVolleyWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreVolleyWinner();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Drive_Volley: //Drive volley winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreDriveVolleyWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreDriveVolleyWinner();
                            player1LosesPoint();
                        }
                        return true;

                    case R.id.Half_Volley: //Half volley winner
                        if (playerClicked == 1)
                        {
                            player1Data.scoreHalfVolleyWinner();
                            player2LosesPoint();
                        }
                        else
                        {
                            player2Data.scoreHalfVolleyWinner();
                            player1LosesPoint();
                        }
                        return true;

                    default:
                        updateScreen();
                        return false;
                }
            }
        });

        //Inflate and display the menu.
        inflater.inflate(R.menu.volley_popup_menu, popup.getMenu());
        popup.show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_match);
        //Force Screen to open Landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        player1Data = db.getPlayerByID(getIntent().getIntExtra("PlayerOneId", -1));
        player2Data = db.getPlayerByID(getIntent().getIntExtra("PlayerTwoId", -1));

        player1NameTextView = (TextView) findViewById(R.id.player1NameTextView);
        player1PointsTextView = (TextView) findViewById(R.id.player1PointsTextView);
        player1GamesTextView = (TextView) findViewById(R.id.player1GamesTextView);
        player1SetsTextView = (TextView) findViewById(R.id.player1SetsTextView);

        player2NameTextView = (TextView) findViewById(R.id.player2NameTextView);
        player2PointsTextView = (TextView) findViewById(R.id.player2PointsTextView);
        player2GamesTextView = (TextView) findViewById(R.id.player2GamesTextView);
        player2SetsTextView = (TextView) findViewById(R.id.player2SetsTextView);

//        saveMatchBtn = (ImageButton) findViewById(R.id.saveMatchBtn);

        player1NameTextView.setText(player1Data.getPlayerName());
        player2NameTextView.setText(player2Data.getPlayerName());

        player1NameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicked = 1;
                /*
                Player 1 has scored a point.
                Player 2's data is needed for deuce/ad calculations.
                All opponent attributes are set before the scoring so
                they are not reset if the player has won a game or set
                Player 2 has lost a point.
                 */
                player1Data.setOpponentsPointsThisGame(
                        player2Data.getPointsThisGame());
                player1Data.setOpponentsGamesThisSet(
                        player2Data.getGamesThisSet());
                player1Data.setOpponentsSetsThisMatch(
                        player2Data.getSetsThisMatch());

                //player1Data.scorePoint();
                showPointPopup(player1NameTextView);

            }
        });


        player2NameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicked = 2;
                /*
                Player 2 has scored a point.
                Player 1's data is needed for deuce/ad calculations.
                All opponent attributes are set before the scoring so
                they are not reset if the player has won a game or set
                Player 1 has lost a point.
                 */

                player2Data.setOpponentsPointsThisGame(
                        player1Data.getPointsThisGame());
                player2Data.setOpponentsGamesThisSet(
                        player1Data.getGamesThisSet());
                player2Data.setOpponentsSetsThisMatch(
                        player1Data.getSetsThisMatch());


                //player2Data.scorePoint();
                showPointPopup(player2NameTextView);


            }
        });

//        saveMatchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //This contains code to stop more button pushes and saves match data
//                endMatch();
//            }
//        });
    }
}
