package com.samlawton.riotdemo.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
	
	private ArrayList<Player> mPlayerList;
	private HashMap<Player, InGamePlayer> mInGameMap;
	private ArrayList<InGamePlayer> mBlueTeamPlayerList;
	private ArrayList<InGamePlayer> mPurpleTeamPlayerList;
	
	public static final int BLUE_TEAM_IDX = 1;
	public static final int PURPLE_TEAM_IDX = 2;
	
	public static final String jdbcDriver = "org.hsqldb.jdbcDriver";
	public static final String jdbcString = "jdbc:hsqldb:hsql://localhost/AchievementDB";
	public static final String jdbcUser = "sa";
	public static final String jdbcPass = "";
	public static final String dbName = "AchievementDB";
	
	private final String mGameID;
	public static final int GAMEID_IDX = 1;
	
	private final double mGameDate;
	public static final int GAMEDATE_IDX = 2;
	
	public static final int BLUE_PLAYER_ONE_IDX = 3;
	public static final int BLUE_PLAYER_TWO_IDX = 4;
	public static final int BLUE_PLAYER_THREE_IDX = 5;
	public static final int BLUE_PLAYER_FOUR_IDX = 6;
	public static final int BLUE_PLAYER_FIVE_IDX = 7;
	public static final int PURPLE_PLAYER_ONE_IDX = 8;
	public static final int PURPLE_PLAYER_TWO_IDX = 9;
	public static final int PURPLE_PLAYER_THREE_IDX = 10;
	public static final int PURPLE_PLAYER_FOUR_IDX = 11;
	public static final int PURPLE_PLAYER_FIVE_IDX = 12;
	
	private int mGameWinner = 0;
	public static final int VICTOR_IDX = 13;
	public static final int LOSER_IDX = 14;
	
	private int mBlueTeamKills = 0;
	public static final int BLUE_KILLS_IDX = 15;
	
	private int mPurpleTeamKills = 0;
	public static final int PURPLE_KILLS_IDX = 16;
	
	private int mBlueAssists = 0;
	public static final int BLUE_ASSISTS_IDX = 17;
	
	private int mPurpleAssists = 0;
	public static final int PURPLE_ASSISTS_IDX = 18;
	
	private String mFirstBloodTeam = "";
	public static final int FIRST_BLOOD_TEAM_IDX = 19;
	
	private String mFirstBloodPlayer = "";
	public static final int FIRST_BLOOD_PLAYER_IDX = 20;
	
	private double mGameLength = 0.0;
	public static final int GAME_LENGTH_IDX = 21;
	
	/**
	 * Creates the Game object using a list of the involved
	 * Players. Creates the game's ID based on the Player
	 * ArrayList and the current time.
	 * @param aPlayerList ArrayList of Players in current Game
	 */
	public Game(ArrayList<Player> aPlayerList) {
		mPlayerList = aPlayerList;
		mGameID = "Game" + aPlayerList.hashCode() + System.currentTimeMillis();
		mGameDate = System.currentTimeMillis();
	}
	
	public void init(String[] aJDBCParams) {
		initPlayers();
		initGameInDB(aJDBCParams);
	}
	
	/**
	 * Initializes the list of Players in the game,
	 * creating their InGame representatives and
	 * simply arranging them onto teams.
	 */
	private void initPlayers() {
		mInGameMap = createInGamePlayers(mPlayerList);
		ArrayList<InGamePlayer> btpl = new ArrayList<InGamePlayer>();
		ArrayList<InGamePlayer> ptpl = new ArrayList<InGamePlayer>();
		for(int i = 0; i < mPlayerList.size(); i++) {
			InGamePlayer currentIGPlayer = mInGameMap.get(mPlayerList.get(i));
			if(i%2 == 0) {
				currentIGPlayer.setCurrentTeam(BLUE_TEAM_IDX);
				btpl.add(currentIGPlayer);
			} else {
				currentIGPlayer.setCurrentTeam(PURPLE_TEAM_IDX);
				ptpl.add(currentIGPlayer);
			}
		}
		mBlueTeamPlayerList = btpl;
		mPurpleTeamPlayerList = ptpl;
	}
	
	/**
	 * Initializes the Game in the database by adding a new Game
	 * entry with a GameID and assigning each Player to the gameID
	 * in the gamesPlayed Table.
	 */
	private void initGameInDB(String[] aJDBCParams) {
		try {
			Connection connection = null;

			String currentDriver = aJDBCParams[0] == null ? Game.jdbcDriver
					: aJDBCParams[0];
			String currentJDBCURL = aJDBCParams[1] == null ? Game.jdbcString
					: aJDBCParams[1];
			String currentUser = aJDBCParams[2] == null ? Game.jdbcUser
					: aJDBCParams[2];
			String currentPass = aJDBCParams[3] == null ? Game.jdbcPass
					: aJDBCParams[3];

			try {

				Class.forName(currentDriver);

				// Default user of the HSQLDB is 'sa'
				// with an empty password
				connection = DriverManager.getConnection(currentJDBCURL,
						currentUser, currentPass);
				
				ArrayList<String> insertNames = createNameInsertList(mBlueTeamPlayerList, mPurpleTeamPlayerList);

				connection
						.prepareStatement("insert into games values('"
								+ mGameID + "'," 
								+ mGameDate + ",'" 
								+ insertNames.get(0) + "','" 
								+ insertNames.get(1) + "','" 
								+ insertNames.get(2) + "','" 
								+ insertNames.get(3) + "','" 
								+ insertNames.get(4) + "','" 
								+ insertNames.get(5) + "','" 
								+ insertNames.get(6) + "','" 
								+ insertNames.get(7) + "','" 
								+ insertNames.get(8) + "','" 
								+ insertNames.get(9) + "'," 
								+ mGameWinner + "," 
								+ mGameWinner + "," 
								+ mBlueTeamKills + "," 
								+ mPurpleTeamKills + "," 
								+ mBlueAssists + "," 
								+ mPurpleAssists + ",'" 
								+ mFirstBloodTeam + "','" 
								+ mFirstBloodPlayer + "'," 
								+ mGameLength + ")").execute();
				
				for(int i = 0; i < insertNames.size(); i++) {
					String currentUserName = insertNames.get(i);
					if(!currentUserName.isEmpty()) {
						connection.prepareStatement("insert into gamesPlayed values (NULL,'" +
								currentUserName + "','" +
								this.mGameID + "')").execute();
					}
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null)
					connection.close();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates the Name Insert List for the game by looking at the 
	 * Players on each team, entering them in the appropriate places
	 * in the list, and filling in empty spaces for the rest.
	 * @param aBlueTeam The Players on the Blue team for the current Game.
	 * @param aPurpleTeam The Players on the Purple team for the current Game.
	 * @return An ArrayList of Player names with blank spaces for empty names
	 * where there are Teams with size < 5.
	 */
	private ArrayList<String> createNameInsertList(ArrayList<InGamePlayer> aBlueTeam, ArrayList<InGamePlayer> aPurpleTeam) {
		
		ArrayList<String> nameArray = new ArrayList<String>();
		int currentNameArrayIdx = 0;
		
		for(int i = 0; i < aBlueTeam.size(); i++) {
			nameArray.add(currentNameArrayIdx, aBlueTeam.get(i).getPlayerRep().getUserName());
			currentNameArrayIdx++;
		}
		
		for(int j = 0; j < (5-aBlueTeam.size()); j++) {
			nameArray.add(currentNameArrayIdx,"");
			currentNameArrayIdx++;
		}
		
		for(int k = 0; k < aPurpleTeam.size(); k++) {
			nameArray.add(currentNameArrayIdx, aPurpleTeam.get(k).getPlayerRep().getUserName());
			currentNameArrayIdx++;
		}
		
		for(int l = 0; l < (5-aPurpleTeam.size()); l++) {
			nameArray.add(currentNameArrayIdx,"");
			currentNameArrayIdx++;
		}
		
		return nameArray;
	}
	
	/**
	 * Gets a given player's team value for the current Game from their
	 * InGame representative.
	 * @param aCurrentPlayer The Player in question
	 * @return The index with the Team Value.
	 */
	public int getPlayerTeam(Player aCurrentPlayer) {
		InGamePlayer currIGPlayer = mInGameMap.get(aCurrentPlayer);
		if(mBlueTeamPlayerList.contains(currIGPlayer)) {
			return BLUE_TEAM_IDX;
		} else if (mPurpleTeamPlayerList.contains(currIGPlayer)) {
			return PURPLE_TEAM_IDX;
		} else {
			System.err.println("This should never happen. Check logic.");
			return 0;
		}
	}
	
	/**
	 * Returns the game winner (determined by runGame())
	 * @return Team index of winner
	 */
	public int getGameWinner() {
		return mGameWinner;
	}
	
	/**
	 * Returns the game length (in millis)
	 * @return The game's length.
	 */
	public double getGameLength() {
		return mGameLength;
	}
	
	/**
	 * Returns the player/in-game-player Map
	 * @return The Player/InGamePlayer Map of the Game
	 */
	public HashMap<Player,InGamePlayer> getInGameMap() {
		return mInGameMap;
	}
	
	/**
	 * Returns the ID of the current game.
	 * @return The ID of the current Game.
	 */
	public String getGameID() {
		return mGameID;
	}

	/**
	 * Invoked in initPlayers to create InGame representatives
	 * for each Player.
	 * @param aPlayerList The Player list for the current game.
	 * @return The HashMap of Players to InGamePlayers
	 */
	private HashMap<Player, InGamePlayer> createInGamePlayers(ArrayList<Player> aPlayerList) {
		HashMap<Player,InGamePlayer> currentGamePlayers = new HashMap<Player,InGamePlayer>();
		for(int i = 0; i < aPlayerList.size(); i++) {
			InGamePlayer aOnePlayer = new InGamePlayer(aPlayerList.get(i),this);
			currentGamePlayers.put(aPlayerList.get(i), aOnePlayer);
		}
		return currentGamePlayers;
	}
	
	/**
	 * For testing running the Game. There will be a method basically identical to
	 * this to do the actual Game running.
	 */
	public void runTestGame() {
		mGameWinner = BLUE_TEAM_IDX;
		mGameLength = 10000;
		
		for(int i = 0; i < mBlueTeamPlayerList.size(); i++) {
			InGamePlayer blueTestPlayer = mBlueTeamPlayerList.get(i);
			setTestPlayerStats(blueTestPlayer);
		}
		
		for(int j = 0; j < mPurpleTeamPlayerList.size(); j++) {
			InGamePlayer purpleTestPlayer = mPurpleTeamPlayerList.get(j);
			setTestPlayerStats(purpleTestPlayer);
		}
	}
	
	/**
	 * Method exclusive for JUnit testing the stats updates.
	 * @param aCurrentIGPlayer
	 */
	private void setTestPlayerStats(InGamePlayer aCurrentIGPlayer) {
		aCurrentIGPlayer.determineGameVictory();
		aCurrentIGPlayer.setGameAtkAttempts(5);
		aCurrentIGPlayer.setGameHitNum(5.5);
		aCurrentIGPlayer.setGameDmg(100);
		aCurrentIGPlayer.setGameKills(5);
		aCurrentIGPlayer.setGameFirstHitKills(2);
		aCurrentIGPlayer.setGameAssists(1);
		aCurrentIGPlayer.setGameSpellsCast(25);
		aCurrentIGPlayer.setGameSpellDmg(2500);
		aCurrentIGPlayer.setGamePlayTime(mGameLength);
	}
	
}
