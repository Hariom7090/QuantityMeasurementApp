package com.app.quantitymeasurement.serviceImpl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.enums.IMeasurable;
import com.app.quantitymeasurement.enumsImpl.LengthUnit;
import com.app.quantitymeasurement.enumsImpl.VolumeUnit;
import com.app.quantitymeasurement.enumsImpl.WeightUnit;
import com.app.quantitymeasurement.enumsImpl.TemperatureUnit;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.Quantity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.service.Service;

public class ServiceImpl implements Service {

    private final IQuantityMeasurementRepository repository;

    public ServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable getUnit(String unit, String type) throws QuantityMeasurementException {
        if (unit == null || type == null) {
            throw new QuantityMeasurementException("Invalid unit or type");
        }

        return switch (type.toUpperCase()) {
            case "LENGTH" -> LengthUnit.valueOf(unit.toUpperCase());
            case "WEIGHT" -> WeightUnit.valueOf(unit.toUpperCase());
            case "VOLUME" -> VolumeUnit.valueOf(unit.toUpperCase());
            case "TEMPERATURE" -> TemperatureUnit.valueOf(unit.toUpperCase());
            default -> throw new QuantityMeasurementException("Invalid type");
        };
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            Quantity<?> result = new Quantity<>(q1.getValue(), u1)
                    .add(new Quantity<>(q2.getValue(), u2), getUnit(targetUnit, q1.getMeasurementType()));

            repository.save(new QuantityMeasurementEntity("ADD", "input", "success"));
            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new QuantityMeasurementEntity("ADD", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            Quantity<?> result = new Quantity<>(q1.getValue(), u1)
                    .subtract(new Quantity<>(q2.getValue(), u2), getUnit(targetUnit, q1.getMeasurementType()));

            repository.save(new QuantityMeasurementEntity("SUBTRACT", "input", "success"));
            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new QuantityMeasurementEntity("SUBTRACT", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO divide(QuantityDTO q1, QuantityDTO q2) {
        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            double result = new Quantity<>(q1.getValue(), u1)
                    .divide(new Quantity<>(q2.getValue(), u2));

            repository.save(new QuantityMeasurementEntity("DIVIDE", "input", "success"));
            return new QuantityDTO(result, "SCALAR", q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new QuantityMeasurementEntity("DIVIDE", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO q, String targetUnit) {
        try {
            IMeasurable u = getUnit(q.getUnit(), q.getMeasurementType());

            Quantity<?> result = new Quantity<>(q.getValue(), u)
                    .toConvert(getUnit(targetUnit, q.getMeasurementType()));

            repository.save(new QuantityMeasurementEntity("CONVERT", "input", "success"));
            return new QuantityDTO(result.getValue(), targetUnit, q.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new QuantityMeasurementEntity("CONVERT", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO compare(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equalsIgnoreCase(q2.getMeasurementType())) {
                throw new java.lang.Exception("Cross-category comparison not allowed");
            }

            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            boolean result = new Quantity<>(q1.getValue(), u1)
                    .equals(new Quantity<>(q2.getValue(), u2));

            repository.save(new QuantityMeasurementEntity("COMPARE", "input", "success"));
            return new QuantityDTO(result ? 1 : 0, "BOOLEAN", q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new QuantityMeasurementEntity("COMPARE", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }
}