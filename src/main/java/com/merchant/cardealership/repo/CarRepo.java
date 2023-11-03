package com.merchant.cardealership.repo;

import com.merchant.cardealership.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends CrudRepository<Car, Long> {

    List<Car> findByMake(String make);

}
