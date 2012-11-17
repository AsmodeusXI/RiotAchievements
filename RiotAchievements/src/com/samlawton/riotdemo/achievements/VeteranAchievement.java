package com.samlawton.riotdemo.achievements;

import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

public class VeteranAchievement extends Achievement {

	@Override
	public void update(Player currentPlayer, InGamePlayer currentInGamePlayer) {
		if(currentPlayer.getTotalWins() >= 1000) {
			this.setIsAchievedAtEnd(true, currentPlayer.getUserName());
		}
	}

	@Override
	public void printVictoryMessage(String aUserName) {
		System.out.println("Congratulations, " + aUserName + "! You have earned Achievement: Veteran!");
	}

}
