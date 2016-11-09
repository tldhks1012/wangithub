package com.kkkhhh.socialblinddate.Viewholder;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kkkhhh.socialblinddate.Model.Post;
import com.kkkhhh.socialblinddate.R;


/**
 * Created by Dev1 on 2016-11-09.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
    private ImageView cardUserImg;
    private TextView cardUserGender;
    private TextView cardUserAge;
    private TextView cardUserLocal;
    private TextView cardPostTitle;
    private TextView cardPostBody;
    private ViewPager cardPostImg;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    public PostViewHolder(View itemView) {
        super(itemView);
        cardUserImg =(ImageView)itemView.findViewById(R.id.card_img);
        cardUserGender=(TextView)itemView.findViewById(R.id.card_gender);
        cardUserAge=(TextView)itemView.findViewById(R.id.card_age);
        cardUserLocal=(TextView)itemView.findViewById(R.id.card_local);
        cardPostTitle=(TextView)itemView.findViewById(R.id.card_title);
        cardPostBody=(TextView)itemView.findViewById(R.id.card_body);
        cardPostImg=(ViewPager)itemView.findViewById(R.id.card_img_pager);


    }

    public void bindToPost(final Post post,final Activity activity){
        if(post.userProfileImg!=null) {
            storageReference.child(post.userProfileImg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(activity).load(uri).into(cardUserImg);
                    cardUserGender.setText(post.gender);
                    cardUserAge.setText(post.age);
                    cardUserLocal.setText(post.local);
                    cardPostBody.setText(post.body);
                    cardPostTitle.setText(post.title);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else{
            cardUserGender.setText(post.gender);
            cardUserAge.setText(post.age);
            cardUserLocal.setText(post.local);
            cardPostBody.setText(post.body);
            cardPostTitle.setText(post.title);
        }

    }
}
