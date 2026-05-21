package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    private final QuantityMeasurementService service;

    @Autowired
    public QuantityMeasurementController(QuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public QuantityDTO add(@RequestBody QuantityInputDTO inputDTO) {
        return service.add(
                inputDTO.getThisQuantityDTO(),
                inputDTO.getThatQuantityDTO(),
                inputDTO.getTargetUnit()
        );
    }

    @PostMapping("/subtract")
    public QuantityDTO subtract(@RequestBody QuantityInputDTO inputDTO) {
        return service.subtract(
                inputDTO.getThisQuantityDTO(),
                inputDTO.getThatQuantityDTO(),
                inputDTO.getTargetUnit()
        );
    }

    @PostMapping("/divide")
    public QuantityDTO divide(@RequestBody QuantityInputDTO inputDTO) {
        return service.divide(
                inputDTO.getThisQuantityDTO(),
                inputDTO.getThatQuantityDTO()
        );
    }

    @PostMapping("/convert")
    public QuantityDTO convert(@RequestBody QuantityInputDTO inputDTO) {
        return service.convert(
                inputDTO.getThisQuantityDTO(),
                inputDTO.getTargetUnit()
        );
    }

    @PostMapping("/compare")
    public QuantityDTO compare(@RequestBody QuantityInputDTO inputDTO) {
        return service.compare(
                inputDTO.getThisQuantityDTO(),
                inputDTO.getThatQuantityDTO()
        );
    }
}