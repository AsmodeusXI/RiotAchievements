package com.samlawton.riotdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.hsqldb.Server;

import com.samlawton.riotdemo.achievements.StatAchievementUpdater;
import com.samlawton.riotdemo.game.Game;
import com.samlawton.riotdemo.game.Player;

public class RiotDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("By the end of this demo, \nLeona should have the 'Big Winner' achievement,\n" +
				"Diana should have the 'Veteran' achievement,\nIrelia should have the 'Sharpshooter' achievement,\n" +
				"and Riven should have the 'Bruiser' achievement.\n" +
				"Other players may get other achievements, but those are ancillary.\nLet's go!");
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

            setUpTestData();
            
            Player pLeona = new Player("Leona");
    		Player pDiana = new Player("Diana");
    		Player pIrelia = new Player("Irelia");
    		Player pRiven = new Player("Riven");
    		Player pLulu = new Player("Lulu");
    		
    		pLeona.printAllHistoricalPlayerStats();
    		pDiana.printAllHistoricalPlayerStats();
    		pIrelia.printAllHistoricalPlayerStats();
    		pRiven.printAllHistoricalPlayerStats();
    		pLulu.printAllHistoricalPlayerStats();
    		
    		Player pYorick = new Player("Yorick");
    		Player pSona = new Player("Sona");
    		Player pKayle = new Player("Kayle");
    		Player pJayce = new Player("Jayce");
    		Player pTwistedFate = new Player("TwistedFate");
    		
    		ArrayList<Player> playerList = new ArrayList<Player>();
    		playerList.add(pLeona);
    		playerList.add(pYorick);
    		playerList.add(pDiana);
    		playerList.add(pSona);
    		playerList.add(pIrelia);
    		playerList.add(pKayle);
    		playerList.add(pRiven);
    		playerList.add(pJayce);
    		playerList.add(pLulu);
    		playerList.add(pTwistedFate);
    		
    		Game demoGame = new Game(playerList);
    		
    		demoGame.runGame(Game.defaultJDBCParams, true);
    		
    		StatAchievementUpdater demoUpdater = new StatAchievementUpdater(demoGame);
    		demoUpdater.updatesFromRecentGame(Game.defaultJDBCParams);
    		
    		tearDownTestData();

        } finally {
            // Closing the server
            if (hsqlServer != null) {
                hsqlServer.stop();
            }
        }
	}
	
	private static void setUpTestData() {
		try {
			
			Connection connection = null;
			
			try {
				
				Class.forName(Game.jdbcDriver);
				
				connection = DriverManager.getConnection(Game.jdbcString, Game.jdbcUser, Game.jdbcPass);
				
				// Leona - Will get Big Winner Achievement (has Sharpshooter/Bruiser/Shoots First)
				// Everything else is more or less randomized.
				connection.prepareStatement("insert into players values ('" + "Leona" + "','" +
						DateFormat.getDateTimeInstance().format(new Date()) + "'," +
	        			449 + "," +
	        			199 + "," +	//needs 200th win
	        			250 + "," +
	        			200000 + "," +
	        			80.0 + "," +
	        			30000000 + "," +
	        			500 + "," +
	        			120 + "," +
	        			800 + "," +
	        			100000 + "," +
	        			2000000 + "," +
	        			1000000 + "," +
	        			50 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Leona" + "'," +
            			true + "," +
            			true + "," +
            			false + "," +
            			false + "," +
            			true + ")").execute();
				
				// Diana - Will get Veteran Achievement (has Big Winner/Sharpshooter/Bruiser/Shoots First)
				// Everything else is more or less randomized.
				connection.prepareStatement("insert into players values ('" + "Diana" + "','" +
						DateFormat.getDateTimeInstance().format(new Date()) + "'," +
	        			999 + "," + //needs 1000th game
	        			600 + "," +
	        			399 + "," +
	        			350000 + "," +
	        			75.0 + "," +
	        			40050000 + "," +
	        			666 + "," +
	        			333 + "," +
	        			456 + "," +
	        			100000 + "," +
	        			2000000 + "," +
	        			1000000 + "," +
	        			50 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Diana" + "'," +
            			true + "," +
            			true + "," +
            			false + "," +
            			true + "," +
            			true + ")").execute();
				
				// Irelia - Will get Sharpshooter Achievement (has Bruiser/Shoots First)
				// Everything else is more or less randomized.
				connection.prepareStatement("insert into players values ('" + "Irelia" + "','" +
						DateFormat.getDateTimeInstance().format(new Date()) + "'," +
	        			100 + "," +
	        			50 + "," +
	        			50 + "," +
	        			1000 + "," +
	        			70.0 + "," + //needs 90% (to be sure)
	        			700 + "," +
	        			120 + "," +
	        			40 + "," +
	        			400 + "," +
	        			50 + "," +
	        			250 + "," +
	        			50000 + "," +
	        			10 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Irelia" + "'," +
            			false + "," +
            			true + "," +
            			false + "," +
            			false + "," +
            			true + ")").execute();
				
				// Riven - Will get Bruiser Achievement [at least] (has n/a)
				// Everything else is more or less randomized.
				connection.prepareStatement("insert into players values ('" + "Riven" + "','" +
						DateFormat.getDateTimeInstance().format(new Date()) + "'," +
	        			20 + "," +
	        			5 + "," +
	        			15 + "," +
	        			600 + "," +
	        			60.0 + "," +
	        			360 + "," + //needs 140-1 damage
	        			120 + "," +
	        			40 + "," +
	        			400 + "," +
	        			50 + "," +
	        			250 + "," +
	        			50000 + "," +
	        			3 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Riven" + "'," +
            			false + "," +
            			false + "," +
            			false + "," +
            			false + "," +
            			false + ")").execute();
				
				// Lulu - Will get First Blood Achievement [at least] (has n/a)
				// Everything else is more or less randomized.
				connection.prepareStatement("insert into players values ('" + "Lulu" + "','" +
						DateFormat.getDateTimeInstance().format(new Date()) + "'," +
	        			50 + "," +
	        			25 + "," +
	        			25 + "," +
	        			1000 + "," +
	        			70.0 + "," +
	        			360 + "," + 
	        			120 + "," +
	        			40 + "," +
	        			400 + "," +
	        			50 + "," +
	        			250 + "," +
	        			50000 + "," +
	        			4 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Lulu" + "'," +
            			false + "," +
            			false + "," +
            			false + "," +
            			false + "," +
            			false + ")").execute();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if(connection != null) connection.close();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void tearDownTestData() {
		try {
			
			Connection connection = null;
			
			try {
				
				Class.forName(Game.jdbcDriver);
				
				connection = DriverManager.getConnection(Game.jdbcString, "sa", "");
				
				connection.prepareStatement("delete from players where userName = 'Leona'").execute();
				connection.prepareStatement("delete from players where userName = 'Diana'").execute();
				connection.prepareStatement("delete from players where userName = 'Irelia'").execute();
				connection.prepareStatement("delete from players where userName = 'Riven'").execute();
				connection.prepareStatement("delete from players where userName = 'Lulu'").execute();
				
				connection.prepareStatement("delete from playerAchievements where userName = 'Leona'").execute();
				connection.prepareStatement("delete from playerAchievements where userName = 'Diana'").execute();
				connection.prepareStatement("delete from playerAchievements where userName = 'Irelia'").execute();
				connection.prepareStatement("delete from playerAchievements where userName = 'Riven'").execute();
				connection.prepareStatement("delete from playerAchievements where userName = 'Lulu'").execute();
				
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
				
				connection = DriverManager.getConnection(Game.jdbcString, Game.jdbcUser, Game.jdbcPass);
				
				ResultSet tableCount = connection.prepareStatement("select count(*) from INFORMATION_SCHEMA.tables where TABLE_SCHEMA = 'PUBLIC'").executeQuery();
				
				if(tableCount.next()) {
					int tableCountInt = tableCount.getInt(1);
					
					if(tableCountInt == 0) {
						
						System.out.println("Loading the tables for first use.");
						
						connection.prepareStatement(
								"create table players ("
										+ "userName varchar(64) not null primary key, "
										+ "userCreateDate varchar(128), "
										+ "totalGames integer, "
										+ "totalWins integer, "
										+ "totalLosses integer, "
										+ "totalAtkAttempts integer, "
										+ "totalHitNum double, " + "totalDmg integer, "
										+ "totalKills integer, "
										+ "totalFirstHitKills integer, "
										+ "totalAssists integer, "
										+ "totalSpellsCast integer, "
										+ "totalSpellDmg integer, "
										+ "totalPlayTime double, " 
										+ "totalFirstBloods integer" + ");").execute();
										//TODO: New player properties require an added column

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
										+ "victor varchar(8)," + "loser varchar(8),"
										+ "totalKills integer,"
										+ "blueKills integer," + "purpleKills integer,"
										+ "blueAssists integer,"
										+ "purpleAssists integer,"
										+ "firstBloodTeam varchar(8),"
										+ "firstBloodPlayer varchar(64),"
										+ "gameLength double);").execute();
										// TODO: New game level properties require an added column

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
												+ "AShootsFirst boolean);").execute();
												// TODO: New achievements require a new boolean column
					} else if (tableCountInt == 4) {
						System.out.println("All the tables are here.");
						System.out.println();
					} else {
						System.err.println("We don't understand the state we are in.");
						System.out.println();
						System.exit(1);
					}
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if(connection != null) connection.close();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
