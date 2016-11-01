package com.example.korisnik.lnapp2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems = new ArrayList<>(100);
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Desription, Date;
        ImageView Thumbnail;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.title_text);
//            Desription = (TextView) itemView.findViewById(R.id.description_text);
            Date = (TextView) itemView.findViewById(R.id.date_text);
            Thumbnail = (ImageView) itemView.findViewById(R.id.thumb_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public MyAdapter(Context context, ArrayList<FeedItem> feedItems) {
        this.context=context;
        this.feedItems=feedItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custum_row_news_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
//        holder.Desription.setText(current.getDescription());
        holder.Date.setText(current.getPubDate());
        if(current.thumbnailUrl.get(0)==null) {
            holder.Thumbnail.setImageResource(R.drawable.test);
        } else {
            Picasso.with(context).load(current.thumbnailUrl.get(0)).resize((7*currentPx()),(4*currentPx())).centerCrop().into(holder.Thumbnail);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("naslov", current.getTitle());
                intent.putExtra("foto", current.thumbnailUrl.get(0));
                intent.putExtra("tekst", current.getDescription());
                intent.putStringArrayListExtra("list", current.thumbnailUrl);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {return feedItems.size();}


    public int currentPx() {
        int px1;
        px1 = context.getResources().getDisplayMetrics().widthPixels;
        px1 = px1/7;
        return px1;
    }
}
