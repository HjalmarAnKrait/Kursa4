package com.example.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

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
        ImageView advertImage = v.findViewById(R.id.imageView);
        title.setText(list.get(position).getTitle());
        userName.append(list.get(position).getUserName());
        date.append(list.get(position).getDate());
        description.setText(list.get(position).getDescription());
        String path = list.get(position).getImagePath();
        try
        {
            //advertImage.setImageURI(Uri.parse(list.get(position).getImagePath())); ГАВНО
            Picasso.with(v.getContext()).
                    load("file://" + path)
                    .config(Bitmap.Config.ARGB_8888).into(advertImage);
        }
        catch (Exception e)
        {
            Log.e("432", e.toString());
        }


        return v;
    }
}
