package com.mobile.azrinurvani.dagger2practice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.mobile.azrinurvani.dagger2practice.models.User;
import com.mobile.azrinurvani.dagger2practice.ui.auth.AuthActivity;
import com.mobile.azrinurvani.dagger2practice.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subscribeObservers();
    }

    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            break;
                        }
                        case ERROR:{
                            Log.e(TAG,"onChanged : "+userAuthResource.message);
                            break;
                        }
                        case AUTHENTICATED:{
                            Log.d("AuthActivity","onChanged : Login SUCCESS: "+userAuthResource.data.getEmail());
                            break;
                        }
                        case NOT_AUTHENTICATED:{
                            navScreenLogin();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void navScreenLogin() {
        Intent intent = new Intent(this,AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
