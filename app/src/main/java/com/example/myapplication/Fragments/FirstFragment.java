package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapters.AdvertAdapter;
import com.example.myapplication.Networking.RegAuth;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment
{
   private ListView listView;
   private List<RegAuth> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.first_fragment, null);
        list = new ArrayList();

        listView = view.findViewById(R.id.listView);
        AdvertAdapter adapter = new AdvertAdapter(this, R.layout.advert_list_item, list)



        return view;
    }
}
