package com.example.firebaseauth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.URL;

public class Home extends AppCompatActivity {
     private FirebaseAuth mAuth;
     TextView textView,textView1;
     ImageView imageView;
     ProfilePictureView profilePictureView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth= FirebaseAuth.getInstance();
      textView=findViewById(R.id.textView);
      textView1=findViewById(R.id.textView2);
     profilePictureView=findViewById(R.id.image);







    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        updateUi(user);
    }

    public void updateUi(FirebaseUser user){
       // Bitmap image;
        String name=user.getDisplayName();
        String phonenumber= user.getPhoneNumber();
        //Bitmap mIcon = BitmapFactory.decodeStream(user.getPhotoUrl().openConnection().getInputStream());
         imageView.setImageURI(user.getPhotoUrl());
         textView.setText(name);
         textView1.setText(user.getEmail());


       /* Intent intent=new Intent(FacebookLogin.this,Home.class);
        intent.putExtra("name",name);
        startActivity(intent);
*/
    }
}
