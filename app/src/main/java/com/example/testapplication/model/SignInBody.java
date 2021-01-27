package com.example.testapplication.model;

import com.example.testapplication.model.singin.FirstError;
import com.google.gson.annotations.SerializedName;

//ответ
public class SignInBody {

    //это надо будет запомнить в шард
    @SerializedName("token")
    private String token;

    //для обработки ошибки
    @SerializedName("first_errors")
    private FirstError firstError;

    public SignInBody(String token) {
        this.token = token;
    }

    public SignInBody(FirstError firstError) {
        this.firstError = firstError;
    }

    public FirstError getFirstError() {
        return firstError;
    }

    public void setFirstError(FirstError firstError) {
        this.firstError = firstError;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
