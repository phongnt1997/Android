package com.phongnt.day10_pictureandmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryActivity extends AppCompatActivity {
private Integer[] imageIDs = {
  R.drawable.car_wash_01,
        R.drawable.car_wash_02,
        R.drawable.car_wash_03,
        R.drawable.logo,
        R.drawable.main_car,
        R.drawable.logout,
        R.drawable.main_motocycle,
};
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int itemBackground;

    public ImageAdapter(Context context) {
        this.context = context;
        TypedArray arr = this.context.obtainStyledAttributes(R.styleable.GalleryPhongNT);
        arr.recycle();
    }

    @Override
    public int getCount() {
        return imageIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setImageResource(imageIDs[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(200, 150));
        } else {
            imageView = (ImageView)convertView;
        }
        imageView.setBackgroundResource(itemBackground);
        return imageView;
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Gallery gallery = findViewById(R.id.galleryPhongPC);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GalleryActivity.this, "pic", Toast.LENGTH_SHORT).show();
                ImageView imageView = findViewById(R.id.imgView);
                imageView.setBackgroundResource(imageIDs[position]);
            }
        });

    }
}
