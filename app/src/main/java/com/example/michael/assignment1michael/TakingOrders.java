package com.example.michael.assignment1michael;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;

public class TakingOrders extends AppCompatActivity implements com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {


    String totalOrderPriceString = "";
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;
    LinearLayout myLayout;
    int fontSize;
    private TextView dishName;
    private TextView pageTitle;
    private TextView tableNumtitle;
    private TextView quantity;
    private TextView price;
    private Button completeOrder;
    private TextView orderPrice;
    private Double totalCurrentOrderPrice = 0.0;
    private Double newTotalOrderPrice = 0.0;
    private Double newDishTotalOrderPrice = 0.0;
    private Order currentOrder;
    private String dishPrice;
    private com.shawnlin.numberpicker.NumberPicker tableNumber;
    private com.shawnlin.numberpicker.NumberPicker numDishes;
    private boolean isOngoingOrder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_orders);

        pageTitle = (TextView) findViewById(R.id.takingOrderScreenTitle) ;
        tableNumtitle = (TextView) findViewById(R.id.takingOrderTableNumTitle) ;
        quantity = (TextView) findViewById(R.id.takingOrderQuantityTitle);

        Intent intent = getIntent();
        String imageId = intent.getStringExtra("imageid");
        String name = intent.getStringExtra("name");
        currentOrder = (Order) intent.getExtras().getSerializable("currentOrder");
        dishPrice = intent.getStringExtra("price");


        if(currentOrder !=null) {
            totalCurrentOrderPrice = currentOrder.getOrderTotalPrice();
            isOngoingOrder = true;
        }

        ImageView dishImage = (ImageView) findViewById(R.id.orderPicture);
        dishName = (TextView) findViewById(R.id.requestedDish);

        price = (TextView) findViewById(R.id.calculatedPrice);
        completeOrder = (Button) findViewById(R.id.okButton);
        orderPrice = (TextView) findViewById(R.id.totalPrice);


        int imageID = Integer.valueOf(imageId);
        dishImage.setImageResource(imageID);
        dishName.setText(name);


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

        tableNumber.setOnValueChangedListener(new com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
                Log.d("Spinner1", "Calling setpref for TableNumber Spinner");
                //setPreferenceTheme();
            }
        });
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        tableNumber.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        tableNumber.setMaxValue(15);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        tableNumber.setWrapSelectorWheel(true);

        if(isOngoingOrder){
            tableNumber.setValue(currentOrder.getTableNumber());
            //tableNumber.setEnabled(false);
        } else {
            tableNumber.setValue(0);
        }

        //Number of Dishes
        numDishes = (com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.numDishes);
        numDishes.setOnValueChangedListener(new com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
                updatePrices();
                Log.d("Spinner2", "Calling set pref for number of dishes spinner");
                //setPreferenceTheme();
            }
        });

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


                updatePrices();

                addItemToOrder(dishName);

                //Save order details to xml file and delete from memory
                Intent i = new Intent(TakingOrders.this, OrderSummaryActivity.class);
                i.putExtra("currentOrder", currentOrder);
                startActivity(i);

            }
        });


        setPreferenceTheme();
    }

    private void updatePrices(){
        //newTotalOrderPrice = totalCurrentOrderPrice;

        //Integer.parseInt()
        DecimalFormat df = new DecimalFormat("0.00##");
        newDishTotalOrderPrice = Double.valueOf(dishPrice) * numDishes.getValue();
        String totalDishPriceFormatted = df.format(newDishTotalOrderPrice);
        String priceText = "Total Dish Price : $ " + totalDishPriceFormatted;
        price.setText(priceText);

        Log.d("Price", "totalCurrentOrderPrice : " + totalCurrentOrderPrice + " totaldishprice : " + newDishTotalOrderPrice);
        newTotalOrderPrice = totalCurrentOrderPrice + newDishTotalOrderPrice;
        Log.d("New price", "newTotalOrderprice : " + newTotalOrderPrice);
        String newTotalOrderPriceFormatted = df.format(newTotalOrderPrice);
        String totalPriceText = "Total Order Price : $ " + newTotalOrderPriceFormatted;
        orderPrice.setText(totalPriceText);
    }

    private void addItemToOrder(TextView dishName) {
        DecimalFormat df = new DecimalFormat("0.00##");
        HashMap<String, Object> dishOrder = new HashMap<String, Object>();
        dishOrder.put("dishname", dishName.getText().toString());
        dishOrder.put("dishquantity", numDishes.getValue());
        dishOrder.put("dishtotalprice", Double.valueOf(df.format(newDishTotalOrderPrice)));
        if(currentOrder == null) {
            currentOrder = new Order(tableNumber.getValue(), dishOrder,
                   newTotalOrderPrice);
            Log.d("Order", "Creating order with new total order price: " + newTotalOrderPrice);
        } else {
            currentOrder.setTableNumber(tableNumber.getValue());
            currentOrder.addDishtoOrder(dishOrder);
            currentOrder.setOrderTotalPrice(newTotalOrderPrice);
        }
    }

    /**
     * Method to set the background and font based on preferences
     */
    private void setPreferenceTheme() {
        sharedPref = getApplicationContext().getSharedPreferences("myprefs", 0);
        String colorString = "";
        colorString = sharedPref.getString("rgb", "");
        Log.d("ColorSet", "Color set is : " + colorString);

        myLayout = (LinearLayout) findViewById(R.id.takingOrderScreen);
        if(colorString != null && colorString != "") {
            myLayout.setBackgroundColor(Integer.valueOf(colorString));
        }

        String fontSizeString = sharedPref.getString("fontsize", "");
        if("" != fontSizeString) {
            fontSize = Integer.valueOf(fontSizeString);
            Log.d("FontSize", "MainActivity : Read font size from preferences : " + fontSize);

            //Set font for all components in this activity
            dishName.setTextSize(fontSize);
            price.setTextSize(fontSize);
            orderPrice.setTextSize(fontSize);

            /**for(int i=0; i<tableNumber.getChildCount(); i++){
                if(tableNumber.getChildAt(i) instanceof EditText){
                    EditText tableNumPickerText = (EditText) tableNumber.getChildAt(i);
                    Log.d("SpinnerFontSsize", "Setting spinner 1 font size to : " + fontSize);
                    tableNumPickerText.setTextSize(fontSize);
                }
            }

            for(int i=0; i<numDishes.getChildCount(); i++){
                if(numDishes.getChildAt(i) instanceof EditText){
                    EditText numDishesPickerText = (EditText) numDishes.getChildAt(i);
                    Log.d("SpinnerFontSsize", "Setting spinner 2 font size to : " + fontSize);
                    numDishesPickerText.setTextSize(fontSize);
                }
             }**/

            pageTitle.setTextSize(fontSize);
            tableNumtitle.setTextSize(fontSize);
            quantity.setTextSize(fontSize);
        }
    }


    public void onValueChange (com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
        Log.d("NumberPick", "Picker");
        Toast.makeText(getApplicationContext(), "Number Picker Changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
        //result intent needs to be packed with the order data
        setResult(Activity.RESULT_OK, result);
        finish();

    }

    @Override
    public void onBackPressed (){

        updatePrices();

        addItemToOrder(dishName);

        Intent i = new Intent();
        i.putExtra("currentOrder", currentOrder);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        setPreferenceTheme();
    }
}
