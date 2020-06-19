package com.myproject.vietnam.gymapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_logined#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_logined extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView txtName,txtLogout,txtmyorder,txtmyprofile;
    private SharedPreferences sharedPreferences,sharedPreferencesfacebook;
    private ImageView imgUser;
    private int REQUEST_CODE=1;
    GoogleSignInClient googleApiClient;
    GoogleSignInOptions gso;
    private Bitmap bitmap;

    public fragment_logined() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_logined.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_logined newInstance(String param1, String param2) {
        fragment_logined fragment = new fragment_logined();
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
        View view=inflater.inflate(R.layout.fragment_logined, container, false);
        // Inflate the layout for this fragment
        txtName=(TextView) view.findViewById(R.id.txtname_logined);
        txtLogout=(TextView)view.findViewById(R.id.txtlogout_logined);
        imgUser=(ImageView)view.findViewById(R.id.img_logined);
        txtmyorder=(TextView)view.findViewById(R.id.txtmyorder);
        txtmyprofile=(TextView)view.findViewById(R.id.txtmyprofile);
        sharedPreferences=fragment_logined.this.getContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        sharedPreferencesfacebook=fragment_logined.this.getContext().getSharedPreferences("dataLoginFace", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("firstName")){
            String firstname=sharedPreferences.getString("firstName","");
            String lastname=sharedPreferences.getString("lastName","");
            txtName.setText("Xin chào, "+firstname+" "+lastname);
            Glide
                    .with(fragment_logined.this.getContext())
                    .load(getUrl.Url+"upload/avart/"+sharedPreferences.getString("avart",""))
                    .override(150,150)
                    .into(imgUser);
        }
        else{
            if (sharedPreferencesfacebook.contains("name")){
                String name=sharedPreferencesfacebook.getString("name","");
                txtName.setText("Xin chào, "+name);
                String image_url = "http://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large";
                Glide.
                        with(fragment_logined.this.getContext())
                        .load(image_url)
                        .into(imgUser);
            }
        }

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.contains("firstName")){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                }
                else{
                    if (sharedPreferencesfacebook.contains("name")){
                        SharedPreferences.Editor editora=sharedPreferencesfacebook.edit();
                        editora.clear();
                        editora.commit();
                        LoginManager.getInstance().logOut();
                    }
                }

                NavController navController= Navigation.findNavController(fragment_logined.this.getActivity(),R.id.fragment_main);
                navController.navigate(R.id.fragment_homepage);
            }
        });
        //My Order
        txtmyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_logined.this.getContext(),status_product.class));
            }
        });
        //MY PROFILE-------------

        txtmyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_logined.this.getActivity(),MyProfile.class));
            }
        });

        return view;
    }


}

////LOGIN GOOGLE
//        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//                googleApiClient=GoogleSignIn.getClient(this.getActivity(),gso);
//                GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(fragment_logined.this.getContext());
//                if (account != null) {
//                txtName.setText("Xin chào, "+account.getDisplayName());
//                Uri uri=account.getPhotoUrl();
//                Glide
//                .with(this.getContext())
//                .load(String.valueOf(uri))
//                .override(150,150)
//                .into(imgUser);
//                }
