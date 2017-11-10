package com.sangjun.mhp.peace;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sangjun on 2017-09-25.
 */

public class RankListViewAdapter extends ArrayAdapter<String> {

    private RecyclerView.ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private ArrayList<String> list = null;
    private Context mContext = null;
    private int layout_id = 0;

    public RankListViewAdapter(Context c, int textViewResourceId,
                               ArrayList<String> arrays) {
        super(c, textViewResourceId, arrays);
        this.inflater = LayoutInflater.from(c);
        this.mContext = c;
        this.layout_id = textViewResourceId;
        this.list = arrays;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout_id, parent, false);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            TextView index = (TextView) convertView.findViewById(R.id.rank_item_index);
            final TextView content = (TextView) convertView.findViewById(R.id.rank_item_content);

            index.setText(String.valueOf(position + 1));

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("places/" + list.get(list.size() - position - 1) + "/");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    content.setText(dataSnapshot.child("name").getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return convertView;
    }
}
