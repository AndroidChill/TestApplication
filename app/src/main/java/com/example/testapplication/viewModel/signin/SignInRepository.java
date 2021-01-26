package com.example.testapplication.viewModel.signin;

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.model.SignInBody;
import com.example.testapplication.network.ServerFactory;
import com.example.testapplication.network.request.SignInRequest;
import com.example.testapplication.network.service.AuthService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignInRepository {

    private static SignInRepository instance;

    private ObservableInt error;
    private ObservableInt loading;
    private ObservableInt showContent;

    private AuthService service;

    private CompositeDisposable compositeDisposable;

    private SignInRepository(){

        compositeDisposable = new CompositeDisposable();
        service = ServerFactory.getInstance().getAuthService();

        error = new ObservableInt();
        loading = new ObservableInt();
        showContent = new ObservableInt();
    }

    public static SignInRepository getInstance(){
        if (instance == null){
            instance = new SignInRepository();
        }
        return instance;
    }

    public MutableLiveData<SignInBody> signIn(SignInRequest request){
        final MutableLiveData<SignInBody> liveData = new MutableLiveData<>();

        service.getListCoins(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignInBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SignInBody signInBody) {
                        liveData.setValue(signInBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error.set(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.dispose();
                    }
                });

        return liveData;
    }

}
