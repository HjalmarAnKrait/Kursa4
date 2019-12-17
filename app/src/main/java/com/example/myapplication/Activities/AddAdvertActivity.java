package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddAdvertActivity extends AppCompatActivity
{
    private EditText titleEditText, descriptionEditText, costEditText, phoneNumberEditText;
    private Spinner spinner;
    private ImageView advertAddPhotoButton;
    private Button addAdvert;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private String imagePath;
    private String category;
    private String type;
    private int categoryId = 0;
    private int counter = 0;
    private int typeId = 0;
    private Spinner typeSpinner;
    private List<Integer>  categoryIdList = new ArrayList();
    private List<Integer> typeList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        dbInit();
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        //categoryEditText = findViewById(R.id.categoryEditText); // заменить спиннером
        advertAddPhotoButton = findViewById(R.id.addPhotoButton);
        costEditText = findViewById(R.id.costEditText);
        phoneNumberEditText = findViewById(R.id.phoneEditText);
        spinner = findViewById(R.id.categorySpinner);
        typeSpinner = findViewById(R.id.typeSpinner);



        advertAddPhotoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(getCounter() <= 0)
                {
                    makePhoto();
                    setCounter(++counter);
                }
                else
                {
                    Snackbar.make(v, "Вы уверены, что хотите сменить фото?", Snackbar.LENGTH_LONG).setAction("Да", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            makePhoto();
                        }
                    }).show();
                }

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getCategoryList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getTypeList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(adapter1);
        typeSpinner.setSelection(2);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                setTypeId(typeList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);
        spinner.setSelection(5);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                setCategoryId(categoryIdList.get(position));
                Toast.makeText(AddAdvertActivity.this, "" + categoryIdList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setCategory(spinner.getSelectedItem().toString());






    }

    public void onClick(View view)
    {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        int id_type = 0;
        Integer phoneNumber = Integer.valueOf(phoneNumberEditText.getText().toString());

        //String category = categoryEditText.getText().toString();

        int id_user = getSharedPreferences("settings", Context.MODE_PRIVATE).getInt("userId", 0);//тут возможна ошибка. проверь
        String userName = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("userName", "name");

        if(counter > 0)
        {
            if(addAdvert(title, getCategoryId(), date, description, userName, id_user, Uri.parse(imagePath).getPath(), Integer.valueOf(costEditText.getText().toString()),phoneNumber, getTypeId(), db) && counter >0)
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
        else
        {
            Toast.makeText(this, "У объявления обязательно должно быть фото!", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean addAdvert(String title, int id_category ,String date, String description, String userName, int id_user, String imagePath, int cost, int phoneNumber,int id_type, SQLiteDatabase database)
    {
        if(title.isEmpty()  || date.isEmpty() ||  description.isEmpty() || userName.isEmpty() || imagePath.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
        }
        database.execSQL(String.format("INSERT INTO adverts(id_user, date, id_category, title, description, user_name, image_path, cost, phone_number, id_type)" +
                " VALUES (%d, '%s', '%d', '%s', '%s', '%s', '%s', %d, %d, %d);", id_user, date, id_category, title, description, userName, imagePath, cost, phoneNumber, id_type));
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM adverts WHERE id_user = %d;", id_user),null);
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public List<String> getCategoryList()
    {
        List<String> categoryList = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM category;", null);
        cursor.moveToFirst();
        do {
            categoryList.add(cursor.getString(1));
            this.categoryIdList.add(cursor.getInt(0));
        }while (cursor.moveToNext());
        cursor.close();
        return categoryList;
    }

    public List<String> getTypeList()
    {
        List<String> typeList = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM type;", null);
        cursor.moveToFirst();
        do {
            typeList.add(cursor.getString(1));
            this.typeList.add(cursor.getInt(0));
        }while (cursor.moveToNext());
        cursor.close();
        return typeList;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
