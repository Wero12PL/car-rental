package com.jwolodzko.car_rental.car;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @PostMapping
    public void createCar(@RequestBody Car car) {
        carRepository.save(car);
    }
}
