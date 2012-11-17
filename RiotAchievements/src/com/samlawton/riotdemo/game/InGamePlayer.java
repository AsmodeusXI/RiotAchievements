package com.samlawton.riotdemo.game;

public class InGamePlayer {
	
	private int mGameAtkAttempts = 0;
	private double mGameHitNum = 0.0;
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
	
	private int mCurrentTeam;
	
	public InGamePlayer(Player aCurrentPlayer, Game aCurrentGame) {
		mCurrentPlayer = aCurrentPlayer;
		mCurrentGame = aCurrentGame;
		
	}
	
	public void determineGameVictory() {
		int gameWinner = mCurrentGame.getGameWinner(); 
		if(gameWinner == 0) {
			System.out.println("Game is not over; this should not be happening.");
		} else if(mCurrentTeam == gameWinner) {
			setGameWin(true);
		} else {
			setGameWin(false);
		}
	}
	
	public void setCurrentTeam(int teamIdx) {
		mCurrentTeam = teamIdx;
	}

	private void setGameWin(boolean b) {
		mGameWin = b;
	}

	public int getGameAtkAttempts() {
		return mGameAtkAttempts;
	}

	public void setGameAtkAttempts(int mGameAtkAttempts) {
		this.mGameAtkAttempts = mGameAtkAttempts;
	}

	public double getGameHitNum() {
		return mGameHitNum;
	}

	public void setGameHitNum(double mGameHitNum) {
		this.mGameHitNum = mGameHitNum;
	}

	public int getGameDmg() {
		return mGameDmg;
	}

	public void setGameDmg(int mGameDmg) {
		this.mGameDmg = mGameDmg;
	}

	public int getGameKills() {
		return mGameKills;
	}

	public void setGameKills(int mGameKills) {
		this.mGameKills = mGameKills;
	}

	public int getGameFirstHitKills() {
		return mGameFirstHitKills;
	}

	public void setGameFirstHitKills(int mGameFirstHitKills) {
		this.mGameFirstHitKills = mGameFirstHitKills;
	}

	public int getGameAssists() {
		return mGameAssists;
	}

	public void setGameAssists(int mGameAssists) {
		this.mGameAssists = mGameAssists;
	}

	public int getGameSpellsCast() {
		return mGameSpellsCast;
	}

	public void setGameSpellsCast(int mGameSpellsCast) {
		this.mGameSpellsCast = mGameSpellsCast;
	}

	public int getGameSpellDmg() {
		return mGameSpellDmg;
	}

	public void setGameSpellDmg(int mGameSpellDmg) {
		this.mGameSpellDmg = mGameSpellDmg;
	}

	public long getGamePlayTime() {
		return mGamePlayTime;
	}

	public void setGamePlayTime(long mGamePlayTime) {
		this.mGamePlayTime = mGamePlayTime;
	}

	public boolean isGameWin() {
		return mGameWin;
	}
}
