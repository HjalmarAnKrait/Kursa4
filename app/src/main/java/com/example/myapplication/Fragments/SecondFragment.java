package com.example.myapplication.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapters.AdvertAdapter;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SecondFragment extends Fragment
{
    private ListView listView;
    private List<AdvertPOJO> list;
    private AdvertAdapter adapter;
    private ImageButton searchButton;
    private Cursor cursor;
    private EditText searchText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.second_fragment, null);
        list = new ArrayList();
        listView = view.findViewById(R.id.listView3);
        adapter = new AdvertAdapter(getContext(), R.layout.advert_list_item, list);
        listView.setAdapter(adapter);




        int userId = getActivity().getSharedPreferences("settings", MODE_PRIVATE).getInt("userId", 0);
        getAllUserAdverts(userId);



        return view;
    }

    public boolean getAllUserAdverts(int userId)
    {
        final SQLiteDatabase db = getContext().openOrCreateDatabase("db.db", MODE_PRIVATE, null);
        cursor = db.rawQuery("SELECT * FROM adverts WHERE id_user = " + userId, null);

        if(!cursor.moveToFirst())
        {
            Toast.makeText(getContext(), "У вас нет объявлений", Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();
            return false;
        }
        else
        {
            do {
                String userName = cursor.getString(6);
                AdvertPOJO advertPOJO = new AdvertPOJO(cursor.getString(4), userName
                        , cursor.getString(2), cursor.getString(5), cursor.getInt(0) );
                list.add(advertPOJO);
                adapter.notifyDataSetChanged();

                Log.e("432", "" + cursor.getInt(0));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
            return true;
        }
    }
}