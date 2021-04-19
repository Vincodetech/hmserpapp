package com.beingknow.eatit2020.ModelResponse;

public class LoginResponse {
    int id;
    String email;
    String user_name;
    String error, message;

    public LoginResponse(int id, String email, String user_name, String error, String message) {
        this.id = id;
        this.email = email;
        this.user_name = user_name;
        this.error = error;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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
