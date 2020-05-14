package com.firozanwar.mvvm.blueprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.firozanwar.mvvm.blueprint.model.User;
import com.firozanwar.mvvm.blueprint.ui.auth.AuthActivity;
import com.firozanwar.mvvm.blueprint.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sunscribeObserve();
    }

    private void sunscribeObserve() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING:
                            break;
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            break;
                        case NOT_AUTHENTICATED:
                            navLoginScreen();
                            break;
                        case ERROR:

                            break;
                    }
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent in = new Intent(this, AuthActivity.class);
        startActivity(in);
        finish();
    }
}
