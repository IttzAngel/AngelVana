package com.merchant.cardealership.controller;

import com.merchant.cardealership.exceptions.CarNotFoundException;
import com.merchant.cardealership.model.Car;
import com.merchant.cardealership.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/cars")
    public ResponseEntity<Void> postCar(@RequestBody Car car){
        carService.createCar(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // /cars?make=<insert-brand>
    @GetMapping("/cars")
    public ResponseEntity<Iterable<Car>> getAllOrGetCarByMake(@RequestParam(value = "make", required = false) String make) {
        Iterable<Car> cars = carService.findCarsByMake(make);
        if (cars.iterator().hasNext()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } else if (make != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
        }
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<?> getCarById(@PathVariable Long carId) {
        try {
            Optional<Car> car = carService.getCarById(carId);
            if (car.isPresent()) {
                return new ResponseEntity<>(car.get(), HttpStatus.OK);
            } else {
                throw new CarNotFoundException("Car not found with ID: " + carId);
            }
        } catch (CarNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cars/{carId}")
    public ResponseEntity<Void> updateCarDetails(@PathVariable Long carId, @RequestBody Car car) {
        try {
            carService.updateCar(carId, car);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CarNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<String> deleteCarById(@PathVariable Long carId) {
        try {
            carService.deleteCarById(carId);
            return new ResponseEntity<>("Car with ID " + carId + " has been deleted", HttpStatus.NO_CONTENT);
        } catch (CarNotFoundException ex) {
            return new ResponseEntity<>("Car not found with ID: " + carId, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while deleting the car.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
