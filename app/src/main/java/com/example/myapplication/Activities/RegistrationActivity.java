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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity
{
    private EditText loginEditText, passwordEditText, passwordAcceptEditText, nameEditText;
    private Button registrationButton;
    private boolean isSuccess = false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewsInit();

        registrationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               String name, login, password, passwordAccept;
               name = nameEditText.getText().toString();
               login = loginEditText.getText().toString();
               password = passwordEditText.getText().toString();
               passwordAccept = passwordAcceptEditText.getText().toString();
               if(checkIsEmpty(name, login, password, passwordAccept) && checkPassword(password, passwordAccept))
               {
                   registration(name, login, password);
               }
            }
        });


    }

    private void viewsInit()
    {
        nameEditText = findViewById(R.id.name_editText);
        loginEditText = findViewById(R.id.login_editText);
        passwordEditText = findViewById(R.id.password_editText);
        passwordAcceptEditText = findViewById(R.id.password_accept_editText);
        registrationButton = findViewById(R.id.registration_button);
    }

    private boolean registration(String name, String login, String password)
    {
        Map<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("password", password);
        map.put("name", name);
        if(login.contains(" ") || password.contains(" "))
        {
           setSuccess(false);
            Toast.makeText(this, "В пароле и логине не должно быть пробелов", Toast.LENGTH_SHORT).show();
        }
        else
        {
            RegAuth.getInstance().requests().registration(map).enqueue(new Callback<JsonElement>()
            {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response)
                {
                    if(response.isSuccessful())
                    {
                        setSuccess(true);
                        Log.e("432", response.body().toString());
                        startActivity(new Intent(getApplicationContext(), AuthorizationActivity.class));
                        Toast.makeText(RegistrationActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        setSuccess(false);
                        Toast.makeText(RegistrationActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t)
                {
                    setSuccess(false);
                    Toast.makeText(RegistrationActivity.this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
                }
            });

        }

        return isSuccess;
    }

    private boolean checkIsEmpty(String name, String login, String password, String passwordAccept)
    {
        if(name.isEmpty() || login.isEmpty() || password.isEmpty() || passwordAccept.isEmpty())
        {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    private boolean checkPassword(String password, String passwordAccept)
    {
        if(password.equals(passwordAccept))
            return true;
        else
        {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void setSuccess(boolean result)
    {
        this.isSuccess = result;
    }

}
