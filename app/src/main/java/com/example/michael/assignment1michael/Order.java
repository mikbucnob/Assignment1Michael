package com.example.michael.assignment1michael;

/**
 * Created by Michael on 17/05/2017.
 */

public class Order {

    private Integer tableNumber;
    private Integer numDishes;
    private String dishName;
    private Double dishUnitPrice;
    private Double dishTotalPrice;

    public Order(Integer tableNumber, Integer numDishes, String dishName, Double dishUnitPrice, Double dishTotalPrice) {
        this.tableNumber = tableNumber;
        this.numDishes = numDishes;
        this.dishName = dishName;
        this.dishUnitPrice = dishUnitPrice;
        this.dishTotalPrice = dishTotalPrice;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getNumDishes() {
        return numDishes;
    }

    public void setNumDishes(Integer numDishes) {
        this.numDishes = numDishes;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Double getDishUnitPrice() {
        return dishUnitPrice;
    }

    public void setDishUnitPrice(Double dishUnitPrice) {
        this.dishUnitPrice = dishUnitPrice;
    }

    public Double getDishTotalPrice() {
        return dishTotalPrice;
    }

    public void setDishTotalPrice(Double dishTotalPrice) {
        this.dishTotalPrice = dishTotalPrice;
    }
}

