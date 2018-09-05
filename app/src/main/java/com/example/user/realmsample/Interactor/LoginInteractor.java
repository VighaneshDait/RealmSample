package com.example.user.realmsample.Interactor;

import android.os.Handler;
import android.text.TextUtils;

public class LoginInteractor {

  public interface Confirmation{

        void onUsernameError();

        void onPasswordError();

        void onConfirmPasswordError();

        void neartosuccess();

        void onSuccess();
    }

    public void login(final String username, final String password,final String confirm, final Confirmation listener) {

        String pass=password;
        String con=confirm;
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(() -> {
            if (TextUtils.isEmpty(username)) {
                listener.onUsernameError();
                return;
            }
            else if (TextUtils.isEmpty(password)) {
                listener.onPasswordError();
                return;
            }
            else if (TextUtils.isEmpty(confirm)) {
                listener.onConfirmPasswordError();
                return;
            }
            else if (!pass.equals(con)) {
                listener.neartosuccess();
                return;
            }
            listener.onSuccess();
        }, 2000);
    }
}
