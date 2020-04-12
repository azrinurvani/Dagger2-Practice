package com.mobile.azrinurvani.dagger2practice.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.mobile.azrinurvani.dagger2practice.R;
import com.mobile.azrinurvani.dagger2practice.models.User;
import com.mobile.azrinurvani.dagger2practice.ui.main.MainActivity;
import com.mobile.azrinurvani.dagger2practice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    /*Inject Component here*/
    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    private AuthViewModel viewModel;

    private EditText userId;
    private ProgressBar progressBar;

    @Inject
    @Named("app_scope")
    User user1;

    @Inject
    @Named("auth_scope")
    User user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);

        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);

        setLogo();
        subscribeObservers();

        Log.d(TAG, "onCreate: User1: "+user1);
        Log.d(TAG, "onCreate: User2: "+user2);
    }

    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            showProgresBar(true);
                            break;
                        }
                        case ERROR:{
                            showProgresBar(false);
                            Toast.makeText(AuthActivity.this,userAuthResource.message +
                                    "\nDid you enter number betwen 1 and 10 ?",Toast.LENGTH_LONG).show();
                            break;
                        }
                        case AUTHENTICATED:{
                            showProgresBar(false);
                            Log.d("AuthActivity","onChanged : Login SUCCESS: "+userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        }
                        case NOT_AUTHENTICATED:{
                            showProgresBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void showProgresBar(boolean isVisible){
        if (isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setLogo(){
        requestManager.load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button :{
                attemptLogin();
                break;
            }
        }
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userId.getText().toString())){
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }

    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
