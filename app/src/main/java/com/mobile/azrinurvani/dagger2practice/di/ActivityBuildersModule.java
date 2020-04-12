package com.mobile.azrinurvani.dagger2practice.di;

import com.mobile.azrinurvani.dagger2practice.di.auth.AuthModule;
import com.mobile.azrinurvani.dagger2practice.di.auth.AuthScope;
import com.mobile.azrinurvani.dagger2practice.di.auth.AuthViewModelsModule;
import com.mobile.azrinurvani.dagger2practice.di.main.MainFragmentBuildersModule;
import com.mobile.azrinurvani.dagger2practice.di.main.MainModule;
import com.mobile.azrinurvani.dagger2practice.di.main.MainScope;
import com.mobile.azrinurvani.dagger2practice.di.main.MainViewModelsModule;
import com.mobile.azrinurvani.dagger2practice.ui.auth.AuthActivity;
import com.mobile.azrinurvani.dagger2practice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*Dibuat abstract class karena menggunakan @ContribueAndroidInjector*/
@Module
public abstract class ActivityBuildersModule {

    /*Setiap activity wajib memiliki ContributeAndroidInjector annotation*/
    @AuthScope
    @ContributesAndroidInjector(modules = {
            AuthViewModelsModule.class,
            AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainFragmentBuildersModule.class,
            MainViewModelsModule.class,
            MainModule.class})
    abstract MainActivity contributeMainActivity();


    /*Note****
    * Modules AuthViewModelsModule merupakan implementasi sub component,
    * yang mana secara otomatis di generate ketika memasukkan module dari activity ke dalam @ContributeAndroidInjector,
    * hasilnya bisa dilihat di package java (generated), harus di rebuild terlebih dahulu
    */
}
