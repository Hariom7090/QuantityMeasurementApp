package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuantityDTO {

    @NotNull(message = "Value cannot be null")
    private Double value;

    @NotBlank(message = "Unit cannot be empty")
    private String unit;

    @NotBlank(message = "Measurement type cannot be empty")
    private String measurementType;

    private boolean error;
    private String errorMessage;

    // Default constructor (important for JSON)
    public QuantityDTO() {
    }

    // Success constructor
    public QuantityDTO(
            double value,
            String unit,
            String measurementType
    ) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    // Error constructor
    public QuantityDTO(
            boolean error,
            String errorMessage
    ) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(
            String measurementType
    ) {
        this.measurementType =
                measurementType;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(
            String errorMessage
    ) {
        this.errorMessage =
                errorMessage;
    }

    @Override
    public String toString() {
        return "QuantityDTO{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", measurementType='" + measurementType + '\'' +
                '}';
    }
}