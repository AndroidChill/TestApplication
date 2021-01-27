package com.example.testapplication.viewModel.signin;

import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableChar;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.model.SignInBody;
import com.example.testapplication.network.ServerFactory;
import com.example.testapplication.network.request.SignInRequest;
import com.example.testapplication.network.service.AuthService;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class SignInRepository {

    private static SignInRepository instance;

    //показывать ошибку
    private ObservableInt error;

    //показывать загрузку
    private ObservableInt loading;
    private ObservableInt showContent;

    private AuthService service;

    //очистка от мусора запросов
    private CompositeDisposable compositeDisposable;

//    private ObservableChar errorText = BehaviorSubject.create();

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

        error.set(View.GONE);
        loading.set(View.VISIBLE);
        showContent.set(View.GONE);

        //из интерфейса с запросами дергаем функцию
        service.getListCoins(request)
                //выполнение в фоне
                .subscribeOn(Schedulers.io())
                //передача в основной поток
                .observeOn(AndroidSchedulers.mainThread())
                //подписка на действия
                .subscribe(new Observer<SignInBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    //правильный ответ
                    @Override
                    public void onNext(SignInBody signInBody)    {
                        liveData.setValue(signInBody);
                        loading.set(View.GONE);
                        showContent.set(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {

                        //получение тела ошибки
                        if(e instanceof HttpException){
                            HttpException httpException = (HttpException)e;
                            String errorResponse = null;
                            try {
                                errorResponse = httpException.response().errorBody().string();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            SignInBody errorModel =  new Gson().fromJson(errorResponse, SignInBody.class);
                            Log.d("tag", errorModel.getFirstError().getEmail());
                        }
                        error.set(View.VISIBLE);
                        loading.set(View.GONE);

                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.dispose();
                        loading.set(View.GONE);
                    }
                });

        return liveData;
    }

    public ObservableInt getError() {
        return error;
    }

    public ObservableInt getLoading() {
        return loading;
    }

    public ObservableInt getShowContent() {
        return showContent;
    }

    //    public Observable<String> showTextError(){
//        return errorText;
//    }

}
