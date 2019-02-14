package com.example.firebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FacebookLogin extends AppCompatActivity {
        private Button btn;
        private FirebaseAuth mAuth;
        String TAG="FacebookLogin";
         CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        callbackManager = CallbackManager.Factory.create();
        mAuth=FirebaseAuth.getInstance();

        final LoginButton loginButton=findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG,"facebook on success:"+loginResult);
                handleFaceBookAccessToken(loginResult.getAccessToken());

            }


            @Override
            public void onCancel() {
                            Log.e(TAG,"Facebook cancel ");
            }

            @Override
            public void onError(FacebookException error) {
                          Log.e(TAG,"Facebook error");
            }
        });





      /*  btn=findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //pass the activity result back to facebook sdk
       callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void handleFaceBookAccessToken(AccessToken accessToken) {
    Log.e(TAG,"handle facebook token "+accessToken);

        AuthCredential authCredential= FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    Log.e(TAG,"login successfull");

                    FirebaseUser user = mAuth.getCurrentUser();
                   updateUI(user);

                }
                else
                {

                    Log.e(TAG,"LOgin failed");
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        updateUI(user);
    }
public void updateUI(FirebaseUser user){

        /*String name=user.getDisplayName();
       String phonenumber= user.getPhoneNumber();*/
        Intent intent=new Intent(FacebookLogin.this,Home.class);
        //intent.putExtra("name",name);
        startActivity(intent);

}
}
