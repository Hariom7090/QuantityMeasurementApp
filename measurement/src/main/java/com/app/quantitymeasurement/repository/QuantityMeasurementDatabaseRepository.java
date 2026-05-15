package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.database.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);

    private static final String INSERT_QUERY =
            "INSERT INTO quantity_measurement_entity " +
                    "(operation, input, result, error) VALUES (?, ?, ?, ?)";

    private final ConnectionPool connectionPool;

    public QuantityMeasurementDatabaseRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        logger.info("Database Repository initialized");
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        Connection connection = null;

        try {
            logger.info("Saving entity to database: {}", entity);

            connection = connectionPool.getConnection();
            logger.debug("Database connection acquired");

            PreparedStatement statement =
                    connection.prepareStatement(INSERT_QUERY);

            statement.setString(1, entity.getOperation());
            statement.setString(2, entity.getInput());
            statement.setString(3, entity.getResult());
            statement.setBoolean(4, entity.hasError());

            int rowsAffected = statement.executeUpdate();

            logger.info("Entity saved successfully. Rows affected: {}", rowsAffected);

            statement.close();

        } catch (SQLException e) {

            logger.error("Failed to save entity to database: {}", e.getMessage(), e);

            throw new DatabaseException(
                    "Failed to save entity to database", e);

        } finally {

            if (connection != null) {
                connectionPool.releaseConnection(connection);
                logger.debug("Database connection released");
            }
        }
    }
}