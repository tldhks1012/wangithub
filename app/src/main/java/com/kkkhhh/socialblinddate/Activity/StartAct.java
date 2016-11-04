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
import com.kkkhhh.socialblinddate.R;

public class StartAct extends AppCompatActivity {

  private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference().getRoot();
  private Handler mHandler;
  private Runnable mRunnable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(StartAct.this);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String firstActivityCheck =mPref.getString("SIGN_FIRST_ACTIVITY","");
                String secondActivityCheck=mPref.getString("SIGN_SECOND_ACTIVITY","");


                    if(user !=null){
                    if(firstActivityCheck.equals("OK")&&!secondActivityCheck.equals("OK")) {
                        Log.d("FirstAct", firstActivityCheck);
                        Intent intent = new Intent(StartAct.this, SignProfileAct.class);
                        startActivity(intent);
                        finish();
                    }else if(firstActivityCheck.equals("OK")&&secondActivityCheck.equals("OK")){
                        Log.d("SecondActivity",secondActivityCheck);
                        Intent intent = new Intent(StartAct.this,SignImageAct.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(StartAct.this,MainAct.class);
                        startActivity(intent);
                        finish();
                    }
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
