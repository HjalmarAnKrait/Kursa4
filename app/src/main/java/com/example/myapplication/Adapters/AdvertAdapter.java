package com.example.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DatabaseWork.DatabaseHelper;
import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class AdvertAdapter extends ArrayAdapter<AdvertPOJO> {

    private List<AdvertPOJO> list;
    private LayoutInflater inflater;
    private int ListID;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;


    public AdvertAdapter(Context context, int resource, List<AdvertPOJO> objects)
    {
        super(context, resource, objects);
        list = objects;
        inflater = LayoutInflater.from(context);
        ListID = resource;
        dbInit(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = inflater.inflate(ListID, parent, false);
        TextView title = v.findViewById(R.id.titleTextView);
        TextView category = v.findViewById(R.id.categoryTextView);
        TextView date = v.findViewById(R.id.dateTextView);
        TextView description = v.findViewById(R.id.descriptionTextView);
        ImageView advertImage = v.findViewById(R.id.imageView);
        TextView cost = v.findViewById(R.id.costTextView);
        TextView type = v.findViewById(R.id.typeTextView);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM type WHERE id_type = %d;",  list.get(position).getTypeId()),null);
        cursor.moveToFirst();
        do {
            Log.e("432", cursor.getString(1));
            type.setText(cursor.getString(1));
        }while (cursor.moveToNext());
        cursor.close();


        title.setText(list.get(position).getTitle());

        cursor = db.rawQuery(String.format("SELECT * FROM category WHERE id_category = %d;",  list.get(position).getCategoryId()),null);
        cursor.moveToFirst();
        do {
            Log.e("432", cursor.getString(1));
            category.append(cursor.getString(1));
        }while (cursor.moveToNext());
        cursor.close();


        date.append(list.get(position).getDate());
        cost.setText("" + list.get(position).getCost() + " Руб.");
        description.setText(list.get(position).getDescription());
        String path = list.get(position).getImagePath();
        cursor.close();

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
