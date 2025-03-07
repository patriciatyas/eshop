package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarRepositoryTest {

    @Mock
    private IdGeneratorService idGeneratorService;

    private CarRepository carRepository;
    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carRepository = new CarRepository(idGeneratorService);

        // Mock ID generation
        when(idGeneratorService.generateId()).thenReturn(UUID.randomUUID().toString());

        car1 = new Car();
        car1.setCarId("car-123");
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);

        car2 = new Car();
        car2.setCarId("car-456");
        car2.setCarName("Honda");
        car2.setCarColor("Blue");
        car2.setCarQuantity(3);
    }

    @Test
    void testCreateCarWithGivenId() {
        Car createdCar = carRepository.create(car1);
        assertNotNull(createdCar);
        assertEquals("car-123", createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
    }

    @Test
    void testCreateCarWithoutIdGeneratesNewId() {
        car2.setCarId(null);
        Car createdCar = carRepository.create(car2);
        assertNotNull(createdCar.getCarId());
        verify(idGeneratorService, times(1)).generateId();
    }

    @Test
    void testFindAllReturnsCorrectCars() {
        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertNotNull(iterator);

        int count = 0;
        while (iterator.hasNext()) {
            Car car = iterator.next();
            assertNotNull(car);
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testFindByIdReturnsCorrectCar() {
        carRepository.create(car1);
        Car foundCar = carRepository.findById("car-123");

        assertNotNull(foundCar);
        assertEquals("Toyota", foundCar.getCarName());
        assertEquals("Red", foundCar.getCarColor());
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        Car foundCar = carRepository.findById("car-999");
        assertNull(foundCar);
    }

    @Test
    void testUpdateExistingCar() {
        carRepository.create(car1);

        Car updatedCar = new Car();
        updatedCar.setCarName("Ford");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("car-123", updatedCar);

        assertNotNull(result);
        assertEquals("Ford", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateNonExistingCarReturnsNull() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Ford");

        Car result = carRepository.update("car-999", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteCar() {
        carRepository.create(car1);
        carRepository.create(car2);

        carRepository.delete("car-123");
        assertNull(carRepository.findById("car-123"));
    }

    @Test
    void testDeleteNonExistingCarDoesNothing() {
        carRepository.delete("car-999");
        assertNull(carRepository.findById("car-999"));
    }
}
