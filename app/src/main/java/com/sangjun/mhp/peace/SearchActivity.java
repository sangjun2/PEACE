package com.sangjun.mhp.peace;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, SearchDetailFragment.OnFragmentInteractionListener {
    Context mContext;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.search_fragment, new SearchFragment());
        fragmentTransaction.commit();

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        input = (EditText) findViewById(R.id.input_search);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        input.clearFocus();

                        // 검색 동작
                        String searchData = input.getText().toString();
                        if(searchData.equals("")) {
                            Toast.makeText(getApplicationContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {

                            SharedPreferences preferences = getSharedPreferences("Search", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            long current = System.currentTimeMillis();

                            editor.putString(String.valueOf(current), searchData);
                            editor.commit();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = database.getReference();
                            Query query = ref.child("places/").orderByChild("name").equalTo(searchData);
                            query.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    long searchCount = (long) dataSnapshot.child("searchCount").getValue();

                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put("places/" + dataSnapshot.getKey() + "/searchCount/", searchCount + 1);

                                    ref.updateChildren(childUpdates);
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


                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.search_fragment, SearchDetailFragment.newInstance(searchData));
                            fragmentTransaction.commit();

                        }
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}