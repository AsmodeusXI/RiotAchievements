package com.samlawton.riotdemo.achievements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.samlawton.riotdemo.game.Game;
import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

public class BigWinnerAchievement extends Achievement {
	
	public BigWinnerAchievement() {
		mAchievementName = "Big Winner";
	}

	@Override
	public void update(Player currentPlayer, InGamePlayer currentInGamePlayer, String[] aJDBCParams) {
		if(currentPlayer.getTotalWins() >= 200) {
			this.setIsAchievedAtEnd(true, currentPlayer.getUserName());
			
			try {
				
				Connection connection = null;
				
				String currentDriver = aJDBCParams[0] == null ? Game.jdbcDriver : aJDBCParams[0];
				String currentJDBCURL = aJDBCParams[1] == null ? Game.jdbcString : aJDBCParams[1];
				String currentUser = aJDBCParams[2] == null ? Game.jdbcUser : aJDBCParams[2];
				String currentPass = aJDBCParams[3] == null ? Game.jdbcPass : aJDBCParams[3];
				
				try {
					
					Class.forName(currentDriver);
					
					connection = DriverManager.getConnection(currentJDBCURL, currentUser, currentPass);
					
					connection.prepareStatement("update playerAchievements set ABigWinner = " +
							true + " where userName = '" + currentPlayer.getUserName() + "'").execute();
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					connection.close();
				}
				
			} catch(SQLException ex) {
				
			}
		}
	}

	@Override
	public void printVictoryMessage(String aUserName) {
		System.out.println("Congratulations, " + aUserName + "! You have earned Achievement: Big Winner!");
	}

}
