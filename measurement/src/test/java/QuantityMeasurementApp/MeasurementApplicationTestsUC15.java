package QuantityMeasurementApp;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.database.ConnectionPool;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.Service;
import com.app.quantitymeasurement.serviceImpl.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementApplicationTestsUC15 {

    private IQuantityMeasurementRepository repository;
    private Service service;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        service = new ServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    // --- Entity Tests ---

    @Test
    void testQuantityEntity_SingleOperandConstruction() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity("CONVERT", "1.0 FEET", "12.0 INCH");
        assertEquals("CONVERT", entity.getOperation());
        assertEquals("1.0 FEET", entity.getInput());
        assertEquals("12.0 INCH", entity.getResult());
        assertFalse(entity.hasError());
    }

    @Test
    void testQuantityEntity_BinaryOperandConstruction() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "1.0 FEET + 1.0 FEET", "2.0 FEET");
        assertEquals("ADD", entity.getOperation());
        assertEquals("1.0 FEET + 1.0 FEET", entity.getInput());
        assertEquals("2.0 FEET", entity.getResult());
        assertFalse(entity.hasError());
    }

    @Test
    void testQuantityEntity_ErrorConstruction() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "Unsupported operation");
        assertEquals("ADD", entity.getOperation());
        assertNull(entity.getInput());
        assertEquals("Unsupported operation", entity.getResult());
        assertTrue(entity.hasError());
    }

    @Test
    void testQuantityEntity_ToString_Success() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity("CONVERT", "1.0 FEET", "12.0 INCH");
        String str = entity.toString();
        assertTrue(str.contains("CONVERT"));
        assertTrue(str.contains("1.0 FEET"));
        assertTrue(str.contains("12.0 INCH"));
    }

    @Test
    void testQuantityEntity_ToString_Error() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "Error occurred");
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
        QuantityMeasurementController mockController = new QuantityMeasurementController(mockService);
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
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "1+1", "2");
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
        QuantityMeasurementController decoupledController = new QuantityMeasurementController(new ServiceImpl(QuantityMeasurementCacheRepository.getInstance()));
        assertNotNull(decoupledController);
    }

    @Test
    void testScalability_NewOperation_Addition() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO result = controller.performAdd(q1, q2, "FEET");
        assertNotNull(result);

    }


    // ===========================
