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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FacebookLogin extends AppCompatActivity {
        private Button btn;
        private FirebaseAuth mAuth;
        String TAG="FacebookLogin";
         CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);


        mAuth=FirebaseAuth.getInstance();
        facebooklogin();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("589768944676-8a6t562at4392vjnv5ceaa48hd8j7l4c.apps.googleusercontent.com")
                .requestEmail()
                .build();





        final GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);

        SignInButton signInButton=findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
         signInButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent signInIntent=googleSignInClient.getSignInIntent();
                 startActivityForResult(signInIntent,8009);
             }
         });




    }























    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //pass the activity result back to facebook sdk

        if(requestCode==8009){

            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {

                Log.e("Google sign ","Sing in failed");
                e.printStackTrace();
            }
        }else {

            callbackManager.onActivityResult(requestCode, resultCode, data);

        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                            //updateUI(null);
                        }

                        // ...
                    }
                });
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
         if(user!=null) {
             updateUI(user);
         }/*else{
             Toast.makeText(this,"there is something wrong",Toast.LENGTH_SHORT).show();
         }*/
    }

    private void updateUIgoogle(GoogleSignInAccount googleSignInAccount) {

        Toast.makeText(FacebookLogin.this,"you are already sign in ",Toast.LENGTH_SHORT).show();





    }

    public void updateUI(FirebaseUser user){

        /*String name=user.getDisplayName();
       String phonenumber= user.getPhoneNumber();*/
        Intent intent=new Intent(FacebookLogin.this,Home.class);
        //intent.putExtra("name",name);
        startActivity(intent);
        finish();

}
public void facebooklogin(){

    callbackManager = CallbackManager.Factory.create();


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

}




}
