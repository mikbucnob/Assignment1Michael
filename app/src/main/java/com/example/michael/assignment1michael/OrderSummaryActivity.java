package com.example.michael.assignment1michael;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.id.list;

public class OrderSummaryActivity extends AppCompatActivity {

    private Order currentOrder;
    private TextView title;
    private TextView tableNumber;
    private TextView totalOrderPrice;
    private ListView orderSummary;
    private Button saveOrder;

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;
    LinearLayout myLayout;
    int fontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        title = (TextView) findViewById(R.id.orderSummaryTitle) ;
        Intent intent = getIntent();
        currentOrder = (Order) intent.getExtras().getSerializable("currentOrder");

        tableNumber = (TextView) findViewById(R.id.orderTableNumber);
        totalOrderPrice = (TextView) findViewById(R.id.totalOrderPrice);
        orderSummary = (ListView) findViewById(R.id.orderSummaryListView);
        saveOrder = (Button) findViewById(R.id.saveOrder);

        ArrayList<HashMap<String, Object>> orderInfo = currentOrder.getDishesOrderInfo();
        ArrayList<String> dishesDisplay = new ArrayList<String>();
        for(HashMap<String, Object> dishInfo : orderInfo){
            String dishName = (String) dishInfo.get("dishname");
            Integer dishQuantity = (Integer) dishInfo.get("dishquantity");
            Double dishTotalPrice = (Double) dishInfo.get("dishtotalprice");

            String dishInfoString = dishName + " : " + "Quantity : " + dishQuantity + " price : $" + dishTotalPrice +"0";
            Log.d("DishVal", "Dish display string : " + dishInfoString);
            dishesDisplay.add(dishInfoString);
        }


        Log.d("DishVal", "CurrentOrder : " + currentOrder.getTableNumber() + " : " + currentOrder.getOrderTotalPrice());
        tableNumber.setText("Table number is : " + String.valueOf(currentOrder.getTableNumber()));
        totalOrderPrice.setText("Total Order Price is : $" + String.valueOf(currentOrder.getOrderTotalPrice())+"0");


        saveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save to xml file and return to main screen

                try {
                    String ordersString = currentOrder.createXML(currentOrder);
                    Log.d("XMLOutput", "Orders xml output : " + ordersString);
                    FileOutputStream fos = openFileOutput("orders.xml", Context.MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                    outputStreamWriter.write(ordersString);
                    fos.close();
                } catch (Exception e){
                    Log.e("XMLError", "Error saving to XML File");
                    //Toast message
                }

                Intent i = new Intent(OrderSummaryActivity.this, MenuScreen.class);
                startActivity(i);
                finish();
            }
        });

        setPreferenceTheme();
        String[] items = dishesDisplay.toArray(new String[dishesDisplay.size()]);

        orderSummary.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                Log.d("OrderSummaryListView", "Setting font size for list view to : " + fontSize);
                textView.setTextSize(fontSize);

                return textView;
            }
        });
    }



    @Override
    public void onBackPressed (){
        Intent i = new Intent(OrderSummaryActivity.this, MenuScreen.class);
        startActivity(i);
        finish();
    }

    /**
     * Method to set the background and font based on preferences
     */
    private void setPreferenceTheme() {
        sharedPref = getApplicationContext().getSharedPreferences("myprefs", 0);
        String colorString = "";
        colorString = sharedPref.getString("rgb", "");
        Log.d("ColorSet", "Color set is : " + colorString);

        myLayout = (LinearLayout) findViewById(R.id.orderSummaryScreen);
        if(colorString != null && colorString != "") {
            myLayout.setBackgroundColor(Integer.valueOf(colorString));
        }

        String fontSizeString = sharedPref.getString("fontsize", "");
        if("" != fontSizeString) {
            fontSize = Integer.valueOf(fontSizeString);
            Log.d("FontSize", "MainActivity : Read font size from preferences : " + fontSize);

            //Set font for all components in this activity
            tableNumber.setTextSize(fontSize);
            totalOrderPrice.setTextSize(fontSize);
            saveOrder.setTextSize(fontSize);
            title.setTextSize(fontSize);
        }
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        setPreferenceTheme();
    }
}
