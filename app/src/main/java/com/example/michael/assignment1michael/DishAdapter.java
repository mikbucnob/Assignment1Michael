package com.example.michael.assignment1michael;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by Michael on 10/04/2017.
 */

public class DishAdapter extends BaseAdapter
{
    private Activity activity;
    private ArrayList<Dish> data;
    private static LayoutInflater inflater=null;

    public DishAdapter(Activity a, ArrayList<Dish> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //service locator pattern (inflates xml to objects)

        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_menu, null);

        TextView title = (TextView)vi.findViewById(R.id.dishText); // title
        TextView description = (TextView)vi.findViewById(R.id.descriptionText); // artist name
        TextView price = (TextView)vi.findViewById(R.id.priceText); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.myImage); // thumb image

        Dish dish = data.get(position);

        // Setting all values in listview
        title.setText(dish.getName());
        description.setText(dish.getDescription());
        price.setText(dish.getPrice());
        int imageId = dish.getImageId();

        Drawable image = ContextCompat.getDrawable(activity, imageId);
        //returns the getDrawable object based on the image identifier
        thumb_image.setImageDrawable(image);

        return vi;
    }

}
