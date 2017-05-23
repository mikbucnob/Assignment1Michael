package com.example.michael.assignment1michael;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class TakingOrders extends AppCompatActivity implements com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {

    private ImageView dishImage;

    private TextView dishName;

    private TextView price;
    private Button addMore;
    private Button completeOrder;
    private TextView orderPrice;
    private Double totalCurrentOrderPrice = 0.0;
    private Double newTotalOrderPrice = 0.0;
    String totalOrderPriceString="";
    private Order currentOrder;

    private com.shawnlin.numberpicker.NumberPicker tableNumber;
    private com.shawnlin.numberpicker.NumberPicker numDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_orders);

        Intent intent = getIntent();
        String imageId = intent.getStringExtra("imageid");
        String name = intent.getStringExtra("name");
        currentOrder = (Order) intent.getExtras().getSerializable("currentOrder");
        final String dishPrice = intent.getStringExtra("price");

        final SharedPreferences myprefs= getSharedPreferences("orderprice", MODE_WORLD_READABLE);

        String totalOrderPriceString = myprefs.getString("totalorderprice", null);

        if(totalOrderPriceString != null && ""!=totalOrderPriceString) {
            totalCurrentOrderPrice = Double.valueOf(totalOrderPriceString);
        }

        ImageView dishImage = (ImageView) findViewById(R.id.orderPicture);
        final TextView dishName = (TextView) findViewById(R.id.requestedDish);

        price = (TextView) findViewById(R.id.calculatedPrice);
        addMore = (Button) findViewById(R.id.addmore);
        completeOrder = (Button) findViewById(R.id.okButton);
        orderPrice = (TextView) findViewById(R.id.totalPrice);


        int imageID = Integer.valueOf(imageId);
        dishImage.setImageResource(imageID);
        dishName.setText(name);



        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save Order details in memory and move to Menu Screen
                myprefs.edit().putString(getString(R.string.total_order_price), String.valueOf(newTotalOrderPrice)).commit();

                HashMap<String, Object> dishOrder = new HashMap<String, Object>();
                dishOrder.put("dishname", dishName.getText().toString());
                dishOrder.put("dishquantity", numDishes.getValue());
                dishOrder.put("dishtotalprice", Double.valueOf(price.getText().toString()));
                if(currentOrder == null) {
                    currentOrder = new Order(tableNumber.getValue(), dishOrder,
                            Double.valueOf(orderPrice.getText().toString()));
                } else {
                    currentOrder.addDishtoOrder(dishOrder);
                }

                Intent i = new Intent(TakingOrders.this, MenuScreen.class);
                i.putExtra("currentOrder", currentOrder);
            }
        });

/*        numDishes.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                newTotalOrderPrice = totalCurrentOrderPrice;

                //Integer.parseInt()
                Double totalDishPrice = Double.valueOf(dishPrice) * Integer.valueOf(numDishes.getText().toString());
                String priceText = getString(R.string.orderprice) + String.valueOf(Double.valueOf(dishPrice)
                        * Integer.valueOf(numDishes.getText().toString()));
                price.setText(priceText);

                newTotalOrderPrice += totalDishPrice;
                String totalPriceText = getString(R.string.torderprice) + String.valueOf(newTotalOrderPrice);
                orderPrice.setText(totalPriceText);

                myprefs.edit().putString("totalorderprice", String.valueOf(newTotalOrderPrice)).commit();
                hasBeenPut=true;
                return false;
            }
        });*/

        tableNumber = (com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.table);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        tableNumber.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        tableNumber.setMaxValue(15);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        tableNumber.setWrapSelectorWheel(true);

        tableNumber.setValue(0);

        //Number of Dishes
        numDishes = (com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.numDishes);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        numDishes.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        numDishes.setMaxValue(20);

        numDishes.setValue(0);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        numDishes.setWrapSelectorWheel(true);



        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save order details to xml file and delete from memory
                Intent i = new Intent(TakingOrders.this, OrderSummaryActivity.class);
                i.putExtra("currentOrder", currentOrder);
                startActivity(i);

            }
        });


    }


    public void onValueChange (com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
        Log.d("NumberPick", "Picker");
        Toast.makeText(getApplicationContext(), "Number Picker Changed", Toast.LENGTH_SHORT).show();
    }



}
