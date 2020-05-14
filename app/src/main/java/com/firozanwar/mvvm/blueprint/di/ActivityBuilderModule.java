package com.firozanwar.mvvm.blueprint.di;

import com.firozanwar.mvvm.blueprint.di.auth.AuthModule;
import com.firozanwar.mvvm.blueprint.di.auth.AuthScope;
import com.firozanwar.mvvm.blueprint.di.auth.AuthViewModelModule;
import com.firozanwar.mvvm.blueprint.di.main.MainFragmentBuilderModule;
import com.firozanwar.mvvm.blueprint.di.main.MainModule;
import com.firozanwar.mvvm.blueprint.di.main.MainScope;
import com.firozanwar.mvvm.blueprint.di.main.MainViewModelsModule;
import com.firozanwar.mvvm.blueprint.ui.auth.AuthActivity;
import com.firozanwar.mvvm.blueprint.ui.main.MainLandingActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    // This behave like a subComponent
    @AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    // Using this we can directly get the string  @Inject String myString in AuthActivity;
    /*@Provides
    static String someString() {
        return "Hi this string is getting injected from ActivityBuilderModule";
    }*/

    // This behave like a subComponent
    // MainFragmentBuilderModule will only be availble to MainLandingActivity
    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuilderModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainLandingActivity contributeMainLandingActivity();
}
