package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class FullAdvertActivity extends AppCompatActivity
{
    private AdvertPOJO advertPOJO;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_advert);

        Bundle bundle = getIntent().getExtras();
        AdvertPOJO pojo = (AdvertPOJO) bundle.getSerializable("item");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView categoryEditText = findViewById(R.id.categoryTextView);
        TextView title = findViewById(R.id.titleTextView);
        TextView userName = findViewById(R.id.userTextView);
        TextView date = findViewById(R.id.dateTextView);
        TextView description = findViewById(R.id.descriptionTextView);
        TextView phone = findViewById(R.id.phoneTextView);
        ImageView advertImage = findViewById(R.id.imageView);
        TextView cost = findViewById(R.id.costTextView);

        this.advertPOJO = pojo;

        dbInit(getApplicationContext());
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM category WHERE id_category = %d;",  pojo.getCategoryId()),null);
        cursor.moveToFirst();
        do {
            Log.e("432", cursor.getString(1));
            categoryEditText.append(cursor.getString(1));
        }while (cursor.moveToNext());
        cursor.close();

        //cursor = db.rawQuery(String.format("SELECT * FROM type WHERE id_type =
        cursor = db.rawQuery(String.format("SELECT * FROM type WHERE id_type = %d;",  pojo.getTypeId()),null);
        cursor.moveToFirst();
        do {
            Log.e("432", cursor.getString(1));
            getSupportActionBar().setTitle("Тип объявления: " + cursor.getString(1));
        }while (cursor.moveToNext());

        phone.append(String.valueOf(pojo.getPhone_number()));
        title.setText(pojo.getTitle());
        userName.append(pojo.getUserName());
        date.append(pojo.getDate());
        cost.setText("" + pojo.getCost() + " Руб.");
        description.setText(pojo.getDescription());
        String path = pojo.getImagePath();

        try
        {
            //advertImage.setImageURI(Uri.parse(list.get(position).getImagePath())); ГАВНО
            Picasso.with(getApplicationContext()).
                    load("file://" + path)
                    .config(Bitmap.Config.ARGB_8888).into(advertImage);
        }
        catch (Exception e)
        {
            Log.e("432", e.toString());
        }
    }

    public void onClick(View view)
    {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + advertPOJO.getPhone_number())));
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

    public void dbInit(Context context)
    {
        this.databaseHelper = new DatabaseHelper(context);
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
