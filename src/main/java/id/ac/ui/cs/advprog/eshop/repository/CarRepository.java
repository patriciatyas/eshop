package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CarRepository {
    private final IdGeneratorService idGeneratorService;
    private final List<Car> carData = new ArrayList<>();

    public CarRepository(IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    public Car create(Car car) {
        if (car.getCarId() == null) {
            car.setCarId(idGeneratorService.generateId());
        }
        carData.add(car);
        return car;
    }

    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    public Car findById(String id) {
        return carData.stream().filter(car -> car.getCarId().equals(id)).findFirst().orElse(null);
    }

    public Car update(String id, Car updateCar) {
        for (int i = 0; i < carData.size(); i++) {
            Car car = carData.get(i);
            if (car.getCarId().equals(id)) {
                car.setCarName(updateCar.getCarName());
                car.setCarColor(updateCar.getCarColor());
                car.setCarQuantity(updateCar.getCarQuantity());
                return car;
            }
        }
        return null;
    }

    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}
