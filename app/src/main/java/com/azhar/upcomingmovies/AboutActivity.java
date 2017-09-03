package com.azhar.upcomingmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by WIN 10 on 8/30/2017.
 */
public class AboutActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    TextView tv_label;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
        setSupportActionBar(toolbar);

        tv_label.setText(R.string.information);
    }
    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_label = (TextView) findViewById(R.id.tv_label);
    }
}
