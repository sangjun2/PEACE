package com.sangjun.mhp.peace;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Sangjun on 2017-09-25.
 */

public class RecentListViewAdapter extends ArrayAdapter<String> {

    private RecyclerView.ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private ArrayList<SearchData> list = null;
    private Context mContext = null;
    private int layout_id = 0;

    public RecentListViewAdapter(Context c, int textViewResourceId,
                               ArrayList<SearchData> strings) {
        super(c, textViewResourceId);
        this.inflater = LayoutInflater.from(c);
        this.mContext = c;
        this.layout_id = textViewResourceId;

        this.list = strings;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return this.list.get(position).getData();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout_id, parent, false);
        final TextView content = (TextView) convertView.findViewById(R.id.recent_item_content);
        Button clear = (Button) convertView.findViewById(R.id.recent_item_clear);
        content.setText(this.list.get(position).getData());
        clear.setTag(position);
        clear.setOnClickListener(listener);


        return convertView;
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());

            String key = String.valueOf(list.get(position).getTime());

            SharedPreferences preferences = mContext.getSharedPreferences("Search", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(key);
            editor.commit();

            list.remove(position);
            notifyDataSetChanged();
        }
    };

}
