package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeAdvertActivity extends AppCompatActivity
{
    private AdvertPOJO advertPOJO;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private String imagePath;
    private ImageView advertImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_advert);

        Bundle bundle = getIntent().getExtras();
        final AdvertPOJO pojo = (AdvertPOJO) bundle.getSerializable("item");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView categoryEditText = findViewById(R.id.categoryTextView);
        final EditText title = findViewById(R.id.titleTextView);
        TextView userName = findViewById(R.id.userTextView);
        TextView date = findViewById(R.id.dateTextView);
        final EditText description = findViewById(R.id.descriptionTextView);
        final EditText phone = findViewById(R.id.phoneTextView);
        setImagePath(pojo.getImagePath());

        advertImage = findViewById(R.id.imageView);

        final EditText cost = findViewById(R.id.costTextView);

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

        Button button = findViewById(R.id.change_button);
        advertImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                makePhoto();
            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(title.getText().toString().isEmpty() || cost.getText().toString().isEmpty() || phone.getText().toString().isEmpty())
                {
                    Toast.makeText(ChangeAdvertActivity.this, "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
                }

                if(imagePath.isEmpty())
                {
                    setImagePath(pojo.getImagePath());
                }
                else
                {

                    @SuppressLint
                            ("DefaultLocale") String sql = String.format("UPDATE adverts SET" +
                                    " title = '%s', description = '%s', image_path= '%s', cost= %d, phone_number= %d" +
                                    " WHERE id_advert = %d;"
                            ,title.getText().toString(),
                            description.getText().toString(),
                            getImagePath(),
                            Integer.valueOf(cost.getText().toString().replaceAll("Руб.", "").trim()),
                            Integer.valueOf(phone.getText().toString().replaceAll("Телефон:.", "").trim()),
                            pojo.getAdvertID());
                    Log.e("432", sql);
                    db.execSQL(sql);
                    finish();
                }


            }
        });
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
                advertImage.setImageURI(Uri.parse(imagePath));
            }
        }

    }
}
