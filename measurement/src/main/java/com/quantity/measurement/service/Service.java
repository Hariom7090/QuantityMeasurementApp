package com.quantity.measurement.service;

import com.quantity.measurement.dto.QuantityDTO;

// Application/service layer contract
// Purpose:
// 1. Defines what operations your application provides
// 2. Wraps your domain logic (Quantity)

public interface Service {

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityDTO divide(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO convert(QuantityDTO q, String targetUnit);

    QuantityDTO compare(QuantityDTO q1, QuantityDTO q2);
}