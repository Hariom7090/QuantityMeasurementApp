package com.app.quantitymeasurement.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private static ConnectionPool instance;

    private final Queue<Connection> availableConnections = new LinkedList<>();

    private static final int POOL_SIZE = 5;

    // H2 In-Memory Database URL
    private static final String DB_URL =
            "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private ConnectionPool() {
        try {

            for (int i = 0; i < POOL_SIZE; i++) {

                Connection connection = DriverManager.getConnection(
                        DB_URL,
                        USERNAME,
                        PASSWORD
                );

                availableConnections.add(connection);
            }

            // Create table automatically
            initializeDatabase();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error creating connection pool", e
            );
        }
    }

    public static synchronized ConnectionPool getInstance() {

        if (instance == null) {
            instance = new ConnectionPool();
        }

        return instance;
    }

    public synchronized Connection getConnection() {

        if (availableConnections.isEmpty()) {
            throw new RuntimeException(
                    "No available database connections"
            );
        }

        return availableConnections.poll();
    }

    public synchronized void releaseConnection(
            Connection connection
    ) {

        if (connection != null) {
            availableConnections.offer(connection);
        }
    }

    // Create table automatically
    private void initializeDatabase() {

        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    operation VARCHAR(255),
                    input VARCHAR(255),
                    result VARCHAR(255),
                    error BOOLEAN
                )
                """;

        try {

            Connection connection = availableConnections.peek();

            if (connection != null) {

                Statement statement =
                        connection.createStatement();

                statement.execute(createTableQuery);

                statement.close();
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to initialize database", e
            );
        }
    }
}