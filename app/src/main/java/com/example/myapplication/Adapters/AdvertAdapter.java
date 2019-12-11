package com.example.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.myapplication.POJO.AdvertPOJO;

import java.util.List;

public class AdvertAdapter extends ArrayAdapter<AdvertPOJO> {

    private List<AdvertPOJO> list;
    private LayoutInflater inflater;
    private int ListID;
    public AdvertAdapter(Context context, int resource, List<AdvertPOJO> objects)
    {
        super(context, resource, objects);
        list = objects;
        inflater = LayoutInflater.from(context);
        ListID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        @SuppressLint("ViewHolder") View v = inflater.inflate(ListID, parent, true);

        return v;
    }
}
