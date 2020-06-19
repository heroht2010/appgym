package com.myproject.vietnam.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       bottomNavigationView=findViewById(R.id.navibottommain);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.fragment_homepage, R.id.fragment_productlist, R.id.fragment_productlove,R.id.fragment_userprofile)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.fragment_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        final SharedPreferences sharedPreferences=getSharedPreferences("dataLogin",0);
        final SharedPreferences sharedPreferencesface=getSharedPreferences("dataLoginFace",MODE_PRIVATE);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavController navController=Navigation.findNavController(MainActivity.this,R.id.fragment_main);
                switch (item.getItemId()){
                    case R.id.fragment_homepage: navController.navigate(R.id.fragment_homepage);
                        break;
                    case R.id.fragment_productlist: navController.navigate(R.id.fragment_productlist);
                        break;
                    case R.id.fragment_productlove: navController.navigate(R.id.fragment_productlove);
                        break;
                    case R.id.fragment_userprofile:

                        if (sharedPreferences.contains("firstName")||sharedPreferencesface.contains("name")){
                            navController.navigate(R.id.fragment_logined);

                        }
                        else{
                            navController.navigate(R.id.fragment_userprofile);
                        }
                        break;
                }
                return false;
            }
        });


    }

}
