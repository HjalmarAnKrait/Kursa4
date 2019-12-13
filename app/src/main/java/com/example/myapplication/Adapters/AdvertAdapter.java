package com.example.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;

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
        View v = inflater.inflate(ListID, parent, false);
        TextView title = v.findViewById(R.id.titleTextView);
        TextView userName = v.findViewById(R.id.userTextView);
        TextView date = v.findViewById(R.id.dateTextView);
        TextView description = v.findViewById(R.id.descriptionTextView);
        title.setText(list.get(position).getTitle());
        userName.append(list.get(position).getUserName());
        date.append(list.get(position).getDate());
        description.setText(list.get(position).getDescription());

        return v;
    }
}
