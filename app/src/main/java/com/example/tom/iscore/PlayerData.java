package com.example.tom.iscore;


import java.lang.reflect.Field;

/**
 * Created by Tom on 13/10/2016.
 */

public class PlayerData {
    public PlayerData(){
    }

    private int playerID;
    private String playerName;
    private int playerAge;
    private String playerGender;
    private String playerHand;

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerAge(int playerAge) {
        this.playerAge = playerAge;
    }

    public void setPlayerGender(String playerGender) {
        this.playerGender = playerGender;
    }

    public void setPlayerHand(String playerHand) {
        this.playerHand = playerHand;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public String getPlayerGender() {
        return playerGender;
    }

    public String getPlayerHand() {
        return playerHand;
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
    private int opponentServetype = 0;

    private boolean isServing = false;
    private int serveType = 0;

    //Statistical attributes
    private int totalPointsWon = 0;
    private int totalPointsPlayed = 0;
    private int totalGamesWon = 0;
    private int totalGamesPlayed = 0;
    private int totalSetsPlayed = 0;

    private int firstServePointsPlayed = 0;
    private int firstServePointsWon = 0;
    private int secondServePointsPlayed = 0;
    private int secondServePointsWon = 0;
    private int receivingFirstServePointsPlayed = 0;
    private int receivingFirstServePointsWon = 0;
    private int receivingSecondServePointsPlayed = 0;
    private int receivingSecondServePointsWon = 0;

    private int breakPointChances = 0;
    private int breakPointsConverted = 0;
    private int breakPointsAgainst = 0;
    private int breakPointsSaved = 0;

    private int currentTieBreakpoints = 0;
    private int tieBreaksPlayed = 0;
    private int tieBreaksWon = 0;
    private int tieBreakPointsPlayed = 0;
    private int tieBreakPointsWon = 0;

    private int deucePointsPlayed = 0;
    private int deucePointsWon = 0;
    private int advantagePointsPlayed = 0;
    private int advantagePointsWon = 0;

    private int totalAces = 0;
    private int totalFaults = 0;
    private int totalDoubleFaults = 0;
    private int totalUnforcedErrors = 0;
    private int totalForcedErrors = 0;
    private int totalWinners = 0;
    private int totalForehandWinners = 0;
    private int totalBackhandWinners = 0;
    private int totalVolleyWinners = 0;
    private int totalSmashWinners = 0;
    private int totalDropShotWinners = 0;
    private int totalDriveVolleyWinners = 0;
    private int totalHalfVolleyWinners = 0;
    private int totalLobWinners = 0;
    private int totalReturnerServeWinners = 0;


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

    public void setOpponentServetype(int _opponentServeType)
    {
        this.opponentServetype = _opponentServeType;
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
        /*
        Function to check if player has lost a set.
        */
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
        this.totalPointsWon += 1;
        this.totalPointsPlayed += 1;

        if (isServing) //Player is serving
        {
            if (serveType == 1) //First serve
            {
                this.firstServePointsWon += 1;
                this.firstServePointsPlayed += 1;
            }
            else //Second serve
            {
                this.secondServePointsWon += 1;
                this.secondServePointsPlayed += 1;
            }
        }
        else //Opponent is serving
        {
            if (opponentServetype == 1) //Opponent's first serve
            {
                this.receivingFirstServePointsWon += 1;
                this.receivingFirstServePointsPlayed += 1;
            }
            else //Opponent's second serve
            {
                this.receivingSecondServePointsWon += 1;
                this.receivingFirstServePointsPlayed += 1;
            }
        }

        //Check if the player has won the game
        if (hasWonGame())
        {
            if (!isServing)
            {
                this.breakPointsConverted += 1;
            }
            //If they have won the game, score a game.
            scoreGame();
            return;
        }

        //Check if the score is 40-40.
        if (isDeuce())
        {
            //If it is set the score to deuce.
            this.pointsThisGame = 5;
            this.deucePointsPlayed += 1;
            return;
        }

        //If score is Deuce or advantage.
        if (this.pointsThisGame > 4)
        {
            //if opponent has advantage.
            if (this.opponentsPointsThisGame == 6)
            {
                //Score stays at 40
                //Opponent will also be set back to 40
                //So that score is deuce
                this.pointsThisGame = 5;
                if (isServing)
                {
                    this.breakPointsSaved += 1;
                }
                return;
            }
            //Score is deuce
            else
            {
                //Score moves to advantage
                this.pointsThisGame = 6;
                if (!isServing)
                {
                    breakPointChances += 1;
                }
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

        this.totalPointsPlayed += 1;

        //if the score is deuce
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

        //if opponent has advantage or 40 (and score isn't deuce)
        if ((opponentsPointsThisGame == 6 && isServing) || (opponentsPointsThisGame == 3 && !isDeuce()))
        {
            this.breakPointsAgainst += 1;
        }

        //If player has lost the game
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
        If the player has won the set then the appropriate function will be called.
         */

        //Score the game
        this.gamesThisSet += 1;

        this.totalGamesWon += 1;
        this.totalGamesPlayed += 1;

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

        this.totalGamesPlayed += 1;

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

        this.totalSetsPlayed += 1;

        if (hasWonMatch() == true)
        {
            //Do something here to end the match
            //Buttons will need to be disabled and data later saved to database
            return;
        }
    }

    public void loseSet()
    {
        this.totalSetsPlayed += 1;
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
    public int getServeType() { return this.serveType; }


    /*
    Statistical methods that will update the relevant statistical attributes,
    and then call any scoring methods.
     */

    public void scoreAce()
    {
        this.totalAces += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void scoreForehandWinner()
    {
        this.totalForehandWinners += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void scoreBackhandWinner()
    {
        this.totalBackhandWinners += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void scoreVolleyWinner()
    {
        this.totalVolleyWinners += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void scoreDriveVolleyWinner()
    {
        this.totalDriveVolleyWinners += 1;
        scoreVolleyWinner();
    }

    public void scoreHalfVolleyWinner()
    {
        this.totalHalfVolleyWinners += 1;
        scoreVolleyWinner();
    }

    public void scoreSmashWinner()
    {
        this.totalSmashWinners += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void scoreDropShotWinner()
    {
        this.totalDropShotWinners += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void scoreLobWinner()
    {
        this.totalLobWinners += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void scoreReturnerServeWinner()
    {
        this.totalReturnerServeWinners += 1;
        this.totalWinners += 1;
        scorePoint();
    }

    public void recordFault()
    {
        this.totalFaults += 1;
    }

    public void recordDoubleFault()
    {
        this.totalDoubleFaults += 1;
    }

    public void recordUnforcedError()
    {
        this.totalUnforcedErrors += 1;
    }

    public void recordForcedError()
    {
        this.totalForcedErrors += 1;
    }


    public void switchServer()
    {
        /*
        Method that changes the server.
         */
        this.isServing = !isServing;
    }

    public int getTotalReturnerServeWinners() {
        return totalReturnerServeWinners;
    }

    public int getTotalLobWinners() {
        return totalLobWinners;
    }

    public int getTotalHalfVolleyWinners() {
        return totalHalfVolleyWinners;
    }

    public int getTotalDriveVolleyWinners() {
        return totalDriveVolleyWinners;
    }

    public int getTotalDropShotWinners() {
        return totalDropShotWinners;
    }

    public int getTotalSmashWinners() {
        return totalSmashWinners;
    }

    public int getTotalVolleyWinners() {
        return totalVolleyWinners;
    }

    public int getTotalBackhandWinners() {
        return totalBackhandWinners;
    }

    public int getTotalForehandWinners() {
        return totalForehandWinners;
    }

    public int getTotalWinners() {
        return totalWinners;
    }

    public int getTotalForcedErrors() {
        return totalForcedErrors;
    }

    public int getTotalUnforcedErrors() {
        return totalUnforcedErrors;
    }

    public int getTotalDoubleFaults() {
        return totalDoubleFaults;
    }

    public int getTotalFaults() {
        return totalFaults;
    }

    public int getTotalAces() {
        return totalAces;
    }

    public int getAdvantagePointsWon() {
        return advantagePointsWon;
    }

    public int getAdvantagePointsPlayed() {
        return advantagePointsPlayed;
    }

    public int getDeucePointsWon() {
        return deucePointsWon;
    }

    public int getDeucePointsPlayed() {
        return deucePointsPlayed;
    }

    public int getTieBreakPointsWon() {
        return tieBreakPointsWon;
    }

    public int getTieBreakPointsPlayed() {
        return tieBreakPointsPlayed;
    }

    public int getTieBreaksWon() {
        return tieBreaksWon;
    }

    public int getTieBreaksPlayed() {
        return tieBreaksPlayed;
    }


    public int getBreakPointsSaved() {
        return breakPointsSaved;
    }

    public int getBreakPointsAgainst() {
        return breakPointsAgainst;
    }

    public int getBreakPointsConverted() {
        return breakPointsConverted;
    }

    public int getReceivingSecondServePointsWon() {
        return receivingSecondServePointsWon;
    }

    public int getBreakPointChances() {
        return breakPointChances;
    }

    public int getReceivingSecondServePointsPlayed() {
        return receivingSecondServePointsPlayed;
    }

    public int getReceivingFirstServePointsWon() {
        return receivingFirstServePointsWon;
    }

    public int getReceivingFirstServePointsPlayed() {
        return receivingFirstServePointsPlayed;
    }

    public int getSecondServePointsWon() {
        return secondServePointsWon;
    }

    public int getSecondServePointsPlayed() {
        return secondServePointsPlayed;
    }

    public int getFirstServePointsWon() {
        return firstServePointsWon;
    }

    public int getFirstServePointsPlayed() {
        return firstServePointsPlayed;
    }

    public int getTotalSetsPlayed() {
        return totalSetsPlayed;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getTotalGamesWon() {
        return totalGamesWon;
    }

    public int getTotalPointsPlayed() {
        return totalPointsPlayed;
    }

    public int getTotalPointsWon() {
        return totalPointsWon;
    }


    public void setSetsThisMatch(int sets)
    {
        this.setsThisMatch = sets;
    }
    public void setTotalSetsPlayed(int setsPlayed)
    {
        this.totalSetsPlayed = setsPlayed;
    }

    public void setTotalPointsWon(int totalPointsWon) {
        this.totalPointsWon = totalPointsWon;
    }

    public void setTotalPointsPlayed(int totalPointsPlayed) {
        this.totalPointsPlayed = totalPointsPlayed;
    }

    public void setTotalGamesWon(int totalGamesWon) {
        this.totalGamesWon = totalGamesWon;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public void setFirstServePointsPlayed(int firstServePointsPlayed) {
        this.firstServePointsPlayed = firstServePointsPlayed;
    }

    public void setFirstServePointsWon(int firstServePointsWon) {
        this.firstServePointsWon = firstServePointsWon;
    }

    public void setSecondServePointsPlayed(int secondServePointsPlayed) {
        this.secondServePointsPlayed = secondServePointsPlayed;
    }

    public void setSecondServePointsWon(int secondServePointsWon) {
        this.secondServePointsWon = secondServePointsWon;
    }

    public void setReceivingFirstServePointsPlayed(int receivingFirstServePointsPlayed) {
        this.receivingFirstServePointsPlayed = receivingFirstServePointsPlayed;
    }

    public void setReceivingFirstServePointsWon(int receivingFirstServePointsWon) {
        this.receivingFirstServePointsWon = receivingFirstServePointsWon;
    }

    public void setReceivingSecondServePointsPlayed(int receivingSecondServePointsPlayed) {
        this.receivingSecondServePointsPlayed = receivingSecondServePointsPlayed;
    }

    public void setReceivingSecondServePointsWon(int receivingSecondServePointsWon) {
        this.receivingSecondServePointsWon = receivingSecondServePointsWon;
    }

    public void setBreakPointChances(int breakPointChances) {
        this.breakPointChances = breakPointChances;
    }

    public void setBreakPointsConverted(int breakPointsConverted) {
        this.breakPointsConverted = breakPointsConverted;
    }

    public void setBreakPointsAgainst(int breakPointsAgainst) {
        this.breakPointsAgainst = breakPointsAgainst;
    }

    public void setBreakPointsSaved(int breakPointsSaved) {
        this.breakPointsSaved = breakPointsSaved;
    }

    public void setCurrentTieBreakpoints(int currentTieBreakpoints) {
        this.currentTieBreakpoints = currentTieBreakpoints;
    }

    public void setTieBreaksPlayed(int tieBreaksPlayed) {
        this.tieBreaksPlayed = tieBreaksPlayed;
    }

    public void setTieBreaksWon(int tieBreaksWon) {
        this.tieBreaksWon = tieBreaksWon;
    }

    public void setTieBreakPointsPlayed(int tieBreakPointsPlayed) {
        this.tieBreakPointsPlayed = tieBreakPointsPlayed;
    }

    public void setTieBreakPointsWon(int tieBreakPointsWon) {
        this.tieBreakPointsWon = tieBreakPointsWon;
    }

    public void setDeucePointsPlayed(int deucePointsPlayed) {
        this.deucePointsPlayed = deucePointsPlayed;
    }

    public void setDeucePointsWon(int deucePointsWon) {
        this.deucePointsWon = deucePointsWon;
    }

    public void setAdvantagePointsPlayed(int advantagePointsPlayed) {
        this.advantagePointsPlayed = advantagePointsPlayed;
    }

    public void setAdvantagePointsWon(int advantagePointsWon) {
        this.advantagePointsWon = advantagePointsWon;
    }

    public void setTotalAces(int totalAces) {
        this.totalAces = totalAces;
    }

    public void setTotalFaults(int totalFaults) {
        this.totalFaults = totalFaults;
    }

    public void setTotalDoubleFaults(int totalDoubleFaults) {
        this.totalDoubleFaults = totalDoubleFaults;
    }

    public void setTotalUnforcedErrors(int totalUnforcedErrors) {
        this.totalUnforcedErrors = totalUnforcedErrors;
    }

    public void setTotalForcedErrors(int totalForcedErrors) {
        this.totalForcedErrors = totalForcedErrors;
    }

    public void setTotalWinners(int totalWinners) {
        this.totalWinners = totalWinners;
    }

    public void setTotalForehandWinners(int totalForehandWinners) {
        this.totalForehandWinners = totalForehandWinners;
    }

    public void setTotalBackhandWinners(int totalBackhandWinners) {
        this.totalBackhandWinners = totalBackhandWinners;
    }

    public void setTotalVolleyWinners(int totalVolleyWinners) {
        this.totalVolleyWinners = totalVolleyWinners;
    }

    public void setTotalSmashWinners(int totalSmashWinners) {
        this.totalSmashWinners = totalSmashWinners;
    }

    public void setTotalDropShotWinners(int totalDropShotWinners) {
        this.totalDropShotWinners = totalDropShotWinners;
    }

    public void setTotalDriveVolleyWinners(int totalDriveVolleyWinners) {
        this.totalDriveVolleyWinners = totalDriveVolleyWinners;
    }

    public void setTotalHalfVolleyWinners(int totalHalfVolleyWinners) {
        this.totalHalfVolleyWinners = totalHalfVolleyWinners;
    }

    public void setTotalLobWinners(int totalLobWinners) {
        this.totalLobWinners = totalLobWinners;
    }

    public void setTotalReturnerServeWinners(int totalReturnerServeWinners) {
        this.totalReturnerServeWinners = totalReturnerServeWinners;
    }


    public String dataToString()
    {
        //Define a string constant for a new line
        String newLine = System.getProperty("line.separator");

        //get the names of all the attributes of the class
        Field[] fields = this.getClass().getDeclaredFields();

        //initialise the string to hold all the data
        String string = "";

        //Iterate through all of the attributes of the class
        for ( Field field: fields )
        {
            /*As long as the attributes is accessible, i.e. it isaccessiblee to developers and not
            just used by the compiler.
             */
            try
            {
                //The attributes name is concatinated with its value and added to the string
                string += field.getName() ;
                string += ": " + field.get(this);
                string += newLine;
            }
            catch (IllegalAccessException e) {
                //This stops the app crashing if there is an attribute that cant be accessed and
                //would not be viewable for a end user.
                e.printStackTrace();
            }
        }
        //Return the string containing all of the data
        return string;
    }
}