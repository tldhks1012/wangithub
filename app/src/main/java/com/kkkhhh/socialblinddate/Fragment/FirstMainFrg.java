package com.kkkhhh.socialblinddate.Fragment;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kkkhhh.socialblinddate.Activity.PostWriterAct;

import com.kkkhhh.socialblinddate.Adapter.PostAdapter;
import com.kkkhhh.socialblinddate.Etc.EndlessRecyclerOnScrollListener;
import com.kkkhhh.socialblinddate.Model.Post;
import com.kkkhhh.socialblinddate.R;


import com.melnykov.fab.FloatingActionButton;
import com.poliveira.parallaxrecyclerview.HeaderLayoutManagerFixed;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.rey.material.widget.ProgressView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;


public class FirstMainFrg extends Fragment {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private LinearLayoutManager mManager;
    private DatabaseReference mDatabase;
    private DatabaseReference mPostRef;
/*    private ParallaxRecyclerAdapter<Post> mAdapter;*/
    private PostAdapter mAdapter;
    private List<Post> postList;
    private ProgressView progressView;
    private RequestManager mGlideRequestManager;




    public FirstMainFrg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first_main, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostWriterAct.class);
                startActivity(intent);
            }
        });

        progressView = (ProgressView) rootView.findViewById(R.id.frg_first_progress);
        mGlideRequestManager=Glide.with(this);
        postList = new ArrayList<Post>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPostRef = mDatabase.child("/posts/man-posts/");
        mPostRef.keepSynced(true);



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mManager);

        new Thread() {
            @Override
            public void run() {
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPostRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                postList.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Post postModel = postSnapshot.getValue(Post.class);
                                    postList.add(postModel);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }, 0);

                mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        init();
                    }
                }, 500);
            }
        }.start();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {

        }
    }

    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onStop() {
        super.onStop();

    }


    private void init() {





        mAdapter=new PostAdapter(postList,getActivity(),mPostRef,progressView,recyclerView);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaAdapter.setDuration(1000);
        recyclerView.setAdapter(alphaAdapter);




        /*postValueListner=new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post postModel = postSnapshot.getValue(Post.class);
                    postList.add(postModel);
                }
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };*/






     /*   mAdapter = new ParallaxRecyclerAdapter<Post>(postList) {

            @Override
            public void onBindViewHolderImpl(final RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<Post> adapter,final int position) {
                final Post post = postList.get(position);
                if (post.userProfileImg != null) {
                    storageReference.child(post.userProfileImg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri!=null) {
                                mGlideRequestManager.load(uri).listener(new RequestListener<Uri, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                        ((ViewHolder) viewHolder).cardUserGender.setText(post.gender);
                                        ((ViewHolder) viewHolder).cardUserAge.setText(post.age);
                                        ((ViewHolder) viewHolder).cardUserLocal.setText(post.local);
                                        ((ViewHolder) viewHolder).cardPostTitle.setText(post.title);
                                        progressView.setVisibility(View.INVISIBLE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        return false;

                                    }
                                }).into(((ViewHolder) viewHolder).cardUserImg);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } else {

                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<Post> adapter, int i) {
                return new ViewHolder(getActivity().getLayoutInflater().inflate(R.layout.card_post, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<Post> adapter) {
                return postList.size();
            }

        };


        HeaderLayoutManagerFixed layoutManagerFixed = new HeaderLayoutManagerFixed(getActivity());
        recyclerView.setLayoutManager(layoutManagerFixed);
        View header = getActivity().getLayoutInflater().inflate(R.layout.card_post_header, recyclerView, false);
        layoutManagerFixed.setHeaderIncrementFixer(header);
        mAdapter.setShouldClipView(false);
        mAdapter.setParallaxHeader(header, recyclerView);
        mAdapter.setData(postList);*/
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cardUserImg;
        private TextView cardUserGender;
        private TextView cardUserAge;
        private TextView cardUserLocal;
        private TextView cardPostTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            cardUserImg = (ImageView) itemView.findViewById(R.id.card_img);
            cardUserGender = (TextView) itemView.findViewById(R.id.card_gender);
            cardUserAge = (TextView) itemView.findViewById(R.id.card_age);
            cardUserLocal = (TextView) itemView.findViewById(R.id.card_local);
            cardPostTitle = (TextView) itemView.findViewById(R.id.card_title);
        }
    }




}


