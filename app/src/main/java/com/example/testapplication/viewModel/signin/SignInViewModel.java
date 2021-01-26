package com.example.testapplication.viewModel.signin;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapplication.model.SignInBody;
import com.example.testapplication.network.request.SignInRequest;

public class SignInViewModel extends ViewModel {

    private SignInRepository repository;

    public SignInViewModel(){
        repository = SignInRepository.getInstance();
    }

    public MutableLiveData<SignInBody> getCoinsList(SignInRequest request){
        return repository.signIn(request);
    }

}
