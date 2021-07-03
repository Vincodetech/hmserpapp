package com.beingknow.eatit2020.ModelResponse;

public class CartDataResponse
{
    private int id;
    private String name;
    private String quantity;
    private Double price;
    private Double amount;
    private int i_id;
    private int u_id;
    private int active;

    public CartDataResponse(int id, String name, String quantity, Double price, Double amount, int i_id, int u_id, int active) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.i_id = i_id;
        this.u_id = u_id;
        this.active = active;
    }

    public CartDataResponse() {
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


    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
