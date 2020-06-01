package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homepage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homepage extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //search
    private EditText edtSearch;
    //SliderImage
    SliderView sliderView;
    //home_product_sale
    private RecyclerView recyclerViewProduct_sale;
    private ArrayList<home_product_sale> product_saleArrayList;
    private home_product_sale_Adapter adapterProduct_sale;
    //home_product
    private RecyclerView recyclerViewProduct;
    private ArrayList<home_product> product_ArrayList;
    private home_product_Apdater adapterProduct;
    //home_PT
    private RecyclerView recyclerViewPt;
    private ArrayList<home_PT> home_ptArrayList;
    private home_pt_Adapter adapterPt;
    //home_producttype
    private RecyclerView recyclerViewtype;
    private ArrayList<home_product_type> home_product_types;
    private home_product_type_Adapter adaptertype;
    public homepage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homepage.
     */
    // TODO: Rename and change types and number of parameters
    public static homepage newInstance(String param1, String param2) {
        homepage fragment = new homepage();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        SharedPreferences sharedPreferences=homepage.this.getContext().getSharedPreferences("dataLogin", 0);
        Toast.makeText(homepage.this.getContext(),""+sharedPreferences.getString("firstName","123"),Toast.LENGTH_LONG).show();
        //search
        edtSearch=(EditText)view.findViewById(R.id.edtsearch);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    RequestQueue requestQueue= Volley.newRequestQueue(homepage.this.getContext());
                    String urlSearch="http://192.168.1.3/VN-GYM/public/searchproduct";
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, urlSearch, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(homepage.this.getContext(),""+response,Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(homepage.this.getContext(),""+error,Toast.LENGTH_LONG).show();

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

        //slider
         sliderView= view.findViewById(R.id.imageSlider);
         SliderImage();
        //recycleview
        recyclerViewProduct_sale=view.findViewById(R.id.recycle_product_sale);
        recyclerViewProduct=view.findViewById(R.id.recycle_product);
        recyclerViewPt=view.findViewById(R.id.recycle_pt);
        recyclerViewtype=view.findViewById(R.id.recycle_producttype);
        String urlGetProductsale="http://192.168.1.3/VN-GYM/public/getproductsale";
        String urlGetProduct="http://192.168.1.3/VN-GYM/public/getproduct";
        String urlGetPt="http://192.168.1.3/VN-GYM/public/getpt";
        String urlGetType="http://192.168.1.3/VN-GYM/public/getproducttype";
        getProduct_sale(urlGetProductsale);
        getProduct(urlGetProduct);
        getProductType(urlGetType);
        getPt(urlGetPt);
        // Inflate the layout for this fragment
        return view;
    }

    private void getProduct_sale(String url){
        product_saleArrayList=new ArrayList<>();
        adapterProduct_sale=new home_product_sale_Adapter(this.getContext(), R.layout.home_product_sale, product_saleArrayList, new home_product_sale_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(home_product_sale item) {
                NavController navController= Navigation.findNavController(getActivity(),R.id.fragment_main);
                navController.navigate(R.id.fragment_productlove);
            }
        });
        recyclerViewProduct_sale.setAdapter(adapterProduct_sale);
        recyclerViewProduct_sale.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false));
        RequestQueue requestQueue= Volley.newRequestQueue(homepage.this.getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        product_saleArrayList.add(new home_product_sale(
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
                adapterProduct_sale.notifyDataSetChanged();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(homepage.this.getContext(),"loi",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //home_product
    private void getProduct(String url){
        product_ArrayList=new ArrayList<>();
        adapterProduct=new home_product_Apdater(homepage.this.getContext(), product_ArrayList, new home_product_Apdater.OnItemClickListener() {
            @Override
            public void onItemClick(home_product item) {
                Bundle bundle=new Bundle();
                bundle.putInt("id",item.getId());
                bundle.putString("nameProduct",item.getNameProduct());
                bundle.putString("detailProduct",item.getDetailProduct());
                bundle.putString("priceProduct",item.getPriceProduct());
                bundle.putString("pricesaleProduct",item.getPriceSale());
                bundle.putString("flavor1",item.getFlavor1());
                bundle.putString("flavor2",item.getFlavor2());
                bundle.putString("image",item.getImageProduct());
                bundle.putString("image1",item.getImage1());
                bundle.putString("image2",item.getImage2());
                bundle.putString("image3",item.getImage3());
                bundle.putString("image4",item.getImage4());
                bundle.putString("weightProduct",item.getWeightProduct());
                bundle.putString("distriProduct",item.getDistributor());
                bundle.putFloat("ratingProduct",item.getRatingProduct());
                bundle.putString("desProduct",item.getDescri());
                NavController navController= Navigation.findNavController(getActivity(),R.id.fragment_main);
                navController.navigate(R.id.action_fragment_homepage_to_detail_product,bundle);
            }
        });
        recyclerViewProduct.setAdapter(adapterProduct);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false));
        RequestQueue requestQueue= Volley.newRequestQueue(homepage.this.getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        product_ArrayList.add(new home_product(
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
                adapterProduct.notifyDataSetChanged();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(homepage.this.getContext(),"loi",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //home_PT
    private void getPt(String url){
        home_ptArrayList=new ArrayList<>();
        adapterPt=new home_pt_Adapter(this.getContext(),home_ptArrayList);
        recyclerViewPt.setAdapter(adapterPt);
        recyclerViewPt.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false));
        RequestQueue requestQueue= Volley.newRequestQueue(homepage.this.getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        home_ptArrayList.add(new home_PT(
                                object.getInt("id"),
                                object.getString("image"),
                                object.getString("name"),
                                object.getString("follow")
                        ));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapterPt.notifyDataSetChanged();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(homepage.this.getContext(),"loi",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //home_Product_Type
    private void getProductType(String url){
        home_product_types=new ArrayList<>();
        adaptertype=new home_product_type_Adapter(homepage.this.getContext(),home_product_types);
        recyclerViewtype.setAdapter(adaptertype);
        recyclerViewtype.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        RequestQueue requestQueue= Volley.newRequestQueue(homepage.this.getContext());
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
                adaptertype.notifyDataSetChanged();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(homepage.this.getContext(),"loi",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
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

}
