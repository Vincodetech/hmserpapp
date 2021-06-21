package com.beingknow.eatit2020.Interface;

public class OrderResponse4
{
    private int id;
    private String order_status;
    private String payment_status;

    public OrderResponse4(int id, String order_status, String payment_status) {
        this.id = id;
        this.order_status = order_status;
        this.payment_status = payment_status;
    }

    public OrderResponse4() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
