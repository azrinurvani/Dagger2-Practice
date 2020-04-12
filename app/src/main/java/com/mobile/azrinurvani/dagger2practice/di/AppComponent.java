package com.mobile.azrinurvani.dagger2practice.di;

import android.app.Application;
import com.mobile.azrinurvani.dagger2practice.BaseApplication;
import com.mobile.azrinurvani.dagger2practice.SessionManager;
import com.mobile.azrinurvani.dagger2practice.viewmodels.ViewModelProviderFactory;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelProviderFactory.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
