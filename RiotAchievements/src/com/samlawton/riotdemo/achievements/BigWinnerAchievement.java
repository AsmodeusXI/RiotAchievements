package com.samlawton.riotdemo.achievements;

import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

public class BigWinnerAchievement extends Achievement {

	@Override
	public void update(Player currentPlayer, InGamePlayer currentInGamePlayer) {
		if(currentPlayer.getTotalWins() >= 200) {
			this.setIsAchievedAtEnd(true, currentPlayer.getUserName());
		}
	}

	@Override
	public void printVictoryMessage(String aUserName) {
		System.out.println("Congratulations, " + aUserName + "! You have earned Achievement: Big Winner!");
	}

}
