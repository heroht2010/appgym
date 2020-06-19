package com.myproject.vietnam.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cart_product extends AppCompatActivity {
    private RecyclerView recyclercart;
    private cart_Adapter cart_adapter;
    private ArrayList<cart_attribute> cart_attributeArrayList;
    private SharedPreferences sharedPreferences;
    private TextView txtpriceallcart;
    String allprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_product);
        recyclercart=(RecyclerView) findViewById(R.id.recyclercart);
        cart_attributeArrayList=new ArrayList<>();
        cart_adapter=new cart_Adapter(cart_product.this,cart_attributeArrayList);
        recyclercart.setAdapter(cart_adapter);
        recyclercart.setLayoutManager(new LinearLayoutManager(cart_product.this,LinearLayoutManager.VERTICAL,false));
        sharedPreferences=cart_product.this.getSharedPreferences("dataLogin",MODE_PRIVATE);
        txtpriceallcart=(TextView) findViewById(R.id.txtpriceallcart);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url=getUrl.Url+"cartproduct";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<=response.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        cart_attributeArrayList.add(new cart_attribute(
                                object.getInt("id"),
                                object.getString("image"),
                                object.getString("ten"),
                                object.getString("huongvi"),
                                object.getString("price"),
                                object.getString("pricesale"),
                                (float)object.getDouble("rating")
                        ));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cart_adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cart_product.this,""+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id_user",sharedPreferences.getString("id",""));
                return params;
            }
        };
        requestQueue.add(stringRequest);
        cart_adapter.stringMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (txtpriceallcart.equals("0 VNĐ")){
                    txtpriceallcart.setText(s);
                }
                else{
                    allprice=txtpriceallcart.getText().toString().replace(".","");
                    allprice=allprice.substring(0,allprice.length()-4);
                    s=s.replace(".","");
                    s=s.substring(0,s.length()-4);
                    int price=Integer.parseInt(allprice)+Integer.parseInt(s);
                    txtpriceallcart.setText(String.valueOf(price)+" VNĐ");

                }

            }
        });
    }
}
