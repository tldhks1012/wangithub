package com.kkkhhh.socialblinddate.Activity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kkkhhh.socialblinddate.Model.User;
import com.kkkhhh.socialblinddate.Model.UserModel;
import com.kkkhhh.socialblinddate.R;

public class StartAct extends AppCompatActivity{

  private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference().getRoot();
  private Handler mHandler;
  private Runnable mRunnable;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user !=null){
                        String uid=user.getUid().toString();


                }else{
                    Intent intent = new Intent(StartAct.this,WelcomeAct.class);
                    startActivity(intent);
                    finish();
                }


            }
        };
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable,1000);
    }



}
