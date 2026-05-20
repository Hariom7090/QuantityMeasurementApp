package com.app.quantitymeasurement.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_entity")
public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operation;

    @Column(length = 500)
    private String input;

    @Column(length = 500)
    private String result;

    @Column(nullable = false)
    private boolean error;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Default constructor required by JPA
    public QuantityMeasurementEntity() {
    }

    // Success constructor
    public QuantityMeasurementEntity(String operation,
                                     String input,
                                     String result) {
        this.operation = operation;
        this.input = input;
        this.result = result;
        this.error = false;
    }

    // Error constructor
    public QuantityMeasurementEntity(String operation,
                                     String errorMessage) {
        this.operation = operation;
        this.input = null;
        this.result = errorMessage;
        this.error = true;
    }

    // Auto set timestamp before save
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getInput() {
        return input;
    }

    public String getResult() {
        return result;
    }

    public boolean isError() {
        return error;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        if (error) {
            return "QuantityMeasurementEntity{" +
                    "id=" + id +
                    ", operation='" + operation + '\'' +
                    ", error='" + result + '\'' +
                    '}';
        }

        return "QuantityMeasurementEntity{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", input='" + input + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
    public boolean hasError() {
        return error;
    }
}