package com.example.user.realmsample.Presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.example.user.realmsample.Interactor.LoginInteractor;
import com.example.user.realmsample.ViewInterface.LoginView;

public class LoginPresenter implements LoginInteractor.Confirmation{


    String username;
    String password;
    String confirm;

    LoginInteractor loginInteractor;
    LoginView loginView;

    public LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {

        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }


    public void validation(String username,String password,String confirm) {

        loginInteractor.login(username,password,confirm,  this);
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {

            loginView.setUsernameError();
        }

    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {

            loginView.setPasswordError();
        }
    }

    @Override
    public void onConfirmPasswordError() {
        if (loginView != null) {

            loginView.setConfirmPasswordError();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }

    @Override
    public void neartosuccess(){
        if (loginView != null) {
            loginView.matchpassword();
        }

    }


}
