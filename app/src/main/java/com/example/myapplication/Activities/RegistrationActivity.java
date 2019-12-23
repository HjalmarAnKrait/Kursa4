package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity
{
    private EditText loginEditText, passwordEditText, passwordAcceptEditText, nameEditText;
    private Button registrationButton;
    private boolean isSuccess = false;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private String path = "";




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewsInit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Регистрация");

        databaseHelper = new DatabaseHelper(this);
        try
        {
            databaseHelper.updateDataBase();
        }
        catch (IOException e)
        {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            db = databaseHelper.getWritableDatabase();
        }
        catch (SQLException e)
        {
            throw e;
        }

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
               if(checkIsEmpty(name, login, password, passwordAccept, path) && checkPassword(password, passwordAccept))
               {
                   registration(name, login, password, path);
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

    public boolean registration(String name, String login, String password, String path1)
    {
        String sql = String.format("SELECT * FROM users WHERE login='%s'",login);
        Cursor query = db.rawQuery(sql,null);
        if(!query.moveToFirst())
        {
            String sqlInsert = String.format("INSERT INTO users (name, login, password, profile_image) VALUES ('%s','%s','%s', '%s')",name, login, password, path1);
            db.execSQL(sqlInsert);
            setSuccess(true);
            setResult(1);
            Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
            query.close();
            finish();
        }
        else
        {
            Toast.makeText(this,"Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show();
            setSuccess(false);
        }
        return isSuccess;
    }

    public boolean checkIsEmpty(String name, String login, String password, String passwordAccept, String path1)
    {
        if(name.isEmpty() || login.isEmpty() || password.isEmpty() || passwordAccept.isEmpty() || path1.isEmpty())
        {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    public boolean checkPassword(String password, String passwordAccept)
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


    public void takePhoto(View view)
    {
        try {
            // Намерение для запуска камеры
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // название из даты

            File file = new File(Environment.getExternalStorageDirectory(),timeStamp+".png");
            Uri photodir1 = Uri.fromFile(file);

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photodir1);
            startActivityForResult(captureIntent, 1);

            // записываю путь в String photodir
            setPath(photodir1.toString());
            SharedPreferences.Editor editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
            editor.putString("path", photodir1.toString());
            editor.apply();


        } catch (ActivityNotFoundException e)
        {
            // Выводим сообщение об ошибке
            String errorMessage = "Ваше устройство не поддерживает съемку";
            Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if(data == null)
            {
                ImageView imageView = findViewById(R.id.userPhoto);
                Log.e("432", Uri.parse(getPath()).getPath());
                imageView.setImageURI(Uri.parse(getPath()));
            }
        }

    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
}
