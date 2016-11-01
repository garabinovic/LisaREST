package com.example.korisnik.lnapp2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Korisnik on 27.6.2016..
 */
public class AbLisaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_ab_lisa);

        ImageView imageView = (ImageView) findViewById(R.id.AbLimageView);
        Picasso.with(this).load("http://www.lisanorden.com/wp-content/uploads/2009/10/stf-photoshoot.jpg").resize(800,0).into(imageView);

    }
}