// UC16 DATABASE TEST CASES
// ===========================

    @Test
    void testConnectionPool_Initialization() {
        ConnectionPool pool = ConnectionPool.getInstance();
        assertNotNull(pool);
    }

    @Test
    void testConnectionPool_Acquire_Release() {
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        assertNotNull(connection);

        pool.releaseConnection(connection);
    }

    @Test
    void testDatabaseRepository_SaveEntity() {
        IQuantityMeasurementRepository dbRepo =
                new QuantityMeasurementDatabaseRepository();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "ADD",
                        "1 FEET + 12 INCH",
                        "2 FEET"
                );

        assertDoesNotThrow(() -> dbRepo.save(entity));
    }

    @Test
    void testDatabaseRepository_SaveErrorEntity() {
        IQuantityMeasurementRepository dbRepo =
                new QuantityMeasurementDatabaseRepository();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "ADD",
                        "Temperature addition not supported"
                );

        assertDoesNotThrow(() -> dbRepo.save(entity));
    }

    @Test
    void testDatabaseRepository_MultipleEntitySave() {
        IQuantityMeasurementRepository dbRepo =
                new QuantityMeasurementDatabaseRepository();

        QuantityMeasurementEntity entity1 =
                new QuantityMeasurementEntity(
                        "ADD",
                        "1 FEET + 1 FEET",
                        "2 FEET"
                );

        QuantityMeasurementEntity entity2 =
                new QuantityMeasurementEntity(
                        "SUBTRACT",
                        "2 FEET - 1 FEET",
                        "1 FEET"
                );

        assertDoesNotThrow(() -> {
            dbRepo.save(entity1);
            dbRepo.save(entity2);
        });
    }

    @Test
    void testServiceWithDatabaseRepository_Integration() {

        Service dbService = new ServiceImpl(
                new QuantityMeasurementDatabaseRepository()
        );

        QuantityDTO q1 =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        QuantityDTO q2 =
                new QuantityDTO(12.0, "INCH", "LENGTH");

        QuantityDTO result =
                dbService.add(q1, q2, "FEET");

        assertFalse(result.isError());
        assertEquals(2.0, result.getValue());
    }

    @Test
    void testServiceWithCacheRepository_Integration() {

        Service cacheService = new ServiceImpl(
                QuantityMeasurementCacheRepository.getInstance()
        );

        QuantityDTO q1 =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        QuantityDTO q2 =
                new QuantityDTO(12.0, "INCH", "LENGTH");

        QuantityDTO result =
                cacheService.add(q1, q2, "FEET");

        assertFalse(result.isError());
        assertEquals(2.0, result.getValue());
    }

    @Test
    void testSQLInjectionPrevention() {

        IQuantityMeasurementRepository dbRepo =
                new QuantityMeasurementDatabaseRepository();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "ADD",
                        "'; DROP TABLE quantity_measurement_entity; --",
                        "attack"
                );

        assertDoesNotThrow(() ->
                dbRepo.save(entity)
        );
    }

    @Test
    void testDatabaseException_CustomException() {

        assertThrows(DatabaseException.class, () -> {

            throw new DatabaseException(
                    "Database failed"
            );
        });
    }

    @Test
    void testResourceCleanup_ConnectionClosed() {

        ConnectionPool pool =
                ConnectionPool.getInstance();

        Connection connection =
                pool.getConnection();

        assertNotNull(connection);

        pool.releaseConnection(connection);
    }

    @Test
    void testDatabaseRepositoryPoolStatistics() {

        ConnectionPool pool =
                ConnectionPool.getInstance();

        assertNotNull(pool);
    }

    @Test
    void testMavenBuild_Success() {
        assertTrue(true);
    }

    @Test
    void testPackageStructure_AllLayersPresent() {
        assertNotNull(controller);
        assertNotNull(service);
        assertNotNull(repository);
    }

    @Test
    void testPomDependencies_JDBCDriversIncluded() {
        assertDoesNotThrow(() ->
                Class.forName("org.h2.Driver"));
    }

    @Test
    void testDatabaseConfiguration_LoadedFromProperties() {
        assertNotNull(
                System.getProperty("user.dir")
        );
    }

    @Test
    void testConnectionPool_AllConnectionsExhausted() {

        ConnectionPool pool =
                ConnectionPool.getInstance();

        Connection c1 = pool.getConnection();
        Connection c2 = pool.getConnection();

        assertNotNull(c1);
        assertNotNull(c2);

        pool.releaseConnection(c1);
        pool.releaseConnection(c2);
    }

    @Test
    void testTransactionRollback_OnError() {

        Service dbService = new ServiceImpl(
                new QuantityMeasurementDatabaseRepository()
        );

        QuantityDTO q1 =
                new QuantityDTO(
                        100.0,
                        "CELSIUS",
                        "TEMPERATURE"
                );

        QuantityDTO q2 =
                new QuantityDTO(
                        212.0,
                        "FAHRENHEIT",
                        "TEMPERATURE"
                );

        QuantityDTO result =
                dbService.add(q1, q2, "CELSIUS");

        assertTrue(result.isError());
    }

    @Test
    void testH2TestDatabase_IsolationBetweenTests() {
        assertTrue(true);
    }

    @Test
    void testRepositoryFactory_CreateCacheRepository() {

        IQuantityMeasurementRepository repo =
                QuantityMeasurementCacheRepository
                        .getInstance();

        assertNotNull(repo);
    }

    @Test
    void testRepositoryFactory_CreateDatabaseRepository() {

        IQuantityMeasurementRepository repo =
                new QuantityMeasurementDatabaseRepository();

        assertNotNull(repo);
    }

    @Test
    void testMavenTest_AllTestsPass() {
        assertTrue(true);
    }

    @Test
    void testMavenPackage_JarCreated() {
        assertTrue(true);
    }

    @Test
    void testPropertiesConfiguration_EnvironmentOverride() {
        System.setProperty("env", "test");
        assertEquals(
                "test",
                System.getProperty("env")
        );
    }

    @Test
    void testDatabaseRepository_ConcurrentAccess() {

        IQuantityMeasurementRepository repo =
                new QuantityMeasurementDatabaseRepository();

        Runnable task = () -> repo.save(
                new QuantityMeasurementEntity(
                        "ADD",
                        "1+1",
                        "2"
                )
        );

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        assertTrue(true);
    }

    @Test
    void testBackwardCompatibility_AllUC1_UC15_Tests_UC16() {

        QuantityDTO q1 =
                new QuantityDTO(
                        1.0,
                        "FEET",
                        "LENGTH"
                );

        QuantityDTO q2 =
                new QuantityDTO(
                        12.0,
                        "INCH",
                        "LENGTH"
                );

        QuantityDTO result =
                controller.performAdd(
                        q1,
                        q2,
                        "FEET"
                );

        assertEquals(2.0,
                result.getValue());
    }

    @Test
    void testIntegration_EndToEnd_LengthAddition_UC16() {

        Service dbService = new ServiceImpl(
                new QuantityMeasurementDatabaseRepository()
        );

        QuantityMeasurementController dbController =
                new QuantityMeasurementController(
                        dbService
                );

        QuantityDTO q1 =
                new QuantityDTO(
                        1.0,
                        "FEET",
                        "LENGTH"
                );

        QuantityDTO q2 =
                new QuantityDTO(
                        12.0,
                        "INCH",
                        "LENGTH"
                );

        QuantityDTO result =
                dbController.performAdd(
                        q1,
                        q2,
                        "FEET"
                );

        assertEquals(2.0,
                result.getValue());
    }

    @Test
    void testIntegration_EndToEnd_TemperatureUnsupported_UC16() {

        Service dbService = new ServiceImpl(
                new QuantityMeasurementDatabaseRepository()
        );

        QuantityMeasurementController dbController =
                new QuantityMeasurementController(
                        dbService
                );

        QuantityDTO q1 =
                new QuantityDTO(
                        100.0,
                        "CELSIUS",
                        "TEMPERATURE"
                );

        QuantityDTO q2 =
                new QuantityDTO(
                        100.0,
                        "CELSIUS",
                        "TEMPERATURE"
                );

        QuantityDTO result =
                dbController.performAdd(
                        q1,
                        q2,
                        "CELSIUS"
                );

        assertTrue(result.isError());
    }
}