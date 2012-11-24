package com.samlawton.riotdemo.achievements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.samlawton.riotdemo.game.Game;
import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

/**
 * A class that takes in a recent Game and uses
 * its data to update the Stats and Achievements of
 * all the Players involved. This class calls all of
 * the update code for Players in a given Game.
 * @author Samuel H. Lawton
 *
 */
public class StatAchievementUpdater {
	
	private final Game mFinishedGame;
	
	/**
	 * Creates an Achievement & Stat Updater based on
	 * the recently finished Game.
	 * @param aGame The Game which has just concluded.
	 */
	public StatAchievementUpdater(Game aGame) {
		mFinishedGame = aGame;
	}
	
	/**
	 * A public method to update all stats and
	 * achievement scores from the most recently
	 * played Game.
	 * @param aJDBCParams
	 */
	public void updatesFromRecentGame(String[] aJDBCParams) {
		updateStats(aJDBCParams);
		updateAchievements(aJDBCParams);
		System.out.println();
	}
	
	/**
	 * Iterates through each player in the game and
	 * updates their statistics both in their objects
	 * and in the database.
	 * @param aJDBCParams The JDBC parameters required to add to the 
	 * database. If the String[] contains nulls, the defaults in Game.java
	 * are used.
	 */
	private void updateStats(String[] aJDBCParams) {
		HashMap<Player,InGamePlayer> gamePlayers = mFinishedGame.getInGameMap();
		for(Map.Entry<Player,InGamePlayer> entry : gamePlayers.entrySet()) {
			Player updatePlayer = entry.getKey();
			InGamePlayer lastGamePlayer = entry.getValue();
			updatePlayer.updatePlayerHistoricalStats(lastGamePlayer);
			try {
				updatePlayer.updatePlayerDataInDB(aJDBCParams);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Iterates through each player in the game and
	 * updates their achievements in their objects and
	 * within the database.
	 * @param aJDBCParams The JDBC parameters required to add to the 
	 * database. If the String[] contains nulls, the defaults in Game.java
	 * are used.
	 */
	private void updateAchievements(String[] aJDBCParams) {
		HashMap<Player,InGamePlayer> gamePlayers = mFinishedGame.getInGameMap();
		for(Map.Entry<Player,InGamePlayer> entry : gamePlayers.entrySet()) {
			Player updatePlayer = entry.getKey();
			InGamePlayer lastGamePlayer = entry.getValue();
			ArrayList<Achievement> achievementList = updatePlayer.getPlayerAchievements();
			
			for(int i = 0; i < achievementList.size(); i++) {
				achievementList.get(i).update(updatePlayer, lastGamePlayer, aJDBCParams);
			}	
		}
	}

}
