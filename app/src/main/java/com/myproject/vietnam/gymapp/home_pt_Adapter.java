package com.myproject.vietnam.gymapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

public class home_pt_Adapter extends RecyclerView.Adapter<home_pt_Adapter.ViewHolder>{
    private Context context;
    private List<home_PT> home_ptList;
    ShareDialog shareDialog;
    SharePhotoContent sharePhotoContent;
    SharedPreferences sharedPreferencesface;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide
                .with(context)
                .load(getUrl.Url+"upload/pt/"+home_ptList.get(position).getImagePt())
                .override(150,500)
                .into(holder.imgPt);
        holder.txtNamePt.setText(home_ptList.get(position).getNamePt());
        holder.txtFollowPt.setText(home_ptList.get(position).getFollowPt());
        holder.btnShareFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesface=context.getSharedPreferences("dataLoginFace",Context.MODE_PRIVATE);
//                Toast.makeText(context,"hanh phuc o dau",Toast.LENGTH_LONG).show();
                if (sharedPreferencesface.contains("name")){
                    holder.imgPt.buildDrawingCache();
                    Bitmap bitmap=holder.imgPt.getDrawingCache();
                    shareDialog=new ShareDialog((Activity) context);
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        SharePhoto photo = new SharePhoto.Builder()
                                .setBitmap(bitmap)
                                .build();
                        sharePhotoContent  = new SharePhotoContent.Builder()
                                .addPhoto(photo)
                                .build();

                    }
                    shareDialog.show(sharePhotoContent);
                }
                else{
                    NavController navController= Navigation.findNavController((Activity) context,R.id.fragment_main);
                    navController.navigate(R.id.fragment_Login);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return home_ptList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPt;
        TextView txtNamePt,txtFollowPt;
        Button btnShareFace;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPt=(ImageView)itemView.findViewById(R.id.image_Pt);
            txtNamePt=(TextView) itemView.findViewById(R.id.txtNamePt);
            txtFollowPt=(TextView) itemView.findViewById(R.id.txtFollowPt);
            btnShareFace=(Button) itemView.findViewById(R.id.btnshare);
        }
    }
}
