package com.lcl.beer_community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG="SignUpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.LoginButton).setOnClickListener(onClickListener);
        findViewById(R.id.SignUpButton).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);						// 태스크를 백그라운드로 이동
        finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid());
    }




    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.LoginButton:
                    login();
                    break;
                case R.id.SignUpButton:
                    startSignActivity();
                    break;
            }
        }
    };

    private void login(){

        String email=((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password=((EditText)findViewById(R.id.passwordEditText)).getText().toString();

        if(email.length()>0&& password.length()>0) {


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                toast("로그인 성공");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                if(task.getException()!=null){
                                    toast(task.getException().getMessage());
                                }
                            }
                        }
                    });
        }else{
            toast("이메일 또는 비밀번호를 입력해주세요");
        }

    }

    private void toast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    private void startMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void startSignActivity(){
        Intent intent=new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

}
