package com.sangjun.mhp.peace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout profileLayout;
    View headerView;
    MenuItem loginItem;
    ViewFlipper viewFlipper;
    public static UserProfile KAKAO_PROFILE;

    public static ArrayList<String> SEARCH_COUNT_LIST;
    public static ArrayList<Reservation> RESERVATION_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        loginItem = navigationView.getMenu().findItem(R.id.etc_login);

        loadUserData();

        viewFlipper = (ViewFlipper) findViewById(R.id.main_viewflipper);

        for(int i = 0; i < SplashActivity.bannerList.size(); i++) {
            setFlipperImage(i);
        }

        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this, android.R.anim.fade_out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MainRecyclerViewAdapter());

        SEARCH_COUNT_LIST = new ArrayList<>();

        LinearLayout navReservation = (LinearLayout) headerView.findViewById(R.id.nav_reservation);
        navReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = mDatabase.getReference("reservation/");
                RESERVATION_LIST = new ArrayList<>();

                ref.child(String.valueOf(KAKAO_PROFILE.getId()) + "/").addListenerForSingleValueEvent(new ValueEventListener() {
                    long index = 0;

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Reservation reservation = snapshot.getValue(Reservation.class);
                            Log.d("RESERVATION==", reservation.toString());
                            RESERVATION_LIST.add(reservation);
                            index = index + 1;
                        }
                        if(dataSnapshot.getChildrenCount() == 0) {;
                            Toast.makeText(getApplicationContext(), "예약 내역이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        } else if(index == dataSnapshot.getChildrenCount()) {
                            Intent intent = new Intent(MainActivity.this, ReservationListActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_search) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("places/");

            SEARCH_COUNT_LIST = new ArrayList<>();

            Query searchCountQuery = ref.orderByChild("searchCount").limitToLast(3);
            searchCountQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d("QUERY==", dataSnapshot.toString() + "," + s);
                    long searchCount = (long) dataSnapshot.child("searchCount").getValue();
                    if(searchCount != 0) {
                        SEARCH_COUNT_LIST.add(dataSnapshot.getKey());
                    }

                    if(SEARCH_COUNT_LIST.size() == 3) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) { // 검색어 결과
            String token = data.getStringExtra("token");
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.info_facility: //시설정보
                break;
            case R.id.info_reserve: //예약 현황
                break;
            case R.id.reserve_menu1:
                break;
            case R.id.reserve_menu2:
                break;
            case R.id.reserve_menu3:
                break;
            case R.id.reserve_menu4:
                break;
            case R.id.reserve_menu5:
                break;
            case R.id.etc_complain:
                break;
            case R.id.etc_login:
                String text = item.getTitle().toString();
                if(text.equals("로그인")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    logout();
                }
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }

    public void loadUserData() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                profileLayout = (LinearLayout) headerView.findViewById(R.id.profile);

                profileLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(UserProfile result) {
                KAKAO_PROFILE = result;

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("/users");

                User user = new User(result.getId(), result.getEmail(), result.getNickname());
                Map<String, Object> values = user.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/" + String.valueOf(result.getId()) + "/", values);
                ref.updateChildren(childUpdates);

                setUserData(result);
                loginItem.setTitle("로그아웃");
            }
        });
    }

    private void setUserData(UserProfile profile) {
        ImageView userImage = (ImageView) headerView.findViewById(R.id.user_image);
        TextView userName = (TextView) headerView.findViewById(R.id.user_name);

        Picasso.with(this).load(profile.getThumbnailImagePath()).into(userImage);
        userName.setText(profile.getNickname());
    }

    private void setFlipperImage(int index) {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setTag(index);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = v.getTag().toString();
                Intent intent = new Intent(MainActivity.this, BannerDetailActivity.class);
                intent.putExtra("index", tag);
                startActivity(intent);
            }
        });
        Picasso.with(this).load(SplashActivity.bannerList.get(index).getImgSrc()).into(imageView);
        viewFlipper.addView(imageView);
    }

}