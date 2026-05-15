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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceImpl implements Service {

    private static final Logger logger =
            LoggerFactory.getLogger(ServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public ServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("ServiceImpl initialized");
    }

    private IMeasurable getUnit(String unit, String type)
            throws QuantityMeasurementException {

        logger.debug("Getting unit: {} for type: {}", unit, type);

        if (unit == null || type == null) {
            logger.error("Unit or type is null");
            throw new QuantityMeasurementException(
                    "Invalid unit or type"
            );
        }

        return switch (type.toUpperCase()) {
            case "LENGTH" ->
                    LengthUnit.valueOf(unit.toUpperCase());

            case "WEIGHT" ->
                    WeightUnit.valueOf(unit.toUpperCase());

            case "VOLUME" ->
                    VolumeUnit.valueOf(unit.toUpperCase());

            case "TEMPERATURE" ->
                    TemperatureUnit.valueOf(unit.toUpperCase());

            default -> {
                logger.error("Invalid measurement type: {}", type);
                throw new QuantityMeasurementException(
                        "Invalid type"
                );
            }
        };
    }

    @Override
    public QuantityDTO add(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit) {

        try {

            logger.info(
                    "Addition started: {} {} + {} {}",
                    q1.getValue(),
                    q1.getUnit(),
                    q2.getValue(),
                    q2.getUnit()
            );

            IMeasurable u1 =
                    getUnit(q1.getUnit(),
                            q1.getMeasurementType());

            IMeasurable u2 =
                    getUnit(q2.getUnit(),
                            q2.getMeasurementType());

            Quantity<?> result =
                    new Quantity<>(q1.getValue(), u1)
                            .add(
                                    new Quantity<>(
                                            q2.getValue(), u2
                                    ),
                                    getUnit(
                                            targetUnit,
                                            q1.getMeasurementType()
                                    )
                            );

            logger.info(
                    "Addition successful. Result = {} {}",
                    result.getValue(),
                    targetUnit
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "ADD",
                            "input",
                            "success"
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

            logger.error(
                    "Addition failed: {}",
                    e.getMessage(),
                    e
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "ADD",
                            e.getMessage()
                    )
            );

            return new QuantityDTO(
                    true,
                    e.getMessage()
            );
        }
    }

    @Override
    public QuantityDTO subtract(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit) {

        try {

            logger.info(
                    "Subtraction started: {} {} - {} {}",
                    q1.getValue(),
                    q1.getUnit(),
                    q2.getValue(),
                    q2.getUnit()
            );

            IMeasurable u1 =
                    getUnit(q1.getUnit(),
                            q1.getMeasurementType());

            IMeasurable u2 =
                    getUnit(q2.getUnit(),
                            q2.getMeasurementType());

            Quantity<?> result =
                    new Quantity<>(q1.getValue(), u1)
                            .subtract(
                                    new Quantity<>(
                                            q2.getValue(), u2
                                    ),
                                    getUnit(
                                            targetUnit,
                                            q1.getMeasurementType()
                                    )
                            );

            logger.info(
                    "Subtraction successful. Result = {} {}",
                    result.getValue(),
                    targetUnit
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "SUBTRACT",
                            "input",
                            "success"
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

            logger.error(
                    "Subtraction failed: {}",
                    e.getMessage(),
                    e
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "SUBTRACT",
                            e.getMessage()
                    )
            );

            return new QuantityDTO(
                    true,
                    e.getMessage()
            );
        }
    }

    @Override
    public QuantityDTO divide(
            QuantityDTO q1,
            QuantityDTO q2) {

        try {

            logger.info(
                    "Division started: {} {} / {} {}",
                    q1.getValue(),
                    q1.getUnit(),
                    q2.getValue(),
                    q2.getUnit()
            );

            IMeasurable u1 =
                    getUnit(q1.getUnit(),
                            q1.getMeasurementType());

            IMeasurable u2 =
                    getUnit(q2.getUnit(),
                            q2.getMeasurementType());

            double result =
                    new Quantity<>(q1.getValue(), u1)
                            .divide(
                                    new Quantity<>(
                                            q2.getValue(),
                                            u2
                                    )
                            );

            logger.info(
                    "Division successful. Result = {}",
                    result
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "DIVIDE",
                            "input",
                            "success"
                    )
            );

            return new QuantityDTO(
                    result,
                    "SCALAR",
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

            logger.error(
                    "Division failed: {}",
                    e.getMessage(),
                    e
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "DIVIDE",
                            e.getMessage()
                    )
            );

            return new QuantityDTO(
                    true,
                    e.getMessage()
            );
        }
    }

    @Override
    public QuantityDTO convert(
            QuantityDTO q,
            String targetUnit) {

        try {

            logger.info(
                    "Conversion started: {} {} to {}",
                    q.getValue(),
                    q.getUnit(),
                    targetUnit
            );

            IMeasurable u =
                    getUnit(q.getUnit(),
                            q.getMeasurementType());

            Quantity<?> result =
                    new Quantity<>(q.getValue(), u)
                            .toConvert(
                                    getUnit(
                                            targetUnit,
                                            q.getMeasurementType()
                                    )
                            );

            logger.info(
                    "Conversion successful. Result = {} {}",
                    result.getValue(),
                    targetUnit
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "CONVERT",
                            "input",
                            "success"
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    q.getMeasurementType()
            );

        } catch (Exception e) {

            logger.error(
                    "Conversion failed: {}",
                    e.getMessage(),
                    e
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "CONVERT",
                            e.getMessage()
                    )
            );

            return new QuantityDTO(
                    true,
                    e.getMessage()
            );
        }
    }

    @Override
    public QuantityDTO compare(
            QuantityDTO q1,
            QuantityDTO q2) {

        try {

            logger.info(
                    "Comparison started: {} {} and {} {}",
                    q1.getValue(),
                    q1.getUnit(),
                    q2.getValue(),
                    q2.getUnit()
            );

            if (!q1.getMeasurementType()
                    .equalsIgnoreCase(
                            q2.getMeasurementType())) {

                logger.warn(
                        "Cross-category comparison attempted"
                );

                throw new Exception(
                        "Cross-category comparison not allowed"
                );
            }

            IMeasurable u1 =
                    getUnit(q1.getUnit(),
                            q1.getMeasurementType());

            IMeasurable u2 =
                    getUnit(q2.getUnit(),
                            q2.getMeasurementType());

            boolean result =
                    new Quantity<>(q1.getValue(), u1)
                            .equals(
                                    new Quantity<>(
                                            q2.getValue(),
                                            u2
                                    )
                            );

            logger.info(
                    "Comparison successful. Result = {}",
                    result
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "COMPARE",
                            "input",
                            "success"
                    )
            );

            return new QuantityDTO(
                    result ? 1 : 0,
                    "BOOLEAN",
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

            logger.error(
                    "Comparison failed: {}",
                    e.getMessage(),
                    e
            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "COMPARE",
                            e.getMessage()
                    )
            );

            return new QuantityDTO(
                    true,
                    e.getMessage()
            );
        }
    }
}