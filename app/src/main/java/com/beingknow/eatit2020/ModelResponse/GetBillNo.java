package com.beingknow.eatit2020.ModelResponse;

public class GetBillNo
{
    private int bill_no;

    public GetBillNo(int bill_no) {
        this.bill_no = bill_no;
    }

    public int getBill_no() {
        return bill_no;
    }

    public void setBill_no(int bill_no) {
        this.bill_no = bill_no;
    }

    public GetBillNo() {
    }
}
