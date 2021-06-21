package com.beingknow.eatit2020.ModelResponse;

public class UpdateAddressResponse
{
    private int id;
    private String error;
    private String message;

    public UpdateAddressResponse() {
    }

    public UpdateAddressResponse(int id, String error, String message) {
        this.id = id;
        this.error = error;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
