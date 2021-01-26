package com.example.testapplication.model;

import com.google.gson.annotations.SerializedName;

public class SignInBody {

    @SerializedName("token")
    private String token;

    public SignInBody(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
