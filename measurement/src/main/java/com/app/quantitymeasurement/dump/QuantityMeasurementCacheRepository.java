package com.app.quantitymeasurement.dump;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private final List<QuantityMeasurementEntity> cache =
            new ArrayList<>();

    // Public constructor for Spring Bean creation
    public QuantityMeasurementCacheRepository() {
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
    }

    public List<QuantityMeasurementEntity> getAll() {
        return cache;
    }
}