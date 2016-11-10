package com.kkkhhh.socialblinddate.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kkkhhh.socialblinddate.Model.Post;
import com.kkkhhh.socialblinddate.R;

import com.rey.material.widget.ProgressView;

import java.net.URL;
import java.util.List;

/**
 * Created by Dev1 on 2016-11-09.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   /* private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;*/
    private List<Post> postList;

    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private Activity activity;

    private boolean test = false;


    public PostAdapter(List<Post> postList,Activity activity) {
        this.postList = postList;
        this.activity=activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType ==TYPE_HEADER) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_post_header, parent, false);
//            return new HeaderViewHolder(v);
//        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_post, parent, false);
            return new ViewHolder(v);
//        }
//        /*View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_post, parent, false);*/
//        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        if(holder instanceof HeaderViewHolder){
//
//        }
//        else if (holder instanceof ViewHolder) {
            final Post post=postList.get(position);
            if(post.userProfileImg!=null) {
                storageReference.child(post.userProfileImg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(activity).load(uri).into(((ViewHolder) holder).cardUserImg);
                        ((ViewHolder) holder).cardUserGender.setText(post.gender);
                        ((ViewHolder) holder).cardUserAge.setText(post.age);
                        ((ViewHolder) holder).cardUserLocal.setText(post.local);
                        ((ViewHolder) holder).cardPostTitle.setText(post.title);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }else{
                ((ViewHolder) holder).cardUserGender.setText(post.gender);
                ((ViewHolder) holder).cardUserAge.setText(post.age);
                ((ViewHolder) holder).cardUserLocal.setText(post.local);
                ((ViewHolder) holder).cardPostTitle.setText(post.title);
            }
//        }
    }


//    @Override
//    public int getItemViewType(int position) {
//        if(isPositionHeader(position)) {
//            return TYPE_HEADER;
//        }else {
//            return TYPE_ITEM;
//        }
//    }


//
//    private Post getItem(int position)
//    {
//        return postList.get(position);
//    }


    @Override
    public int getItemCount() {

        return  postList.size();
    }

//    private boolean isPositionHeader(int position)
//    {
//        return position == TYPE_HEADER;
//    }

    ////컨텐츠 뷰
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView cardUserImg;
        private TextView cardUserGender;
        private TextView cardUserAge;
        private TextView cardUserLocal;
        private TextView cardPostTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            cardUserImg =(ImageView)itemView.findViewById(R.id.card_img);
            cardUserGender=(TextView)itemView.findViewById(R.id.card_gender);
            cardUserAge=(TextView)itemView.findViewById(R.id.card_age);
            cardUserLocal=(TextView)itemView.findViewById(R.id.card_local);
            cardPostTitle=(TextView)itemView.findViewById(R.id.card_title);

        }
    }

    ////헤드 뷰
//    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
//
//        public HeaderViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
}
