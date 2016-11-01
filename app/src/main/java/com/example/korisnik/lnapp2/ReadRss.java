package com.example.korisnik.lnapp2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;

public class ReadRss extends AsyncTask<Void,Void,Void> {

    Context context;

//    String address = "http://www.lisanorden.com/wp-json/wp/v2/posts?filter[posts_per_page]=-1";
    String address = "http://www.lisanorden.com/wp-json/wp/v2/posts?per_page=20";
    ProgressDialog progressDialog;
    ArrayList<FeedItem>feedItems = new ArrayList<>(100);
    RecyclerView recyclerView;
    URL url;

    public ReadRss(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter adapter = new MyAdapter(context,feedItems);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);



        recyclerView.setAdapter(adapter);



    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessJson(getJsonDoc(address));
        return null;
    }

    private void ProcessJson(JSONArray jsonArray) {
        if (jsonArray != null) {
            feedItems=new ArrayList<>(100);

            try {
                JSONArray json = new JSONArray(jsonArray.toString());
                for (int i = 0; i < json.length(); i++) {
                    FeedItem item=new FeedItem();
                    JSONObject jObj = json.getJSONObject(i);
                    String naslov = jObj.getJSONObject("title").getString("rendered");
                    item.setTitle(Html.fromHtml(naslov).toString());
                    item.setPubDate(jObj.optString("date").toString());

                    String siroviOpis = jObj.getJSONObject("content").getString("rendered");
                    String opis = Html.fromHtml(siroviOpis).toString();
                    opis = opis.replaceAll("(Photo)(.*?)(\n)",""); //brisanje atora fotografija
                    opis = opis.replaceAll("￼",""); //brisanje objekata dobijenih na mestu div-ova
                    opis = opis.replaceAll("(?m)^[ \t]*\r?\n", ""); // brisanje praznih redova




//                    String opis = new String(siroviOpis);

//                    opis = opis.replaceAll("&#8217;","'");
//                    opis = opis.replaceAll("&#8211;","-");
//                    opis = opis.replaceAll("&#8220;","\"");
//                    opis = opis.replaceAll("&#8221;","\"");
//                    opis = opis.replaceAll("&#8230;","...");
//                    opis = opis.replaceAll("<(.*?)\\>","");//Removes all items in brackets
//                    opis = opis.replaceAll("<(.*?)\\\n","");//Must be undeneath
//                    opis = opis.replaceFirst("(.*?)\\>", "");//Removes any connected item to the last bracket
//                    opis = opis.replaceAll("&nbsp;","");
//                    opis = opis.replaceAll("&amp;","");
//                    opis = opis.replaceAll("&hellip;","...");
//
//                    item.setDescription(opis);

                    item.setDescription(opis);

                    item.setLink(jObj.optString("link"));

                    int idV = jObj.getInt("id");
                    item.thumbnailUrl = getImageUrl(idV);


                    feedItems.add(item);

//                    Log.d("AGRB itemTitle", item.getTitle());
//                    Log.d("AGRB itemDescription",item.getDescription());
//                    Log.d("AGRB itemPubDate",item.getPubDate());
//                    Log.d("AGRB itemLink",item.getLink());
//                    Log.d("AGRB itemTumbnailUrl",item.thumbnailUrl.get(0));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private ArrayList<String> getImageUrl(Integer id) {
        List<String> listaImgUrl = new ArrayList<>();
        String address2 = "http://www.lisanorden.com/wp-json/wp/v2/media?parent=" + id;

        if (getJsonDoc(address2) != null) {
            try {
                JSONArray imageUrls = new JSONArray(getJsonDoc(address2).toString());
                for (int i = 0; i < imageUrls.length(); i++) {
                    JSONObject media = imageUrls.getJSONObject(i);
                    JSONObject md = media.getJSONObject("media_details");
                    JSONObject s = md.getJSONObject("sizes");
                    JSONObject m;
                    if(context.getResources().getDisplayMetrics().widthPixels>600){
                        m = s.getJSONObject("medium");
                    } else {
                        m = s.getJSONObject("thumbnail");
                    }
                    String aktUrl = m.getString("source_url").toString();
                    listaImgUrl.add(aktUrl);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return (ArrayList<String>) listaImgUrl;
    }


    public JSONArray getJsonDoc(String adr) {
        try {
            url = new URL(adr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
                }
            streamReader.close();
            String all = responseStrBuilder.toString();

            JSONArray jsonArray = new JSONArray(all);

            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
