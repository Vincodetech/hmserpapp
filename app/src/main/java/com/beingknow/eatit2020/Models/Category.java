package com.beingknow.eatit2020.Models;

public class Category {

    private String Name;
    private int Image;

    public Category() {}

    public Category(String name, int image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
