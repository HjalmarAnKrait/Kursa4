package com.example.myapplication.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAdvertActivity extends AppCompatActivity
{
    private EditText titleEditText, descriptionEditText, categoryEditText, costEditText;
    private ImageView advertAddPhotoButton;
    private Button addAdvert;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        advertAddPhotoButton = findViewById(R.id.addPhotoButton);
        costEditText = findViewById(R.id.costEditText);
        advertAddPhotoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                makePhoto();
            }
        });


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



    }

    public void onClick(View view)
    {
        Calendar calendar = Calendar.getInstance();
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        String category = categoryEditText.getText().toString();
        int id_user = getSharedPreferences("settings", Context.MODE_PRIVATE).getInt("userId", 0);//тут возможна ошибка. проверь
        String userName = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("userName", "name");

        if(addAdvert(title, category, date, description, userName, id_user, Uri.parse(imagePath).getPath(), Integer.valueOf(costEditText.getText().toString()), db))
        {
            //startActivity(new Intent(getApplicationContext(), AddAdvertActivity.class));
            Toast.makeText(this, "Объявлениие успешно добавлено", Toast.LENGTH_SHORT).show();
            setResult(1);
            finish();
        }
        else
        {
            Toast.makeText(this, "Ошибка добавления объявления", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean addAdvert(String title, String category ,String date, String description, String userName, int id_user, String imagePath, int cost, SQLiteDatabase database)
    {
        if(title.isEmpty() || category.isEmpty() || date.isEmpty() ||  description.isEmpty() || userName.isEmpty() || imagePath.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
        }
        database.execSQL(String.format("INSERT INTO adverts(id_user, date, category, title, description, user_name, image_path, cost)" +
                " VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', %d);", id_user, date, category, title, description, userName, imagePath, cost));
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM adverts "),null);
        if (!cursor.moveToNext())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if(data == null)
            {
                Log.e("432", Uri.parse(imagePath).getPath());
                advertAddPhotoButton.setImageURI(Uri.parse(imagePath));
            }
        }

    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public void makePhoto()
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
            setImagePath(photodir1.toString());


        } catch (ActivityNotFoundException e)
        {
            // Выводим сообщение об ошибке
            String errorMessage = "Ваше устройство не поддерживает съемку";
            Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
