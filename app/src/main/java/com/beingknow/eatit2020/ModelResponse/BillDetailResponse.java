package com.beingknow.eatit2020.ModelResponse;

import java.util.Date;

public class BillDetailResponse
{
    private int bill_no;
    private String bill_date;
    private int order_id;
    private double grand_total;
    private int active;

    public BillDetailResponse(int bill_no, String bill_date, int order_id, double grand_total, int active) {
        this.bill_no = bill_no;
        this.bill_date = bill_date;
        this.order_id = order_id;
        this.grand_total = grand_total;
        this.active = active;
    }

    public BillDetailResponse() {
    }

    public int getBill_no() {
        return bill_no;
    }

    public void setBill_no(int bill_no) {
        this.bill_no = bill_no;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
