package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class productsame_detail_Adapter extends RecyclerView.Adapter<productsame_detail_Adapter.ViewHolder> {
    private Context context;
    private List<home_product> product_List;

    public productsame_detail_Adapter(Context context, List<home_product> product_List) {
        this.context = context;
        this.product_List = product_List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_productsame,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide
                .with(context)
                .load(getUrl.Url+"upload/product/"+product_List.get(position).getImageProduct())
                .override(150,150)
                .into(holder.imgItemproductsame);
        holder.txtnameItemproductsame.setText(product_List.get(position).getNameProduct());
        holder.txtpriceItemproductsame.setText(product_List.get(position).getPriceProduct());
        holder.txtpricesaleItemproductsame.setText(product_List.get(position).getPriceSale());
    }

    @Override
    public int getItemCount() {
        return product_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemproductsame;
        TextView txtnameItemproductsame,txtpriceItemproductsame,txtpricesaleItemproductsame;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemproductsame=(ImageView) itemView.findViewById(R.id.imgproductsame);
            txtnameItemproductsame=(TextView) itemView.findViewById(R.id.txtnameproductsame);
            txtpriceItemproductsame=(TextView)itemView.findViewById(R.id.txtpriceproductsame);
            txtpricesaleItemproductsame=(TextView)itemView.findViewById(R.id.txtpricesaleproductsame);
        }
    }
}
