package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.database.ConnectionPool;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        String sql = """
                CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                operation VARCHAR(100),
                input VARCHAR(255),
                result VARCHAR(255),
                error BOOLEAN
                )
                """;

        try {
            Connection connection =
                    ConnectionPool.getInstance().getConnection();

            Statement statement =
                    connection.createStatement();

            statement.execute(sql);

            statement.close();

            ConnectionPool.getInstance()
                    .releaseConnection(connection);

            System.out.println("Database Initialized");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}