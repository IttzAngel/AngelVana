package com.merchant.cardealership.exceptions;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException() {
        super("Car not found");
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
