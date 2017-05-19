package com.example.michael.assignment1michael;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 8/05/2017.
 */

public class XMLDish extends XMLUtil{

    Context context;

    public XMLDish(Context context){
        this.context = context;
    }

    //@ is an annotation - it's like notes
    @Override
    protected List readRoot(XmlPullParser parser) throws XmlPullParserException, IOException {
        List dishesList = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "dishes");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the dish tag
            if (name.equals("dish")) {
                dishesList = readDish(parser);
                break;
            } else {
                skip(parser);
            }
        }
        return dishesList;
    }


    // Parses the contents of a dish.
    private ArrayList<Dish> readDish(XmlPullParser parser) throws XmlPullParserException, IOException {


        ArrayList<Dish> dishes = null;
        int eventType = parser.getEventType();
        Dish dish = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if(null == dishes){
                        dishes = new ArrayList();
                    }
                    name = parser.getName();
                    if (name.equals("dish")){
                        Log.d("dish", "Start Tag dish");
                        dish = new Dish() ;
                    } else if (dish != null){
                        Log.d("check title", "name : " + name );
                        if (name.equals("title")){
                            dish.setName(parser.nextText());
                        } else if (name.equals("description")){
                            dish.setDescription(parser.nextText());
                        } else if (name.equals("category")){
                            dish.setCategory(parser.nextText());
                        } else if(name.equals("on_special")){
                            dish.setOnSpecial(Boolean.valueOf(parser.nextText()));
                        } else if(name.equals("popularity")){
                            dish.setPopularity(Integer.valueOf(parser.nextText()));
                        } else if(name.equals("picture")){
                            int dishImageId = context.getResources().getIdentifier(parser.nextText(), null, context.getPackageName());
                            dish.setImageId(Integer.valueOf(dishImageId));
                        } else if(name.equals("price")){
                            dish.setPrice(parser.nextText());
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

        return dishes;
    }


    // Parses the contents of a dish.
   /* private Dish readDish(XmlPullParser parser) throws XmlPullParserException, IOException {
        Dish dish = null;

        parser.require(XmlPullParser.START_TAG, ns, "dish");

        //Fetch the title of the dish
        String title = null;
        String description = null;
        String category = null;
        String onSpecial = null;
        String popularity = null;
        String picture = null;
        String price = null;
        int dishImageId = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            Log.d("CheckTag", "Checking tagName : " +  tagName);
            if (tagName.equals("title")) {
                title = readTag(parser,"title");
            } else if (tagName.equals("description")) {
                description = readTag(parser, "description");
            } else if (tagName.equals("category")) {
                category = readTag(parser, "category");
            } else if (tagName.equals("onSpecial")) {
                onSpecial = readTag(parser, "onSpecial");
            } else if (tagName.equals("popularity")) {
                popularity = readTag(parser, "popularity");
            } else if (tagName.equals("picture")) {
                picture = readTag(parser, "picture");
                dishImageId = context.getResources().getIdentifier(picture, null, context.getPackageName());
            } else if (tagName.equals("price")) {
                price = readTag(parser, "price");
            } else {
                skip(parser);
            }


            boolean dishSpecial = false;
            if(null != onSpecial && onSpecial.equals("true")) {
                dishSpecial = true;
            } else if(null != onSpecial && onSpecial.equals("false")){
                dishSpecial = false;// converts string to boolean
            }

            Log.d("DishValues", "Title : " + title + " Desc : " + description +
                   " Price : " + price + " dishImgID : " + dishImageId + " cat : " + category
                   + " dishSpecial : " + dishSpecial + " : popularity : " + popularity);

            dish = new Dish(title, description, price, dishImageId, category,
                    dishSpecial, Integer.valueOf(popularity));
        }
        return dish;
    }*/

    public String createXML(Object object) throws IllegalArgumentException, IllegalStateException, IOException {
      return null;
    }

   /* public String createXML(Object object) throws IllegalArgumentException, IllegalStateException, IOException
    {
        Person person = (Person)object;

        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        xmlSerializer.setOutput(stringWriter);

        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        xmlSerializer.startTag(ns, "person");
        xmlSerializer.attribute(ns, "age", String.valueOf(person.age));

        xmlSerializer.startTag(ns, "name");
        xmlSerializer.text(person.name);
        xmlSerializer.endTag(ns, "name");

        xmlSerializer.endTag(ns, "person");

        xmlSerializer.endDocument();
        return stringWriter.toString();
    }*/

}
