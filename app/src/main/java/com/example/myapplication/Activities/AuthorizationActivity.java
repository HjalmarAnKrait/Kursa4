package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthorizationActivity extends AppCompatActivity
{
    private EditText loginEditText, passwordEditText;
    private Button authButton, registrationButton;
    private boolean isSuccess = false;
    private SharedPreferences preferences;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        viewsInit();
        dbInit();

        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        Log.e("432", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));



        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED)
        {

        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }

        authButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(auth(loginEditText.getText().toString(), passwordEditText.getText().toString(), db))
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getApplicationContext(), RegistrationActivity.class),1);
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




    public boolean auth(final String login, final String password, SQLiteDatabase database)
    {
        if(login.isEmpty() || password.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            setSuccess(false);
        }
        else
        {
            Cursor cursor = database.rawQuery(String.format("SELECT * FROM users WHERE login = '%s' and password = '%s';", login, password),null);
            Log.e("432", String.format("SELECT * FROM users WHERE login = '%s' and password = '%s';", login, password));
            if(!cursor.moveToFirst())
            {
                setSuccess(false);
                Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                cursor.close();
            }
            else
            {
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("userId", cursor.getInt(0));
                editor.putString("userName", login);
                editor.apply();

                cursor.close();
                setSuccess(true);

                Toast.makeText(getApplicationContext(), "Вы успешно авторизовались", Toast.LENGTH_SHORT).show();

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

    public void dbInit()
    {
        this.databaseHelper = new DatabaseHelper(this);
        try
        {
            this.databaseHelper.updateDataBase();
        }
        catch (IOException e)
        {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            this.db = databaseHelper.getWritableDatabase();
        }
        catch (SQLException e)
        {
            throw e;
        }
    }


}
