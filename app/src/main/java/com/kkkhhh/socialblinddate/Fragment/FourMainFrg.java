package com.kkkhhh.socialblinddate.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kkkhhh.socialblinddate.Model.UserImg;
import com.kkkhhh.socialblinddate.Model.UserModel;
import com.kkkhhh.socialblinddate.Model.UserProfile;
import com.kkkhhh.socialblinddate.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rey.material.widget.ProgressView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourMainFrg extends Fragment {
    private FirebaseDatabase mFirebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef=mFirebaseDatabase.getReference();
    private FirebaseAuth mFireBaseAuth=FirebaseAuth.getInstance();
    private FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    private StorageReference mStoreRef=firebaseStorage.getReference();
    private CircularImageView profileImg;
    private TextView nickName;
    private String uID;
    private ProgressView progressView;
    private ScrollView scrollView;


    public FourMainFrg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_four_main, container, false);


        init(rootView);

        return rootView;
    }
    private void init(View view){
      profileImg=(CircularImageView)view.findViewById(R.id.frg_four_profile_img);
      nickName=(TextView)view.findViewById(R.id.frg_four_nickname);
        progressView=(ProgressView)view.findViewById(R.id.frg_four_progress);
        scrollView=(ScrollView)view.findViewById(R.id.frg_four_scroll);
        scrollView.setVisibility(View.GONE);
      uID=mFireBaseAuth.getCurrentUser().getUid();


        ValueEventListener profileListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    nickName.setText(userProfile._uNickname);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        DatabaseReference userProfRef=mDatabaseRef.child("users").child(uID).child("profile");
        userProfRef.addListenerForSingleValueEvent(profileListener);


        ValueEventListener profileImgListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    UserImg userProfile = dataSnapshot.getValue(UserImg.class);
                    mStoreRef.child(userProfile._uImage1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            scrollView.setVisibility(View.VISIBLE);
                            progressView.setVisibility(View.GONE);
                            Glide.with(getActivity()).load(uri).into(profileImg);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference userImgRef=mDatabaseRef.child("users").child(uID).child("profileImg");
        userImgRef.addListenerForSingleValueEvent(profileImgListener);
    }


}
