CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
                                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                                           operation VARCHAR(255),
    input VARCHAR(255),
    result VARCHAR(255),
    error BOOLEAN
    );