package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("123");
    }

    @Test
    void testCreateCarPage() {
        String viewName = carController.createCarPage(model);
        verify(model, times(1)).addAttribute(eq("car"), any(Car.class));
        assertEquals("CreateCar", viewName);
    }

    @Test
    void testCreateCarPost() {
        String viewName = carController.createCarPost(car, model);
        verify(carService, times(1)).create(car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testCarListPage() {
        List<Car> cars = Arrays.asList(car, new Car());
        when(carService.findAll()).thenReturn(cars);

        String viewName = carController.carListPage(model);
        verify(model, times(1)).addAttribute("cars", cars);
        assertEquals("CarList", viewName);
    }

    @Test
    void testEditCarPage() {
        when(carService.findById("123")).thenReturn(car);

        String viewName = carController.editCarPage("123", model);
        verify(model, times(1)).addAttribute("car", car);
        assertEquals("EditCar", viewName);
    }

    @Test
    void testEditCarPost() {
        String viewName = carController.editCarPost(car, model);
        verify(carService, times(1)).update(car.getCarId(), car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testDeleteCar() {
        String viewName = carController.deleteCar("123");
        verify(carService, times(1)).deleteCarById("123");
        assertEquals("redirect:listCar", viewName);
    }
}