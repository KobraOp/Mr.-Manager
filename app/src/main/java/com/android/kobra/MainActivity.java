package com.android.kobra;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.kobra.designPackage.colorAnimation;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    View line1, line2;
    LinearLayout emailField, passwordField;
    private colorAnimation emailColorAnimation, passwordColorAnimation;

    private boolean isEmailFocused = false;
    private boolean isPasswordFocused = false;
    private boolean wasEmailValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailField = findViewById(R.id.emailFeild);
        passwordField = findViewById(R.id.passwordFeild);
        line1 = findViewById(R.id.emailLine);
        line2 = findViewById(R.id.passwordLine);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        emailColorAnimation = new colorAnimation(line1);
        passwordColorAnimation = new colorAnimation(line2);

        emailField.setOnClickListener(v -> {
            if (!isEmailFocused) {
                isEmailFocused = true;
                isPasswordFocused = false;
                updateFieldColor(false);
            }
            email.requestFocus();
        });

        passwordField.setOnClickListener(v -> {
            if (!isPasswordFocused) {
                isPasswordFocused = true;
                isEmailFocused = false;
                updateFieldColor(false);
            }
            password.requestFocus();
        });

        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus != isEmailFocused) {
                isEmailFocused = hasFocus;
                isPasswordFocused = !hasFocus;
                updateFieldColor(false);
            }
        });

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus != isPasswordFocused) {
                isPasswordFocused = hasFocus;
                isEmailFocused = !hasFocus;
                updateFieldColor(false);
            }
        });

        email.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isValid = isValidEmail(email.getText().toString());
                if (isValid != wasEmailValid) {
                    wasEmailValid = isValid;
                    password.requestFocus();
                    updateFieldColor(true);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }
    private void updateFieldColor(boolean emailCheck) {
        int defaultColor = getResources().getColor(R.color.waveBalck);
        int selectedColor = getResources().getColor(R.color.neela);
        int validColor = Color.GREEN;

        if (emailCheck) {
            int emailColor = isValidEmail(email.getText().toString()) ? validColor : selectedColor;
            emailColorAnimation.setColor(defaultColor, emailColor);
            emailColorAnimation.animateColorChange();
        } else {
            if (isEmailFocused) {
                int emailColor = isValidEmail(email.getText().toString()) ? validColor : selectedColor;
                emailColorAnimation.setColor(defaultColor, emailColor);
                emailColorAnimation.animateColorChange();

                passwordColorAnimation.setColor(selectedColor, defaultColor);
                passwordColorAnimation.animateColorChange();
            } else if (isPasswordFocused) {
                passwordColorAnimation.setColor(defaultColor, selectedColor);
                passwordColorAnimation.animateColorChange();

                int emailColor = isValidEmail(email.getText().toString()) ? validColor : defaultColor;
                emailColorAnimation.setColor(selectedColor, emailColor);
                emailColorAnimation.animateColorChange();
            }
        }
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
