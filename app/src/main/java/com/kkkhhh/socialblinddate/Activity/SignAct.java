package com.kkkhhh.socialblinddate.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kkkhhh.socialblinddate.Model.UserModel;
import com.kkkhhh.socialblinddate.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignAct extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEdit,passwordEdit;
    private String emailStr,passwordStr,nicknameStr,ageStr;
    private Button signPostBtn,signPerfectBtn;
    private CheckBox signUserInfoCb,signUseCb;
    private TextView signUserInfoView,signUseView;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef= mFirebaseDatabase.getReference().getRoot();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign);
        init();

    }

    private void init(){
        emailEdit=(EditText)findViewById(R.id.sign_email);
        passwordEdit = (EditText)findViewById(R.id.sign_password);


        progressDialog=new ProgressDialog(this);

        signPostBtn=(Button)findViewById(R.id.sign_next_btn);


        signPostBtn.setOnClickListener(this);



    }

    private void createAuth(){
        mFirebaseAuth.createUserWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userID=mFirebaseAuth.getCurrentUser().getUid().toString();
                    userSignPutData(userID,emailStr);
                    Intent intent= new Intent(SignAct.this,SignProfileAct.class);
                    startActivity(intent);
                    progressDialog.cancel();
                    SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(SignAct.this);
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putString("SIGN_FIRST_ACTIVITY","OK");
                    editor.commit();
                    finish();
                }else{
                    Toast.makeText(SignAct.this,"메일을 확인해주세요.",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view==signPostBtn) {
            emailStr = emailEdit.getText().toString().trim();
            passwordStr = passwordEdit.getText().toString().trim();


            if (TextUtils.isEmpty(emailStr)) {

                Toast.makeText(SignAct.this, "이메일을 확인 해주세요.", Toast.LENGTH_SHORT).show();

                return;
            }
            else if (TextUtils.isEmpty(passwordStr)) {
                Toast.makeText(SignAct.this, "패스워드를 확인 해주세요.", Toast.LENGTH_SHORT).show();

                return;
            }else if (passwordStr.length() < 6) {

                Toast.makeText(SignAct.this, "패스워드는 6글자 이상 입니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                alertDialog();
            }
        }
        }
    @Override
    protected void onStart(){
           super.onStart();
    }


    private void userSignPutData(String uID,String email){

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strCurDate = CurDateFormat.format(date);

        UserModel mUserModel= new UserModel(uID,email,strCurDate);

        mDatabaseRef.child("users").child(uID).setValue(mUserModel);
    }


    private void alertDialog(){
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_sign,null);
        signUserInfoCb=(CheckBox)dialogView.findViewById(R.id.sign_user_info_check);
        signUseCb=(CheckBox)dialogView.findViewById(R.id.sign_user_use_check);
        signUserInfoView=(TextView)dialogView.findViewById(R.id.sign_user_info_view);
        signUserInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignAct.this,SignUserInfoAct.class);
                startActivity(intent);
            }
        });
        signUseView=(TextView)dialogView.findViewById(R.id.sign_user_use_view);
        signPerfectBtn=(Button)dialogView.findViewById(R.id.sign_perfect_btn);
        signPerfectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUserInfoCb.isChecked()==false){
                    Toast.makeText(SignAct.this,"개인정보 방침에 동의해주세요.",Toast.LENGTH_SHORT).show();
                }else if(signUseCb.isChecked()==false){
                        Toast.makeText(SignAct.this,"이용약관에 동의해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setMessage("회원정보를 저장하고 있습니다.");
                    progressDialog.show();
                    createAuth();
                }
            }
        });


        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
