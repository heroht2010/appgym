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

public class home_product_type_Adapter extends RecyclerView.Adapter<home_product_type_Adapter.ViewHolder>{
    private Context context;
    private List<home_product_type> home_product_typeList;

    public home_product_type_Adapter(Context context, List<home_product_type> home_product_typeList) {
        this.context = context;
        this.home_product_typeList = home_product_typeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.home_producttype,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide
                .with(context)
                .load(getUrl.Url+"upload/typeproduct/"+home_product_typeList.get(position).getImageProductType())
                .override(125,100)
                .into(holder.imgProductType);
        Glide
                .with(context)
                .load(R.drawable.icon_facebook)
                .override(40,40)
                .into(holder.imgIcontype);
        holder.txtNameType.setText(home_product_typeList.get(position).getTxtProductType());
    }

    @Override
    public int getItemCount() {
        return home_product_typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProductType,imgIcontype;
        TextView txtNameType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductType=(ImageView) itemView.findViewById(R.id.image_product_type);
            imgIcontype=(ImageView) itemView.findViewById(R.id.image_icon_type);
            txtNameType=(TextView) itemView.findViewById(R.id.txtproduct_type);
        }
    }
}
