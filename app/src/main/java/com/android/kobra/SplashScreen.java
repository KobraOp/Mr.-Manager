package com.android.kobra;

import android.content.Context;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.kobra.authManager.FirebaseLogin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;

public class SplashScreen extends AppCompatActivity {

    LinearLayout l1,l2;
    private static final  int RC_SIGN_IN = 123;
    private FirebaseLogin firebaseLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        l1 = findViewById(R.id.googleLoginButton);
        l2 = findViewById(R.id.emailLoginButton);

        firebaseLogin = new FirebaseLogin(this);

        l1.setOnClickListener(v -> {
            GoogleSignInClient signInClient = firebaseLogin.getGoogleSignInClient();
            Intent signInIntent = signInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        l2.setOnClickListener(v -> {
            Intent i1 = new Intent(this, MainActivity.class);
            startActivity(i1);
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(getApplicationContext(),
                        "Login Successful",
                        Toast.LENGTH_SHORT).show();

                if (account != null) {
                    Intent navigateToMain = new Intent(getApplicationContext(),
                            MainScreen.class);
                    startActivity(navigateToMain);
                    finish();
                }
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),
                        "Google Login Error",
                        Toast.LENGTH_SHORT).show();
                Log.d("Google SigIn Error", "" + e.getStatusCode());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Intent navigate = new Intent(getApplicationContext(), MainScreen.class);
            startActivity(navigate);
        }
    }
}