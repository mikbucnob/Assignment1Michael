package com.example.michael.assignment1michael;

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
        List entries = new ArrayList();
        return entries;
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

                xmlSerializer.startTag(ns, "dish");

                String dishName = (String) dish.get("dishname");
                Integer dishQuantity = (Integer) dish.get("dishquantity");
                Double dishTotalPrice = (Double) dish.get("dishtotalprice");

                xmlSerializer.attribute(ns, "name", dishName);
                xmlSerializer.attribute(ns, "quantity", String.valueOf(dishQuantity));
                xmlSerializer.attribute(ns, "totalprice", String.valueOf(dishTotalPrice));

                xmlSerializer.endTag(ns, "dish");
            }
        }

        xmlSerializer.endTag(ns, "order");

    }
}

