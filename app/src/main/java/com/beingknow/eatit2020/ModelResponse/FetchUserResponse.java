package com.beingknow.eatit2020.ModelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchUserResponse
{
    @SerializedName("users")
    List<UserResponse> userResponseList;
    String error;

    public FetchUserResponse(List<UserResponse> userResponseList, String error)
    {
        this.userResponseList = userResponseList;
        this.error = error;
    }

    public List<UserResponse> getUserResponseList() {
        return userResponseList;
    }

    public void setUserResponseList(List<UserResponse> userResponseList) {
        this.userResponseList = userResponseList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
