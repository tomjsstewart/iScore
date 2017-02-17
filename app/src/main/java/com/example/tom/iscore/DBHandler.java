package com.example.tom.iscore;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 18/11/2016.
 */

public class DBHandler extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "iScoreDB.db";
    private static final int DATABASE_VERSION = 1;

    //Table names
    private static final String TABLE_PLAYERS = "PlayerTbl";
    private static final String TABLE_MATCH_DATA = "MatchDataTbl";

    //PlayerTbl columns
    public static final String KEY_PLAYER_NAME = "PlayerName";
    public static final String KEY_PLAYER_AGE = "PlayerAge";
    public static final String KEY_PLAYER_GENDER = "PlayerGender";
    public static final String KEY_PLAYER_HAND = "PlayerHand";


    //MatchDataTbl columns


    public DBHandler(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /*
        Function to create the tables to store player infomation and the match data generated
         */
        String CREATE_PLAYER_TABLE = "CREATE TABLE PlayerTbl ("
                + "_id INTEGER primary key AUTOINCREMENT, "
                + "PlayerName VARCHAR(100) NOT NULL, "
                + "playerAge INTEGER NOT NULL, "
                + "PlayerGender TEXT, "
                + "PlayerHand TEXT NOT NULL);";

        String CREATE_MATCH_DATA_TABLE = "CREATE TABLE IF NOT EXISTS MatchDataTbl("
                + "MatchID INTEGER NOT NULL, "
                + "_id INTEGER NOT NULL, "
                + "opp_Name VARCHAR(100) NOT NULL, "
                + "score TEXT NOT NULL, "
                + "matchDate TEXT NOT NULL, "
                + "TotalPointsWon INTEGER NOT NULL, "
                + "totalPointsPlayed INTEGER NOT NULL, "
                + "totalGamesWon INTEGER NOT NULL, "
                + "totalGamesPlayed INTEGER NOT NULL, "
                + "totalSetsWon INTEGER NOT NULL, "
                + "totalSetsPlayed INTEGER NOT NULL, "
                + "firstServePointsPlayed INTEGER NOT NULL, "
                + "secondServePointsPlayed INTEGER NOT NULL, "
                + "firstServePointsWon INTEGER NOT NULL, "
                + "secondServePointsWon INTEGER NOT NULL, "
                + "receivingFirstServePointsPlayed INTEGER NOT NULL, "
                + "receivingFirstServePointsWon INTEGER NOT NULL, "
                + "receivingSecondServePointsPlayed INTEGER NOT NULL, "
                + "receivingSecondServePointsWon INTEGER NOT NULL, "
                + "breakPointChances INTEGER NOT NULL, "
                + "breakPointsConverted INTEGER NOT NULL, "
                + "breakPointsAgainst INTEGER NOT NULL, "
                + "breakPointsSaved INTEGER NOT NULL, "
                + "tieBreaksPlayed INTEGER NOT NULL, "
                + "tieBreaksWon INTEGER NOT NULL, "
                + "tieBreakPointsWon INTEGER NOT NULL, "
                + "tieBreakPointsPlayed INTEGER NOT NULL, "
                + "deucePointsWon INTEGER NOT NULL, "
                + "deucePointsPlayed INTEGER NOT NULL, "
                + "advantagePointsWon INTEGER NOT NULL, "
                + "advantagePointsPlayed INTEGER NOT NULL, "
                + "totalAces INTEGER NOT NULL, "
                + "totalFaults INTEGER NOT NULL, "
                + "totalDoubleFaults INTEGER NOT NULL, "
                + "totalUnforcedError INTEGER NOT NULL, "
                + "totalForcedError INTEGER NOT NULL, "
                + "totalWinners INTEGER NOT NULL, "
                + "totalForehandWinners INTEGER NOT NULL, "
                + "totalBackhandWinners INTEGER NOT NULL, "
                + "totalVolleyWinners INTEGER NOT NULL, "
                + "totalSmashWinners INTEGER NOT NULL, "
                + "totalDropshotWinners INTEGER NOT NULL, "
                + "totalLobWinners INTEGER NOT NULL, "
                + "totalReturnerServeWinners INTEGER NOT NULL, "
                + "totalDriveVolleyWinners INTEGER NOT NULL, "
                + "totalHalfVolleyWinners INTEGER NOT NULL, "
                + "PRIMARY KEY (MatchID, _id), "
                + "FOREIGN KEY (_id) REFERENCES PlayerTbl(_id));"; //Defines foreign key and tells which table it came from.

        //Create tables
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_MATCH_DATA_TABLE);
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH_DATA);
        // Create tables again
        onCreate(db);
    }

    public void deleteForTest()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Drop older tables if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH_DATA);
        // Create tables again
        onCreate(db);
    }


    public boolean saveMatch(int matchID, int playerID, String opp_Name, String score, String date, PlayerData DataClass)
    {
        /*
        Function to save any matches that are completed.
        Data is read directly from  the PlayerData class instance for the player's data being saved.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("MatchID", matchID);
        data.put("_id", playerID);
        data.put("opp_Name", opp_Name);

        data.put("score", score);
        data.put("matchDate", date);

        data.put("TotalPointsWon", DataClass.getTotalPointsWon());
        data.put("totalPointsPlayed", DataClass.getTotalPointsPlayed());
        data.put("totalGamesWon", DataClass.getTotalGamesWon());
        data.put("totalGamesPlayed", DataClass.getTotalGamesPlayed());
        data.put("totalSetsWon", DataClass.getSetsThisMatch());
        data.put("totalSetsPlayed", DataClass.getTotalSetsPlayed());
        data.put("firstServePointsPlayed", DataClass.getFirstServePointsPlayed());
        data.put("secondServePointsPlayed", DataClass.getSecondServePointsPlayed());
        data.put("firstServePointsWon", DataClass.getFirstServePointsWon());
        data.put("secondServePointsWon", DataClass.getSecondServePointsWon());
        data.put("receivingFirstServePointsPlayed", DataClass.getReceivingFirstServePointsPlayed());
        data.put("receivingFirstServePointsWon", DataClass.getReceivingFirstServePointsWon());
        data.put("receivingSecondServePointsPlayed", DataClass.getReceivingSecondServePointsPlayed());
        data.put("receivingSecondServePointsWon", DataClass.getReceivingSecondServePointsWon());
        data.put("breakPointChances", DataClass.getBreakPointChances());
        data.put("breakPointsConverted", DataClass.getBreakPointsConverted());
        data.put("breakPointsAgainst", DataClass.getBreakPointsAgainst());
        data.put("breakPointsSaved", DataClass.getBreakPointsSaved());
        data.put("tieBreaksPlayed", DataClass.getTieBreaksPlayed());
        data.put("tieBreaksWon", DataClass.getTieBreaksWon());
        data.put("tieBreakPointsWon", DataClass.getTieBreakPointsWon());
        data.put("tieBreakPointsPlayed", DataClass.getTieBreakPointsPlayed());
        data.put("deucePointsWon", DataClass.getDeucePointsWon());
        data.put("deucePointsPlayed", DataClass.getDeucePointsPlayed());
        data.put("advantagePointsWon", DataClass.getAdvantagePointsWon());
        data.put("advantagePointsPlayed", DataClass.getAdvantagePointsPlayed());
        data.put("totalAces", DataClass.getTotalAces());
        data.put("totalFaults", DataClass.getTotalFaults());
        data.put("totalDoubleFaults", DataClass.getTotalDoubleFaults());
        data.put("totalUnforcedError", DataClass.getTotalUnforcedErrors());
        data.put("totalForcedError", DataClass.getTotalForcedErrors());
        data.put("totalWinners", DataClass.getTotalWinners());
        data.put("totalForehandWinners", DataClass.getTotalForehandWinners());
        data.put("totalBackhandWinners", DataClass.getTotalBackhandWinners());
        data.put("totalVolleyWinners", DataClass.getTotalVolleyWinners());
        data.put("totalSmashWinners", DataClass.getTotalSmashWinners());
        data.put("totalDropshotWinners", DataClass.getTotalDropShotWinners());
        data.put("totalLobWinners", DataClass.getTotalLobWinners());
        data.put("totalReturnerServeWinners", DataClass.getTotalReturnerServeWinners());
        data.put("totalDriveVolleyWinners", DataClass.getTotalDriveVolleyWinners());
        data.put("totalHalfVolleyWinners", DataClass.getTotalHalfVolleyWinners());

        long success = db.insert(TABLE_MATCH_DATA, null, data);
        db.close();
        if (success != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public boolean addPlayer(String playerName,
                          int playerAge,
                          String playerGender,
                          String playerHand)

    {
        /*
        Function to save a players information in to the database, function assumes that all inputs
        it is given are valid, if an error occurs false is returned, if the data is correctly saved
        true is returned.
         */
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues info = new ContentValues();

        //Data to add
        info.put(KEY_PLAYER_NAME, playerName);
        info.put(KEY_PLAYER_AGE, playerAge);
        info.put(KEY_PLAYER_GENDER, playerGender);
        info.put(KEY_PLAYER_HAND, playerHand);

        long success = db.insert(TABLE_PLAYERS, null, info); //Insert row
        db.close(); //Close database
        if (success != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public Cursor getAllPlayersNamesCursor()
    {
        /*
        Function that returns a cursor that holds all of the players names and Id's
        This can then be queried.
         */
        //Open a database connection
        SQLiteDatabase db = this.getReadableDatabase();

        //Create a cursor which points to the player names
        Cursor mCursor = db.rawQuery("SELECT _id, " + KEY_PLAYER_NAME + " FROM PlayerTbl", null);

        //Check there is data
        if (mCursor != null)
        {
            //If there is data make sure the cursor points to the first item, so no one is missed off
            mCursor.moveToFirst();
        }
        db.close();
        return mCursor;
    }

    public Cursor getAllPlayersNamesExceptParmCursor(int id)
    {
        /*
        Function that returns a cursor that holds all of the players names and Id's, except that of
        the ID passed in as the parameter.
        This can then be queried.
         */
        SQLiteDatabase db = this.getReadableDatabase();

        //Return a cursor with all player id's except the one in the parameter
        Cursor mCursor = db.rawQuery("SELECT _id, " + KEY_PLAYER_NAME + " FROM PlayerTbl WHERE _id <> " + id, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        db.close();
        return mCursor;
    }


    public PlayerData getPlayerByID(int id)
    {
        /*
        Function to read all of the data for a specific player from the database.
        Data is returned as an instance of PlayerData
         */
        //Open connection
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the database to get the data for the player with the given ID
        Cursor cursor = db.rawQuery("SELECT * FROM PlayerTbl WHERE _id" + " = " + id , null);

        //Instantiate a new PlayerData that can be used to store the data from the database
        PlayerData player = new PlayerData();

        //Checks there is data
        if(cursor.moveToFirst())
        {
            //Set the relevant attributes within the PlayerData instance
            //Columns are zero-indexed and follow the order that the database was declared
            player.setPlayerID(Integer.parseInt(cursor.getString(0)));
            player.setPlayerName(cursor.getString(1));
            player.setPlayerAge(Integer.parseInt(cursor.getString(2)));
            player.setPlayerGender(cursor.getString(3));
            player.setPlayerHand(cursor.getString(4));


        }

        cursor.close();
        db.close();
        return player;
    }


    public int generateMatchID()
    {
        /*
        Function to get the next match id as it is repeated in the database, so cant be an auto-increment
         */

        //Open connection to the database
        SQLiteDatabase db = this.getReadableDatabase();

        //Query the database for all matches
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MATCH_DATA, null);

        /*
        Check there are matches, if there are move to last and return the match id + 1 as the new Id
        if there are no previous matches assume first match and return a match id of 1.
         */
        if (cursor.moveToLast())//If there is a last
        {
            //get the previous match ID, add one to get new match id, close database connection and
            //return match ID
            Integer matchId = (Integer.parseInt(cursor.getString(0)) + 1);
            db.close();
            return matchId;
        }
        else
        {
            //There are no previous matches so return 1 as a first match, close database connection
            //and return the match ID
            db.close();
            return 1;
        }
    }

    public Cursor getAllMatches()
    {
        /*
        Function to return a cursor containing all matches, which will be used by HistoryActivity
        to display all of the matches for the user to select one from.
         */

        //Open connection to the database
        SQLiteDatabase db = this.getReadableDatabase();

        //Query the database for all matches
        Cursor cursor = db.rawQuery("SELECT "
                + "PlayerTbl.PlayerName AS _id, MatchDataTbl.opp_Name AS Player2, MatchDataTbl.score AS Score, MatchDataTbl.matchDate "
                + "FROM PlayerTbl "
                + "JOIN MatchDataTbl ON PlayerTbl._id = MatchDataTbl._id "
                + "WHERE MatchDataTbl.ROWID % 2 = 1;", null);
        


        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public PlayerData setPlayerData(Cursor cursor)
    {
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        PlayerData player = new PlayerData();
        //Set the relevant attributes within the PlayerData instance
        //Columns are zero-indexed and follow the order that the database was declared
        player.setPlayerID(Integer.parseInt(cursor.getString(0)));

        player.setTotalPointsWon(Integer.parseInt(cursor.getString(5)));
        player.setTotalPointsPlayed(Integer.parseInt(cursor.getString(6)));
        player.setTotalGamesWon(Integer.parseInt(cursor.getString(7)));
        player.setTotalGamesPlayed(Integer.parseInt(cursor.getString(8)));
        player.setSetsThisMatch(Integer.parseInt(cursor.getString(9)));
        player.setTotalSetsPlayed(Integer.parseInt(cursor.getString(10)));

        player.setFirstServePointsPlayed(Integer.parseInt(cursor.getString(11)));
        player.setSecondServePointsPlayed(Integer.parseInt(cursor.getString(12)));
        player.setFirstServePointsWon(Integer.parseInt(cursor.getString(13)));
        player.setSecondServePointsWon(Integer.parseInt(cursor.getString(14)));

        player.setReceivingFirstServePointsPlayed(Integer.parseInt(cursor.getString(15)));
        player.setReceivingFirstServePointsWon(Integer.parseInt(cursor.getString(16)));
        player.setReceivingSecondServePointsPlayed(Integer.parseInt(cursor.getString(17)));
        player.setReceivingSecondServePointsWon(Integer.parseInt(cursor.getString(18)));

        player.setBreakPointChances(Integer.parseInt(cursor.getString(19)));
        player.setBreakPointsConverted(Integer.parseInt(cursor.getString(20)));
        player.setBreakPointsAgainst(Integer.parseInt(cursor.getString(21)));
        player.setBreakPointsSaved(Integer.parseInt(cursor.getString(22)));

        player.setTieBreaksPlayed(Integer.parseInt(cursor.getString(23)));
        player.setTieBreaksWon(Integer.parseInt(cursor.getString(24)));
        player.setTieBreakPointsWon(Integer.parseInt(cursor.getString(25)));
        player.setTieBreakPointsPlayed(Integer.parseInt(cursor.getString(26)));

        player.setDeucePointsWon(Integer.parseInt(cursor.getString(27)));
        player.setDeucePointsPlayed(Integer.parseInt(cursor.getString(28)));
        player.setAdvantagePointsWon(Integer.parseInt(cursor.getString(29)));
        player.setAdvantagePointsPlayed(Integer.parseInt(cursor.getString(30)));

        player.setTotalAces(Integer.parseInt(cursor.getString(31)));
        player.setTotalFaults(Integer.parseInt(cursor.getString(32)));
        player.setTotalDoubleFaults(Integer.parseInt(cursor.getString(33)));
        player.setTotalUnforcedErrors(Integer.parseInt(cursor.getString(34)));
        player.setTotalForcedErrors(Integer.parseInt(cursor.getString(35)));

        player.setTotalWinners(Integer.parseInt(cursor.getString(36)));
        player.setTotalForehandWinners(Integer.parseInt(cursor.getString(37)));
        player.setTotalBackhandWinners(Integer.parseInt(cursor.getString(38)));
        player.setTotalVolleyWinners(Integer.parseInt(cursor.getString(39)));
        player.setTotalSmashWinners(Integer.parseInt(cursor.getString(40)));
        player.setTotalDropShotWinners(Integer.parseInt(cursor.getString(41)));
        player.setTotalLobWinners(Integer.parseInt(cursor.getString(42)));
        player.setTotalReturnerServeWinners(Integer.parseInt(cursor.getString(43)));
        player.setTotalDriveVolleyWinners(Integer.parseInt(cursor.getString(44)));
        player.setTotalHalfVolleyWinners(Integer.parseInt(cursor.getString(45)));

        return player;
    }

    public List<PlayerData> getMatchData(int matchID) {


        //Open connection to database
        SQLiteDatabase db = this.getReadableDatabase();

        //List to hold the player data instances for both players
        List<PlayerData> players = new ArrayList<PlayerData>();

        //Select the data
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MATCH_DATA + " WHERE MatchID = " + matchID + ";", null);


        //Checks there is data
        if (cursor.moveToFirst()) {
            players.add(setPlayerData(cursor));
            cursor.moveToNext();
            players.add(setPlayerData(cursor));
        }

        db.close();
        return players;
    }


public void viewAll() {
    SQLiteDatabase db = this.getReadableDatabase();


    Cursor cursor1 = db.rawQuery("SELECT * FROM " + TABLE_MATCH_DATA + ";", null);
    Cursor cursor2 = db.rawQuery("SELECT * FROM " + TABLE_PLAYERS + ";", null);

    cursor1.moveToFirst();
    cursor2.moveToFirst();


    Log.v("Cursor Players Table", DatabaseUtils.dumpCursorToString(cursor2));
    Log.v("Cursor Match Data Tbl", DatabaseUtils.dumpCursorToString(cursor1));
    }

}