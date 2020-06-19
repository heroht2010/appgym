package com.myproject.vietnam.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class confirm_payment extends AppCompatActivity {
    ImageView imgdetailProduct;
    TextView txtnameProduct,txtflavorProduct,txtpriceProduct,
            txtsumpriceProduct,txtpricepayProduct,txtnamephone,txtaddr,
            txtpricedeliver,txtdeliver,txtpaymenta,txtpaymentb;
    Button btnpurchase;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);
        imgdetailProduct=(ImageView) findViewById(R.id.imgpaymentproduct);
        txtnameProduct=(TextView) findViewById(R.id.txtnamepaymentpro);
        txtflavorProduct=(TextView)findViewById(R.id.txtflavorpaymentpro);
        txtpriceProduct=(TextView)findViewById(R.id.txtpricepro);
        txtsumpriceProduct=(TextView)findViewById(R.id.txtsumprice);
        txtpricepayProduct=(TextView)findViewById(R.id.txtpricepaymentpro);
        txtnamephone=(TextView)findViewById(R.id.txtnameandphone);
        txtaddr=(TextView)findViewById(R.id.txtaddr);
        txtdeliver=(TextView)findViewById(R.id.txtdeliver);
        txtpricedeliver=(TextView)findViewById(R.id.txtpricedeliver);
        txtpaymenta=(TextView)findViewById(R.id.sumpaymenta);
        txtpaymentb=(TextView)findViewById(R.id.sumpaymentb);
        btnpurchase=(Button) findViewById(R.id.btnpurchase);
        String deliverprice,priceproduct;
        int pricepayment;
        sharedPreferences=getSharedPreferences("dataLogin",MODE_PRIVATE);
        txtnamephone.setText(sharedPreferences.getString("firstName","")+" "+sharedPreferences.getString("lastName","")+" | "+sharedPreferences.getString("phone",""));
        txtaddr.setText(sharedPreferences.getString("addr",""));
        final Intent intent=getIntent();
        Glide
                .with(this)
                .load(getUrl.Url+"upload/product/"+intent.getStringExtra("imgProduct"))
                .override(100,100)
                .into(imgdetailProduct);
        txtnameProduct.setText(intent.getStringExtra("nameProduct"));
        txtflavorProduct.setText(intent.getStringExtra("flavorProduct"));
        txtpriceProduct.setText(intent.getStringExtra("priceProduct"));
        priceproduct=intent.getStringExtra("priceProduct").replace(".","");
        txtsumpriceProduct.setText(priceproduct);
        txtpricepayProduct.setText(priceproduct);
        deliverprice=txtdeliver.getText().toString().replace(".","");
        txtpricedeliver.setText(deliverprice);
        priceproduct=priceproduct.substring(0,priceproduct.length()-4);
        deliverprice=deliverprice.substring(0,deliverprice.length()-4);
        pricepayment=Integer.parseInt(priceproduct)+Integer.parseInt(deliverprice);
        txtpaymenta.setText(String.valueOf(pricepayment)+" VNĐ");
        txtpaymentb.setText(String.valueOf(pricepayment)+" VNĐ");

        btnpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(confirm_payment.this);
                String urladdorder=getUrl.Url+"addorder";
                StringRequest stringRequest=new StringRequest(Request.Method.POST, urladdorder, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(confirm_payment.this,""+response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("id_product",String.valueOf(intent.getIntExtra("idProduct",0)));
                        params.put("id_user",String.valueOf(sharedPreferences.getString("id","")));
                        params.put("flavor",txtflavorProduct.getText().toString());
                        params.put("count",String.valueOf(1));
                        params.put("price",txtpriceProduct.getText().toString());
                        params.put("pricesum",txtpaymentb.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}
