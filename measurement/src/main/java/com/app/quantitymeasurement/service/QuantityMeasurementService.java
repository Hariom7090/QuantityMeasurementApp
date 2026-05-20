package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

public interface QuantityMeasurementService {

    QuantityDTO add(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit
    );

    QuantityDTO subtract(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit
    );

    QuantityDTO divide(
            QuantityDTO q1,
            QuantityDTO q2
    );

    QuantityDTO convert(
            QuantityDTO q,
            String targetUnit
    );

    QuantityDTO compare(
            QuantityDTO q1,
            QuantityDTO q2
    );

    // UC17 new methods

    List<QuantityMeasurementEntity>
    getHistoryByOperation(String operation);

    List<QuantityMeasurementEntity>
    getErrorHistory();

    long countByOperation(String operation);
}