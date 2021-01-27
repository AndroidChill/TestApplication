package com.example.testapplication.network.service;

import com.example.testapplication.model.SignInBody;
import com.example.testapplication.network.request.SignInRequest;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {

    //запрос
    @POST("auth/login")
    Observable<SignInBody> getListCoins(@Body SignInRequest request);

}
