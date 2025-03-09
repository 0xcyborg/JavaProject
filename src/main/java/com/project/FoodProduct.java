package com.project;
public class FoodProduct extends Product{
    private String expirationDate;

    FoodProduct(String id, String name, double price, int quantity, String category, String expirationDate) throws InvalidValueException{
        super(id, name, price, quantity, category);
        this.expirationDate = expirationDate;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Expiration Date: " + this.expirationDate);
    }

    /*
    @Override
    void specificInfo(){
        System.out.println("Expiration Date: " + this.expirationDate);
    }
    */
}