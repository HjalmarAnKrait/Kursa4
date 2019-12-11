package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Fragments.FirstFragment;
import com.example.myapplication.Fragments.SecondFragment;
import com.example.myapplication.Fragments.ThirdFragment;

import com.example.myapplication.Networking.RegAuth;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    private FragmentTransaction transaction;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private int containerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        containerId = R.id.fragment;

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, firstFragment);
        transaction.commit();

        RegAuth.getInstance().requests().authorization("gf", "gf").enqueue(new Callback<JsonElement>()
        {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response)
            {

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });




        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                transaction = getSupportFragmentManager().beginTransaction();
                int id = menuItem.getItemId();
                switch (id)
                {
                    case R.id.first:
                        transaction.replace(containerId, firstFragment);
                        setToolbarTitle("Просмотр по категориям");
                        break;
                    case R.id.second:
                        transaction.replace(containerId,secondFragment);
                        setToolbarTitle("Поиск по категориям");
                        break;

                        default:
                            transaction.replace(containerId, thirdFragment);
                            setToolbarTitle("Мой профиль");
                            break;

                }
                transaction.addToBackStack(null);
                transaction.commit();
                return true;

            }
        });

    }


    public void setToolbarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
       getMenuInflater().inflate(R.menu.bottom_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        transaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();
        switch (id)
        {

        }
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }
}
