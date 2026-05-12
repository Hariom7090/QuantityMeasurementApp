package com.quantity.measurement.serviceImpl;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.entity.Entity;
import com.quantity.measurement.enums.IMeasurable;
import com.quantity.measurement.enumsImpl.LengthUnit;
import com.quantity.measurement.enumsImpl.VolumeUnit;
import com.quantity.measurement.enumsImpl.WeightUnit;
import com.quantity.measurement.enumsImpl.TemperatureUnit;
import com.quantity.measurement.exception.Exception;
import com.quantity.measurement.model.Quantity;
import com.quantity.measurement.repository.Repository;
import com.quantity.measurement.service.Service;

public class ServiceImpl implements Service {

    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    private IMeasurable getUnit(String unit, String type) throws Exception {
        if (unit == null || type == null) {
            throw new Exception("Invalid unit or type");
        }

        return switch (type.toUpperCase()) {
            case "LENGTH" -> LengthUnit.valueOf(unit.toUpperCase());
            case "WEIGHT" -> WeightUnit.valueOf(unit.toUpperCase());
            case "VOLUME" -> VolumeUnit.valueOf(unit.toUpperCase());
            case "TEMPERATURE" -> TemperatureUnit.valueOf(unit.toUpperCase());
            default -> throw new Exception("Invalid type");
        };
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            Quantity<?> result = new Quantity<>(q1.getValue(), u1)
                    .add(new Quantity<>(q2.getValue(), u2), getUnit(targetUnit, q1.getMeasurementType()));

            repository.save(new Entity("ADD", "input", "success"));
            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new Entity("ADD", e.getMessage()));
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

            repository.save(new Entity("SUBTRACT", "input", "success"));
            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new Entity("SUBTRACT", e.getMessage()));
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

            repository.save(new Entity("DIVIDE", "input", "success"));
            return new QuantityDTO(result, "SCALAR", q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new Entity("DIVIDE", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO q, String targetUnit) {
        try {
            IMeasurable u = getUnit(q.getUnit(), q.getMeasurementType());

            Quantity<?> result = new Quantity<>(q.getValue(), u)
                    .toConvert(getUnit(targetUnit, q.getMeasurementType()));

            repository.save(new Entity("CONVERT", "input", "success"));
            return new QuantityDTO(result.getValue(), targetUnit, q.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new Entity("CONVERT", e.getMessage()));
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

            repository.save(new Entity("COMPARE", "input", "success"));
            return new QuantityDTO(result ? 1 : 0, "BOOLEAN", q1.getMeasurementType());
        } catch (java.lang.Exception e) {
            repository.save(new Entity("COMPARE", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }
}