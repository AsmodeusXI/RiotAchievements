package com.samlawton.riotdemo.achievements;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.samlawton.riotdemo.game.Game;
import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

public class StatAchievementUpdater {
	
	private final Game mFinishedGame;
	
	public StatAchievementUpdater(Game aGame) {
		mFinishedGame = aGame;
	}
	
	public void updateStats(String[] aJDBCParams) {
		HashMap<Player,InGamePlayer> gamePlayers = mFinishedGame.getInGameMap();
		for(Map.Entry<Player,InGamePlayer> entry : gamePlayers.entrySet()) {
			Player updatePlayer = entry.getKey();
			InGamePlayer lastGamePlayer = entry.getValue();
			updatePlayer.updatePlayerHistoricalStats(lastGamePlayer);
			try {
				updatePlayer.updatePlayerDataInDB(aJDBCParams);
			} catch (SQLException e) {
				e.printStackTrace();
				fail();
			}
		}
	}
	
	public void updateAchievements() {
		HashMap<Player,InGamePlayer> gamePlayers = mFinishedGame.getInGameMap();
		for(Map.Entry<Player,InGamePlayer> entry : gamePlayers.entrySet()) {
			Player updatePlayer = entry.getKey();
			InGamePlayer lastGamePlayer = entry.getValue();
			ArrayList<Achievement> achievementList = updatePlayer.getPlayerAchievements();
			
			for(int i = 0; i < achievementList.size(); i++) {
				achievementList.get(i).update(updatePlayer, lastGamePlayer);
			}	
		}
	}

}
