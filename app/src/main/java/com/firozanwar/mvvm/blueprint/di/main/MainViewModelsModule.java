package com.firozanwar.mvvm.blueprint.di.main;

import androidx.lifecycle.ViewModel;

import com.firozanwar.mvvm.blueprint.di.ViewModelKey;
import com.firozanwar.mvvm.blueprint.ui.main.post.PostViewModel;
import com.firozanwar.mvvm.blueprint.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindMainViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostViewModel(PostViewModel postViewModel);
}
