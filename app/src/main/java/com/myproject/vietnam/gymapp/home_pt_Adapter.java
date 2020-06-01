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

public class home_pt_Adapter extends RecyclerView.Adapter<home_pt_Adapter.ViewHolder>{
    private Context context;
    private List<home_PT> home_ptList;

    public home_pt_Adapter(Context context, List<home_PT> home_ptList) {
        this.context = context;
        this.home_ptList = home_ptList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.home_pt,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide
                .with(context)
                .load("http://192.168.1.3/VN-GYM/public/upload/pt/"+home_ptList.get(position).getImagePt())
                .override(150,500)
                .into(holder.imgPt);
        holder.txtNamePt.setText(home_ptList.get(position).getNamePt());
        holder.txtFollowPt.setText(home_ptList.get(position).getFollowPt());
    }

    @Override
    public int getItemCount() {
        return home_ptList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPt;
        TextView txtNamePt,txtFollowPt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPt=(ImageView)itemView.findViewById(R.id.image_Pt);
            txtNamePt=(TextView) itemView.findViewById(R.id.txtNamePt);
            txtFollowPt=(TextView) itemView.findViewById(R.id.txtFollowPt);
        }
    }
}
