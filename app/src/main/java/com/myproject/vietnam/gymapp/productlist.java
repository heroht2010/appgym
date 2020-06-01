package com.myproject.vietnam.gymapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link productlist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class productlist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SliderView sliderView;
    //product_type
    private RecyclerView recyclerViewProductType;
    private ArrayList<home_product_type> home_product_types;
    private home_product_type_Adapter typeAdapter;
    //product_list
    private RecyclerView recyclerViewProduct;
    private ArrayList<home_product> home_productArrayList;
    private productlist_recyclegrid_Adapter recyclegridAdapter;
    public productlist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment productlist.
     */
    // TODO: Rename and change types and number of parameters
    public static productlist newInstance(String param1, String param2) {
        productlist fragment = new productlist();
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
        View view=inflater.inflate(R.layout.fragment_productlist, container, false);
        sliderView=(SliderView) view.findViewById(R.id.imageSlider);
        recyclerViewProductType=(RecyclerView)view.findViewById(R.id.recycle_productlist_type);
        recyclerViewProduct=(RecyclerView)view.findViewById(R.id.recycle_grid_product);

        SliderImage();
        //url
        String urlGetType="http://192.168.1.3/VN-GYM/public/getproducttype";
        String urlGetProduct="http://192.168.1.3/VN-GYM/public/getallproduct";
        getProductType(urlGetType);
        getProduct(urlGetProduct);
        return view;
    }
    //Slider
    private void SliderImage(){
        ArrayList<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(1,R.drawable.imagewwhey));
        sliderItems.add(new SliderItem(2,R.drawable.wheyc));
        sliderItems.add(new SliderItem(3,R.drawable.wheyd));
        sliderItems.add(new SliderItem(4,R.drawable.unnamed));
        sliderItems.add(new SliderItem(5,R.drawable.imagewheyb));
        sliderItems.add(new SliderItem(6,R.drawable.wheye));
        Slider_Adapter adapter = new Slider_Adapter(this.getContext(),sliderItems);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }
    //productlist_Product_Type
    private void getProductType(String url){
        home_product_types=new ArrayList<>();
        typeAdapter=new home_product_type_Adapter(this.getContext(),home_product_types);
        recyclerViewProductType.setAdapter(typeAdapter);
        recyclerViewProductType.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        RequestQueue requestQueue= Volley.newRequestQueue(this.getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        home_product_types.add(new home_product_type(
                                object.getInt("id"),
                                object.getString("image"),
                                object.getString("image"),
                                object.getString("product_type")
                        ));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                typeAdapter.notifyDataSetChanged();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productlist.this.getContext(),"loi",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //home_product
    private void getProduct(String url){
        home_productArrayList=new ArrayList<>();
        recyclegridAdapter=new productlist_recyclegrid_Adapter(this.getContext(),home_productArrayList);
        recyclerViewProduct.setAdapter(recyclegridAdapter);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        recyclerViewProduct.setNestedScrollingEnabled(false);
        RequestQueue requestQueue= Volley.newRequestQueue(this.getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclegridAdapter.notifyDataSetChanged();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productlist.this.getContext(),"loi",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
