package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class AuthorizationActivity extends AppCompatActivity
{
    private EditText loginEditText, passwordEditText;
    private Button authButton, registrationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        viewsInit();

    }

    private void viewsInit()
    {
        loginEditText = findViewById(R.id.login_editText);
        passwordEditText = findViewById(R.id.password_editText);
        authButton = findViewById(R.id.auth_button);
        registrationButton = findViewById(R.id.registration_button);
    }

    private boolean Auth(String login, String password)
    {
        return true;
    }
}
