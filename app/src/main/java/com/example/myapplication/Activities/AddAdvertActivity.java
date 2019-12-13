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
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Calendar;

public class AddAdvertActivity extends AppCompatActivity
{
    EditText titleEditText, descriptionEditText, categoryEditText;
    Button addAdvert;
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
        String userName = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("userName", "null");

        if(addAdvert(title, category, date, description, userName, id_user))
        {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public boolean addAdvert(String title, String category ,String date, String description, String userName, int id_user)
    {
        try
        {
            if(title.isEmpty() || category.isEmpty() || date.isEmpty() ||  description.isEmpty() || userName.isEmpty())
            {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                return false;
            }
            String sql = "INSERT INTO adverts(id_user, date, category, title, description, userName)" +
                    " VALUES(" + id_user +", '"+date + "' ,"+ "'"+category + "' ,"+"'"+title + "' ,"+"'"+description + "'" + ",'" + userName + "'" +")";
            Log.e("432", sql);

            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("db.db", MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM adverts WHERE description = '" + description +"'", null);
            if(!cursor.moveToFirst())
            {
                db.execSQL(sql);
                db.close();
                Toast.makeText(this, "Ошибка добавления объявления", Toast.LENGTH_SHORT).show();

                return  false;

            }
            else
            {
                Toast.makeText(this, "Объявление успешно добавлено", Toast.LENGTH_SHORT).show();
                db.execSQL(sql);
                db.close();
                return  true;
            }
        }
        catch (Exception e)
        {
            Log.e("432", e.toString());
            return false;
        }

    }
}
