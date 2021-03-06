package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_status_wait#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_status_wait extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewwaitstatus;
    private ArrayList<Status_product_tab> status_product_tabArrayList;
    private status_wait_Adapter wait_adapter;
    SharedPreferences sharedPreferences;
    public fragment_status_wait() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_status_wait.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_status_wait newInstance(String param1, String param2) {
        fragment_status_wait fragment = new fragment_status_wait();
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
        View view=inflater.inflate(R.layout.fragment_status_wait, container, false);
        // Inflate the layout for this fragment
        recyclerViewwaitstatus=(RecyclerView)view.findViewById(R.id.recyclerstatuswait);
        status_product_tabArrayList=new ArrayList<>();
        wait_adapter=new status_wait_Adapter(this.getContext(),status_product_tabArrayList);
        recyclerViewwaitstatus.setAdapter(wait_adapter);
        recyclerViewwaitstatus.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        sharedPreferences=this.getContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(this.getContext());
        String url=getUrl.Url+"getorder";
       StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONArray jsonArray = new JSONArray(response);
                   for (int i=0;i<=response.length();i++){
                       JSONObject object=jsonArray.getJSONObject(i);
                       status_product_tabArrayList.add(new Status_product_tab(
                               object.getInt("id"),
                               object.getString("image"),
                               object.getString("name"),
                               object.getString("huongvisp"),
                               object.getString("dongia"),
                               object.getString("tongtien"),
                               object.getInt("soluong")
                       ));
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
                wait_adapter.notifyDataSetChanged();
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(fragment_status_wait.this.getContext(),""+error,Toast.LENGTH_LONG).show();
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> params=new HashMap<>();
               params.put("id_user",sharedPreferences.getString("id",""));
               return params;
           }
       };

        return view;
    }
}

