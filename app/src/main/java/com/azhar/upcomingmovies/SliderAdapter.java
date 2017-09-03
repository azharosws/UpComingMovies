package com.azhar.upcomingmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azhar.upcomingmovies.pojo.Poster;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by WIN 10 on 8/30/2017.
 */
public class SliderAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    List<Poster> image_arraylist;

    public SliderAdapter(Activity activity, List<Poster> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_item, container, false);
        ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
        Picasso.with(activity.getApplicationContext())
                .load("https://image.tmdb.org/t/p/w342/" + image_arraylist.get(position).getFilePath())
                .into(iv_image);

        System.out.println("image :- " + image_arraylist.get(position).getFilePath());
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        if (image_arraylist.size() < 2) {
            return 1;
        } else if (image_arraylist.size() < 3) {
            return 2;
        } else if (image_arraylist.size() < 4) {
            return 3;
        } else if (image_arraylist.size() < 5) {
            return 4;
        } else {
            return 5;
        }
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
