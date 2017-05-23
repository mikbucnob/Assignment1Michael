package com.example.michael.assignment1michael;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.id.list;

public class OrderSummaryActivity extends AppCompatActivity {

    private Order currentOrder;
    private TextView tableNumber;
    private TextView totalOrderPrice;
    private ListView orderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Intent intent = getIntent();
        currentOrder = (Order) intent.getExtras().getSerializable("currentOrder");

        tableNumber = (TextView) findViewById(R.id.orderTableNumber);
        totalOrderPrice = (TextView) findViewById(R.id.totalOrderPrice);
        orderSummary = (ListView) findViewById(R.id.orderSummaryListView);

        ArrayList<HashMap<String, Object>> orderInfo = currentOrder.getDishesOrderInfo();
        ArrayList<String> dishesDisplay = new ArrayList<String>();
        for(HashMap<String, Object> dishInfo : orderInfo){
            String dishName = (String) dishInfo.get("dishname");
            Integer dishQuantity = (Integer) dishInfo.get("dishquantity");
            Double dishTotalPrice = (Double) dishInfo.get("dishtotalprice");

            String dishInfoString = dishName + " : " + "Quantity : " + dishQuantity + " price : " + dishTotalPrice;
            dishesDisplay.add(dishInfoString);
        }

        String[] items = dishesDisplay.toArray(new String[dishesDisplay.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);

        orderSummary.setAdapter(adapter);


        tableNumber.setText(currentOrder.getTableNumber());
        totalOrderPrice.setText(String.valueOf(currentOrder.getOrderTotalPrice()));

    }
}
