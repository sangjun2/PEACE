package com.sangjun.mhp.peace;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.HashMap;
import java.util.Map;


public class ReserveActivity extends AppCompatActivity implements ReserveCalendarFragment.OnFragmentInteractionListener, ReserveEtcFragment.OnFragmentInteractionListener, ReserveCompletedFragment.OnFragmentInteractionListener, ReserveFinishFragment.OnFragmentInteractionListener {
    public static int FRAGMENT_INDEX;
    public static int FRAGMENT_SIZE = 4;

    public static Place PLACE;

    public LinearLayout prevButton;
    public ProgressBar bar;
    public LinearLayout nextButton;

    public ReserveDate selectedDate;
    public ReserveUser reserveUser;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ReserveEtcFragment etcFragment;
    ReserveCompletedFragment completedFragment;

    public int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        PLACE = SplashActivity.placeList.get(index);

        FRAGMENT_INDEX = 0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.reserve_detail_bar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Button bt = (Button) toolbar.findViewById(R.id.toolbar_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("예약하기");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.reserve_fragment, ReserveCalendarFragment.newInstance(index));
        fragmentTransaction.commit();

        prevButton = (LinearLayout) findViewById(R.id.stepper_prev);
        prevButton.setVisibility(View.GONE);
        prevButton.setOnClickListener(prevFragmentListener);

        bar = (ProgressBar) findViewById(R.id.stepper_progress);
        bar.setProgress(25);

        nextButton = (LinearLayout) findViewById(R.id.stepper_next);
        nextButton.setOnClickListener(nextFragmentListener);
    }

    @Override
    public void onCalendarSelected(CalendarDay date, int min, int max) {
        selectedDate = new ReserveDate(date, min, max);
    }

    @Override
    public void onEtcCompleted(ReserveUser user) {
        reserveUser = user;
    }

    @Override
    public void onReservePlace(Reservation reservation) {
        ProgressBar loadingBar = (ProgressBar) findViewById(R.id.reserve_place_progress);
        loadingBar.setVisibility(View.VISIBLE);

        String in = index > 9 ? String.valueOf(index) : "0" + String.valueOf(index);
        String date = String.valueOf(selectedDate.getDate().getCalendar().getTimeInMillis());

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference();

        Map<String, Object> childUpdates = new HashMap<>();
        if(selectedDate.getMax() - selectedDate.getMin() == 0) {
            childUpdates.put("places/" + in + "/reservation/" + date + "/" + String.valueOf(selectedDate.getMax()) + "/", MainActivity.KAKAO_PROFILE.getId());
        } else {
            childUpdates.put("places/" + in + "/reservation/" + date + "/" + String.valueOf(selectedDate.getMax()) + "/", MainActivity.KAKAO_PROFILE.getId());
            childUpdates.put("places/" + in + "/reservation/" + date + "/" + String.valueOf(selectedDate.getMin()) + "/", MainActivity.KAKAO_PROFILE.getId());
        }

        childUpdates.put("reservation/" + reservation.getId() + "/" + reservation.getDate() + "/", reservation.toMap());

        ref.updateChildren(childUpdates);
        loadingBar.setVisibility(View.GONE);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    View.OnClickListener prevFragmentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (FRAGMENT_INDEX) {
                case 1:
                    FRAGMENT_INDEX--;

                    prevButton.setVisibility(View.GONE);
                    int progress = (int) (((double) (FRAGMENT_INDEX + 1) / (double) FRAGMENT_SIZE) * 100);
                    bar.setProgress(progress);
                    selectedDate = null;

                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.reserve_fragment, ReserveCalendarFragment.newInstance(index));
                    fragmentTransaction.commit();
                    break;
                case 2:
                    FRAGMENT_INDEX--;

                    progress = (int) (((double) (FRAGMENT_INDEX + 1) / (double) FRAGMENT_SIZE) * 100);
                    bar.setProgress(progress);
                    reserveUser = null;

                    etcFragment = ReserveEtcFragment.newInstance();

                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.reserve_fragment, etcFragment);
                    fragmentTransaction.commit();
                    break;
            }
        }
    };

    View.OnClickListener nextFragmentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (FRAGMENT_INDEX) {
                case 0:
                    if(selectedDate == null) {
                        Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        FRAGMENT_INDEX++;

                        prevButton.setVisibility(View.VISIBLE);
                        int progress = (int) (((double) (FRAGMENT_INDEX + 1) / (double) FRAGMENT_SIZE) * 100);
                        bar.setProgress(progress);

                        etcFragment = ReserveEtcFragment.newInstance();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.reserve_fragment, etcFragment);
                        fragmentTransaction.commit();
                    }

                    break;
                case 1:
                    etcFragment.onButtonPressed();

                    if(reserveUser.getName() == null || reserveUser.getPhone() == null || reserveUser.getEmail() == null || reserveUser.getType() == null) {
                        Toast.makeText(getApplicationContext(), "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        FRAGMENT_INDEX++;

                        int progress = (int) (((double) (FRAGMENT_INDEX + 1) / (double) FRAGMENT_SIZE) * 100);
                        bar.setProgress(progress);

                        completedFragment = ReserveCompletedFragment.newInstance();
                        completedFragment.reserveDate = selectedDate;
                        completedFragment.reserveUser = reserveUser;

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.reserve_fragment, completedFragment);
                        fragmentTransaction.commit();
                    }
                    break;
                case 2:
                    FRAGMENT_INDEX++;
                    completedFragment.onButtonPressed();

                    int progress = (int) (((double) (FRAGMENT_INDEX + 1) / (double) FRAGMENT_SIZE) * 100);
                    bar.setProgress(progress);

                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.reserve_fragment, ReserveFinishFragment.newInstance(null, null));
                    fragmentTransaction.commit();

                    prevButton.setVisibility(View.GONE);
                    TextView nextText = (TextView) nextButton.getChildAt(0);
                    nextText.setText("확인");
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });

                    break;
            }

        }
    };
}
