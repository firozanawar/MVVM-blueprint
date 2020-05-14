package com.firozanwar.mvvm.blueprint.di.main;


import com.firozanwar.mvvm.blueprint.ui.main.post.PostFragment;
import com.firozanwar.mvvm.blueprint.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostFragment contributePostFragment();
}
