package com.app.quantitymeasurement.dump;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);
}