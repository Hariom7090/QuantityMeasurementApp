package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementController {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    QuantityMeasurementController.class
            );

    private final Service service;

    public QuantityMeasurementController(Service service) {
        this.service = service;
        logger.info("QuantityMeasurementController initialized");
    }

    public QuantityDTO performAdd(
            QuantityDTO q1,
            QuantityDTO q2,
            String target
    ) {

        logger.info(
                "Controller received ADD request: {} {} + {} {}",
                q1.getValue(),
                q1.getUnit(),
                q2.getValue(),
                q2.getUnit()
        );

        QuantityDTO result =
                service.add(q1, q2, target);

        logger.info(
                "ADD operation completed"
        );

        return result;
    }

    public QuantityDTO performSubtract(
            QuantityDTO q1,
            QuantityDTO q2,
            String target
    ) {

        logger.info(
                "Controller received SUBTRACT request"
        );

        QuantityDTO result =
                service.subtract(q1, q2, target);

        logger.info(
                "SUBTRACT operation completed"
        );

        return result;
    }

    public QuantityDTO performDivide(
            QuantityDTO q1,
            QuantityDTO q2
    ) {

        logger.info(
                "Controller received DIVIDE request"
        );

        QuantityDTO result =
                service.divide(q1, q2);

        logger.info(
                "DIVIDE operation completed"
        );

        return result;
    }

    public QuantityDTO performConvert(
            QuantityDTO q,
            String target
    ) {

        logger.info(
                "Controller received CONVERT request: {} {} -> {}",
                q.getValue(),
                q.getUnit(),
                target
        );

        QuantityDTO result =
                service.convert(q, target);

        logger.info(
                "CONVERT operation completed"
        );

        return result;
    }

    public QuantityDTO performCompare(
            QuantityDTO q1,
            QuantityDTO q2
    ) {

        logger.info(
                "Controller received COMPARE request"
        );

        QuantityDTO result =
                service.compare(q1, q2);

        logger.info(
                "COMPARE operation completed"
        );

        return result;
    }
}