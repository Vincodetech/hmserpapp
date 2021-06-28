package com.beingknow.eatit2020.ModelResponse;

public class OrderResponse
{
    private int id;
    private String order_no;
    private int active;
    private int user_id;
    private String error;
    private String message;

    public OrderResponse()
    {

    }

    public OrderResponse(int id, String order_no, int active, int user_id, String error, String message) {
        this.id = id;
        this.order_no = order_no;
        this.active = active;
        this.user_id = user_id;
        this.error = error;
        this.message = message;
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

    public int isActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
