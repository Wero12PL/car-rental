package com.jwolodzko.car_rental.car;

import com.jwolodzko.car_rental.car.dto.CarRequest;
import com.jwolodzko.car_rental.car.dto.CarResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final CarRepository carRepository;

    public CarController(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    @GetMapping
    public List<CarResponse> getCars() {
        return carRepository.findAll().stream().map(CarResponse::new).toList();
    }

    @PostMapping
    public void createCar(@Valid @RequestBody CarRequest carRequest) {
        carService.createCar(carRequest);
    }
}
