package com.myproject.vietnam.gymapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //
    private EditText edtSearch;
    private RecyclerView recyclerSearch;
    private searchAdapter searchAdapter;
    private ArrayList<home_product> home_productArrayList;

    public fragment_search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_search.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_search newInstance(String param1, String param2) {
        fragment_search fragment = new fragment_search();
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
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        edtSearch=(EditText) view.findViewById(R.id.edtsearchproduct);
        recyclerSearch=(RecyclerView) view.findViewById(R.id.recyclersearch);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    RequestQueue requestQueue= Volley.newRequestQueue(fragment_search.this.getContext());
                    String urlSearch=getUrl.Url+"searchproduct";
                    home_productArrayList=new ArrayList<>();
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, urlSearch, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<=response.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    home_productArrayList.add(new home_product(
                                            object.getInt("id"),
                                            object.getString("image"),
                                            object.getString("name"),
                                            object.getString("price"),
                                            object.getString("onsale"),
                                            object.getString("weight"),
                                            object.getString("huongvi1"),
                                            object.getString("huongvi2"),
                                            object.getString("image1"),
                                            object.getString("image2"),
                                            object.getString("image3"),
                                            object.getString("image4"),
                                            object.getInt("id_product_type"),
                                            object.getString("mota"),
                                            object.getString("thuonghieu"),
                                            object.getString("detail"),
                                            (float)object.getDouble("rating")
                                    ));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            searchAdapter=new searchAdapter(fragment_search.this.getContext(),home_productArrayList);
                            recyclerSearch.setAdapter(searchAdapter);
                            recyclerSearch.setLayoutManager(new GridLayoutManager(fragment_search.this.getContext(),2));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(fragment_search.this.getContext(),""+error,Toast.LENGTH_LONG).show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<>();
                            params.put("key",edtSearch.getText().toString().trim());
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
                    return true;
                }
                return false;
            }
        });
        return view;
    }
}
