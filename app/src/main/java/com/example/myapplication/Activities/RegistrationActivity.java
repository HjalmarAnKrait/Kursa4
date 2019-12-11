package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewsInit();
        this.db = getBaseContext().openOrCreateDatabase("db.db", MODE_PRIVATE, null);

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
        Cursor query = db.rawQuery("SELECT * FROM users WHERE login=" + "'"+login +  "'"+" and " + " password=" + "'"+ password+  "'"+";",null);
        if(!query.moveToFirst())
        {
            db.execSQL("INSERT INTO users (name, login, password) VALUES(" + "'" + name + "'," + "'" + login + "'," + "'" + password + "');");
            setSuccess(true);
            startActivity(new Intent(this, AuthorizationActivity.class));
            Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show();
            setSuccess(false);
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
