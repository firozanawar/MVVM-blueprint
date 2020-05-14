package com.firozanwar.mvvm.blueprint.di;

import androidx.lifecycle.ViewModelProvider;

import com.firozanwar.mvvm.blueprint.viewmodels.ViewModelProvidersFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ViewModelFactoryModule {

    // The below is same as above but upper one more convinient because it doesn't return anything.
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProvidersFactory viewModelProvidersFactory);

    // The below is same as above but upper one more convinient but it return same things which is in parameter so no use.
    /*@Provides
    static ViewModelProvider.Factory bindViewModelFactoryByProvides(ViewModelProvidersFactory viewModelProvidersFactory){
        return viewModelProvidersFactory;
    }*/
}
