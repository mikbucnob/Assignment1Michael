package com.example.michael.assignment1michael;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael on 17/05/2017.
 */

public class Order extends XMLUtil implements Serializable{

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


    //@ is an annotation - it's like notes
    @Override
    protected List readRoot(XmlPullParser parser) throws XmlPullParserException, IOException {
        List ordersList = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "orders");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the dish tag
            if (name.equals("order")) {
                ordersList = readOrders(parser);
                break;
            } else {
                skip(parser);
            }
        }
        return ordersList;
    }

    // Parses the contents of existing orders.
    private ArrayList<Order> readOrders(XmlPullParser parser) throws XmlPullParserException, IOException {

/*
        ArrayList<Order>  orders = null;
        int eventType = parser.getEventType();
        Order order = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if(null == orders){
                        orders = new ArrayList();
                    }
                    name = parser.getName();
                    Dish dishes = null;
                    if (name.equals("dish")){
                        Log.d("dish", "Start Tag dish");
                        dish = new Dish();
                        while(eventType != XmlPullParser.END_DOCUMENT){
                            String name;
                            switch (eventType){
                                case XmlPullParser.START_DOCUMENT:
                                    break;
                                case XmlPullParser.START_TAG:
                                    if(null == orders){
                                        orders = new ArrayList();
                                    }
                                    name = parser.getName();
                                    Dish dish = null;
                                    if (name.equals("dish")){

                                    } else if(){

                                    }
                        }

                    } else if (user != null){
                        Log.d("check title", "name : " + name );
                        if (name.equals("username")){
                            user.setUsername(parser.nextText());
                        } else if (name.equals("password")){
                            user.setPassword(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    Log.d("end", "end tag");
                    name = parser.getName();
                    if (name.equalsIgnoreCase("dish") && dish != null){
                        Log.d("Adding", "Adding dish to dishes");
                        dishes.add(dish);
                    }
            }
            eventType = parser.next();
        }

        return users;
        */

        return null;
    }

    public String createXML(Object object) throws IllegalArgumentException, IllegalStateException, IOException
    {
        Order order = (Order)object;

        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        xmlSerializer.setOutput(stringWriter);

        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        xmlSerializer.startTag(ns, "orders");
        createOrder(xmlSerializer, order);
        xmlSerializer.endTag(ns, "orders");

        xmlSerializer.endDocument();
        return stringWriter.toString();
    }

    public void createOrder(XmlSerializer xmlSerializer, Order order) throws IllegalArgumentException,
            IllegalStateException, IOException{

        xmlSerializer.startTag(ns, "order");
        xmlSerializer.attribute(ns, "tablenumber", String.valueOf(order.getTableNumber()));
        xmlSerializer.attribute(ns, "totalorderprice", String.valueOf(order.getOrderTotalPrice()));
        ArrayList<HashMap<String, Object>> dishes = order.getDishesOrderInfo();

        if(dishes.size() > 0) {

            for (HashMap<String, Object> dish : dishes) {

                String dishName = (String) dish.get("dishname");
                Integer dishQuantity = (Integer) dish.get("dishquantity");
                Double dishTotalPrice = (Double) dish.get("dishtotalprice");

                xmlSerializer.startTag(ns, "dish");

                xmlSerializer.startTag(ns, "name");
                xmlSerializer.text(dishName);
                xmlSerializer.endTag(ns, "name");

                xmlSerializer.startTag(ns, "quantity");
                xmlSerializer.text(String.valueOf(dishQuantity));
                xmlSerializer.endTag(ns, "quantity");

                xmlSerializer.startTag(ns, "price");
                xmlSerializer.text(String.valueOf(dishTotalPrice));
                xmlSerializer.endTag(ns, "price");

                xmlSerializer.endTag(ns, "dish");
            }
        }

        xmlSerializer.endTag(ns, "order");

    }
}

