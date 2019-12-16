package com.example.myapplication.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapters.AdvertAdapter;
import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SecondFragment extends Fragment
{
    private ListView listView;
    private List<AdvertPOJO> list;
    private AdvertAdapter adapter;
    private Cursor cursor;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.second_fragment, null);
        list = new ArrayList();
        listView = view.findViewById(R.id.listView3);
        adapter = new AdvertAdapter(getContext(), R.layout.advert_list_item, list);
        listView.setAdapter(adapter);
        dbInit(getContext());

        int userId = getActivity().getSharedPreferences("settings", MODE_PRIVATE).getInt("userId", 0);


        getAllUserAdverts(userId, db);




        return view;
    }

    public boolean getAllUserAdverts(int userId, SQLiteDatabase database)
    {
        cursor = database.rawQuery(String.format("SELECT * FROM adverts WHERE id_user=%d;",userId), null);

        if(!cursor.moveToFirst())
        {
            Toast.makeText(getContext(), "У вас нет объявлений", Toast.LENGTH_SHORT).show();
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
}