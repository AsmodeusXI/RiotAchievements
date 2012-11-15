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
	
	public void setUp() {
		
		Server hsqlServer = null;
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
        hsqlServer.setDatabaseName(0, "AchievementDB");
        hsqlServer.setDatabasePath(0, "file:AchievementDB");

        // Start the database!
        hsqlServer.start();
        
        Connection connection = null;
        
        // Getting a connection to the newly started database
        try {
			Class.forName("org.hsqldb.jdbcDriver");
		
			connection = DriverManager.getConnection(
			    "jdbc:hsqldb:hsql://localhost/AchievementDB", "sa", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} finally {
			
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	/**
	 * Creates a user then checks to see if it can be recalled 
	 * from the database.
	 */
	public void testNewAndExistingPlayer() {
		
		Player testPlayer = new Player("TestUser");

		try {
			Connection testConn = null;

			try {
				
				testConn = DriverManager.getConnection(
						"jdbc:hsqldb:hsql://localhost/AchievementDB", "sa", "");
				ResultSet testRS = testConn.prepareStatement(
						"select name from players where name = 'TestUser'")
						.executeQuery();
				testRS.next();
				
				assertEquals("This should be the name we just entered.", "TestUser", testRS.getString(1));
				
				testRS.close();
				
				
			} catch (SQLException e) {

			} finally {
				testConn.close();
			}
		} catch (SQLException e) {

		}
		
	}
	
	public void tearDown() {
		
	}

}
