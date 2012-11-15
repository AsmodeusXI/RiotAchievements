package com.samlawton.riotdemo.game;

public class InGamePlayer {
	
	private int mGameAtkAttempts = 0;
	private int mGameHitNum = 0;
	private int mGameDmg = 0;
	private int mGameKills = 0;
	private int mGameFirstHitKills = 0;
	private int mGameAssists = 0;
	private int mGameSpellsCast = 0;
	private int mGameSpellDmg = 0;
	private long mGamePlayTime = 0;
	private boolean mGameWin = false;
	
	private final Player mCurrentPlayer;
	private final Game mCurrentGame;
	
	public InGamePlayer(Player aCurrentPlayer, Game aCurrentGame) {
		mCurrentPlayer = aCurrentPlayer;
		mCurrentGame = aCurrentGame;
	}
	
	public void updatePlayerHistory() {
		
	}

}
