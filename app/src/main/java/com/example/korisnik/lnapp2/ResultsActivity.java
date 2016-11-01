package com.example.korisnik.lnapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Korisnik on 27.6.2016..
 */
public class ResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_results);

        ImageView imageView = (ImageView) findViewById(R.id.WinImageView);
        Picasso.with(this).load("http://www.lisanorden.com/wp-content/uploads/2014/02/DEL_48431__medium-600x399.jpg").resize(800,0).into(imageView);

    }
}
