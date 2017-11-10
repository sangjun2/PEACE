package com.sangjun.mhp.peace;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BannerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_detail);

        Intent intent = getIntent();
        String index = intent.getStringExtra("index");

        final Banner banner = SplashActivity.bannerList.get(Integer.parseInt(index));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        Button closeButton = (Button) findViewById(R.id.toolbar_bt);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("상세보기");

        ImageView image = (ImageView) findViewById(R.id.detail_image);
        Picasso.with(this).load(banner.getImgSrc()).into(image);

        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText(banner.getTitle());

        TextView content = (TextView) findViewById(R.id.detail_content);
        content.setText(banner.getContent());

        Button link = (Button) findViewById(R.id.detail_link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BannerDetailActivity.this, WebViewActivity.class);
                intent.putExtra("url", banner.getUrl());
                startActivity(intent);
            }
        });
    }
}
