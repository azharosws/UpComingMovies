package com.azhar.upcomingmovies;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.azhar.upcomingmovies.interfaces.ApiInterface;
import com.azhar.upcomingmovies.pojo.ImagesItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WIN 10 on 8/30/2017.
 */
public class MoviesDetail extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_label;
    ViewPager vp_images;
    LinearLayout ll_dots;
    TextView tv_title, tv_overview;
    RatingBar rb;
    String num_star, title, overview;
    int id = 0;
    String api_key = "b7cd3340a794e5a2f35e3abb820b497f";
    SliderAdapter sliderAdapter;
    List<ImagesItem> list;
    private TextView[] dots;
    int page_position = 0;
    int i;
    public static String MovieError = "Slider Error";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        init();
        setSupportActionBar(toolbar);
        tv_label.setText(R.string.information);
        list = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        title = bundle.getString("title");
        overview = bundle.getString("overview");
        num_star = bundle.getString("num_star");
        tv_title.setText(title);
        tv_overview.setText(overview);
        float num = Float.parseFloat(num_star);
        rb.setRating(num);
        addBottomDots(0);
        get_method();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == 4) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_images.setCurrentItem(page_position, true);
            }
        };

    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_label = (TextView) findViewById(R.id.tv_label);
        vp_images = (ViewPager) findViewById(R.id.vp_images);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rb = (RatingBar) findViewById(R.id.rb);
    }

    public void get_method() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigUrl.Movies_Url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ImagesItem> call = apiInterface.getImgMethod(id, api_key);
        call.enqueue(new Callback<ImagesItem>() {
            @Override
            public void onResponse(Call<ImagesItem> call, Response<ImagesItem> response) {

                list.add(response.body());
                for (i = 0; i < list.size(); i++) {
                    sliderAdapter = new SliderAdapter(MoviesDetail.this, list.get(i).getPosters());
                    System.out.println("list :- " + list.get(i).getPosters());
                    vp_images.setAdapter(sliderAdapter);
                }
                vp_images.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        addBottomDots(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ImagesItem> call, Throwable t) {

            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[5];
        System.out.println("currentPage :- " + currentPage);
        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            try {
                dots[currentPage].setTextColor(Color.parseColor("#FF0000"));
            } catch (Exception e) {
                Log.e(MovieError, "Error:-" + e);
                e.printStackTrace();
            }
        }
    }
}
