package com.sangjun.mhp.peace;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sangjun on 2017-11-09.
 */

public class SearchDetailListAdapter extends RecyclerView.Adapter<SearchDetailListAdapter.ViewHolder>  {

    private Context mContext;
    private ArrayList<Place> list;

    public SearchDetailListAdapter(ArrayList<Place> places) {
        this.list = places;
    }

    @Override
    public SearchDetailListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.search_list_item,parent,false);
        SearchDetailListAdapter.ViewHolder viewHolder = new SearchDetailListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchDetailListAdapter.ViewHolder holder, final int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReferenceFromUrl("gs://peace-c66f9.appspot.com");

        holder.mTitleText.setText(this.list.get(position).getName());
        holder.mTimeText.setText("최소 : " + this.list.get(position).getTime().getMin() +"시간, 최대 : " + this.list.get(position).getTime().getMax() + "시간");
        holder.mPriceText.setText("성인 : " + this.list.get(position).getPrice().getAdult() + "원, 학생 : " + this.list.get(position).getPrice().getStudent() + "원");

        reference.child(this.list.get(position).getUrl() + "/00.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext).load(uri).into(holder.mImage);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mTitleText;
        TextView mTimeText;
        TextView mPriceText;

        ViewHolder(View view) {
            super(view);
            mImage = (ImageView) view.findViewById(R.id.search_item_image);
            mTitleText = (TextView) view.findViewById(R.id.search_item_title);
            mTimeText = (TextView) view.findViewById(R.id.search_item_time);
            mPriceText = (TextView) view.findViewById(R.id.search_item_price);
        }
    }
}
