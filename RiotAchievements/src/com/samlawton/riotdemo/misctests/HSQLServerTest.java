package com.samlawton.riotdemo.misctests;

import java.sql.SQLException;
import java.util.Scanner;

import org.hsqldb.Server;

public class HSQLServerTest {

    public static void main(String[] args) throws
        ClassNotFoundException, SQLException {

        // 'Server' is a class of HSQLDB representing
        // the database server
        Server hsqlServer = null;
        try {
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
            
            System.out.println("Starting HSQL Server...");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Press Enter to stop server: ");
            scanner.nextLine();
            System.out.println("Stopping HSQL Server.");

        } finally {
            // Closing the server
            if (hsqlServer != null) {
                hsqlServer.stop();
            }
        }
    }
}
