package com.samlawton.riotdemo.achievements;

import com.samlawton.riotdemo.game.InGamePlayer;
import com.samlawton.riotdemo.game.Player;

/**
 * An abstract class containing the basic methods
 * and fields that every Achievement requires. All
 * Achievements will inherit from this class.
 * @author Samuel H. Lawton
 *
 */
public abstract class Achievement {
	
	private boolean mIsAchieved = false;
	
	protected String mAchievementName;
	
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
	
	public String getAchievementName() {
		return mAchievementName;
	}
	public void setAchievementName(String aAchievementName) {
		mAchievementName = aAchievementName;
	}
	
	// TODO: New achievements require a new class created that extend Achievement and override the abstract methods.

}
