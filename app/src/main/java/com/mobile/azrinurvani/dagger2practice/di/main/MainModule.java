package com.mobile.azrinurvani.dagger2practice.di.main;

import com.mobile.azrinurvani.dagger2practice.network.main.MainApi;
import com.mobile.azrinurvani.dagger2practice.ui.main.posts.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static PostRecyclerAdapter provideAdapter(){
        return new PostRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
