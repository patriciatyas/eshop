package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car1, car2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        car1 = new Car();
        car1.setCarId("car-1");
        car1.setCarName("Toyota");

        car2 = new Car();
        car2.setCarId("car-2");
        car2.setCarName("Honda");
    }

    @Test
    void testCreateCar() {
        when(carRepository.create(car1)).thenReturn(car1);
        Car createdCar = carService.create(car1);

        assertNotNull(createdCar);
        assertEquals("car-1", createdCar.getCarId());
        verify(carRepository).create(car1);
    }

    @Test
    void testFindAllCars() {
        Iterator<Car> carIterator = Arrays.asList(car1, car2).iterator();
        when(carRepository.findAll()).thenReturn(carIterator);

        List<Car> allCars = carService.findAll();

        assertEquals(2, allCars.size());
        assertEquals("Toyota", allCars.get(0).getCarName());
        assertEquals("Honda", allCars.get(1).getCarName());
        verify(carRepository).findAll();
    }

    @Test
    void testFindCarById() {
        when(carRepository.findById("car-1")).thenReturn(car1);

        Car foundCar = carService.findById("car-1");

        assertNotNull(foundCar);
        assertEquals("Toyota", foundCar.getCarName());
        verify(carRepository).findById("car-1");
    }

    @Test
    void testUpdateCar() {
        when(carRepository.update("car-1", car1)).thenReturn(car1);

        carService.update("car-1", car1);

        verify(carRepository).update("car-1", car1);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("car-1");

        carService.deleteCarById("car-1");

        verify(carRepository).delete("car-1");
    }
}
