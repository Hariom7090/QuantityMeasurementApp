package com.quantity.measurement.controller;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.service.Service;


public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public QuantityDTO performAdd(QuantityDTO q1, QuantityDTO q2, String target) {
        return service.add(q1, q2, target);
    }

    public QuantityDTO performSubtract(QuantityDTO q1, QuantityDTO q2, String target) {
        return service.subtract(q1, q2, target);
    }

    public QuantityDTO performDivide(QuantityDTO q1, QuantityDTO q2) {
        return service.divide(q1, q2);
    }

    public QuantityDTO performConvert(QuantityDTO q, String target) {
        return service.convert(q, target);
    }

    public QuantityDTO performCompare(QuantityDTO q1, QuantityDTO q2) {
        return service.compare(q1, q2);
    }
}