package com.kkkhhh.socialblinddate.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kkkhhh.socialblinddate.Activity.PostWriterAct;
import com.kkkhhh.socialblinddate.Interface.OnLoadMoreListener;
import com.kkkhhh.socialblinddate.Model.User;
import com.kkkhhh.socialblinddate.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class FirstMainFrg extends Fragment {
    private FloatingActionButton fab;

    public FirstMainFrg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first_main, container, false);
        fab=(FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PostWriterAct.class);
                startActivity(intent);
            }
        });


     return rootView;
    }


}
