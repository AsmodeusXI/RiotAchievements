package com.samlawton.riotdemo.achievements;

import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

public abstract class Achievement {
	
	private boolean mIsAchieved = false;
	
	public abstract void update(Player currentPlayer, InGamePlayer currentInGamePlayer, String[] aJDBCParams);
	public abstract void printVictoryMessage(String aUserName);
	
	/**
	 * Gets the current state of the Achievement
	 * @return The isAchieved variable.
	 */
	public boolean getIsAchieved() {
		return mIsAchieved;
	}
	
	/**
	 * Sets the isAchieved Variable and prints out the 
	 * Victory Message (since you should only use this to
	 * set to TRUE).
	 * @param aAchieved
	 */
	public void setIsAchievedAtEnd(boolean aAchieved, String aUserName) {
		boolean originalValue = mIsAchieved;
		mIsAchieved = aAchieved;
		if(mIsAchieved && !originalValue) printVictoryMessage(aUserName);
	}
	
	public void setIsAchieved(boolean aAchieved) {
		mIsAchieved = aAchieved;
	}

}
