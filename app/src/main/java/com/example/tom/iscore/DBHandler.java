package com.example.tom.iscore;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tom on 18/11/2016.
 */

public class DBHandler extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "iScoreDB.db";

    //Table names
    private static final String TABLE_PLAYERS = "Playertbl";
    private static final String TABLE_MATCH_DATA = "Playertbl";

    //PlayerTbl columns
    private static final String KEY_PLAYER_NAME = "PlayerName";
    private static final String KEY_PLAYER_AGE = "PlayerAge";
    private static final String KEY_PLAYER_GENDER = "PlayerGender";
    private static final String KEY_PLAYER_HAND = "PlayerHand";


    //MatchDataTbl columns


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS PlayerTbl ("
                + "PlayerID INT NOT NULL AUTO-INCREMENT,"
                + "PlayerName VARCHAR(100) NOT NULL,"
                + "playerAge TINYINT NOT NULL,"
                + "PlayerGender VARCHAR(100),"
                + "PlayerHand CHAR(1) NOT NULL,"
                + "PRIMARY KEY(ID)"; //Defines primary key

        String CREATE_MATCH_DATA_TABLE = "CREATE TABLE IF NOT EXISTS MatchDataTbl("
                + "MatchID INT NOT NULL,"
                + "PlayerID INT NOT NULL,"
                + "TotalPointsWon INT NOT NULL, "
                + "totalPointsPlayed INT NOT NULL, "
                + "totalGamesWon INT NOT NULL, "
                + "totalGamesPlayed INT NOT NULL, "
                + "totalSetsPlayed INT NOT NULL, "
                + "firstServePointsPlayed INT NOT NULL, "
                + "secondServePointsPlayed INT NOT NULL, "
                + "firstServePointsWon INT NOT NULL, "
                + "secondServePointsWon INT NOT NULL, "
                + "receivingFirstServePointsPlayed INT NOT NULL, "
                + "receivingFirstServePointsWon INT NOT NULL, "
                + "receivingSecondServePointsPlayed INT NOT NULL, "
                + "receivingSecondServePointsWon INT NOT NULL, "
                + "breakPointChances INT NOT NULL, "
                + "breakPointsConverted INT NOT NULL, "
                + "breakPointsAgainst INT NOT NULL, "
                + "breakPointsSaved INT NOT NULL, "
                + "tieBreaksPlayed INT NOT NULL, "
                + "tieBreaksWon INT NOT NULL, "
                + "tieBreakPointsWon INT NOT NULL, "
                + "tieBreakPointsPlayed INT NOT NULL, "
                + "deucePointsWon INT NOT NULL, "
                + "deucePointsPlayed INT NOT NULL, "
                + "advantagePointsWon INT NOT NULL, "
                + "advantagePointsPlayed INT NOT NULL, "
                + "totalAces INT NOT NULL, "
                + "totalFaults INT NOT NULL, "
                + "totalDoubleFaults INT NOT NULL, "
                + "totalUnforcedError INT NOT NULL, "
                + "totalForcedError INT NOT NULL, "
                + "totalWinners INT NOT NULL, "
                + "totalForehandWinners INT NOT NULL, "
                + "totalBackhandWinners INT NOT NULL, "
                + "totalVolleyWinners INT NOT NULL, "
                + "totalSmashWinners INT NOT NULL, "
                + "totalDropshotWinners INT NOT NULL, "
                + "totalLobWinners INT NOT NULL, "
                + "totalReturnerServeWinners INT NOT NULL, "
                + "totalDriveVolleyWinners INT NOT NULL, "
                + "totalHalfVolleyWinners INT NOT NULL, "
                + "PRIMARY KEY (MatchID, PlayerID)," //Defines primary keys
                + "FOREIGN KEY (PlayerID) REFERENCES PlayerTbl(PlayerID)"; //Defines foreign key and tells which table it came from.

        //Create tables
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_MATCH_DATA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
        data.put("PlayerID", playerID);

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


    public void addPlayer(String playerName,
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

        db.insert(TABLE_PLAYERS, null, info); //Insert row
        db.close(); //Close database
    }


}

