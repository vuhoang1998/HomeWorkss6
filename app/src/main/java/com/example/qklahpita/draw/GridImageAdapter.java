package com.example.qklahpita.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2/4/2018.
 */

public class GridImageAdapter extends BaseAdapter {
    Context context;
    List<ImageModel> imageModelList = ImageUltis.getListImage();


    public GridImageAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //1. Create image view
        ImageView imageView = new ImageView(context);

        imageView.setLayoutParams(
                new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, 400));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(5,5,5,5);
        imageView.setCropToPadding(true);


        //2.set image
        Bitmap bitmap = BitmapFactory.decodeFile(imageModelList.get(i).path);
        imageView.setImageBitmap(bitmap);

        return imageView;
    }
}
