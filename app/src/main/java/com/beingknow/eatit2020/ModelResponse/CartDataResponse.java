package com.beingknow.eatit2020.ModelResponse;

public class CartDataResponse
{
    private String name;
    private String quantity;
    private Double price;
    private Double amount;
    private int item_id;
    private int active;

    public CartDataResponse(String name, String quantity, Double price, Double amount, int item_id, int active) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.item_id = item_id;
        this.active = active;
    }

    public CartDataResponse() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
