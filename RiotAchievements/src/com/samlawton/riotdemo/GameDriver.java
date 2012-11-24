package com.samlawton.riotdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.hsqldb.Server;

import com.samlawton.riotdemo.achievements.StatAchievementUpdater;
import com.samlawton.riotdemo.game.Game;
import com.samlawton.riotdemo.game.Player;

/**
 * A driver for running miscellaneous parts of the
 * Game and Player classes created for the Demo.
 * Allows for additional inspection of the created 
 * settings, classes, and database interactions.
 * @author Samuel H. Lawton
 *
 */
public class GameDriver {

	private static boolean mContinue = true;
	private final static Scanner mConsole = new Scanner(System.in);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to Sam Lawton's Game Achievement Driver.");
		System.out.println();
		System.out.println("PLEASE RUN THE RiotDemo APPLICATION BEFORE THIS ONE!");
		System.out.println();
		System.out
				.println("Running the RiotDemo class should demonstrate the functionality requested\n"
						+ "in the exercise document, but this is around so that you can run Games yourself\n"
						+ "with your own Players and see statistics and achievements build up over time.\n"
						+ "These should be locally persistent (though I know this was not in the requirements).\n");
		System.out.println();

		Server hsqlServer = null;
		try {
			hsqlServer = new Server();

			hsqlServer.setLogWriter(null);
			hsqlServer.setSilent(true);

			hsqlServer.setDatabaseName(0, "AchievementDB");
			hsqlServer.setDatabasePath(0, "file:AchievementDB");

			// Start the database!
			hsqlServer.start();

			firstTimeDBInit();
			
			while(mContinue) {
				
				System.out.println("Choose from the following options!");
				System.out.println("\t(1) Create a new player.");
				System.out.println("\t(2) Add Players to a new Game and run that Game.");
				System.out.println("\t(3) Look at a Player's stats and achievements.");
				System.out.println("\t(4) Look at the statistics of a given Game.");
				System.out.println("\t(5) Quit.");
				System.out.print("Select an option: ");
				
				int selection = mConsole.nextInt();
				
				if(selection == 1) {
					createNewPlayer();
				} else if (selection == 2) {
					createPlayerListAndRunGame();
				} else if (selection == 3) {
					examinePlayerStatistics();
				} else if (selection == 4) {
					examineGameStatistics();
				} else if (selection == 5) {
					mContinue = false;
					System.out.println("Thank you for using Sam Lawton's Game Achievement Driver! Hope you enjoyed it!");
				} else {
					System.out.println("Invalid option selected. Please choose again!");
				}
				
			}

		} finally {
			if (hsqlServer != null) {
				hsqlServer.stop();
			}
		}

	}
	
	/**
	 * Create a new player to be used in later Games.
	 * Requires input from User.
	 */
	private static void createNewPlayer() {
		
		boolean notCreated = true;

		while (notCreated) {

			System.out.println("Enter the name of a new Player: ");

			String playerName = mConsole.next();

			Player customPlayer = new Player(playerName);

			if (customPlayer.getTotalGames() != 0) {
				System.out
						.println("This name is shared with an existing player!");
				System.out.println("Please select another name!");
			} else {
				notCreated = false;
				System.out.println("New Player created!");
			}
		}
	}
	
	/**
	 * User enters a comma separated list of Players
	 */
	private static void createPlayerListAndRunGame() {
		
		System.out.println("Submit a comma-separated list of Player names!");
		System.out.println("(no spaces please; these will result in invalid player names)");
		System.out.println("(please enter an even number of Players between 6-10)");
		System.out.print("Enter list: ");
		
		String playerListString = mConsole.next();
		
		String[] playerListArray = playerListString.split(",");
		
		ArrayList<Player> gamePlayerList = new ArrayList<Player>();
		
		for(int i = 0; i < playerListArray.length; i++) {
			String playerName = playerListArray[i];
			Player gamePlayer = new Player(playerName);
			gamePlayerList.add(gamePlayer);
		}
		
		Game customGame = new Game(gamePlayerList);
		customGame.runGame(Game.defaultJDBCParams);
		
		StatAchievementUpdater achievementUpdater = new StatAchievementUpdater(customGame);
		achievementUpdater.updatesFromRecentGame(Game.defaultJDBCParams);
		
		System.out.print("Would you like to see the recent Game's stats? (y/n) ");
		boolean seeStatsAnswer = true;
		
		while (seeStatsAnswer) {
			String answer = mConsole.next();
			if (answer.equalsIgnoreCase("y")) {
				seeStatsAnswer = false;
				System.out.println("Printing all InGamePlayer's and Game stats.");
				customGame.printAllStats();
			} else if (answer.equalsIgnoreCase("n")) {
				seeStatsAnswer = false;
			} else {
				System.out.println("Please enter either 'y' or 'n'.");
			}
		}
	}
	
	private static void examinePlayerStatistics() {
		System.out.print("Please enter a Player name: ");
		String queryPlayerName = mConsole.next();
		
		Player queryPlayer = new Player(queryPlayerName);
		
		queryPlayer.printAllHistoricalPlayerStats();
	}
	
	private static void examineGameStatistics() {
		System.out.print("Please enter a Game ID: ");
		String queryGameID = mConsole.next();
		
		try {
			
			Connection connection = null;
			
			try {
				
				Class.forName(Game.jdbcDriver);
				
				connection = DriverManager.getConnection(Game.jdbcString, Game.jdbcUser, Game.jdbcPass);
				
				ResultSet gameRecords = connection.prepareStatement("select * from games where gameID = '" + queryGameID + "'").executeQuery();
				
				while(gameRecords.next()) {
					
					System.out.println("Stats for Game " + queryGameID);
					System.out.println("Game Date: " + gameRecords.getString(Game.GAMEDATE_IDX));
					System.out.println("Blue Team Players:");
					System.out.println("\t- " + gameRecords.getString(Game.BLUE_PLAYER_ONE_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.BLUE_PLAYER_TWO_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.BLUE_PLAYER_THREE_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.BLUE_PLAYER_FOUR_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.BLUE_PLAYER_FIVE_IDX));
					System.out.println("Purple Team Players:");
					System.out.println("\t- " + gameRecords.getString(Game.PURPLE_PLAYER_ONE_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.PURPLE_PLAYER_TWO_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.PURPLE_PLAYER_THREE_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.PURPLE_PLAYER_FOUR_IDX));
					System.out.println("\t- " + gameRecords.getString(Game.PURPLE_PLAYER_FIVE_IDX));
					System.out.println("Victor: " + gameRecords.getString(Game.VICTOR_IDX));
					System.out.println("Loser: " + gameRecords.getString(Game.LOSER_IDX));
					System.out.println("Total Kills: " + gameRecords.getInt(Game.TOTAL_KILLS_IDX));
					System.out.println("Blue Team Kills: " + gameRecords.getInt(Game.BLUE_KILLS_IDX));
					System.out.println("Purple Team Kills: " + gameRecords.getInt(Game.PURPLE_KILLS_IDX));
					System.out.println("Blue Team Assists: " + gameRecords.getInt(Game.BLUE_ASSISTS_IDX));
					System.out.println("Purple Team Assists: " + gameRecords.getInt(Game.PURPLE_ASSISTS_IDX));
					System.out.println("First Blood Team: " + gameRecords.getString(Game.FIRST_BLOOD_TEAM_IDX));
					System.out.println("First Blood Player: " + gameRecords.getString(Game.FIRST_BLOOD_PLAYER_IDX));
					System.out.println("Game Length: " + gameRecords.getDouble(Game.GAME_LENGTH_IDX));
					System.out.println();
					
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				connection.close();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}

	private static void firstTimeDBInit() {
		try {

			Connection connection = null;

			try {

				Class.forName(Game.jdbcDriver);

				connection = DriverManager.getConnection(Game.jdbcString,
						Game.jdbcUser, Game.jdbcPass);

				ResultSet tableCount = connection
						.prepareStatement(
								"select count(*) from INFORMATION_SCHEMA.tables where TABLE_SCHEMA = 'PUBLIC'")
						.executeQuery();

				if (tableCount.next()) {
					int tableCountInt = tableCount.getInt(1);

					if (tableCountInt == 0) {

						System.out.println("Loading the tables for first use.");

						connection
								.prepareStatement(
										"create table players ("
												+ "userName varchar(64) not null primary key, "
												+ "userCreateDate varchar(128), "
												+ "totalGames integer, "
												+ "totalWins integer, "
												+ "totalLosses integer, "
												+ "totalAtkAttempts integer, "
												+ "totalHitNum double, "
												+ "totalDmg integer, "
												+ "totalKills integer, "
												+ "totalFirstHitKills integer, "
												+ "totalAssists integer, "
												+ "totalSpellsCast integer, "
												+ "totalSpellDmg integer, "
												+ "totalPlayTime double, "
												+ "totalFirstBloods integer"
												+ ");").execute();
						// TODO: New player properties require an added column

						connection.prepareStatement(
								"create table games(gameID varchar(128) primary key,"
										+ "gameDate varchar(128),"
										+ "bluePlayerOne varchar(64),"
										+ "bluePlayerTwo varchar(64),"
										+ "bluePlayerThree varchar(64),"
										+ "bluePlayerFour varchar(64),"
										+ "bluePlayerFive varchar(64),"
										+ "purplePlayerOne varchar(64),"
										+ "purplePlayerTwo varchar(64),"
										+ "purplePlayerThree varchar(64),"
										+ "purplePlayerFour varchar(64),"
										+ "purplePlayerFive varchar(64),"
										+ "victor varchar(8),"
										+ "loser varchar(8),"
										+ "totalKills integer,"
										+ "blueKills integer,"
										+ "purpleKills integer,"
										+ "blueAssists integer,"
										+ "purpleAssists integer,"
										+ "firstBloodTeam varchar(8),"
										+ "firstBloodPlayer varchar(64),"
										+ "gameLength double);").execute();
						// TODO: New game level properties require an added
						// column

						connection
								.prepareStatement(
										"create table gamesPlayed(gamesPlayedID integer generated by default as identity(start with 1, increment by 1) primary key,"
												+ "userName varchar(64) not null,"
												+ "gameID varchar(128) not null);")
								.execute();

						connection
								.prepareStatement(
										"create table playerAchievements(achieveID integer generated by default as identity(start with 1, increment by 1) primary key, "
												+ "userName varchar(64) not null,"
												+ "ASharpshooter boolean,"
												+ "ABruiser boolean,"
												+ "AVeteran boolean,"
												+ "ABigWinner boolean,"
												+ "AShootsFirst boolean);")
								.execute();
						// TODO: New achievements require a new boolean column
					} else if (tableCountInt == 4) {
						System.out.println("All the tables are here.");
						System.out.println();
					} else {
						System.err
								.println("We don't understand the state we are in.");
						System.out.println();
						System.exit(1);
					}
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (connection != null)
					connection.close();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
