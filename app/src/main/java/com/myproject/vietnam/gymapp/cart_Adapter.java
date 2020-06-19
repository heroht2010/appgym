package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class cart_Adapter extends RecyclerView.Adapter<cart_Adapter.ViewHolder>{
    private Context context;
    private List<cart_attribute> cart_productList;
    public static String priceItem;
    public MutableLiveData<String> stringMutableLiveData=new MutableLiveData<>();

    public cart_Adapter(Context context, List<cart_attribute> cart_attributes) {
        this.context = context;
        this.cart_productList = cart_attributes;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }

    public void setStringMutableLiveData(MutableLiveData<String> stringMutableLiveData) {
        this.stringMutableLiveData = stringMutableLiveData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_product_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Glide
                .with(context)
                .load(getUrl.Url+"upload/product/"+cart_productList.get(position).getImage())
                .override(200,200)
                .into(holder.imgProductGrid);
        holder.txtNameProductGrid.setText(cart_productList.get(position).getName());
        holder.txtPriceProductGrid.setText(cart_productList.get(position).getPrice());
        holder.txtSaleProductGrid.setText(cart_productList.get(position).getPricesale());
        holder.txtDetailProductGrid.setText("Hương vị: "+cart_productList.get(position).getFlavor());
        holder.ratingBarProductGrid.setRating(cart_productList.get(position).getRating());
        holder.cbItemcart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    stringMutableLiveData.postValue("+"+holder.txtPriceProductGrid.getText().toString().replace(".",""));

                }
                if (isChecked==false){
                    stringMutableLiveData.postValue("-"+holder.txtPriceProductGrid.getText().toString().replace(".",""));

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return cart_productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProductGrid;
        TextView txtNameProductGrid,txtPriceProductGrid,txtSaleProductGrid,txtDetailProductGrid;
        RatingBar ratingBarProductGrid;
        CheckBox cbItemcart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductGrid=(ImageView) itemView.findViewById(R.id.imgcart);
            txtNameProductGrid=(TextView)itemView.findViewById(R.id.txtnamecart);
            txtPriceProductGrid=(TextView)itemView.findViewById(R.id.txtpricecart);
            txtSaleProductGrid=(TextView)itemView.findViewById(R.id.txtpricesalecart);
            txtDetailProductGrid=(TextView)itemView.findViewById(R.id.txtdetailcart);
            ratingBarProductGrid=(RatingBar)itemView.findViewById(R.id.ratingbarcart);
            cbItemcart=(CheckBox) itemView.findViewById(R.id.cbcart);
        }
    }
}
