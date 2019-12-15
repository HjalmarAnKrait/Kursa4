package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.AddAdvertActivity;
import com.example.myapplication.R;

public class ThirdFragment extends Fragment
{
    private Button addAdvertButton, deleteAdvertButton, changeAdvertButton, exitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.third_fragment, null);
        addAdvertButton = view.findViewById(R.id.addAdvertButton);
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
}
