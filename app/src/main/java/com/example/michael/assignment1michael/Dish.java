package com.example.michael.assignment1michael;

/**
 * Created by Michael on 10/04/2017.
 */

public class Dish {

    private String name; //Name of the Dish
    private String description;
    private String price;
    private int imageId; //stores the int id value for String filename of image
    private String category;
    private boolean onSpecial;
    private int popularity;

    /*Parameterless Constructor*/
    public Dish(){

    }

    /*
    Constructor for Dish class
     */
    public Dish(String name, String description, String price, int imageId, String category,
                boolean onSpecial, int popularity){

        this.name = name;
        this.description = description;
        this.price = price;
        this.imageId = imageId;
        this.category = category;
        this.onSpecial = onSpecial;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isOnSpecial() {
        return onSpecial;
    }

    public void setOnSpecial(boolean onSpecial) {
        this.onSpecial = onSpecial;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
