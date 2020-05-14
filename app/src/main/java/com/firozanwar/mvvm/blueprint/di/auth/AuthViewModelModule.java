package com.firozanwar.mvvm.blueprint.di.auth;

import androidx.lifecycle.ViewModel;

import com.firozanwar.mvvm.blueprint.di.ViewModelKey;
import com.firozanwar.mvvm.blueprint.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
