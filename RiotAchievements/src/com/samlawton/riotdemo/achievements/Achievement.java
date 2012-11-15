package com.samlawton.riotdemo.achievements;

public abstract class Achievement {
	
	private boolean mIsAchieved = false;
	
	public abstract void update();
	public abstract void printVictoryMessage();
	
	public boolean getIsAchieved() {
		return mIsAchieved;
	}
	
	public void setIsAchieved(boolean aAchieved) {
		mIsAchieved = aAchieved;
		printVictoryMessage();
	}

}
