package com.example.tom.iscore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.tom.iscore.PlayerData;
import org.w3c.dom.Text;

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

    //Lookup for the values of different points
    private String[] pointsValues = {"0", "15", "30", "40", "game", "40", "ad", "game"};

    //Instantiate an object of PlayerData for player 1 and player 2
    PlayerData player1Data = new PlayerData();
    PlayerData player2Data = new PlayerData();

    private int playerClicked = 0;

    public void updateScreen()
    {
        player1PointsTextView.setText(pointsValues[player1Data.getPointsThisGame()]);
        player1GamesTextView.setText(String.format("%d", player1Data.getGamesThisSet()));
        player1SetsTextView.setText(String.format("%d", player1Data.getSetsThisMatch()));

        player2PointsTextView.setText(pointsValues[player2Data.getPointsThisGame()]);
        player2GamesTextView.setText(String.format("%d", player2Data.getGamesThisSet()));
        player2SetsTextView.setText(String.format("%d", player2Data.getSetsThisMatch()));

    }

    public void endMatch()
    {
        player1NameTextView.setEnabled(false);
        player2NameTextView.setEnabled(false);
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
                        {player1Data.scoreAce();}
                        else
                        {player2Data.scoreAce();}
                        return true;
                    case R.id.Winner:
                        //Call the winner popup menu
                        showWinnerPopup(v);
                        return true;
                    case R.id.Fault: //Fault
                        if (playerClicked == 1)
                        {player1Data.recordFault();}
                        else
                        {player2Data.recordFault();}
                        return true;
                    case R.id.Double_Fault: //Double fault
                        if (playerClicked == 1)
                        {player1Data.recordDoubleFault();}
                        else
                        {player2Data.recordDoubleFault();}
                        return true;
                    case R.id.Unforced_Error: //Unforced error
                        if (playerClicked == 1)
                        {player1Data.recordUnforcedError();}
                        else
                        {player2Data.recordUnforcedError();}
                        return true;
                    case R.id.Forced_Error: //Forced error
                        if (playerClicked == 1)
                        {player1Data.recordForcedError();}
                        else
                        {player2Data.recordForcedError();}
                        return true;
                    default:
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
                        {player1Data.scoreForehandWinner();}
                        else
                        {player2Data.scoreForehandWinner();}
                        return true;
                    case R.id.Backhand: //Backhand winner
                        if (playerClicked == 1)
                        {player1Data.scoreBackhandWinner();}
                        else
                        {player2Data.scoreBackhandWinner();}
                        return true;
                    case R.id.Volley: //Volley
                        //Call volley popup menu.
                        showVolleyPopup(v);
                        return true;
                    case R.id.Smash: //Smash winner
                        if (playerClicked == 1)
                        {player1Data.scoreSmashWinner();}
                        else
                        {player2Data.scoreSmashWinner();}
                        return true;
                    case R.id.Dropshot: //Dropshot winner
                        if (playerClicked == 1)
                        {player1Data.scoreDropShotWinner();}
                        else
                        {player2Data.scoreDropShotWinner();}
                        return true;
                    case R.id.Lob: //Lob winner
                        if (playerClicked == 1)
                        {player1Data.scoreLobWinner();}
                        else
                        {player2Data.scoreLobWinner();}
                        return true;
                    case R.id.Returner_Serve: //returner serve winner
                        if (playerClicked == 1)
                        {player1Data.scoreReturnerServeWinner();}
                        else
                        {player2Data.scoreReturnerServeWinner();}
                        return true;
                    default:
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
                        {player1Data.scoreVolleyWinner();}
                        else
                        {player2Data.scoreVolleyWinner();}
                        return true;

                    case R.id.Drive_Volley: //Drive volley winner
                        if (playerClicked == 1)
                        {player1Data.scoreDriveVolleyWinner();}
                        else
                        {player2Data.scoreDriveVolleyWinner();}
                        return true;

                    case R.id.Half_Volley: //Half volley winner
                        if (playerClicked == 1)
                        {player1Data.scoreHalfVolleyWinner();}
                        else
                        {player2Data.scoreHalfVolleyWinner();}
                        return true;
                    default:
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


        player1NameTextView = (TextView) findViewById(R.id.player1NameTextView);
        player1PointsTextView = (TextView) findViewById(R.id.player1PointsTextView);
        player1GamesTextView = (TextView) findViewById(R.id.player1GamesTextView);
        player1SetsTextView = (TextView) findViewById(R.id.player1SetsTextView);

        player2NameTextView = (TextView) findViewById(R.id.player2NameTextView);
        player2PointsTextView = (TextView) findViewById(R.id.player2PointsTextView);
        player2GamesTextView = (TextView) findViewById(R.id.player2GamesTextView);
        player2SetsTextView = (TextView) findViewById(R.id.player2SetsTextView);


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

                player2Data.setOpponentsPointsThisGame(
                        player1Data.getPointsThisGame());
                player2Data.setOpponentsGamesThisSet(
                        player1Data.getGamesThisSet());
                player2Data.setOpponentsSetsThisMatch(
                        player1Data.getSetsThisMatch());

                player2Data.losePoint();

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
                If either player has won a game reset games
                 */
                if (
                        ((player1Data.getGamesThisSet() >=6) && ((player2Data.getGamesThisSet()+2) <= player1Data.getGamesThisSet())) ||
                                ((player2Data.getGamesThisSet() >=6) && ((player1Data.getGamesThisSet()+2) <= player2Data.getGamesThisSet()))

                        )
                {
                    player1Data.resetGames();
                    player2Data.resetGames();
                }

                if (player1Data.getSetsThisMatch() == 3 || player2Data.getSetsThisMatch() == 3)
                {
                    player1NameTextView.setEnabled(false);
                    player2NameTextView.setEnabled(false);
                }

                updateScreen();
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

                player1Data.setOpponentsPointsThisGame(
                        player2Data.getPointsThisGame());
                player1Data.setOpponentsGamesThisSet(
                        player2Data.getGamesThisSet());
                player1Data.setOpponentsSetsThisMatch(
                        player2Data.getSetsThisMatch());


                player1Data.losePoint();


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
                If either player has won a game reset games
                 */
                if (
                        ((player2Data.getGamesThisSet() >=6) && ((player1Data.getGamesThisSet()+2) <= player2Data.getGamesThisSet())) ||
                                ((player1Data.getGamesThisSet() >=6) && ((player2Data.getGamesThisSet()+2) <= player1Data.getGamesThisSet()))

                        )
                {
                    player1Data.resetGames();
                    player2Data.resetGames();
                }

                if (player1Data.getSetsThisMatch() == 3 || player2Data.getSetsThisMatch() == 3)
                {
                    player1NameTextView.setEnabled(false);
                    player2NameTextView.setEnabled(false);
                }

                updateScreen();
            }
        });



    }

}
