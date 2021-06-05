package com.beingknow.eatit2020.ModelResponse;

public class CartResponse
{
    private int item_id;
    private int order_id;
    private String quantity;
    private float amount;
    private String error;
    private String message;

    public CartResponse(int item_id, int order_id, String quantity, float amount, String error, String message) {
        this.item_id = item_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.amount = amount;
        this.error = error;
        this.message = message;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}