package com.example.alromero.googlelogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import static android.R.attr.name;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView imageViewphoto;
    private TextView textViewname, textViewemail, textViewid;

    //Test the secondary branch

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewphoto = (ImageView) findViewById(R.id.photoImageView);
        textViewname = (TextView) findViewById(R.id.nameTextView);
        textViewemail = (TextView) findViewById(R.id.emailTextView);
        textViewid = (TextView) findViewById(R.id.idTextView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){

            GoogleSignInResult result = opr.get();
            handleSignInResult(result);

        } else {

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);

                }
            });

        }

    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()){

           GoogleSignInAccount account = result.getSignInAccount();
            textViewname.setText(account.getDisplayName());
            textViewemail.setText(account.getEmail());
            textViewid.setText(account.getId());

        } else {

            goLogInScreen();

        }

    }

    private void goLogInScreen() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    public void LogOut(View view) {
    }

    public void Revoke(View view) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
