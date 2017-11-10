package com.sangjun.mhp.peace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    public static ArrayList<Banner> bannerList;
    public static ArrayList<Place> placeList;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.loading_bar);
        bannerList = new ArrayList<>();
        placeList = new ArrayList<>();

        SplashAsyncTask task = new SplashAsyncTask();
        task.execute();

    }

    private class SplashAsyncTask extends AsyncTask<Void, Void, ArrayList<Banner>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Banner> stringBannerMap) {
            super.onPostExecute(stringBannerMap);

            bannerList = stringBannerMap;

            mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference placeRef = mDatabase.getReference("places");
            placeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Place place = snapshot.getValue(Place.class);

                        placeList.add(place);
                    }

                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        @Override
        protected ArrayList<Banner> doInBackground(Void... params) {
            ArrayList<Banner> result = new ArrayList<>();

            try {
                Document document = Jsoup.connect("http://www.pyeongtaek.go.kr/main.do").get();
                Elements elements = document.select("#box_policy_container1 li");

                for(Element element : elements) {
                    Banner banner = new Banner();
                    banner.setImgSrc("http://www.pyeongtaek.go.kr" + element.select("img").attr("src"));
                    banner.setTitle(parseText(element.select(".txt h3").html()));
                    banner.setContent(parseText(element.select(".txt p").html()));
                    banner.setUrl(element.select(".txt a").attr("href"));

                    result.add(banner);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    private String parseText(String text) {
        StringBuffer buf = new StringBuffer();

        StringTokenizer token = new StringTokenizer(text, "<br>");
        while(token.hasMoreTokens()) {
            buf.append(token.nextToken() + "\n");
        }

        return buf + "";
    }
}
