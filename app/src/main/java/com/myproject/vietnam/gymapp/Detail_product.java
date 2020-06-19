package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;
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
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detail_product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detail_product extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SliderView sliderView;
    private TextView txtNameProduct,txtDetailProduct,txtPriceProduct,
                    txtPriceSaleProduct,txtDesProduct,txtratingcount,txtratingcountb;
    private RadioButton btnrdoFlavor1,btnrdoFlavor2;
    private Button btnaddcart,btnpayproduct;
    private RatingBar ratingProduct,ratingProductb;
    private Bundle bundle;
    private SharedPreferences sharedPreferences;
    //sameproduct
    private RecyclerView recyclerViewsameproduct;
    private ArrayList<home_product> home_products;
    private productsame_detail_Adapter adapter;
    //Rating_product
    private RecyclerView recyclerViewrating;
    private ArrayList<Rating> ratingArrayList;
    private rating_productdetail_Adapter adapterrating;
    String huongvi;

    private Button btnrating;
    public Detail_product() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detail_product.
     */
    // TODO: Rename and change types and number of parameters
    public static Detail_product newInstance(String param1, String param2) {
        Detail_product fragment = new Detail_product();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail_product, container, false);
        findID(view);
        String urlgetComment=getUrl.Url+"getcomment";
        String urlsameproduct=getUrl.Url+"getsameproduct";
        String urlRating=getUrl.Url+"ratingdetailproduct";
        RequestQueue requestQueue= Volley.newRequestQueue(this.getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, urlgetComment, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getActivity(),""+response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                bundle=getArguments();
                Map<String, String> params=new HashMap<>();
                params.put("id_product",String.valueOf(bundle.getInt("id")));
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
        SliderImage();
        setData();
        get_sameproduct(urlsameproduct);
        get_rating(urlRating);
        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huongvi="";
                if (!btnrdoFlavor1.isChecked() && !btnrdoFlavor2.isChecked()){
                    Toast.makeText(Detail_product.this.getContext(),"Vui lòng chọn hương vị",Toast.LENGTH_LONG).show();
                }
                else{
                    if (btnrdoFlavor1.isChecked()){
                        huongvi=btnrdoFlavor1.getText().toString();
                    }
                    if (btnrdoFlavor2.isChecked()){
                        huongvi=btnrdoFlavor2.getText().toString();
                    }
                    String urladdcart=getUrl.Url+"addcartproduct";
                    bundle=getArguments();
                    sharedPreferences=Detail_product.this.getContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                    RequestQueue requestQueue= Volley.newRequestQueue(Detail_product.this.getActivity());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, urladdcart, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("0")){
                                Toast.makeText(Detail_product.this.getContext(),"Sản phẩm đã có trong giỏ hàng",Toast.LENGTH_LONG).show();
                            }
                            if (response.equals("1")){
                                Toast.makeText(Detail_product.this.getContext(),"Thêm giỏ hàng thành công",Toast.LENGTH_LONG).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Detail_product.this.getContext(),""+error,Toast.LENGTH_LONG).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<>();
                            params.put("id_user",sharedPreferences.getString("id",""));
                            params.put("id_product",String.valueOf(bundle.getInt("id")));
                            params.put("image",bundle.getString("image"));
                            params.put("ten",bundle.getString("nameProduct"));
                            params.put("huongvi",huongvi);
                            params.put("price",bundle.getString("priceProduct"));
                            params.put("pricesale",bundle.getString("pricesaleProduct"));
                            params.put("rating",String.valueOf(bundle.getFloat("ratingProduct")));
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }

            }
        });
        //PAY PRODUCT
        btnpayproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huongvi="";
                if (!btnrdoFlavor1.isChecked() && !btnrdoFlavor2.isChecked()){
                    Toast.makeText(Detail_product.this.getContext(),"Vui lòng chọn hương vị",Toast.LENGTH_LONG).show();
                }
                else {
                    if (btnrdoFlavor1.isChecked()) {
                        huongvi = btnrdoFlavor1.getText().toString();
                    }
                    if (btnrdoFlavor2.isChecked()) {
                        huongvi = btnrdoFlavor2.getText().toString();
                    }
                    Intent intent = new Intent(Detail_product.this.getActivity(), confirm_payment.class);
                    intent.putExtra("idProduct", bundle.getInt("id"));
                    intent.putExtra("imgProduct", bundle.getString("image"));
                    intent.putExtra("nameProduct", bundle.getString("nameProduct"));
                    intent.putExtra("flavorProduct", huongvi);
                    intent.putExtra("priceProduct", bundle.getString("priceProduct"));
                    startActivity(intent);
                }
            }
        });
        //RATING_PRODUCT
        btnrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences=Detail_product.this.getContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                bundle=getArguments();
                if (sharedPreferences.contains("firstName")){
                    Intent intent=new Intent(Detail_product.this.getActivity(),CommentProduct.class);
                    intent.putExtra("id_user",sharedPreferences.getString("id",""));
                    intent.putExtra("id_product",bundle.getInt("id"));
                    intent.putExtra("user",sharedPreferences.getString("user",""));
                    startActivity(intent);
                }
                else{
                    NavController navController= Navigation.findNavController(Detail_product.this.getActivity(),R.id.fragment_main);
                    navController.navigate(R.id.fragment_Login);

                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    //find_ID
    public void findID(View view){
        sliderView=(SliderView)view.findViewById(R.id.imageSliderdetail);
        txtNameProduct=(TextView)view.findViewById(R.id.txtname_product_detail);
        txtDetailProduct=(TextView) view.findViewById(R.id.txtdetail_product2);
        txtPriceProduct=(TextView) view.findViewById(R.id.txtprice_product_detail);
        txtPriceSaleProduct=(TextView) view.findViewById(R.id.txtonsale_product);
        btnrdoFlavor1=(RadioButton)view.findViewById(R.id.btnradioflavor1);
        btnrdoFlavor2=(RadioButton)view.findViewById(R.id.btnradioflavor2);
        ratingProduct=(RatingBar)view.findViewById(R.id.ratingDetailProduct);
        txtratingcount=(TextView)view.findViewById(R.id.txtcount_rating);
        ratingProductb=(RatingBar)view.findViewById(R.id.ratingDetailProductb);
        txtratingcountb=(TextView)view.findViewById(R.id.txtcount_ratingb);
        txtDesProduct=(TextView)view.findViewById(R.id.txtdes_productdetail);
        btnaddcart=(Button)view.findViewById(R.id.btnaddcart);
        recyclerViewsameproduct=(RecyclerView)view.findViewById(R.id.recyclersameproduct);
        recyclerViewrating=(RecyclerView)view.findViewById(R.id.recyclerratingproduct);
        btnpayproduct=(Button) view.findViewById(R.id.btnpayproduct);
        btnrating=(Button)view.findViewById(R.id.btnratingproduct);
    }
    //setData
    public void setData(){
        bundle=getArguments();
        txtNameProduct.setText(bundle.getString("nameProduct")+" "+bundle.getString("weightProduct"));
        txtDetailProduct.setText(bundle.getString("detailProduct"));
        txtPriceProduct.setText(bundle.getString("priceProduct"));
        txtPriceSaleProduct.setText(bundle.getString("pricesaleProduct"));
        btnrdoFlavor1.setText(bundle.getString("flavor1"));
        btnrdoFlavor2.setText(bundle.getString("flavor2"));
        ratingProduct.setRating(bundle.getFloat("ratingProduct"));
        float ratingCount=ratingProduct.getRating();
        txtratingcount.setText(String.valueOf(ratingCount));
        ratingProductb.setRating(bundle.getFloat("ratingProduct"));
        txtratingcountb.setText(String.valueOf(ratingCount)+" / 5");
        txtDesProduct.setText(Html.fromHtml(bundle.getString("desProduct")));
    }
    //Slider
    private void SliderImage(){
        bundle=getArguments();
        ArrayList<Slider_Item_Detailproduct> sliderItems=new ArrayList<>();
//        Toast.makeText(this.getContext(),""+bundle.getString("image"),Toast.LENGTH_LONG).show();
//        sliderItems.add(new Slider_Item_Detailproduct(1,bundle.getString("image")));
        sliderItems.add(new Slider_Item_Detailproduct(2,bundle.getString("image3")));
        sliderItems.add(new Slider_Item_Detailproduct(3,bundle.getString("image2")));
        sliderItems.add(new Slider_Item_Detailproduct(4,bundle.getString("image4")));
        sliderItems.add(new Slider_Item_Detailproduct(5,bundle.getString("image1")));
        Slider_Adapter_detailproduct adapter = new Slider_Adapter_detailproduct(this.getContext(),sliderItems);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }
    //get_sameproduct
    private void get_sameproduct(String url){
        bundle=getArguments();
        home_products=new ArrayList<>();
        adapter=new productsame_detail_Adapter(Detail_product.this.getContext(),home_products);
        recyclerViewsameproduct.setAdapter(adapter);
        recyclerViewsameproduct.setLayoutManager(new LinearLayoutManager(Detail_product.this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        RequestQueue requestQueue= Volley.newRequestQueue(Detail_product.this.getActivity());
        StringRequest stringReques=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(Detail_product.this.getContext(),""+response,Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<=response.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        home_products.add(new home_product(
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
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_product.this.getContext(),""+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("nameproduct",bundle.getString("nameProduct"));
                return params;
            }
        };
        requestQueue.add(stringReques);
    }

    //get_comment
    private void get_rating(String url){
        ratingArrayList=new ArrayList<>();
        adapterrating=new rating_productdetail_Adapter(Detail_product.this.getContext(),ratingArrayList);
        recyclerViewrating.setAdapter(adapterrating);
        recyclerViewrating.setLayoutManager(new LinearLayoutManager(Detail_product.this.getContext(),LinearLayoutManager.VERTICAL,false));
        RequestQueue requestQueue= Volley.newRequestQueue(Detail_product.this.getActivity());
        StringRequest stringReques=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(Detail_product.this.getContext(),""+response,Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<=response.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        ratingArrayList.add(new Rating(
                                object.getInt("id"),
                                object.getString("user"),
                                object.getString("comment"),
                                (float)object.getDouble("rating")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterrating.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_product.this.getContext(),""+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id_product",bundle.getInt("id")+"");
                return params;
            }
        };
        requestQueue.add(stringReques);
    }

}
