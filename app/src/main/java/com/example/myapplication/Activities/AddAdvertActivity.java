package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.Calendar;

public class AddAdvertActivity extends AppCompatActivity
{
    EditText titleEditText, descriptionEditText, categoryEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        categoryEditText = findViewById(R.id.categoryEditText);



    }

    public void onClick(View view)
    {
        Calendar calendar = Calendar.getInstance();
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String date = ""+calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR);
        String category = categoryEditText.getText().toString();
        int id_user = getSharedPreferences("settings", Context.MODE_PRIVATE).getInt("userId", 0);//тут возможна ошибка. проверь


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("db.db", MODE_PRIVATE, null);
        db.execSQL("INSERT INTO adverts(id_user, date, category, title, description)" +
                " VALUES(" +id_user +", '"+date + "' ,"+ ", '"+category + "' ,"+", '"+title + "' ,"+", '"+description + "'" +")"); //ДОДЕЛАЙ, ДОЛБАЁБ. СХЕМА НАХОДИТСЯ В ВК
        db.close();
    }
}
