package com.beingknow.eatit2020.ModelResponse;

public class OrderResponse1
{
    private int id;
    private String order_no;

    public OrderResponse1(int id, String order_no) {
        this.id = id;
        this.order_no = order_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}
