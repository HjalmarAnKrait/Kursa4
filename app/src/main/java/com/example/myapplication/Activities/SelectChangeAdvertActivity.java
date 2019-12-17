package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapters.AdvertAdapter;
import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectChangeAdvertActivity extends AppCompatActivity
{

    private ListView listView;
    private List<AdvertPOJO> list;
    private AdvertAdapter adapter;
    private Cursor cursor;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_change_advert);
        dbInit(getApplicationContext());
        list = new ArrayList();
        listView = findViewById(R.id.listView3);
        int userId = getSharedPreferences("settings", MODE_PRIVATE).getInt("userId", 0);
        adapter = new AdvertAdapter(getApplicationContext(), R.layout.advert_list_item, list);
        listView.setAdapter(adapter);
        getAllUserAdverts(userId, db);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Редактирование объявления");





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final AdvertPOJO advertPOJO = list.get(position);
                Intent intent = new Intent(getApplicationContext(), ChangeAdvertActivity.class);
                intent.putExtra("item", list.get(position));
                startActivity(intent);
            }
        });




    }



    public boolean getAllUserAdverts(int userId, SQLiteDatabase database)
    {
        cursor = database.rawQuery(String.format("SELECT * FROM adverts WHERE id_user=%d;",userId), null);

        if(!cursor.moveToFirst())
        {
            Toast.makeText(getApplicationContext(), "У вас нет объявлений", Toast.LENGTH_SHORT).show();
            cursor.close();
            return false;
        }
        else
        {
            do {
                String userName = cursor.getString(6);
                AdvertPOJO advertPOJO = new AdvertPOJO(
                        cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getInt(3));
                list.add(advertPOJO);
                adapter.notifyDataSetChanged();

                Log.e("432", "" + cursor.getInt(0));
            }while (cursor.moveToNext());
            cursor.close();
            return true;
        }
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
}
