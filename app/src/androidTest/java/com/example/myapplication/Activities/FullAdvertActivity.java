package com.example.myapplication.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.POJO.AdvertPOJO;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class FullAdvertActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_advert);

        Bundle bundle = getIntent().getExtras();
        AdvertPOJO pojo = (AdvertPOJO) bundle.getSerializable("item");

        TextView title = findViewById(R.id.titleTextView);
        TextView userName = findViewById(R.id.userTextView);
        TextView date = findViewById(R.id.dateTextView);
        TextView description = findViewById(R.id.descriptionTextView);
        ImageView advertImage = findViewById(R.id.imageView);
        TextView cost = findViewById(R.id.costTextView);
        title.setText(pojo.getTitle());
        userName.append(pojo.getUserName());
        date.append(pojo.getDate());
        cost.setText("" + pojo.getCost() + " Рублей");
        description.setText(pojo.getDescription());
        String path = pojo.getImagePath();
        try
        {
            //advertImage.setImageURI(Uri.parse(list.get(position).getImagePath())); ГАВНО
            Picasso.with(getApplicationContext()).
                    load("file://" + path)
                    .config(Bitmap.Config.ARGB_8888).into(advertImage);
        }
        catch (Exception e)
        {
            Log.e("432", e.toString());
        }
    }

    public void onClick(View view)
    {

    }

}
