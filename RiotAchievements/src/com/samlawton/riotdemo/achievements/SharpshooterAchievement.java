package com.samlawton.riotdemo.achievements;

import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

public class SharpshooterAchievement extends Achievement {

	@Override
	public void update(Player currentPlayer, InGamePlayer currentInGamePlayer) {
		if(currentInGamePlayer.getGameHitNum() >= 75.0 && currentInGamePlayer.getGameHitNum() != 0.0) {
			this.setIsAchievedAtEnd(true, currentPlayer.getUserName());
		}
	}

	@Override
	public void printVictoryMessage(String aUserName) {
		System.out.println("Congratulations, " + aUserName + "! You have been awarded with Achievement: Sharpshooter!");
	}

}
