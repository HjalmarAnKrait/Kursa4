package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.Calendar;

public class AddAdvertActivity extends AppCompatActivity
{
    private EditText titleEditText, descriptionEditText, categoryEditText;
    private ImageButton advertAddPhotoButton;
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



    }

    public void onClick(View view)
    {
        Calendar calendar = Calendar.getInstance();
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String date = ""+calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR);
        String category = categoryEditText.getText().toString();
        int id_user = getSharedPreferences("settings", Context.MODE_PRIVATE).getInt("userId", 0);//тут возможна ошибка. проверь
        String userName = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("userName", "name");

        if(addAdvert(title, category, date, description, userName, id_user, imagePath, db))
        {
            startActivity(new Intent(getApplicationContext(), AddAdvertActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "Ошибка добавления объявления", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean addAdvert(String title, String category ,String date, String description, String userName, int id_user, String imagePath, SQLiteDatabase database)
    {
        if(title.isEmpty() || category.isEmpty() || date.isEmpty() ||  description.isEmpty() || userName.isEmpty() || imagePath.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
        }
        database.execSQL(String.format("INSERT INTO adverts(id_user, date, category, title, description, user_name, image_path)" +
                " VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s'", id_user, date, category, title, description, userName, imagePath));
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
}
