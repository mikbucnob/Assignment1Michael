package com.example.michael.assignment1michael;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MenuScreen extends AppCompatActivity {

    public static final String SPICY = "spicy";
    public static final String VEGETARIAN = "vegetarian";
    public static final String NORMAL = "normal";
    public static final String ALL = "all";
    public static final String ON_SPECIAL = "onSpecial";
    public static final String ALPHABETICALLY = "alphabetically";
    List<Dish> dishesList = new ArrayList<>();

    private Double totalOrderPrice = 0.0;

    private Menu menu;

    // All static variables
    // XML node keys
    static final String KEY_DISH = "dish"; // parent node
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_CATEGORY = "category";
    static final String KEY_SPECIAL = "on_special";
    public static final String POPULARITY = "popularity";
    static final String KEY_POPULARITY = POPULARITY;
    static final String KEY_PICTURE = "picture";
    static final String KEY_PRICE = "price";

    double price = 0.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        final SharedPreferences myprefs= getSharedPreferences("orderprice", MODE_WORLD_READABLE);
        String totalOrderPriceString = myprefs.getString("R.string.total_order_price", null);
        if(totalOrderPriceString != null && ""!=totalOrderPriceString) {
            totalOrderPrice = Double.valueOf(totalOrderPriceString);
        }

        XMLUtil xmlUtil = new XMLDish(this);

        try {
            InputStream is = getAssets().open("dishes.xml");
            dishesList = xmlUtil.parse(is);
            if (dishesList==null){
                Toast.makeText(this,xmlUtil.getError(),Toast.LENGTH_LONG).show();
            }
        }  catch (IOException e) {
            // File not found or unable to open
            //we knwo for sure that the file doesn't exist or we don't have
            //permissions to open it
            Toast.makeText(this, "cannot open dishes.xml!",Toast.LENGTH_LONG).show();
        }


        ListView myList = loadListViewAdapter(ALL);//passes the required arraylist to the listview


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(MenuScreen.this, TakingOrders.class);
                Dish dish = (Dish) parent.getAdapter().getItem(position);
                Log.d("Dish Val", "Values : " + dish.getName());
                intent.putExtra("imageid", String.valueOf(dish.getImageId()));
                intent.putExtra("name", dish.getName());
                intent.putExtra("price", dish.getPrice());
                //intent.putExtra("totalorderprice", String.valueOf(totalOrderPrice));
                startActivity(intent);

            }
        });
    }

    @NonNull
    private ListView loadListViewAdapter(String optionType) {
        ListView myList = (ListView)findViewById(R.id.myListView);

        ArrayList<Dish> updatedDishes = new ArrayList<Dish>();
        if(optionType.equals(SPICY) || optionType.equals(VEGETARIAN) || optionType.equals(NORMAL)) {


           // dishesList.stream().filter(e -> e.getCategory().equals(optionType));

            for(Dish dish : dishesList){
                if (dish.getCategory().equals(optionType)) {
                    updatedDishes.add(dish);
                }
            }
        } else if(optionType.equals(ALL)){
            updatedDishes = (ArrayList<Dish>) dishesList;
        } else if(optionType.equals(ON_SPECIAL)) {
            for(Dish dish : dishesList){
                if (dish.isOnSpecial()) {
                    updatedDishes.add(dish);
                }

            }
        } else if(optionType.equals(POPULARITY)){
            ArrayList<Integer> popularityList = new ArrayList<Integer>();
            for(Dish dish : dishesList){
                popularityList.add(dish.getPopularity());
            }

            Collections.sort(popularityList);
            Collections.reverse(popularityList);
            Set<Integer> ratingSet =new TreeSet<>(popularityList);

            List list = new ArrayList(ratingSet);
            Collections.sort(list, Collections.reverseOrder());
            Set<Integer> resultSet = new LinkedHashSet(list);

            for(Integer rating : resultSet){
                for(Dish dish : dishesList){
                    if(dish.getPopularity()==rating){
                        updatedDishes.add(dish);
                    }
                }
            }
        } else if(optionType.equals(ALPHABETICALLY)){
            ArrayList<String> titleList = new ArrayList<String>();
            for(Dish dish : dishesList){
                titleList.add(dish.getName());
            }

            Collections.sort(titleList);
            Collections.reverse(titleList);
            Set<String> titleSet =new TreeSet<>(titleList);

            List list = new ArrayList(titleSet);
            Set<String> resultSet = new LinkedHashSet(list);

            for(String title : resultSet){
                for(Dish dish : dishesList){
                    if(dish.getName()==title){
                        updatedDishes.add(dish);
                    }
                }
            }
        }

        DishAdapter myAdapter = new DishAdapter(this, updatedDishes);
        myList.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        return myList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.spicy:
                //Logic to filter the screen with spicy items
                loadListViewAdapter(SPICY);
                return true;
            case R.id.vegetarian:
                //Logic to filter the screen with vegetarian items
                loadListViewAdapter(VEGETARIAN);
                return true;
            case R.id.normal:
                loadListViewAdapter(NORMAL);
                return true;
            case R.id.all:
                loadListViewAdapter(ALL);
                return true;
            case R.id.popularity:
                loadListViewAdapter(POPULARITY);
                return true;
            case R.id.alphabetically:
                loadListViewAdapter(ALPHABETICALLY);
                return true;
            case R.id.onSpecial:
                loadListViewAdapter(ON_SPECIAL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
                //if menu item is available in the parent class on options method
        }
    }

    private void updatePriceMenu() {
        MenuItem priceMenuItem = menu.findItem(R.id.priceval);
        String priceTitle = "Price : " + String.valueOf(price);
        priceMenuItem.setTitle(priceTitle);

    }

    /**
     * Function used to fetch an XML file from assets folder
     * @param fileName - XML file name to convert it to String
     * @return - return XML in String form
     */
    private String getXml(String fileName) {
        // this method reads the assets folder file and stores the contents of the xml file into
        // a string
        String xmlString = null;
        AssetManager am = this.getAssets();
        try {
            InputStream is = am.open(fileName);
            int length = is.available();
            Log.d("length", "length : " + length);
            byte[] data = new byte[length];
            is.read(data);
            xmlString = new String(data);
            Log.d("Xml String ", " String : " + xmlString);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return xmlString;
    }

}
