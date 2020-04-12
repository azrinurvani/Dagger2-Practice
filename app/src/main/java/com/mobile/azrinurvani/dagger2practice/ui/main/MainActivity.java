package com.mobile.azrinurvani.dagger2practice.ui.main;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mobile.azrinurvani.dagger2practice.BaseActivity;
import com.mobile.azrinurvani.dagger2practice.R;
import com.mobile.azrinurvani.dagger2practice.ui.main.posts.PostsFragment;
import com.mobile.azrinurvani.dagger2practice.ui.main.profile.ProfileFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        initNavigation();

    }

    private void initNavigation(){
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout);
        NavigationUI.setupWithNavController(navigationView,navController);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout : {
                sessionManager.logout();
                return true;
            }
            //fix issue when Navigation Icon (humberger icon) clicked
            case android.R.id.home : {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }else{
                    return false;
                }

            }

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_profile : {

                //clear backstack issue in Navigation
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.main,true)
                        .build();

                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(
                        R.id.profileScreen,
                        null,
                        navOptions
                );
                break;
            }
            case R.id.nav_posts : {

                //cek apakah screen tujuan adalah post
                if (isValidDestination(R.id.postScreen)){
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.postScreen);
                }
                break;
            }
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isValidDestination(int destination){
        return destination != Navigation.findNavController(this,R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    //fix issue when Navigation Icon (humberger icon) clicked
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.nav_host_fragment), drawerLayout);
    }
}
