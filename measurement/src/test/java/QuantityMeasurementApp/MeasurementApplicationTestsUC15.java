package QuantityMeasurementApp;

import com.quantity.measurement.controller.Controller;
import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.entity.Entity;
import com.quantity.measurement.repository.CacheRepository;
import com.quantity.measurement.repository.Repository;
import com.quantity.measurement.service.Service;
import com.quantity.measurement.serviceImpl.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementApplicationTestsUC15 {

    private Repository repository;
    private Service service;
    private Controller controller;

    @BeforeEach
    void setUp() {
        repository = CacheRepository.getInstance();
        service = new ServiceImpl(repository);
        controller = new Controller(service);
    }

    // --- Entity Tests ---

    @Test
    void testQuantityEntity_SingleOperandConstruction() {
        Entity entity = new Entity("CONVERT", "1.0 FEET", "12.0 INCH");
        assertEquals("CONVERT", entity.getOperation());
        assertEquals("1.0 FEET", entity.getInput());
        assertEquals("12.0 INCH", entity.getResult());
        assertFalse(entity.hasError());
    }

    @Test
    void testQuantityEntity_BinaryOperandConstruction() {
        Entity entity = new Entity("ADD", "1.0 FEET + 1.0 FEET", "2.0 FEET");
        assertEquals("ADD", entity.getOperation());
        assertEquals("1.0 FEET + 1.0 FEET", entity.getInput());
        assertEquals("2.0 FEET", entity.getResult());
        assertFalse(entity.hasError());
    }

    @Test
    void testQuantityEntity_ErrorConstruction() {
        Entity entity = new Entity("ADD", "Unsupported operation");
        assertEquals("ADD", entity.getOperation());
        assertNull(entity.getInput());
        assertEquals("Unsupported operation", entity.getResult());
        assertTrue(entity.hasError());
    }

    @Test
    void testQuantityEntity_ToString_Success() {
        Entity entity = new Entity("CONVERT", "1.0 FEET", "12.0 INCH");
        String str = entity.toString();
        assertTrue(str.contains("CONVERT"));
        assertTrue(str.contains("1.0 FEET"));
        assertTrue(str.contains("12.0 INCH"));
    }

    @Test
    void testQuantityEntity_ToString_Error() {
        Entity entity = new Entity("ADD", "Error occurred");
        String str = entity.toString();
        assertTrue(str.contains("ADD"));
        assertTrue(str.contains("Error occurred"));
    }

    // --- Service Tests ---

    @Test
    void testService_CompareEquality_SameUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = service.compare(q1, q2);
        assertFalse(result.isError());
        assertEquals(1.0, result.getValue());
    }

    @Test
    void testService_CompareEquality_DifferentUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = service.compare(q1, q2);
        assertFalse(result.isError());
        assertEquals(1.0, result.getValue());
    }

    @Test
    void testService_CompareEquality_CrossCategory_Error() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1.0, "GALLON", "VOLUME");
        QuantityDTO result = service.compare(q1, q2);
        assertTrue(result.isError());
    }

    @Test
    void testService_Convert_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = service.convert(q1, "INCH");
        assertFalse(result.isError());
        assertEquals(12.0, result.getValue());
        assertEquals("INCH", result.getUnit());
    }

    @Test
    void testService_Add_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = service.add(q1, q2, "FEET");
        assertFalse(result.isError());
        assertEquals(2.0, result.getValue());
        assertEquals("FEET", result.getUnit());
    }

    @Test
    void testService_Add_UnsupportedOperation_Error() {
        QuantityDTO q1 = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO q2 = new QuantityDTO(212.0, "FAHRENHEIT", "TEMPERATURE");
        QuantityDTO result = service.add(q1, q2, "CELSIUS");
        assertTrue(result.isError());
    }

    @Test
    void testService_Subtract_Success() {
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = service.subtract(q1, q2, "FEET");
        assertFalse(result.isError());
        assertEquals(1.0, result.getValue());
        assertEquals("FEET", result.getUnit());
    }

    @Test
    void testService_Divide_Success() {
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = service.divide(q1, q2);
        assertFalse(result.isError());
        assertEquals(2.0, result.getValue());
    }

    @Test
    void testService_Divide_ByZero_Error() {
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(0.0, "FEET", "LENGTH");
        QuantityDTO result = service.divide(q1, q2);
        assertTrue(result.isError());
    }

    // --- Controller Tests ---

    @Test
    void testController_DemonstrateEquality_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = controller.performCompare(q1, q2);
        assertFalse(result.isError());
        assertEquals(1.0, result.getValue());
    }

    @Test
    void testController_DemonstrateConversion_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = controller.performConvert(q1, "INCH");
        assertFalse(result.isError());
        assertEquals(12.0, result.getValue());
    }

    @Test
    void testController_DemonstrateAddition_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = controller.performAdd(q1, q2, "FEET");
        assertFalse(result.isError());
        assertEquals(2.0, result.getValue());
    }

    @Test
    void testController_DemonstrateAddition_Error() {
        QuantityDTO q1 = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO q2 = new QuantityDTO(212.0, "FAHRENHEIT", "TEMPERATURE");
        QuantityDTO result = controller.performAdd(q1, q2, "CELSIUS");
        assertTrue(result.isError());
        assertNotNull(result.getErrorMessage());
    }

    @Test
    void testController_DisplayResult_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = controller.performConvert(q1, "INCH");
        String formatted = String.format("%.1f %s", result.getValue(), result.getUnit());
        assertEquals("12.0 INCH", formatted);
    }

    @Test
    void testController_DisplayResult_Error() {
        QuantityDTO q1 = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO q2 = new QuantityDTO(212.0, "FAHRENHEIT", "TEMPERATURE");
        QuantityDTO result = controller.performAdd(q1, q2, "CELSIUS");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().length() > 0);
    }

    // --- Layer Separation Tests ---

    @Test
    void testLayerSeparation_ServiceIndependence() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = service.convert(q1, "INCH");
        assertFalse(result.isError());
        assertEquals(12.0, result.getValue());
    }

    @Test
    void testLayerSeparation_ControllerIndependence() {
        Service mockService = new Service() {
            @Override
            public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
                return new QuantityDTO(5.0, targetUnit, "LENGTH");
            }

            @Override
            public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) { return null; }
            @Override
            public QuantityDTO divide(QuantityDTO q1, QuantityDTO q2) { return null; }
            @Override
            public QuantityDTO convert(QuantityDTO q, String targetUnit) { return null; }
            @Override
            public QuantityDTO compare(QuantityDTO q1, QuantityDTO q2) { return null; }
        };
        Controller mockController = new Controller(mockService);
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(3.0, "FEET", "LENGTH");
        QuantityDTO result = mockController.performAdd(q1, q2, "FEET");
        assertEquals(5.0, result.getValue());
    }

    @Test
    void testDataFlow_ControllerToService() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = controller.performConvert(q1, "INCH");
        assertEquals(12.0, result.getValue());
        assertEquals("INCH", result.getUnit());
    }

    @Test
    void testDataFlow_ServiceToController() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = controller.performAdd(q1, q2, "FEET");
        assertFalse(result.isError());
        assertEquals(2.0, result.getValue());
    }

    @Test
    void testBackwardCompatibility_AllUC1_UC14_Tests() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO result = controller.performAdd(q1, q2, "FEET");
        assertEquals(2.0, result.getValue());
    }

    @Test
    void testService_AllMeasurementCategories() {
        assertFalse(service.convert(new QuantityDTO(1.0, "FEET", "LENGTH"), "INCH").isError());
        assertFalse(service.convert(new QuantityDTO(1.0, "KILOGRAM", "WEIGHT"), "GRAM").isError());
        assertFalse(service.convert(new QuantityDTO(1.0, "GALLON", "VOLUME"), "LITRE").isError());
        assertFalse(service.convert(new QuantityDTO(0.0, "CELSIUS", "TEMPERATURE"), "FAHRENHEIT").isError());
    }

    @Test
    void testController_AllOperations() {
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTH");

        assertFalse(controller.performAdd(q1, q2, "FEET").isError());
        assertFalse(controller.performSubtract(q1, q2, "FEET").isError());
        assertFalse(controller.performDivide(q1, q2).isError());
        assertFalse(controller.performConvert(q1, "INCH").isError());
        assertFalse(controller.performCompare(q1, q2).isError());
    }

    @Test
    void testService_ValidationConsistency() {
        QuantityDTO qInvalid = new QuantityDTO(1.0, "INVALID", "LENGTH");
        assertTrue(service.convert(qInvalid, "FEET").isError());
        assertTrue(service.add(qInvalid, qInvalid, "FEET").isError());
    }

    @Test
    void testEntity_Immutability() {
        Entity entity = new Entity("ADD", "1+1", "2");
        assertEquals("ADD", entity.getOperation());
        assertEquals("1+1", entity.getInput());
        assertEquals("2", entity.getResult());
    }

    @Test
    void testService_ExceptionHandling_AllOperations() {
        QuantityDTO qNull = new QuantityDTO(1.0, null, null);
        assertTrue(service.add(qNull, qNull, "FEET").isError());
        assertTrue(service.subtract(qNull, qNull, "FEET").isError());
        assertTrue(service.divide(qNull, qNull).isError());
        assertTrue(service.convert(qNull, "FEET").isError());
        assertTrue(service.compare(qNull, qNull).isError());
    }

    @Test
    void testIntegration_EndToEnd_LengthAddition() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = controller.performAdd(q1, q2, "FEET");
        assertEquals(2.0, result.getValue());
        assertEquals("FEET", result.getUnit());
    }

    @Test
    void testIntegration_EndToEnd_TemperatureUnsupported() {
        QuantityDTO q1 = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO q2 = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO result = controller.performAdd(q1, q2, "CELSIUS");
        assertTrue(result.isError());
    }

    @Test
    void testService_NullEntity_Rejection() {
        QuantityDTO result = service.convert(null, "FEET");
        assertTrue(result.isError());
    }

    @Test
    void testLayerDecoupling_ServiceChange() {
        Controller decoupledController = new Controller(new ServiceImpl(CacheRepository.getInstance()));
        assertNotNull(decoupledController);
    }

    @Test
    void testScalability_NewOperation_Addition() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = controller.performAdd(q1, q2, "FEET");
        assertNotNull(result);
    }
}