package com.sangjun.mhp.peace;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PlaceDetailActivity extends AppCompatActivity {
    private Place place;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Intent intent = getIntent();
        final int index = intent.getIntExtra("index", -1);
        place = SplashActivity.placeList.get(index);

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

        viewFlipper = (ViewFlipper) findViewById(R.id.place_detail_flipper);

        setFlipperImage(place.getPictureLength());
        if(place.getPictureLength() <= 1) {

        } else {
            viewFlipper.setInAnimation(this, android.R.anim.fade_in);
            viewFlipper.setOutAnimation(this, android.R.anim.fade_out);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.startFlipping();
        }

        TextView titleText = (TextView) findViewById(R.id.place_detail_title);
        titleText.setText(place.getName());

        TextView timeText = (TextView) findViewById(R.id.place_detail_time);
        timeText.setText("최소 : " + place.getTime().getMin() + " 시간 , 최대 : " + place.getTime().getMax() + " 시간");

        TextView priceText = (TextView) findViewById(R.id.place_detail_price);
        priceText.setText("학생 : " + place.getPrice().getStudent() + " 원, 성인 : " + place.getPrice().getAdult() + " 원");

        TextView contentText = (TextView) findViewById(R.id.place_detail_content);

        Button reserveButton = (Button) findViewById(R.id.reserve_button);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.KAKAO_PROFILE == null) {
                    Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Intent it = new Intent(PlaceDetailActivity.this, ReserveActivity.class);
                    it.putExtra("index", index);
                    startActivity(it);
                }
            }
        });
    }

    private void setFlipperImage(int size) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReferenceFromUrl("gs://peace-c66f9.appspot.com");

        for(int i = 0; i < size; i++) {
            final ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            reference.child(place.getUrl() + "/0" + i + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(getApplicationContext()).load(uri).into(imageView);
                    viewFlipper.addView(imageView);
                }
            });
        }
    }





}