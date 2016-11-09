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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kkkhhh.socialblinddate.Model.Post;
import com.kkkhhh.socialblinddate.R;
import com.kkkhhh.socialblinddate.Viewholder.PostViewHolder;

import java.util.List;

/**
 * Created by Dev1 on 2016-11-09.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {



    private List<Post> postList;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private Activity activity;

    public PostAdapter(List<Post> postList,Activity activity) {
        this.postList = postList;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Post post=postList.get(position);
        if(post.userProfileImg!=null) {
            storageReference.child(post.userProfileImg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(activity).load(uri).into(holder.cardUserImg);
                    holder.cardUserGender.setText(post.gender);
                    holder.cardUserAge.setText(post.age);
                    holder.cardUserLocal.setText(post.local);
                    holder.cardPostBody.setText(post.body);
                    holder.cardPostTitle.setText(post.title);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else{
            holder.cardUserGender.setText(post.gender);
            holder.cardUserAge.setText(post.age);
            holder.cardUserLocal.setText(post.local);
            holder.cardPostBody.setText(post.body);
            holder.cardPostTitle.setText(post.title);
        }

    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView cardUserImg;
        private TextView cardUserGender;
        private TextView cardUserAge;
        private TextView cardUserLocal;
        private TextView cardPostTitle;
        private TextView cardPostBody;
        private ViewPager cardPostImg;

        public ViewHolder(View itemView) {
            super(itemView);

            cardUserImg =(ImageView)itemView.findViewById(R.id.card_img);
            cardUserGender=(TextView)itemView.findViewById(R.id.card_gender);
            cardUserAge=(TextView)itemView.findViewById(R.id.card_age);
            cardUserLocal=(TextView)itemView.findViewById(R.id.card_local);
            cardPostTitle=(TextView)itemView.findViewById(R.id.card_title);
            cardPostBody=(TextView)itemView.findViewById(R.id.card_body);
            cardPostImg=(ViewPager)itemView.findViewById(R.id.card_img_pager);
        }
    }
}
