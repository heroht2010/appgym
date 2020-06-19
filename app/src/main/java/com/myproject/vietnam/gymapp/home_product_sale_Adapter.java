package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class home_product_sale_Adapter extends RecyclerView.Adapter<home_product_sale_Adapter.productsaleViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(home_product_sale item);
    }
    private Context context;
    private int layout;
    private List<home_product_sale>  product_saleList;
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener=listener;
    }
    public home_product_sale_Adapter(Context context, int layout, List<home_product_sale> product_saleList,OnItemClickListener listener) {
        this.context = context;
        this.layout = layout;
        this.product_saleList = product_saleList;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public productsaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(layout,parent,false);
        return new productsaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productsaleViewHolder holder, int position) {
        holder.bind(product_saleList.get(position), itemClickListener);

    }

    @Override
    public int getItemCount() {
        return product_saleList.size();
    }

    public class productsaleViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewProductsale;
        TextView txtnameProductsale;
        TextView txtpriceProductsale;
        RatingBar ratingBarProductSale;
        public productsaleViewHolder(@NonNull View itemView) {
            super(itemView);
             imageViewProductsale=itemView.findViewById(R.id.image_product_sale);
             txtnameProductsale=itemView.findViewById(R.id.txtnameproduct_sale);
            txtpriceProductsale=itemView.findViewById(R.id.txtprice_product_sale);
            ratingBarProductSale=(RatingBar) itemView.findViewById(R.id.ratingbarProduct);

        }
        public void bind(final home_product_sale item, final OnItemClickListener listener) {
            Glide
                    .with(context)
                    .load(getUrl.Url+"upload/product/"+item.getImageProduct())
                    .override(200,150)
                    .into(imageViewProductsale);
            txtnameProductsale.setText(item.getNameProduct());
            txtpriceProductsale.setText(item.getPriceProduct());
            ratingBarProductSale.setRating(item.getRatingProduct());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    itemClickListener.onItemClick(item);
                }
            });
        }
    }


}
