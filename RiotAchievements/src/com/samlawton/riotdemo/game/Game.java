package com.samlawton.riotdemo.game;

import java.util.ArrayList;

public class Game {
	
	private final ArrayList<Player> mPlayerList;
	private final ArrayList<Player> mBlueTeamPlayerList;
	private final ArrayList<Player> mPurpleTeamPlayerList;
	
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
