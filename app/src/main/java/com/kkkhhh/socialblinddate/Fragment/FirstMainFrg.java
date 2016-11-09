package com.kkkhhh.socialblinddate.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.kkkhhh.socialblinddate.Activity.PostWriterAct;
import com.kkkhhh.socialblinddate.Adapter.PostAdapter;
import com.kkkhhh.socialblinddate.Model.Post;
import com.kkkhhh.socialblinddate.R;

import com.melnykov.fab.FloatingActionButton;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;


public class FirstMainFrg extends Fragment {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private LinearLayoutManager mManager;
    private DatabaseReference mDatabase;
    private DatabaseReference mPostRef;
    private PostAdapter mAdapter;
    private List<Post> postList;
    private ProgressView progressView;


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

        progressView=(ProgressView)rootView.findViewById(R.id.frg_first_progress);

        postList=new ArrayList<Post>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        recyclerView.setHasFixedSize(true);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        mPostRef=mDatabase.child("posts");
        mPostRef.keepSynced(true);
        mPostRef.limitToFirst(1);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mManager);


        PostTask postTask=new PostTask();
        postTask.execute();





    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {

        }
    }

    public void loadData(DataSnapshot dataSnapshot){
        System.out.println(dataSnapshot.getValue());

        Post postModel =dataSnapshot.getValue(Post.class);

        postList.add(postModel);

        mAdapter = new PostAdapter(postList,getActivity());

        recyclerView.setAdapter(mAdapter);
    }

    private class PostTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*recyclerView.setVisibility(View.GONE);*/

        }
        @Override
        protected String doInBackground(String... params) {
            mPostRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    loadData(dataSnapshot);
                    Log.d("getKey",dataSnapshot.getKey());
                    /*recyclerView.setVisibility(View.VISIBLE);*/
                    progressView.setVisibility(View.GONE);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                  /*  loadData(dataSnapshot);*/

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}


