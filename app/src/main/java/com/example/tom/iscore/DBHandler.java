package com.example.tom.iscore;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String TABLE_PLAYERS = "Playertbl";
    private static final String TABLE_MATCH_DATA = "Playertbl";

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
        String CREATE_PLAYER_TABLE = "CREATE TABLE PlayerTbl ("
                + "_id INTEGER primary key AUTOINCREMENT, "
                + "PlayerName VARCHAR(100) NOT NULL, "
                + "playerAge INTEGER NOT NULL, "
                + "PlayerGender TEXT, "
                + "PlayerHand TEXT NOT NULL);";

        String CREATE_MATCH_DATA_TABLE = "CREATE TABLE IF NOT EXISTS MatchDataTbl("
                + "MatchID INTEGER primary key NOT NULL,"
                + "_id INTEGER primary key NOT NULL,"
                + "TotalPointsWon INTEGER NOT NULL, "
                + "totalPointsPlayed INTEGER NOT NULL, "
                + "totalGamesWon INTEGER NOT NULL, "
                + "totalGamesPlayed INTEGER NOT NULL, "
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


    public void saveMatch(int matchID, int playerID, PlayerData DataClass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("MatchID", matchID);
        data.put("_id", playerID);

        data.put("TotalPointsWon", DataClass.getTotalPointsWon());
        data.put("totalPointsPlayed", DataClass.getTotalPointsPlayed());
        data.put("totalGamesWon", DataClass.getTotalGamesWon());
        data.put("totalGamesPlayed", DataClass.getTotalGamesPlayed());
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

        db.insert(TABLE_MATCH_DATA, null, data);
        db.close();
    }


    public boolean addPlayer(String playerName,
                          int playerAge,
                          String playerGender,
                          String playerHand)

    {
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT _id, " + KEY_PLAYER_NAME + " FROM PlayerTbl", null);

        /*
        Cursor mCursor = db.query(TABLE_PLAYERS, new String[] {"_id",
                        KEY_PLAYER_NAME, KEY_PLAYER_AGE, KEY_PLAYER_GENDER, KEY_PLAYER_HAND},
                null, null, null, null, null);
        */

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        db.close();
        return mCursor;
    }

    public Cursor getAllPlayersNamesExceptParmCursor(int id)
    {
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

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PlayerTbl WHERE _id" + " = " + id , null);

        PlayerData player = new PlayerData();

        //Checks there is data
        if(cursor.moveToFirst())
        {

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


}

