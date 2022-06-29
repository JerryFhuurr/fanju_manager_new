package com.jerry.fanju_manager.main.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jerry.fanju_manager.R;
import com.jerry.fanju_manager.main.Apl;
import com.jerry.fanju_manager.main.handler.ActivityManager;
import com.jerry.fanju_manager.main.login.LoginActivity;

import cn.leancloud.LCUser;

public class MainActivity extends AppCompatActivity {
    private LCUser currentUser;
    private long exitTime = 0;
    private NavController navController;
    private AppBarConfiguration configuration;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private View headerView;
    private TextView username;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser = LCUser.getCurrentUser();

        ActivityManager.getInstance().addActivity(this);
        checkUser();
        initView();
        loadUser();
        setupNavigation();
    }

    private void checkUser(){
        if (currentUser == null){
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initView() {
        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.side_navmenu);
        toolbar = findViewById(R.id.toolbar);
        headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.username_menu);
        email = headerView.findViewById(R.id.email_menu);
        bottomNavigationView = findViewById(R.id.bottom_navbar);
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setSupportActionBar(toolbar);

        configuration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setBottomNavigationView();
    }

    private void setBottomNavigationView() {
        navController.addOnDestinationChangedListener(((navController1, navDestination, bundle) -> {
            final int id = navDestination.getId();
            if (id == R.id.nav_home) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } /*
            else if (id == R.id.myFragment) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
            */
        }));
    }

    private void loadUser(){
        username.setText(currentUser.getUsername());
        email.setText(currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, configuration) || super.onSupportNavigateUp();
    }

    public void logout(View view){
        LCUser.logOut();
        currentUser = null;
        checkUser();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), R.string.exit_toast,
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.getInstance().removeActivity(this);
            ActivityManager.getInstance().exit();
            System.exit(0);
        }
    }

    public void exitApp(View v) {
        finish();
        ActivityManager.getInstance().removeActivity(this);
        ActivityManager.getInstance().exit();
    }
}