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
	private double mGamePlayTime = 0;
	private boolean mGameWin = false;
	
	private final Player mCurrentPlayer;
	private final Game mCurrentGame;
	
	//TODO: New Player statistics must be given a property here to be tracked in game and to update historical Player
	
	private int mCurrentTeam;
	
	/**
	 * Creates an InGamePlayer using the Player to represent
	 * and the Game in which the Player is participating.
	 * @param aCurrentPlayer The Player in the Game.
	 * @param aCurrentGame The current Game.
	 */
	public InGamePlayer(Player aCurrentPlayer, Game aCurrentGame) {
		mCurrentPlayer = aCurrentPlayer;
		mCurrentGame = aCurrentGame;
		
	}
	
	/**
	 * This determines for a given InGamePlayer whether they
	 * were victorious or not based on the game and the team they were on.
	 */
	public void determineGameVictory() {
		int gameWinner = mCurrentGame.getGameWinner(); 
		if(gameWinner == 0) {
			System.err.println("Game is not over; this should not be happening.");
		} else if(mCurrentTeam == gameWinner) {
			setGameWin(true);
		} else {
			setGameWin(false);
		}
	}
	
	/**
	 * Visual test method to check current Player's game stats
	 */
	public void printPlayersGameStats() {
		System.out.println("Player: " + mCurrentPlayer.getUserName() + " | Game: " + mCurrentGame.getGameID());
		if(mGameWin) {
			System.out.println(mCurrentPlayer.getUserName() + " was victorious!");
		} else {
			System.out.println(mCurrentPlayer.getUserName() + " was defeated...");
		}
		System.out.println("Attack Attempts: " + mGameAtkAttempts);
		System.out.println("Hit Percentage: " + mGameHitNum);
		System.out.println("Total Damage: " + mGameDmg);
		System.out.println("Kills: " + mGameKills);
		System.out.println("First Hit Kills: " + mGameFirstHitKills);
		System.out.println("Assists: " + mGameAssists);
		System.out.println("Spells Cast: " + mGameSpellsCast);
		System.out.println("Spell Damage: " + mGameSpellDmg);
		System.out.println("Total Game Time (millis): " + mGamePlayTime);
		System.out.println();
		
		// TODO: New player statistics must be added here for print out
	}
	
	/*
	 * 
	 * GETTERS AND SETTERS FOR ALL OF THE VARIABLES 
	 * IN THE INGAMEPLAYER.
	 * 
	 */
	
	public Player getPlayerRep() {
		return mCurrentPlayer;
	}
	
	public void setCurrentTeam(int teamIdx) {
		mCurrentTeam = teamIdx;
	}
	
	public int getCurrentTeam() {
		return mCurrentTeam;
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

	public double getGamePlayTime() {
		return mGamePlayTime;
	}

	public void setGamePlayTime(double mGamePlayTime) {
		this.mGamePlayTime = mGamePlayTime;
	}

	public boolean isGameWin() {
		return mGameWin;
	}
}
