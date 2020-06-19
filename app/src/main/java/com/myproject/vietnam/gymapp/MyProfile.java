package com.myproject.vietnam.gymapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {
    ImageView imgprofile;
    EditText edtNameProfile,edtPhoneProfile,edtAddrProfile,edtLastNameProfile;
    TextView txtUserProfile,txtSaveProfile;
    SharedPreferences sharedPreferences;
    private int REQUEST_CODE=1;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        imgprofile=(ImageView)findViewById(R.id.imgprofile);
        edtNameProfile=(EditText)findViewById(R.id.txtnameprofile);
        edtPhoneProfile=(EditText)findViewById(R.id.txtphoneprofile);
        edtAddrProfile=(EditText)findViewById(R.id.txtaddrprofile);
        txtUserProfile=(TextView)findViewById(R.id.txtuserprofile);
        txtSaveProfile=(TextView)findViewById(R.id.txtsaveprofile);
        edtLastNameProfile=(EditText)findViewById(R.id.txtlastnameprofile);

        sharedPreferences=getSharedPreferences("dataLogin",MODE_PRIVATE);
        Glide
                .with(this)
                .load(getUrl.Url+"upload/avart/"+sharedPreferences.getString("avart",""))
                .override(150,150)
                .into(imgprofile);
        edtNameProfile.setText(sharedPreferences.getString("firstName",""));
        edtLastNameProfile.setText(sharedPreferences.getString("lastName",""));
        edtPhoneProfile.setText(sharedPreferences.getString("phone",""));
        edtAddrProfile.setText(sharedPreferences.getString("addr",""));
        txtUserProfile.setText(sharedPreferences.getString("user",""));

        //EDIT IMAGE PROFILE
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        //SAVE INFO PROFILE
        txtSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_Profile();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                imgprofile.setImageBitmap(bitmap);
                imgprofile.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        if(bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] bytes=byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(bytes,Base64.DEFAULT);
        }
        return "";
    }

    private void Update_Profile(){
        sharedPreferences=getSharedPreferences("dataLogin",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(MyProfile.this);
        String url=getUrl.Url+"updateprofile";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(MyProfile.this,""+response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyProfile.this,""+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id_user",sharedPreferences.getString("id",""));
                params.put("firstName",edtNameProfile.getText().toString());
                params.put("lastName",edtLastNameProfile.getText().toString());
                params.put("phone",edtPhoneProfile.getText().toString());
                params.put("addr",edtAddrProfile.getText().toString());
                params.put("avart",imageToString(bitmap));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(stringRequest);
    }
}