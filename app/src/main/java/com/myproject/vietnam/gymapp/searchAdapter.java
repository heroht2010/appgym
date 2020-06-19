package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.ContentHandler;
import java.util.List;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder>{
    private Context context;
    private List<home_product> home_productList;

    public searchAdapter(Context context, List<home_product> home_productList) {
        this.context = context;
        this.home_productList = home_productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.productlist_gridrecycle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide
                .with(context)
                .load(getUrl.Url+"upload/product/"+home_productList.get(position).getImageProduct())
                .override(200,200)
                .into(holder.imgProductGrid);
        holder.txtNameProductGrid.setText(home_productList.get(position).getNameProduct());
        holder.txtPriceProductGrid.setText(home_productList.get(position).getPriceProduct());
        holder.txtSaleProductGrid.setText(home_productList.get(position).getPriceSale());
        holder.txtDetailProductGrid.setText(home_productList.get(position).getDetailProduct());
        holder.ratingBarProductGrid.setRating(home_productList.get(position).getRatingProduct());
    }

    @Override
    public int getItemCount() {
        return home_productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProductGrid;
        TextView txtNameProductGrid,txtPriceProductGrid,txtSaleProductGrid,txtDetailProductGrid;
        RatingBar ratingBarProductGrid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductGrid=(ImageView) itemView.findViewById(R.id.productlist_imggrid);
            txtNameProductGrid=(TextView)itemView.findViewById(R.id.txtNameProductgrid);
            txtPriceProductGrid=(TextView)itemView.findViewById(R.id.txtpriceProductgrid);
            txtSaleProductGrid=(TextView)itemView.findViewById(R.id.txtsaleProductgrid);
            txtDetailProductGrid=(TextView)itemView.findViewById(R.id.txtdetailProductgrid);
            ratingBarProductGrid=(RatingBar)itemView.findViewById(R.id.ratingbarProduct);

        }
    }
}
