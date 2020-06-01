package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputLayout edtUsername;
    private TextInputLayout edtPassword;
    private Button btnLogin;
    private TextView txtRegister;
    private SharedPreferences sharedPreferences;
    public fragment_Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Login.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_Login newInstance(String param1, String param2) {
        fragment_Login fragment = new fragment_Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__login, container, false);
        txtRegister=(TextView)view.findViewById(R.id.txtregister);
        edtUsername=(TextInputLayout) view.findViewById(R.id.edtusername);
        edtPassword=(TextInputLayout) view.findViewById(R.id.edtpassword);
        btnLogin=(Button)view.findViewById(R.id.btnlogin);
        sharedPreferences= fragment_Login.this.getContext().getSharedPreferences("dataLogin", 0);
        final NavController navController= Navigation.findNavController(getActivity(),R.id.fragment_main);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(fragment_Login.this.getContext(),""+edtUsername.getEditText().getText().toString(),Toast.LENGTH_LONG).show();
                final String username=edtUsername.getEditText().getText().toString().trim();
                final String pass=edtPassword.getEditText().getText().toString().trim();
                RequestQueue requestQueue= Volley.newRequestQueue(fragment_Login.this.getContext());
                String url="http://192.168.1.3/VN-GYM/public/checklogin";

                if (username.isEmpty()){
                    edtUsername.setError("Vui lòng điền đầy đủ các trường");
                }
                else{
                    edtUsername.setError("");
                }
                if (pass.isEmpty()){
                    edtPassword.setError("Vui lòng điền đầy đủ các trường");
                }
                else{
                    edtPassword.setError("");
                }
                if (username.length()>0 && pass.length()>0){
                  if (username.length()>15){
                      edtUsername.setError("Tên đăng nhập không quá 15 ký tự");
                  }
                  else{
                      StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {
                              if (response.equals("0")){
                                  Toast.makeText(fragment_Login.this.getContext(),"Tên đăng nhập hoặc mật khẩu không đúng",Toast.LENGTH_LONG).show();

                              }
                              else{
                                  try {
                                      JSONArray array=new JSONArray(response);
                                      SharedPreferences.Editor editor=sharedPreferences.edit();
                                      editor.clear();
                                      for (int i=0;i<=response.length();i++){
                                          JSONObject object=array.getJSONObject(i);
                                          editor.putString("firstName",object.getString("ten"));
                                          editor.putString("lastName",object.getString("ho"));

                                      }
                                      editor.commit();

                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                                  navController.navigate(R.id.fragment_homepage);
                              }

                          }
                      }, new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError error) {
                              Toast.makeText(fragment_Login.this.getContext(),""+error,Toast.LENGTH_LONG).show();

                          }
                      })
                      {
                          @Override
                          protected Map<String, String> getParams() throws AuthFailureError {
                              Map<String,String> params=new HashMap<>();
                              params.put("username",username);
                              params.put("pass",pass);
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

            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.fragment_register);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
