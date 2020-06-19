package com.myproject.vietnam.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class status_product extends AppCompatActivity {
    private ViewPager viewPagerstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_product);
        viewPagerstatus=findViewById(R.id.pagerstatus);
        viewPagerstatus.setAdapter(new status_tab_Adapter(getSupportFragmentManager()));
        TabLayout tabLayout=findViewById(R.id.tablayoutstatus);
        tabLayout.setupWithViewPager(viewPagerstatus);
    }
}