package com.android.kobra;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    Button b1;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            imageView = findViewById(R.id.blur);
            float radius = 20f;  // Adjust for more or less blur
            imageView.setRenderEffect(RenderEffect.createBlurEffect(radius,
                    radius,
                    Shader.TileMode.CLAMP)
            );
        }

        b1 = findViewById(R.id.button);
        b1.setOnClickListener(v -> {
            Intent i1 = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(i1);
        });
    }
}