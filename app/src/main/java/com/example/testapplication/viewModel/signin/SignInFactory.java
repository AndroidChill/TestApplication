package com.example.testapplication.viewModel.signin;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SignInFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SignInViewModel();
    }
}


//public class CoinsFactory implements ViewModelProvider.Factory {
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return (T) new CoinsViewModel();
//    }
//}

