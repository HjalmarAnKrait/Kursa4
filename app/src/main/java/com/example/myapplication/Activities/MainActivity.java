package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Fragments.FirstFragment;
import com.example.myapplication.Fragments.SecondFragment;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private FragmentTransaction transaction;
    private BottomNavigationView bottomNavigationView;
    private int containerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerId = R.id.fragment;
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
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

                        break;
                        default:
                            transaction.replace(containerId, secondFragment);
                            //transaction.commit();
                            break;

                }
                transaction.addToBackStack(null);
                transaction.commit();
                return true;

            }
        });









    }
}
