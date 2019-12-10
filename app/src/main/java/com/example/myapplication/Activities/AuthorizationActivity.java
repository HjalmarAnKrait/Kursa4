package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Networking.RegAuth;
import com.example.myapplication.R;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends AppCompatActivity
{
    private EditText loginEditText, passwordEditText;
    private Button authButton, registrationButton;
    private boolean isSuccess = false;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        viewsInit();
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);


        authButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                auth(loginEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });


    }

    private void viewsInit()
    {
        loginEditText = findViewById(R.id.login_editText);
        passwordEditText = findViewById(R.id.password_editText);
        authButton = findViewById(R.id.auth_button);
        registrationButton = findViewById(R.id.registration_button);
    }


    private boolean auth(final String login, final String password)
    {
        try
        {
            RegAuth.getInstance().requests().authorization(login, password).enqueue(new Callback<JsonElement>()
            {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response)
                {
                   if (response.isSuccessful())
                   {
                       SharedPreferences.Editor editor = preferences.edit();
                       editor.putString("login", login);
                       editor.putString("password", password);
                       editor.apply();
                       setSuccess(true);
                       Toast.makeText(AuthorizationActivity.this, "Вы успешно авторизовались", Toast.LENGTH_SHORT).show();
                       if(isSuccess)
                       {
                           startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       }

                   }
                   else
                   {
                       Toast.makeText(AuthorizationActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                       setSuccess(false);
                   }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t)
                {
                    setSuccess(false);
                    Toast.makeText(AuthorizationActivity.this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {

        }
        Log.e("432", "agaagag");
        return getSuccess();
    }

    public void setSuccess(boolean result)
    {
        this.isSuccess = result;
    }

    public boolean getSuccess()
    {
        return this.isSuccess;
    }
}
