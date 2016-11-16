package com.example.tom.iscore;



/**
 * Created by Tom on 13/10/2016.
 */

public class PlayerData {
    public PlayerData(){
    }

    private String[] pointsValues = {"0", "15", "30", "40", "game", "deuce", "ad", "game"};

    //Scoring attributes
    private int pointsThisGame = 0;
    private int gamesThisSet = 0;
    private int setsThisMatch = 0;


    //These are used to store the score of the other player
    private int opponentsPointsThisGame = 0;
    private int opponentsGamesThisSet = 0;
    private int opponentsSetsThisMatch = 0;




    /*
    These setters are used so that each time the scorePoint function is called the instance of
    PlayerData know the score of the other player
     */
    public void setOpponentsPointsThisGame(int _opponentsPointsThisGame)
    {
        this.opponentsPointsThisGame = _opponentsPointsThisGame;
    }

    public void setOpponentsGamesThisSet(int _opponentsGamesThisSet)
    {
        this.opponentsGamesThisSet = _opponentsGamesThisSet;
    }

    public void setOpponentsSetsThisMatch(int _opponentsSetsThisMatch)
    {
        this.opponentsSetsThisMatch = _opponentsSetsThisMatch;
    }



    public boolean hasWonGame()
    {
        /*
        Function to check if the player has won the game.
        4 points means score didn't reach deuce, 7 means they
        won from advantage.
         */
        if ((this.pointsThisGame == 4) || (this.pointsThisGame == 7))
        {return true;}
        else
        {return false;}
    }

    public boolean hasWonSet()
    {
        /*
        Function to check if player has won the set.
        To win a set they must have 6 or more games and be 2
        or more above their opponent.
         */
        if ((this.gamesThisSet >=6) && ((this.opponentsGamesThisSet+2) <= this.gamesThisSet))
        {return true;}
        else
        {return false;}
    }

    public boolean hasWonMatch()
    {
        /*
        Function to check if player has won match.
         */
        if (this.setsThisMatch == 3)
        {return true;}
        else
        {return false;}
    }

    public boolean hasLostGame()
    {
        /*
        Function to check if player has lost a game.
        This is to be used to reset the points of both players
        at the end of a game.
         */
       if ( (this.opponentsPointsThisGame == 4) || (this.opponentsPointsThisGame == 7))
       {
           return true;
       }
        else
       {
           return false;
       }
    }

    public boolean hasLostSet()
    {
        if ( (this.opponentsGamesThisSet >= 6) && ((this.gamesThisSet+2) <= this.opponentsGamesThisSet) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isDeuce()
    {
        /*
        Function to check if player has lost a set.
        This is to be used to reset the points and games of both players at
        the end of a set.
         */
        if ((this.pointsThisGame == 3) && (this.opponentsPointsThisGame == 3))
        {return true;}
        else
        {return false;}
    }



    public void scorePoint()
    {
        /*
        Function to score a point for the player.
        If they have also won a game and/ or a set the
        appropriate function will be called from within scorePoint.
         */

        //Score the point
        this.pointsThisGame += 1;

        //Check if the player has won the game
        if (hasWonGame())
        {
            //If they have won the game, score a game.
            scoreGame();
            return;
        }

        //Check if the score is 40-40.
        if (isDeuce())
        {
            //If it is set the score to deuce.
            this.pointsThisGame = 5;
            return;
        }

        //If score is Deuce or advantage.
        if (this.pointsThisGame > 4)
        {
            //if opponent has advantage.
            if (this.opponentsPointsThisGame == 6)
            {
                /*
                Score stays at 40
                Opponent will also be set back to 40
                So that score is deuce
                */
                this.pointsThisGame = 5;
                return;
            }
            //Opponent has deuce.
            else
            {
                //Score moves to advantage
                this.pointsThisGame = 6;
            }
        }
    }

    public void losePoint()
    {

        /*
        Function to handle a player loosing a point, in stage 2 it is only used
        to make sure that if a player loses the point at advantage they are reset
        to deuce.
         */

        if(isDeuce() || this.opponentsPointsThisGame == 5)
        {
            this.pointsThisGame = 5;
            return;
        }

        //If player has advantage
        //Reset score to deuce
        if (this.pointsThisGame == 6)
        {
            this.pointsThisGame = 5;
        }

        if (hasLostGame())
        {
            loseGame();
            return;
        }
    }


    public void scoreGame()
    {
        /*
        Function to score a game.
        If the player has won the set then the ppropriate funtion will be called.
         */

        //Score the game
        this.gamesThisSet += 1;

        //If won the set score a set
        if (hasWonSet() == true)
        {
            scoreSet();
            return;
        }
    }

    public void loseGame()
    {
        /*
        Function to handle player losing a game.
         */
        if (hasLostSet() == true)
        {
            loseSet();
            return;
        }
    }


    public void scoreSet()
    {
        /*
        Function to handle player winning a set.
         */
        this.setsThisMatch += 1;
        if (hasWonMatch() == true)
        {
            //Do something here to end the match
            //Buttons will need to be disabled and data later saved to database
            return;
        }
    }

    public void loseSet()
    {
        return;
    }

    public void resetPoints()
    {
        this.pointsThisGame = 0;
    }

    public void resetGames()
    {
        this.gamesThisSet = 0;
    }

    /*
    Getters for the attributes that need to be accesed from outside the class.
     */
    public int getPointsThisGame()
    {
        return this.pointsThisGame;
    }
    public int getGamesThisSet() { return this.gamesThisSet; }
    public int getSetsThisMatch() { return this.setsThisMatch; }

}
