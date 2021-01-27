package com.example.testapplication.viewModel.signin;

import android.content.Context;

import androidx.databinding.ObservableInt;
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


    public ObservableInt getError() {
        return repository.getError();
    }

    public ObservableInt getLoading() {
        return repository.getLoading();
    }

    public ObservableInt getShowContent() {
        return repository.getShowContent();
    }

}
