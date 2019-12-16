package com.example.myapplication.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.FullAdvertActivity;
import com.example.myapplication.Adapters.AdvertAdapter;
import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment
{
   private ListView listView;
   private List<AdvertPOJO> list;
   private AdvertAdapter adapter;
   private ImageButton searchButton;
   private EditText searchText;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.first_fragment, null);
        list = new ArrayList();

        listView = view.findViewById(R.id.listView);
        searchButton = view.findViewById(R.id.search_button);
        searchText = view.findViewById(R.id.search_editText);
        adapter = new AdvertAdapter(getContext(), R.layout.advert_list_item, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getContext(), FullAdvertActivity.class);
                intent.putExtra("item", list.get(position));
                startActivityForResult(intent,1);
            }
        });

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

        if(isAvaliableAdverts(db))



        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String query = searchText.getText().toString();
                if(!query.isEmpty())
                {
                    search(query);
                }
                else
                {
                    AdvertAdapter adapter1 = new AdvertAdapter(getContext(), R.layout.advert_list_item, list);
                    listView.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                }
            }
        });



        return view;
    }

    public  boolean search(String query)
    {
        boolean succes = false;

        List<AdvertPOJO> pojoList = new ArrayList();

            for(int i = 0; i < list.size(); i++)
            {
                AdvertPOJO advertPOJO = list.get(i);
                if(advertPOJO.getDescription().contains(query) || advertPOJO.getTitle().contains(query))
                {
                  pojoList.add(advertPOJO);
                    //list.add(advertPOJO);
                    Log.e("432", advertPOJO.toString());
                }
            }
            if(pojoList.isEmpty())
            {
                Toast.makeText(getContext(), "По вашему запросу ничего не найдено", Toast.LENGTH_SHORT).show();
                Log.e("432", "empty");
                AdvertAdapter adapter1 = new AdvertAdapter(getContext(), R.layout.advert_list_item, list);
                listView.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                return false;
            }
            else
                {
                    AdvertAdapter adapter1 = new AdvertAdapter(getContext(), R.layout.advert_list_item, pojoList);
                    listView.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                    list = pojoList;
                    adapter1.notifyDataSetChanged();
                    return true;
            }


    }

    public boolean isAvaliableAdverts(SQLiteDatabase database)
    {
        Cursor cursor = database.rawQuery("SELECT * FROM adverts", null);
        if(!cursor.moveToFirst())
        {
            Log.e("432", "There is no adverts");
            Toast.makeText(getContext(), "Список объявлений пуст", Toast.LENGTH_SHORT).show();
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
                        cursor.getInt(8));
                list.add(advertPOJO);
                adapter.notifyDataSetChanged();

                Log.e("432", "" + cursor.getInt(0));
            }while (cursor.moveToNext());
            cursor.close();
            return true;
        }

    }

}
