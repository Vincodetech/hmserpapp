package com.beingknow.eatit2020.Models;

public class Item {
    private int id;
    private String name;
    private String quantity;
    private String description;
    private double price;
    private String server_url_image;

    public Item(int id, String name, String quantity, String description, double price, String server_url_image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.server_url_image = server_url_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getServer_url_image() {
        return server_url_image;
    }

    public void setServer_url_image(String server_url_image) {
        this.server_url_image = server_url_image;
    }
}
