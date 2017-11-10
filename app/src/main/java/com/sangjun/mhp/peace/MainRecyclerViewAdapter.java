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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by USER on 2017-10-17.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.main_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReferenceFromUrl("gs://peace-c66f9.appspot.com");

        reference.child(SplashActivity.placeList.get(position).getUrl() + "/00.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext).load(uri).into(holder.mImageView);
                holder.mTitleText.setText(SplashActivity.placeList.get(position).getName());

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlaceDetailActivity.class);
                intent.putExtra("index", position);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return SplashActivity.placeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTitleText;

        ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.main_item_image);
            mTitleText = (TextView) view.findViewById(R.id.main_item_title);
        }
    }
}
