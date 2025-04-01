package com.android.kobra.authManager;

import android.content.Context;
import com.android.kobra.BuildConfig;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseLogin {
    private final FirebaseAuth mAuth;
    private final GoogleSignInClient googleSignInClient;

    public FirebaseLogin(Context context) {
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.WEB_CLIENT_ID) // ✅ Replace with actual Web Client ID
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions);
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }
}
