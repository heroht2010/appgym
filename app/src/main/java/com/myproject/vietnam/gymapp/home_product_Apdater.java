package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class home_product_Apdater extends RecyclerView.Adapter<home_product_Apdater.ViewHolder>{
    public interface OnItemClickListener {
        void onItemClick(home_product item);
    }
    private Context context;
    private List<home_product> home_productList;
    private final OnItemClickListener listener;

    public home_product_Apdater(Context context, List<home_product> home_productList,OnItemClickListener listener) {
        this.context = context;
        this.home_productList = home_productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.home_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(home_productList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return home_productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView txtNameProduct;
        TextView txtPriceProduct;
        RatingBar ratingBarProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=(ImageView)itemView.findViewById(R.id.image_product);
            txtNameProduct=(TextView) itemView.findViewById(R.id.txtname_product);
            txtPriceProduct=(TextView) itemView.findViewById(R.id.txtprice_product);
            ratingBarProduct=(RatingBar)itemView.findViewById(R.id.ratingbarProduct);
        }
        public void bind(final home_product item, final OnItemClickListener listener) {
            Glide
                    .with(context)
                    .load("http://192.168.1.5/VN-GYM/public/upload/product/"+item.getImageProduct())
                    .override(200,150)
                    .into(imgProduct);
            txtNameProduct.setText(item.getNameProduct());
            txtPriceProduct.setText(item.getPriceProduct());
            ratingBarProduct.setRating(item.getRatingProduct());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
