package com.beingknow.eatit2020.ModelResponse;

public class AmountResponse
{
    private Double amount;

    public AmountResponse(Double amount) {
        this.amount = amount;
    }

    public AmountResponse() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
