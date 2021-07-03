package com.beingknow.eatit2020.ModelResponse;

public class DeleteCartDataResponse
{
    String error;
    String message;

    public DeleteCartDataResponse() {
    }

    public DeleteCartDataResponse(String error, String message) {
        this.error = error;
        this.message = message;
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
