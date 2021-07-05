package com.beingknow.eatit2020.ModelResponse;

public class AmountResponse
{
    private Double total_amount;

    public AmountResponse(Double total_amount) {
        this.total_amount = total_amount;
    }

    public AmountResponse() {
    }

    public Double getAmount() {
        return total_amount;
    }

    public void setAmount(Double total_amount) {
        this.total_amount = total_amount;
    }
}
