package com.example.user.realmsample.First;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.realmsample.Interactor.LoginInteractor;
import com.example.user.realmsample.Models.Login;
import com.example.user.realmsample.Presenter.LoginPresenter;
import com.example.user.realmsample.R;
import com.example.user.realmsample.ViewInterface.LoginView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements LoginView{

    public EditText username;
    public EditText password;
    public EditText confirm;
    Realm realm;

    public Button loginone;
    public Button showone;
    LoginPresenter presenter;
    Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        confirm=(EditText)findViewById(R.id.confirm);
        loginone=(Button)findViewById(R.id.login);
        showone=(Button)findViewById(R.id.disp);

        realm=Realm.getDefaultInstance();

        loginone.setOnClickListener(v -> validation());

        presenter=new LoginPresenter(this, new LoginInteractor());

       showdata();

    }

    private void showdata() {

        showone.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                RealmResults<Login> loginRealmResults = realm.where(Login.class).findAll();
                String op = "";

                for (Login login : loginRealmResults) {
                    op += login.toString();
                }
            Toast.makeText(MainActivity.this, "Message" + op, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void writeToDb(final String username, final String password, final String confirm){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Login login= bgRealm.createObject(Login.class);
                login.setUsername(username);
                login.setPassword(password);
                login.setConfirm(confirm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
               Toast.makeText(MainActivity.this, "Win The Match", Toast.LENGTH_SHORT).show();
                //check(username);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("Database",error.getMessage());
            }
        });
    }

    public void check(String username) {

         login.getUsername();
        Toast.makeText(this, "See->"+login.getUsername(), Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void setUsernameError() {
        username.setError("Username Can't Empty");
    }

    @Override
    public void setPasswordError() {

        password.setError("Password can't Empty");
    }

    @Override
    public void setConfirmPasswordError() {

        confirm.setError("Password can't Empty");
    }

    @Override
    public void navigateToHome() {

        //startActivity(new Intent(this, SecondActivity.class));
        //finish();
        //check(username.getText().toString());

        writeToDb(username.getText().toString().trim(),password.getText().toString().trim(),confirm.getText().toString().trim());
    }

    @Override
    public void matchpassword() {

        Toast.makeText(this, "Password not match.", Toast.LENGTH_SHORT).show();
    }

    public void validation() {

        presenter.validation(username.getText().toString(),password.getText().toString(),confirm.getText().toString());
    }
}
