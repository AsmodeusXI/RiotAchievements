package com.samlawton.riotdemo.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.samlawton.riotdemo.achievements.Achievement;

public class Player {
	
	private String mUserName;
	private static final int NAME_IDX = 1;
	
	private long mUserCreateDate;
	private static final int CREATE_DATE_IDX = 2;
	
	private int mTotalGames;
	private static final int TOTAL_GAMES_IDX = 3;
	
	private int mTotalWins;
	private static final int TOTAL_WINS_IDX = 4;
	
	private int mTotalLosses;
	private static final int TOTAL_LOSSES_IDX = 5;
	
	private int mTotalAtkAttempts;
	private static final int TOTAL_ATK_ATTEMPTS_IDX = 6;
	
	private double mTotalHitNum;
	private static final int TOTAL_HIT_NUM_IDX = 7;
	
	private int mTotalDmg;
	private static final int TOTAL_DMG_IDX = 8;
	
	private int mTotalKills;
	private static final int TOTAL_KILLS_IDX = 9;
	
	private int mTotalFirstHitKills;
	private static final int TOTAL_FIRST_HIT_KILLS_IDX = 10;
	
	private int mTotalAssists;
	private static final int TOTAL_ASSISTS_IDX = 11;
	
	private int mTotalSpellsCast;
	private static final int TOTAL_SPELLS_CAST_IDX = 12;
	
	private int mTotalSpellDmg;
	private static final int TOTAL_SPELL_DMG_IDX = 13;
	
	private long mTotalPlayTime;
	private static final int TOTAL_PLAY_TIME_IDX = 14;
	
	private ArrayList<Achievement> mPlayerAchievements;
	
	public Player(String aUserName) {
		this(aUserName, null);
	}
	
	public Player(String aUserName, String[] aJDBCParams) {
		if(aUserName.contains(" ") || aUserName.contains(";") || aUserName.contains("/")) {
			System.out.println("Error! Player name is invalid!");
			System.exit(1);
		}
		
		mUserName = aUserName;
		try {
			if(aJDBCParams == null) {
				initializePlayerFromDB(aUserName);
			} else {
				if(!(aJDBCParams.length == 4)) {
					initializePlayerFromDB(aUserName);
				} else {
					initializePlayerFromDB(aUserName, aJDBCParams[0], aJDBCParams[1], aJDBCParams[2], aJDBCParams[3]);
				}
			}
		} catch (SQLException aEx) {
			System.out.println("Slight database issue... but no worries!");
			aEx.printStackTrace();
			initializePlayerWithoutDB();
		}
		
	}
	
	private void initializePlayerFromDB(String aUserName) throws SQLException {
		initializePlayerFromDB(aUserName, null, null, null, null);
	}
	
	private void initializePlayerFromDB(String aUserName, String aDriver, String aURL, String aUser, String aPass) throws SQLException {
		Connection connection = null;
		
		String currentDriver = aDriver == null ? Game.jdbcDriver : aDriver;
		String currentJDBCURL = aURL == null ? Game.jdbcString : aURL;
		String currentUser = aUser == null ? Game.jdbcUser : aUser;
		String currentPass = aPass == null ? Game.jdbcPass : aPass;
		
		try {
			
			Class.forName(currentDriver);
            
			// Default user of the HSQLDB is 'sa'
            // with an empty password
            connection = DriverManager.getConnection(currentJDBCURL, currentUser, currentPass);
            
            PreparedStatement playerStmt = connection.prepareStatement("select * from players where userName = '" + aUserName + "'");
            ResultSet playerRS = playerStmt.executeQuery();
            
            if(playerRS.next()) {
            	mUserCreateDate = playerRS.getLong(CREATE_DATE_IDX);
	            mTotalGames = playerRS.getInt(TOTAL_GAMES_IDX);
	            mTotalWins = playerRS.getInt(TOTAL_WINS_IDX);
	        	mTotalLosses = playerRS.getInt(TOTAL_LOSSES_IDX);
	        	mTotalAtkAttempts = playerRS.getInt(TOTAL_ATK_ATTEMPTS_IDX);
	        	mTotalHitNum = playerRS.getDouble(TOTAL_HIT_NUM_IDX);
	        	mTotalDmg = playerRS.getInt(TOTAL_DMG_IDX);
	        	mTotalKills = playerRS.getInt(TOTAL_KILLS_IDX);
	        	mTotalFirstHitKills = playerRS.getInt(TOTAL_FIRST_HIT_KILLS_IDX);
	        	mTotalAssists = playerRS.getInt(TOTAL_ASSISTS_IDX);
	        	mTotalSpellsCast = playerRS.getInt(TOTAL_SPELLS_CAST_IDX);
	        	mTotalSpellDmg = playerRS.getInt(TOTAL_SPELL_DMG_IDX);
	        	mTotalPlayTime = playerRS.getLong(TOTAL_PLAY_TIME_IDX);
            } else {
            	mUserCreateDate = System.currentTimeMillis();
            	mTotalGames = 0;
	            mTotalWins = 0;
	        	mTotalLosses = 0;
	        	mTotalAtkAttempts = 0;
	        	mTotalHitNum = 0.0;
	        	mTotalDmg = 0;
	        	mTotalKills = 0;
	        	mTotalFirstHitKills = 0;
	        	mTotalAssists = 0;
	        	mTotalSpellsCast = 0;
	        	mTotalSpellDmg = 0;
	        	mTotalPlayTime = 0;
	        	connection.prepareStatement("insert into players values ('" + aUserName + "','" +
	        			mUserCreateDate + "','" +
	        			mTotalGames + "','" +
	        			mTotalWins + "','" +
	        			mTotalLosses + "','" +
	        			mTotalAtkAttempts + "','" +
	        			mTotalHitNum + "','" +
	        			mTotalDmg + "','" +
	        			mTotalKills + "','" +
	        			mTotalFirstHitKills + "','" +
	        			mTotalAssists + "','" +
	        			mTotalSpellsCast + "','" +
	        			mTotalSpellDmg + "','" +
	        			mTotalPlayTime + "')").execute();
            }
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(connection != null) connection.close();
		}
	}
	
	private void initializePlayerWithoutDB() {
		mUserCreateDate = System.currentTimeMillis();
		mTotalGames = 0;
        mTotalWins = 0;
    	mTotalLosses = 0;
    	mTotalAtkAttempts = 0;
    	mTotalHitNum = 0.0;
    	mTotalDmg = 0;
    	mTotalKills = 0;
    	mTotalFirstHitKills = 0;
    	mTotalAssists = 0;
    	mTotalSpellsCast = 0;
    	mTotalSpellDmg = 0;
    	mTotalPlayTime = 0;
	}
	
	public long getUserCreateDate() {
		return mUserCreateDate;
	}
	
	public void updatePlayerDataInDB() {
		
	}

}
