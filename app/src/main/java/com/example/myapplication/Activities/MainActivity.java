package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Fragments.FirstFragment;
import com.example.myapplication.Fragments.SecondFragment;
import com.example.myapplication.Fragments.ThirdFragment;
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
                        setToolbarTitle("Все объявления");
                        break;

                    case R.id.second:
                        transaction.replace(containerId,secondFragment);
                        setToolbarTitle("Мои объявления");
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
       getMenuInflater().inflate(R.menu.toolbar_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //transaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        switch (id)
        {
            case R.id.exit_menu_button:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                finish();
                break;
            case R.id.about_menu_button:
                    startActivity(new Intent(getApplicationContext(), AuthorizationActivity.class));
                    break;
        }
        //transaction.addToBackStack(null);
        //transaction.commit();
        return true;

    }
}
