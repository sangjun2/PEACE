package com.sangjun.mhp.peace;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by Sangjun on 2017-11-09.
 */

public class ReservationListViewAdapter extends RecyclerView.Adapter<ReservationListViewAdapter.ViewHolder> {
    private Context mContext;

    @Override
    public ReservationListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.place_list_item,parent,false);
        ReservationListViewAdapter.ViewHolder viewHolder = new ReservationListViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ReservationListViewAdapter.ViewHolder holder, final int position) {
        holder.mTitleText.setText(MainActivity.RESERVATION_LIST.get(position).getPlace());
        holder.mDateText.setText(MainActivity.RESERVATION_LIST.get(position).getDate() + " " + MainActivity.RESERVATION_LIST.get(position).getTime());
    }


    @Override
    public int getItemCount() {
        return MainActivity.RESERVATION_LIST.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleText;
        TextView mDateText;

        ViewHolder(View view) {
            super(view);
            mTitleText = (TextView) view.findViewById(R.id.reservation_list_title);
            mDateText = (TextView) view.findViewById(R.id.reservation_list_date);
        }
    }
}
