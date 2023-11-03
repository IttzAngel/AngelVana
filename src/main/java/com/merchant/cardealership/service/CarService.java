package com.merchant.cardealership.service;

import com.merchant.cardealership.exceptions.CarNotFoundException;
import com.merchant.cardealership.model.Car;
import com.merchant.cardealership.repo.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    CarRepo carRepo;

    public Iterable<Car> getAllCars(){
        return carRepo.findAll();
    }

    public Optional<Car> getCarById(Long carId){
        verifyCar(carId);
        return carRepo.findById(carId);
    }

    public void createCar(Car car){
        carRepo.save(car);
    }

    public void updateCar(Long carId, Car car){
        verifyCar(carId);
        car.setId(carId);
        carRepo.save(car);
    }

    public void deleteCarById(Long carId){
        verifyCar(carId);
        carRepo.deleteById(carId);
    }

    public List<Car> findCarsByMake(String make){
        return carRepo.findByMake(make);
    }

//    protected void verifyCar(Long carId){
//        Optional<Car> car = carRepo.findById(carId);
//        if (car.isEmpty()){
//            new String("We're sorry, that car does not exist");
//        }
//    }

    protected void verifyCar(Long carId) {
        Optional<Car> car = carRepo.findById(carId);
        if (car.isEmpty()) {
            throw new CarNotFoundException("Car not found with ID: " + carId);
        }
    }

}
