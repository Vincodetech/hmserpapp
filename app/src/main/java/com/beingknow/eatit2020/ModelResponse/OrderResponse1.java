package com.beingknow.eatit2020.ModelResponse;

public class OrderResponse1
{
    private int Id;
    private String order_no;

    public OrderResponse1()
    {

    }

    public OrderResponse1(int Id, String order_no) {
        this.Id = Id;
        this.order_no = order_no;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}