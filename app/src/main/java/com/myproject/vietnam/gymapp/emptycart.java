package com.myproject.vietnam.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class emptycart extends AppCompatActivity {

    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emptycart);
        btnlogin=(Button) findViewById(R.id.btnempty);

    }
}