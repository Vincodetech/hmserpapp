package com.beingknow.eatit2020.ModelResponse;

public class OrderResponse3
{
    private int Id;
    private String order_no;
    private String order_type;

    public OrderResponse3()
    {

    }

    public OrderResponse3(int Id, String order_no, String order_type) {
        this.Id = Id;
        this.order_no = order_no;
        this.order_type = order_type;
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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
