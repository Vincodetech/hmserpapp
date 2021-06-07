package com.beingknow.eatit2020.Models;

public class Item1
{
    private String name;
    private String quantity;
    private double price;

    public Item1(String name, String quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public Item1()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
