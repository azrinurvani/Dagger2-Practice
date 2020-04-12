package com.mobile.azrinurvani.dagger2practice.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.azrinurvani.dagger2practice.SessionManager;
import com.mobile.azrinurvani.dagger2practice.models.User;
import com.mobile.azrinurvani.dagger2practice.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager){
        this.sessionManager = sessionManager;
        Log.d(TAG,"ProfileViewModel: viewModel is ready...");
    }


    public LiveData<AuthResource<User>> getAuthenticateUser(){
        return sessionManager.getAuthUser();
    }
}
