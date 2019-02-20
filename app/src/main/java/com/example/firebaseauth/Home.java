package com.example.firebaseauth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;

public class Home extends AppCompatActivity {

      Info info=new Info();
     private FirebaseAuth mAuth;
     TextView textView,textView1,textView2,textView3;
     ImageView imageView;
     ProfilePictureView profilePictureView;
     Button button,btn;
    FirebaseDatabase firebaseDatabase;
    EditText address,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth= FirebaseAuth.getInstance();
      textView=findViewById(R.id.textView);
      textView1=findViewById(R.id.textView2);
     profilePictureView=findViewById(R.id.image);
      firebaseDatabase=FirebaseDatabase.getInstance();
        button=findViewById(R.id.button3);
        btn=findViewById(R.id.button4);

     address=findViewById(R.id.editText3);
     number=findViewById(R.id.editText4);
    textView2=findViewById(R.id.textView4);
    textView3=findViewById(R.id.textView6);

          info.setAdress(address.getText().toString());
          info.setPhone(number.getText().toString());


        final DatabaseReference myRef=firebaseDatabase.getReference("jitendra saini");
         myRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String value=dataSnapshot.getValue(String.class);
             textView2.setText(dataSnapshot.getValue(String.class));
                  Log.e("value","value is"+value);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                              Log.e("canceled ", String.valueOf(databaseError.toException()));
             }
         });

   btn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           mAuth.signOut();
           Intent intent=new Intent(Home.this,FacebookLogin.class);
           startActivity(intent);
       }
   });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();

        updateUi(user);
    }

    public void updateUi(FirebaseUser user){
       String name=user.getDisplayName();
        String phonenumber= user.getPhoneNumber();
        Log.e("phonenumber"," "+phonenumber);
         //imageView.setImageURI(user.getPhotoUrl());
         textView.setText(name);
         textView1.setText(user.getEmail());

        final DatabaseReference myRef=firebaseDatabase.getReference(user.getDisplayName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                myRef.setValue("hello");
             /*   try{
                   myRef.child("Info").setValue(info);
                    Toast.makeText(Home.this,"send ",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    e.printStackTrace();
                }*/

            }
        });






    }
}
