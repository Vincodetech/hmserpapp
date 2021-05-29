package com.beingknow.eatit2020.Models;

public class Category {

    private String Image;
    private String Name;


    public Category() {}

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
