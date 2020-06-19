package com.myproject.vietnam.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rating_productdetail_Adapter extends RecyclerView.Adapter<rating_productdetail_Adapter.ViewHolder>{
    private Context context;
    private List<Rating> ratingList;

    public rating_productdetail_Adapter(Context context, List<Rating> ratingList) {
        this.context = context;
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.rating_product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtuserrating.setText(ratingList.get(position).getUser());
        holder.txtdescri.setText(ratingList.get(position).getDescri());
        holder.ratingproduct.setRating(ratingList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtuserrating,txtdescri;
        RatingBar ratingproduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtuserrating=(TextView)itemView.findViewById(R.id.txtuserrating);
            txtdescri=(TextView)itemView.findViewById(R.id.txtdescrirating);
            ratingproduct=(RatingBar)itemView.findViewById(R.id.ratingproduct);
        }
    }
}
