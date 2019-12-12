package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

public class AddAdvertActivity extends AppCompatActivity
{
    EditText titleEditText, descriptionEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);



    }

    public void onClick(View view)
    {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("db.db", MODE_PRIVATE, null);
        //db.execSQL("INSERT INTO adverts(description, id_user, )"); ДОДЕЛАЙ, ДОЛБАЁБ. СХЕМА НАХОДИТСЯ В ВК

    }
}
