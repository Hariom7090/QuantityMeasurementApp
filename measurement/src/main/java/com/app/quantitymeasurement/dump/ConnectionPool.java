package com.app.quantitymeasurement.dump;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private static final Logger logger =
            LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;

    private final Queue<Connection> availableConnections =
            new LinkedList<>();

    private static final int POOL_SIZE = 5;

    // H2 Database Configuration
    private static final String DB_URL =
            "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private ConnectionPool() {

        try {

            logger.info("Creating connection pool...");

            for (int i = 0; i < POOL_SIZE; i++) {

                Connection connection =
                        DriverManager.getConnection(
                                DB_URL,
                                USERNAME,
                                PASSWORD
                        );

                availableConnections.add(connection);

                logger.info(
                        "Database connection {} created successfully",
                        i + 1
                );
            }

            initializeDatabase();

            logger.info(
                    "Connection pool initialized successfully"
            );

        } catch (SQLException e) {

            logger.error(
                    "Error creating connection pool",
                    e
            );

            throw new RuntimeException(
                    "Error creating connection pool",
                    e
            );
        }
    }

    public static synchronized ConnectionPool getInstance() {

        if (instance == null) {

            logger.info(
                    "Creating new ConnectionPool instance"
            );

            instance = new ConnectionPool();
        }

        return instance;
    }

    public synchronized Connection getConnection() {

        if (availableConnections.isEmpty()) {

            logger.error(
                    "No available database connections"
            );

            throw new RuntimeException(
                    "No available database connections"
            );
        }

        logger.info("Connection fetched from pool");

        return availableConnections.poll();
    }

    public synchronized void releaseConnection(
            Connection connection
    ) {

        if (connection != null) {

            availableConnections.offer(connection);

            logger.info(
                    "Connection returned to pool"
            );
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

            Connection connection =
                    availableConnections.peek();

            if (connection != null) {

                Statement statement =
                        connection.createStatement();

                statement.execute(createTableQuery);

                statement.close();

                logger.info(
                        "Table quantity_measurement_entity created successfully"
                );
            }

        } catch (SQLException e) {

            logger.error(
                    "Failed to initialize database",
                    e
            );

            throw new RuntimeException(
                    "Failed to initialize database",
                    e
            );
        }
    }
}