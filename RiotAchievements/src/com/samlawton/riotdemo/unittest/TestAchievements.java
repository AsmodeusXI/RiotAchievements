package com.samlawton.riotdemo.unittest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hsqldb.Server;
import org.junit.Test;

import com.samlawton.riotdemo.game.Player;

public class TestAchievements {
	
	private Server hsqlServer = null;
	private final String jdbcDriver = "org.hsqldb.jdbcDriver";
	private final String jdbcString = "jdbc:hsqldb:hsql://localhost/TestAchievementDB";
	private final String jdbcUser = "sa";
	private final String jdbcPass = "";
	private final String dbName = "TestAchievementDB";
	
	public void setUp() {
		
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
			Connection connection = null;

			// Getting a connection to the newly started database
			try {
				Class.forName(jdbcDriver);

				connection = DriverManager.getConnection(jdbcString, jdbcUser, jdbcPass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException ce) {
				ce.printStackTrace();
			} finally {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a user then checks to see if it can be recalled 
	 * from the database.
	 */
	@Test
	public void testNewAndExistingPlayer() {
		
		// Creates new player and inserts into database
		Player testPlayer = new Player("TestUser");

		try {
			Connection testConn = null;

			try {
				
				testConn = DriverManager.getConnection(
						"jdbc:hsqldb:hsql://localhost/AchievementDB", "sa", "");
				
				/*
				 * Grabs userName column for the name that we entered in the Player
				 * instantiation above.
				 */ 
				ResultSet testRS = testConn.prepareStatement(
						"select userName from players where name = 'TestUser'")
						.executeQuery();
				testRS.next();
				
				assertEquals("This should be the name we just entered.", "TestUser", testRS.getString(1));
				
				testRS.close();
				
				/*
				 * Creates a new player, but it should just be grabbing the old player.
				 * This will be proven if the create dates are the same.
				 */
				Player testDupPlayer = new Player("TestUser");
				assertEquals("This date should be the same if the users are the same.", 
						testPlayer.getUserCreateDate(),
						testDupPlayer.getUserCreateDate());
				
				
			} catch (SQLException e) {
				System.out.println("Encountered some kind of SQL error (statement level): " + e.getMessage());
			} finally {
				testConn.close();
			}
			
		} catch (SQLException e) {
			System.out.println("Encountered some kind of SQL error (connection level): " + e.getMessage());
		}
		
	}
	
	public void tearDown() {
		if(hsqlServer != null) hsqlServer.stop();
	}

}
