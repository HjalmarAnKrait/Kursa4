package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.AddAdvertActivity;
import com.example.myapplication.Activities.AuthorizationActivity;
import com.example.myapplication.Activities.DeleteAdvertActivity;
import com.example.myapplication.Activities.SelectChangeAdvertActivity;
import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.R;

import java.io.IOException;

public class ThirdFragment extends Fragment
{
    private Button addAdvertButton, deleteAdvertButton, changeAdvertButton, exitButton;
    private ImageView imageView;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.third_fragment, null);
        imageView = view.findViewById(R.id.profile_picture);
        deleteAdvertButton = view.findViewById(R.id.deleteAdvertButton);
        changeAdvertButton = view.findViewById(R.id.changeAdvertButton);

        dbInit(getContext());

        changeAdvertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), SelectChangeAdvertActivity.class));
            }
        });

        deleteAdvertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getContext(), DeleteAdvertActivity.class), 1);
            }
        });

        preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String path = preferences.getString("path", "null");
        try {
            Log.e("432", "profile pic uri" + path);
            imageView.setImageURI(Uri.parse(path));
        }
        catch (Exception e)
        {
            imageView.setImageResource(android.R.drawable.stat_notify_error);
        }
        databaseHelper = new DatabaseHelper(getContext());
        try
        {
            databaseHelper.updateDataBase();
        }
        catch (IOException e)
        {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            db = databaseHelper.getWritableDatabase();
        }
        catch (SQLException e)
        {
            throw e;
        }



        addAdvertButton = view.findViewById(R.id.addAdvertButton);
        exitButton = view.findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AuthorizationActivity.class));
                getActivity().finish();
            }
        });
        addAdvertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getContext(), AddAdvertActivity.class), 1);
            }
        });
        return view;


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
