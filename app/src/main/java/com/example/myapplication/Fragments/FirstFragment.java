package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapters.AdvertAdapter;
import com.example.myapplication.Networking.RegAuth;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FirstFragment extends Fragment
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
        View view = inflater.inflate(R.layout.first_fragment, null);
        list = new ArrayList();

        listView = view.findViewById(R.id.listView);
        searchButton = view.findViewById(R.id.search_button);
        searchText = view.findViewById(R.id.search_editText);
        adapter = new AdvertAdapter(getContext(), R.layout.advert_list_item, list);
        listView.setAdapter(adapter);
        final SQLiteDatabase db = getContext().openOrCreateDatabase("db.db", MODE_PRIVATE, null);

        cursor = db.rawQuery("SELECT * FROM adverts", null);

        if(!cursor.moveToFirst())
        {
            Log.e("432", "There is no adverts");
        }
           else
            {//(String title, String userName, String date, String description, int advertID) {
                do {
                    String userName = cursor.getString(6);
                    AdvertPOJO advertPOJO = new AdvertPOJO(cursor.getString(4), userName
                            , cursor.getString(2), cursor.getString(5), cursor.getInt(0) );
                    list.add(advertPOJO);
                    adapter.notifyDataSetChanged();

                    Log.e("432", "" + cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        db.close();
        cursor.close();

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
}
