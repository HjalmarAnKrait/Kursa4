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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends AppCompatActivity
{
    private EditText loginEditText, passwordEditText;
    private Button authButton, registrationButton;
    private boolean isSuccess = false;
    private SharedPreferences preferences;
    private SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        viewsInit();

        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        this.db = getBaseContext().openOrCreateDatabase("db.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id_user INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, login TEXT NOT NULL, password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS adverts(id_advert INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_user INTEGER NOT NULL, date TEXT NOT NULL ,category TEXT NOT NULL, title TEXT NOT NULL, description TEXT NOT NULL, imagePath TEXT)");

        db.execSQL("INSERT INTO users (name, login, password) VALUES('test1', 'test1', 'test1')");


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
        if(login.isEmpty() || password.isEmpty())
        {
            setSuccess(false);
        }
        else
        {
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE login=" + "'"+login +  "'"+" and " + " password=" + "'"+ password+  "'"+";",null);
            if(!cursor.moveToFirst())
            {
                setSuccess(false);
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Toast.makeText(this, "" + cursor.getInt(0), Toast.LENGTH_SHORT).show();cursor.getInt(1);
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("userId", cursor.getInt(0));
                editor.apply();

                cursor.close();
                db.close();

                setSuccess(true);

                Toast.makeText(this, "Вы успешно авторизовались", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
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
