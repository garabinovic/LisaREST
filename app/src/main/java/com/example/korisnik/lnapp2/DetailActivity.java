package com.example.korisnik.lnapp2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 28.6.2016..
 */
public class DetailActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String naslov = intent.getStringExtra("naslov");
        String foto = intent.getStringExtra("foto");
        String tekst = intent.getStringExtra("tekst");
        ArrayList lista = intent.getStringArrayListExtra("list");




        TextView tv = (TextView) findViewById(R.id.title_text);
        tv.setText(naslov);

        tv = (TextView) findViewById(R.id.description_text);
        tv.setText(tekst);

        ImageView iv = (ImageView) findViewById(R.id.thumb_img);

        Picasso.with(this).load(foto).resize(getResources().getDisplayMetrics().widthPixels,0).into(iv);

        createImageView(lista);

    }

    public void createImageView(ArrayList arrayList) {
        LinearLayout linearLayoutSlike = (LinearLayout) findViewById(R.id.dodatne_slike);
        if(arrayList.size()>1) {
            for (int i = 1; i < arrayList.size(); i++) {
                ImageView newImageView = (ImageView) getLayoutInflater().inflate(R.layout.add_image, null);
                Picasso.with(this).load(String.valueOf(arrayList.get(i))).resize(getResources().getDisplayMetrics().widthPixels, 0).into(newImageView);
                linearLayoutSlike.addView(newImageView);
            }
        }
    }

}
