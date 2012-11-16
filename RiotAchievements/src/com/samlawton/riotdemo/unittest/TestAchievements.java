package com.samlawton.riotdemo.unittest;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hsqldb.Server;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.samlawton.riotdemo.game.Player;

public class TestAchievements {
	
	private static Server hsqlServer = null;
	public static final String jdbcDriver = "org.hsqldb.jdbcDriver";
	public static final String jdbcString = "jdbc:hsqldb:mem:testdb";
	public static final String jdbcUser = "sa";
	public static final String jdbcPass = "";
	public static final String dbName = "testdb";
	public static final String[] jdbcParams = {jdbcDriver, jdbcString, jdbcUser, jdbcPass};
	
	@BeforeClass
	public static void setUp() {
		
		hsqlServer = new Server();

        // HSQLDB prints out a lot of informations when
        // starting and closing, which we don't need now.
        // Normally you should point the setLogWriter
        // to some Writer object that could store the logs.
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);

        // The actual database will be named 'xdb' and its
        // settings and data will be stored in files
        // testdb.properties and testdb.script
        hsqlServer.setDatabaseName(0, dbName);
        hsqlServer.setDatabasePath(0, "file:"+dbName);

        // Start the database!
        hsqlServer.start();
        
		try {
			Connection createEnv = null;

			try {
				
				Class.forName(jdbcDriver);

				createEnv = DriverManager.getConnection(jdbcString, jdbcUser,
						jdbcPass);

				createEnv.prepareStatement(
						"create table players ("
								+ "userName varchar(64) not null primary key, "
								+ "userCreateDate double, "
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
								+ "totalPlayTime double " + ");")
						.execute();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				createEnv.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		
		}

	}
	
	/**
	 * Creates a user then checks to see if it can be recalled 
	 * from the database.
	 */
	@Test
	public void testNewAndExistingPlayer() {
		
		// Creates new player and inserts into database
		Player testPlayer = new Player("TestUser", jdbcParams);

		try {
			Connection testConn = null;

			try {
				
				testConn = DriverManager.getConnection(jdbcString, jdbcUser, jdbcPass);
				
				/*
				 * Grabs userName column for the name that we entered in the Player
				 * instantiation above.
				 */ 
				ResultSet testRS = testConn.prepareStatement(
						"select userName from players where userName = 'TestUser'")
						.executeQuery();
				testRS.next();
				
				assertEquals("This should be the name we just entered.", "TestUser", testRS.getString(1));
				
				testRS.close();
				
				/*
				 * Creates a new player, but it should just be grabbing the old player.
				 * This will be proven if the create dates are the same.
				 */
				Player testDupPlayer = new Player("TestUser", jdbcParams);
				assertEquals("This date should be the same if the users are the same.", 
						testPlayer.getUserCreateDate(),
						testDupPlayer.getUserCreateDate());
				
				
			} catch (SQLException e) {
				System.out.println("Encountered some kind of SQL error (statement level): " + e.getMessage());
			} finally {
				testConn.prepareStatement("delete from players where userName = 'TestUser'").execute();
				testConn.close();
			}
			
		} catch (SQLException e) {
			System.out.println("Encountered some kind of SQL error (connection level): " + e.getMessage());
		} finally {
			
		}
		
	}
	
	@After
	public void tearDown() {
		if(hsqlServer != null) hsqlServer.stop();
	}

}
