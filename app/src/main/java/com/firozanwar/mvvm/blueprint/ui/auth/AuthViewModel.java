package com.firozanwar.mvvm.blueprint.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;


import com.firozanwar.mvvm.blueprint.SessionManager;
import com.firozanwar.mvvm.blueprint.model.User;
import com.firozanwar.mvvm.blueprint.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthActivity";

    private AuthApi mAuthApi;
    private SessionManager sessionManager;

    //private MediatorLiveData<User> authUser = new MediatorLiveData<>();  // General
    //private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();    //

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        Log.d(TAG, "AuthViewModel is working like a charm..");
        mAuthApi = authApi;
        this.sessionManager = sessionManager;

        if (mAuthApi == null) {
            Log.d(TAG, "AuthViewModel authApi is null: ");
        } else {
            Log.d(TAG, "AuthViewModel authApi is not null: ");
        }

        authApi.getUsers(1)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void authenticateWithId(int userId) {

        Log.d(TAG, "authenticateWithId: Attempting to login");
        sessionManager.authenticateWithId(queryUserID(userId));
    }

    private LiveData<AuthResource<User>> queryUserID(int userId) {

        return LiveDataReactiveStreams.fromPublisher(
                mAuthApi.getUsers(userId)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User erroruser = new User();
                                erroruser.setId(-1);
                                return erroruser;
                            }
                        })

                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {

                                if (user.getId() == -1) {
                                    return AuthResource.error("Couldnot authenticate user", (User) null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })

                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<User>> observerAuthState() {
        return sessionManager.getAuthUser();
    }
}
