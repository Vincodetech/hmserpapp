package com.beingknow.eatit2020.ModelResponse;

public class OrderResponse2
{
    private int id;
    private String order_no;
    private String order_type;

    public OrderResponse2()
    {

    }

    public OrderResponse2(int id, String order_no, String order_type) {
        this.id = id;
        this.order_no = order_no;
        this.order_type = order_type;
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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
