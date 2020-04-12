package com.mobile.azrinurvani.dagger2practice.di.main;

import androidx.lifecycle.ViewModel;

import com.mobile.azrinurvani.dagger2practice.di.ViewModelKey;
import com.mobile.azrinurvani.dagger2practice.ui.main.posts.PostViewModel;
import com.mobile.azrinurvani.dagger2practice.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostViewModel viewModel);
}
