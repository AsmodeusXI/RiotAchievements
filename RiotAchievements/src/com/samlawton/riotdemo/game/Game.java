package com.samlawton.riotdemo.game;

import java.util.ArrayList;

public class Game {
	
	private final ArrayList<Player> mPlayerList;
	private final ArrayList<Player> mBlueTeamPlayerList;
	private final ArrayList<Player> mPurpleTeamPlayerList;
	
	public static final String jdbcDriver = "org.hsqldb.jdbcDriver";
	public static final String jdbcString = "jdbc:hsqldb:hsql://localhost/AchievementDB";
	public static final String jdbcUser = "sa";
	public static final String jdbcPass = "";
	public static final String dbName = "AchievementDB";
	
	public Game(ArrayList<Player> aPlayerList) {
		mPlayerList = aPlayerList;
		ArrayList<Player> btpl = new ArrayList<Player>();
		ArrayList<Player> ptpl = new ArrayList<Player>();
		for(int i = 0; i < mPlayerList.size(); i++) {
			if(i%2 == 0) {
				btpl.add(mPlayerList.get(i));
			} else {
				ptpl.add(mPlayerList.get(i));
			}
		}
		mBlueTeamPlayerList = btpl;
		mPurpleTeamPlayerList = ptpl;
	}
	
	public void runGame() {
		
	}

}
