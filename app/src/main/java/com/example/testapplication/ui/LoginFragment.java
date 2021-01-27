package com.example.testapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;
import com.example.testapplication.databinding.FragmentLoginBinding;
import com.example.testapplication.model.SignInBody;
import com.example.testapplication.network.request.SignInRequest;
import com.example.testapplication.viewModel.signin.SignInFactory;
import com.example.testapplication.viewModel.signin.SignInViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private SignInViewModel viewModel;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        viewModel = new ViewModelProvider(getViewModelStore(), new SignInFactory()).get(SignInViewModel.class);

        //пихаем в xml viewmodel
        binding.setViewModel(viewModel);

        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.getCoinsList(initSignInRequest()).observe(getViewLifecycleOwner(), new Observer<SignInBody>() {
                    @Override
                    public void onChanged(SignInBody signInBody) {

                        Toast.makeText(getContext(), "dsf", Toast.LENGTH_SHORT).show();

                        Toast.makeText(getContext(), signInBody.toString(), Toast.LENGTH_SHORT).show();

                        if(signInBody.getToken() != null){
                            Toast.makeText(getContext(), signInBody.getToken(), Toast.LENGTH_SHORT).show();
                        } else {
                            if(signInBody.getFirstError() != null){
                                Toast.makeText(getContext(), signInBody.getFirstError().getEmail(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
            }
        });

        return binding.getRoot();
    }

    private SignInRequest initSignInRequest(){
        return new SignInRequest(
                binding.email.getText().toString(),
                binding.password.getText().toString()
        );
    }
}
