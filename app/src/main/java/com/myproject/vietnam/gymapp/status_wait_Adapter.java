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

public class status_wait_Adapter extends RecyclerView.Adapter<status_wait_Adapter.ViewHolder>{
    private Context context;
    private List<Status_product_tab> status_product_tabList;

    public status_wait_Adapter(Context context, List<Status_product_tab> status_product_tabList) {
        this.context = context;
        this.status_product_tabList = status_product_tabList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.layout_status_wait_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide
                .with(context)
                .load(getUrl.Url+"upload/product/"+status_product_tabList.get(position).getImage())
                .override(100,100)
                .into(holder.imgwaitstatus);
        holder.txtNamewaitstatus.setText(status_product_tabList.get(position).getName());
        holder.txtFlavorwaitstatus.setText("Hương vị: "+status_product_tabList.get(position).getFlavor());
        holder.txtPricewaitstatus.setText(status_product_tabList.get(position).getPrice());
        holder.txtCountwaitstatus.setText("Số lượng: "+status_product_tabList.get(position).getCount()+" sản phẩm");
        holder.txtPricesumwaitstatus.setText(status_product_tabList.get(position).getPricesum());
    }

    @Override
    public int getItemCount() {
        return status_product_tabList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgwaitstatus;
        TextView txtNamewaitstatus,txtFlavorwaitstatus,txtPricewaitstatus,txtCountwaitstatus,txtPricesumwaitstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgwaitstatus=(ImageView) itemView.findViewById(R.id.imgstatuswait);
            txtNamewaitstatus=(TextView)itemView.findViewById(R.id.txtnamestatuswait);
            txtFlavorwaitstatus=(TextView)itemView.findViewById(R.id.txtflavorstatuswait);
            txtCountwaitstatus=(TextView)itemView.findViewById(R.id.txtcountstatuswait);
            txtPricewaitstatus=(TextView)itemView.findViewById(R.id.txtpricestatuswait);
            txtPricesumwaitstatus=(TextView)itemView.findViewById(R.id.txtpricesumstatuswait);
        }
    }
}
