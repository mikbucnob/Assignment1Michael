package com.example.michael.assignment1michael;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michael on 17/05/2017.
 */

public class Order implements Serializable{

    private Integer tableNumber;
    private ArrayList<HashMap<String, Object>> dishesOrderInfo;
    private Double orderTotalPrice;

    public Order(Integer tableNumber, HashMap<String, Object> dishOrderInfo, Double orderTotalPrice) {

        if(null == dishesOrderInfo){
            dishesOrderInfo = new ArrayList<HashMap<String, Object>> ();
        }
        this.tableNumber = tableNumber;
        this.orderTotalPrice = orderTotalPrice;
        dishesOrderInfo.add(dishOrderInfo);
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ArrayList<HashMap<String, Object>> getDishesOrderInfo() {
        return dishesOrderInfo;
    }

    public void setDishesOrderInfo(ArrayList<HashMap<String, Object>> dishesOrderInfo) {
        this.dishesOrderInfo = dishesOrderInfo;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public void addDishtoOrder(HashMap<String, Object> dishOrder){
        dishesOrderInfo.add(dishOrder);
    }
}

