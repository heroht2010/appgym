package com.myproject.vietnam.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CommentProduct extends AppCompatActivity {
    private TextView txtdescri;
    private RatingBar ratingBarstar;
    private Button btnrating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_product);
        txtdescri=(TextView)findViewById(R.id.txtdesproductrating);
        ratingBarstar=(RatingBar)findViewById(R.id.ratingstarproduct);
        btnrating=(Button)findViewById(R.id.btncomment);
        btnrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdescri.setText(txtdescri.getText());
                final Intent intent=getIntent();
                RequestQueue requestQueue= Volley.newRequestQueue(CommentProduct.this.getApplicationContext());
                String url=getUrl.Url+"commentproduct";
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")){
                            Toast.makeText(CommentProduct.this,"Cảm ơn bạn đã đánh giá sản phẩm",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(CommentProduct.this,"Đã xảy ra lỗi!!!!",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CommentProduct.this,""+error,Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("id_product",intent.getIntExtra("id_product",1)+"");
                        params.put("id_user",intent.getStringExtra("id_user"));
                        params.put("user",intent.getStringExtra("user"));
                        params.put("ratingstar",ratingBarstar.getRating()+"");
                        params.put("descrirating",txtdescri.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}