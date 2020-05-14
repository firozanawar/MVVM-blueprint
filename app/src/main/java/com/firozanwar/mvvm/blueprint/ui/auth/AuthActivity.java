package com.firozanwar.mvvm.blueprint.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.firozanwar.mvvm.blueprint.R;
import com.firozanwar.mvvm.blueprint.model.User;
import com.firozanwar.mvvm.blueprint.ui.main.MainLandingActivity;
import com.firozanwar.mvvm.blueprint.viewmodels.ViewModelProvidersFactory;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    @Inject
    String myString;

    @Inject
    boolean isApp;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProvidersFactory providersFactory;

    @Inject
    @Named("app_user")
    User user1;

    @Inject
    @Named("auth_user")
    User user2;

    AuthViewModel authViewModel;

    private EditText userId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);

        Log.d(TAG, "onCreate: " + myString);
        Log.d(TAG, "onCreate, App is : " + isApp);

        // Rotation will give the same user1 because of singlton
        Log.d(TAG, "onCreate, app user1 is : " + user1);
        Log.d(TAG, "onCreate, auth user1 is : " + user2);

        authViewModel = ViewModelProviders.of(this, providersFactory).get(AuthViewModel.class);

        // Loading logo with Glide library into imageview by injected RequestManager
        loadLogo();

        subscribeUser();
    }

    private void loadLogo() {
        requestManager.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        attemptLogin();
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userId.getText().toString())) {
            return;
        }
        authViewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }

    // Commented because it enhanced code.
    /*private void subscribeUser(){
        authViewModel.observerUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null)
                    Log.d(TAG, "onChanged: "+user.getEmail());
            }
        });
    }*/

    private void subscribeUser() {
        authViewModel.observerAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case AUTHENTICATED:
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            break;
                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message
                                    + "\nDid you enter a number between 1 and 10?", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void onLoginSuccess(){
        Log.d(TAG, "onLoginSuccess: login successful!");
        Intent intent = new Intent(this, MainLandingActivity.class);
        startActivity(intent);
        finish();
    }
}
