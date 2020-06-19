package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
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
    private LoginButton btnLoginFace;
    private TextView txtRegister;
    private SharedPreferences sharedPreferences, sharedPreferencesfacebook;
    //FACEBOOK
    CallbackManager callbackManager;

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

        View view = inflater.inflate(R.layout.fragment__login, container, false);
        FacebookSdk.sdkInitialize(fragment_Login.this.getContext());
        txtRegister = (TextView) view.findViewById(R.id.txtregister);
        edtUsername = (TextInputLayout) view.findViewById(R.id.edtusername);
        edtPassword = (TextInputLayout) view.findViewById(R.id.edtpassword);
        btnLogin = (Button) view.findViewById(R.id.btnlogin);
        btnLoginFace = (LoginButton) view.findViewById(R.id.btnloginfacebook);
        btnLoginFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpFbLoginBtn();
            }
        });

        sharedPreferences = fragment_Login.this.getContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        //

        final NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_main);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(fragment_Login.this.getContext(),""+edtUsername.getEditText().getText().toString(),Toast.LENGTH_LONG).show();
                final String username = edtUsername.getEditText().getText().toString().trim();
                final String pass = edtPassword.getEditText().getText().toString().trim();
                RequestQueue requestQueue = Volley.newRequestQueue(fragment_Login.this.getContext());
                String url = getUrl.Url + "checklogin";

                if (username.isEmpty()) {
                    edtUsername.setError("Vui lòng điền đầy đủ các trường");
                } else {
                    edtUsername.setError("");
                }
                if (pass.isEmpty()) {
                    edtPassword.setError("Vui lòng điền đầy đủ các trường");
                } else {
                    edtPassword.setError("");
                }
                if (username.length() > 0 && pass.length() > 0) {
                    if (username.length() > 15) {
                        edtUsername.setError("Tên đăng nhập không quá 15 ký tự");
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                              Toast.makeText(fragment_Login.this.getContext(),""+response,Toast.LENGTH_LONG).show();

                                if (response.equals("0")) {
                                    Toast.makeText(fragment_Login.this.getContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();

                                } else {
                                    try {
                                        JSONArray array = new JSONArray(response);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        for (int i = 0; i <= array.length(); i++) {
                                            JSONObject object = array.getJSONObject(i);
                                            editor.putString("id", object.getString("id"));
                                            editor.putString("user", username);
                                            editor.putString("avart", object.getString("avart"));
                                            editor.putString("firstName", object.getString("ten"));
                                            editor.putString("lastName", object.getString("ho"));
                                            editor.putString("phone", object.getString("sdt"));
                                            editor.putString("addr", object.getString("diachi"));
                                            editor.commit();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    navController.navigate(R.id.fragment_homepage);
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(fragment_Login.this.getContext(), "" + error, Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", username);
                                params.put("pass", pass);
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
//

        return view;
    }

    private void setUpFbLoginBtn() {

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(fragment_Login.this, Arrays.asList("public_profile, email, user_birthday, user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(fragment_Login.this.getContext(), "Success", Toast.LENGTH_SHORT).show();
                sharedPreferencesfacebook = fragment_Login.this.getContext().getSharedPreferences("dataLoginFace", Context.MODE_PRIVATE);
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            SharedPreferences.Editor editor = sharedPreferencesfacebook.edit();
                            editor.putInt("id", object.getInt("id"));
                            editor.putString("name", object.getString("name"));
                            editor.putString("email", object.getString("email"));
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NavController navController = Navigation.findNavController(fragment_Login.this.getActivity(), R.id.fragment_main);
                        navController.navigate(R.id.fragment_logined);
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,name,email,birthday");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(fragment_Login.this.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(fragment_Login.this.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
