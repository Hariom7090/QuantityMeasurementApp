package com.app.quantitymeasurement.serviceImpl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.enums.IMeasurable;
import com.app.quantitymeasurement.enumsImpl.LengthUnit;
import com.app.quantitymeasurement.enumsImpl.TemperatureUnit;
import com.app.quantitymeasurement.enumsImpl.VolumeUnit;
import com.app.quantitymeasurement.enumsImpl.WeightUnit;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.Quantity;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl
        implements QuantityMeasurementService {

    private static final Logger logger =
            LoggerFactory.getLogger(ServiceImpl.class);

    private final QuantityMeasurementRepository repository;

    public ServiceImpl(
            QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable getUnit(
            String unit,
            String type) {

        if (unit == null || type == null) {
            throw new QuantityMeasurementException(
                    "Invalid unit or type"
            );
        }

        try {

            return switch (type.toUpperCase()) {

                case "LENGTH",
                     "LENGTHUNIT" ->
                        LengthUnit.valueOf(
                                unit.toUpperCase()
                        );

                case "WEIGHT",
                     "WEIGHTUNIT" ->
                        WeightUnit.valueOf(
                                unit.toUpperCase()
                        );

                case "VOLUME",
                     "VOLUMEUNIT" ->
                        VolumeUnit.valueOf(
                                unit.toUpperCase()
                        );

                case "TEMPERATURE",
                     "TEMPERATUREUNIT" ->
                        TemperatureUnit.valueOf(
                                unit.toUpperCase()
                        );

                default ->
                        throw new QuantityMeasurementException(
                                "Invalid measurement type: " + type
                        );
            };

        } catch (IllegalArgumentException e) {

            throw new QuantityMeasurementException(
                    "Invalid unit '" + unit +
                            "' for measurement type '" +
                            type + "'"
            );
        }
    }

    @Override
    public QuantityDTO add(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit) {

        try {

            IMeasurable u1 =
                    getUnit(
                            q1.getUnit(),
                            q1.getMeasurementType());

            IMeasurable u2 =
                    getUnit(
                            q2.getUnit(),
                            q2.getMeasurementType());

            Quantity<?> result =
                    new Quantity<>(q1.getValue(), u1)
                            .add(
                                    new Quantity<>(
                                            q2.getValue(),
                                            u2
                                    ),
                                    getUnit(
                                            targetUnit,
                                            q1.getMeasurementType()
                                    )
                            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "ADD",
                            q1.toString() + " + " + q2,
                            String.valueOf(result.getValue())
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

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
                                            q2.getValue(),
                                            u2
                                    ),
                                    getUnit(
                                            targetUnit,
                                            q1.getMeasurementType()
                                    )
                            );

            repository.save(
                    new QuantityMeasurementEntity(
                            "SUBTRACT",
                            q1 + " - " + q2,
                            String.valueOf(result.getValue())
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

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

            repository.save(
                    new QuantityMeasurementEntity(
                            "DIVIDE",
                            q1 + " / " + q2,
                            String.valueOf(result)
                    )
            );

            return new QuantityDTO(
                    result,
                    "SCALAR",
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

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

            repository.save(
                    new QuantityMeasurementEntity(
                            "CONVERT",
                            q.toString(),
                            String.valueOf(result.getValue())
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    q.getMeasurementType()
            );

        } catch (Exception e) {

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

            repository.save(
                    new QuantityMeasurementEntity(
                            "COMPARE",
                            q1 + " vs " + q2,
                            String.valueOf(result)
                    )
            );

            return new QuantityDTO(
                    result ? 1 : 0,
                    "BOOLEAN",
                    q1.getMeasurementType()
            );

        } catch (Exception e) {

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

    @Override
    public List<QuantityMeasurementEntity>
    getHistoryByOperation(String operation) {

        return repository.findByOperation(operation);
    }

    @Override
    public List<QuantityMeasurementEntity>
    getErrorHistory() {

        return repository.findByErrorTrue();
    }

    @Override
    public long countByOperation(String operation) {

        return repository.findByOperation(operation)
                .size();
    }
}