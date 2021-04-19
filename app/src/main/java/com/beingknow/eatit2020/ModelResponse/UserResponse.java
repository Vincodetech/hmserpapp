package com.beingknow.eatit2020.ModelResponse;

public class UserResponse
{
    private int id;
    private String user_name;
    private String email;
    private String phone;
    private int active;

    public UserResponse(int id, String user_name, String email, String phone, int active) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.phone = phone;
        this.active = active;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_Name() {
        return user_name;
    }

    public void setUser_Name(String user_name) {
        this.user_name = user_name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }



}
