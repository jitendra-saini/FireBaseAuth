package com.example.firebaseauth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
        private FirebaseAuth mAuth;
        Button btn,btn2;
        private EditText editText,editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        editText=findViewById(R.id.editText);
        editText1=findViewById(R.id.editText2);
        btn=findViewById(R.id.button);
        btn2=findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(editText.getText().toString(),editText1.getText().toString());

            }
        });
         btn2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startLogin(editText.getText().toString(),editText1.getText().toString());
             }
         });





    }

    private void createAccount(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Log.e("MainActivity","SIgn up successfull");

                }
                else{
                    Log.e("MainActivity","SIgn up Unsuccessfull");


                }
            }
        });


    }

    private void startLogin(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    Log.e("MainActivity","signIn successfull");
                    FirebaseUser user=mAuth.getCurrentUser();
                    updateUi(user);


                }
                else {

                    Log.e("MainActivity","sign in failed");
                }
            }
        });


    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        updateUi(currentUser);



    }

    private void updateUi(FirebaseUser currentUser) {

            currentUser.getEmail();


    }
}
