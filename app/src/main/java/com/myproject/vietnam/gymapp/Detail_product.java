package com.myproject.vietnam.gymapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;

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
                    txtPriceSaleProduct,txtDesProduct,txtratingcount;
    private RadioButton btnrdoFlavor1,btnrdoFlavor2;
    private RatingBar ratingProduct;
    private Bundle bundle;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail_product, container, false);
        findID(view);
        String urlgetComment="http://192.168.1.4/VN-GYM/public/getcomment";
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
        txtDesProduct=(TextView)view.findViewById(R.id.txtdes_productdetail);
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
    //get_comment
//    private void getComment(String url){
//
//    }

}
