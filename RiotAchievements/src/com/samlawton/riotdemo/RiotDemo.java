package com.samlawton.riotdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import org.hsqldb.Server;

import com.samlawton.riotdemo.game.Game;
import com.samlawton.riotdemo.game.Player;

public class RiotDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Server hsqlServer = null;
        try {
            hsqlServer = new Server();

            hsqlServer.setLogWriter(null);
            hsqlServer.setSilent(true);

            hsqlServer.setDatabaseName(0, "AchievementDB");
            hsqlServer.setDatabasePath(0, "file:AchievementDB");

            // Start the database!
            hsqlServer.start();

            setUpTestData();
    		
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
				
				// Leona - Will get Big Winner Achievement (has Sharpshooter/Bruiser)
				// Everything else is more or less randomized.
				connection.prepareStatement("insert into players values ('" + "Leona" + "','" +
						DateFormat.getDateTimeInstance().format(new Date()) + "'," +
	        			750 + "," +
	        			499 + "," +	//needs 500th win
	        			251 + "," +
	        			200000 + "," +
	        			80.0 + "," +
	        			30000000 + "," +
	        			500 + "," +
	        			120 + "," +
	        			800 + "," +
	        			100000 + "," +
	        			2000000 + "," +
	        			1000000 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Leona" + "'," +
            			true + "," +
            			true + "," +
            			false + "," +
            			false + ")").execute();
				
				// Diana - Will get Veteran Achievement (has Big Winner/Sharpshooter/Bruiser)
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
	        			1000000 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Diana" + "'," +
            			true + "," +
            			true + "," +
            			false + "," +
            			true + ")").execute();
				
				// Irelia - Will get Sharpshooter Achievement (has Bruiser)
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
	        			50000 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Irelia" + "'," +
            			false + "," +
            			true + "," +
            			false + "," +
            			false + ")").execute();
				
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
	        			50000 + ")").execute();
				
				connection.prepareStatement("insert into playerAchievements values (NULL,'" +
            			"Riven" + "'," +
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
		
		Player pLeona = new Player("Leona");
		Player pDiana = new Player("Diana");
		Player pIrelia = new Player("Irelia");
		Player pRiven = new Player("Riven");
		
		pLeona.printPlayersAchievementData();
		pDiana.printPlayersAchievementData();
		pIrelia.printPlayersAchievementData();
		pRiven.printPlayersAchievementData();
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
				
				connection.prepareStatement("delete from playerAchievements where userName = 'Leona'").execute();
				connection.prepareStatement("delete from playerAchievements where userName = 'Diana'").execute();
				connection.prepareStatement("delete from playerAchievements where userName = 'Irelia'").execute();
				connection.prepareStatement("delete from playerAchievements where userName = 'Riven'").execute();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				connection.close();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}
