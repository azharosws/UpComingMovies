package com.azhar.upcomingmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.azhar.upcomingmovies.interfaces.ApiInterface;
import com.azhar.upcomingmovies.pojo.MoviesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_label;
    EditText et_title;
    RecyclerView rv_movies;
    MoviesAdapter adapter;
    List<MoviesItem> list;
    RecyclerView.LayoutManager layoutManager;
    String api_key = "b7cd3340a794e5a2f35e3abb820b497f";
    Call<MoviesItem> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setSupportActionBar(toolbar);
        list = new ArrayList<>();
        get_movies_list();
        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String title=et_title.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(title);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_label = (TextView) findViewById(R.id.tv_label);
        rv_movies = (RecyclerView) findViewById(R.id.rv_movies);
        et_title = (EditText) findViewById(R.id.et_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void get_movies_list() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigUrl.Movies_Url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        call = apiInterface.getMethod(api_key);
        System.out.println("call :- " + call);
        call.enqueue(new Callback<MoviesItem>() {
            @Override
            public void onResponse(Call<MoviesItem> call, Response<MoviesItem> response) {
                System.out.println("response :- " + response.body());
                list.add(response.body());
                for (int i = 0; i < list.size(); i++) {
                    adapter = new MoviesAdapter(getApplicationContext(), list.get(i).getResults());
                }
                layoutManager = new LinearLayoutManager(getApplicationContext());
                rv_movies.setLayoutManager(layoutManager);
                rv_movies.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesItem> call, Throwable t) {

            }
        });
    }
}
