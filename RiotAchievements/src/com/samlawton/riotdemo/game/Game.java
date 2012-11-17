package com.samlawton.riotdemo.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
	
	private ArrayList<Player> mPlayerList;
	private HashMap<Player, InGamePlayer> mInGameMap;
	private ArrayList<InGamePlayer> mBlueTeamPlayerList;
	private ArrayList<InGamePlayer> mPurpleTeamPlayerList;
	
	public static final int BLUE_TEAM_IDX = 1;
	public static final int PURPLE_TEAM_IDX = 2;
	
	public static final String jdbcDriver = "org.hsqldb.jdbcDriver";
	public static final String jdbcString = "jdbc:hsqldb:hsql://localhost/AchievementDB";
	public static final String jdbcUser = "sa";
	public static final String jdbcPass = "";
	public static final String dbName = "AchievementDB";
	
	private int mGameWinner = 0;
	private double mGameLength = 0.0;
	
	public Game(ArrayList<Player> aPlayerList) {
		mPlayerList = aPlayerList;
	}
	
	public void initPlayers() {
		mInGameMap = createInGamePlayers(mPlayerList);
		ArrayList<InGamePlayer> btpl = new ArrayList<InGamePlayer>();
		ArrayList<InGamePlayer> ptpl = new ArrayList<InGamePlayer>();
		for(int i = 0; i < mPlayerList.size(); i++) {
			Player currentPlayer = mPlayerList.get(i);
			InGamePlayer currentIGPlayer = mInGameMap.get(currentPlayer);
			if(i%2 == 0) {
				currentIGPlayer.setCurrentTeam(BLUE_TEAM_IDX);
				btpl.add(currentIGPlayer);
			} else {
				currentIGPlayer.setCurrentTeam(PURPLE_TEAM_IDX);
				ptpl.add(currentIGPlayer);
			}
		}
		mBlueTeamPlayerList = btpl;
		mPurpleTeamPlayerList = ptpl;
	}
	
	public void runTestGame() {
		mGameWinner = BLUE_TEAM_IDX;
		mGameLength = 10000;
		
		for(int i = 0; i < mBlueTeamPlayerList.size(); i++) {
			InGamePlayer blueTestPlayer = mBlueTeamPlayerList.get(i);
			setPlayerStats(blueTestPlayer, true);
		}
		
		for(int j = 0; j < mPurpleTeamPlayerList.size(); j++) {
			
		}
	}
	
	public int getPlayerTeam(Player aCurrentPlayer) {
		InGamePlayer currIGPlayer = mInGameMap.get(aCurrentPlayer);
		if(mBlueTeamPlayerList.contains(currIGPlayer)) {
			return BLUE_TEAM_IDX;
		} else if (mPurpleTeamPlayerList.contains(currIGPlayer)) {
			return PURPLE_TEAM_IDX;
		} else {
			System.err.println("This should never happen. Check logic.");
			return 0;
		}
	}
	
	public int getGameWinner() {
		return mGameWinner;
	}
	
	public double getGameLength() {
		return mGameLength;
	}
	
	private HashMap<Player, InGamePlayer> createInGamePlayers(ArrayList<Player> aPlayerList) {
		HashMap<Player,InGamePlayer> currentGamePlayers = new HashMap<Player,InGamePlayer>();
		for(int i = 0; i < aPlayerList.size(); i++) {
			InGamePlayer aOnePlayer = new InGamePlayer(aPlayerList.get(i),this);
			currentGamePlayers.put(aPlayerList.get(i), aOnePlayer);
		}
		return currentGamePlayers;
	}
	
	private void setPlayerStats(InGamePlayer aCurrentIGPlayer, boolean aIsWinner) {
		
	}

}
