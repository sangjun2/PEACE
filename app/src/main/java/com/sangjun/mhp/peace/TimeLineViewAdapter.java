package com.sangjun.mhp.peace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.squareup.picasso.Picasso;

/**
 * Created by Sangjun on 2017-10-30.
 */

public class TimeLineViewAdapter extends RecyclerView.Adapter<TimeLineViewAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private static final int TIME_SIZE = 10;
    private int index;
    private CalendarDay date;
    private int numberOfSelected;
    private int maxSelected;
    private String[] indexSelected;
    private int[] selectedPosition;

    public TimeLineViewAdapter(int index, CalendarDay date) {
        this.index = index;
        this.date = date;
        this.numberOfSelected = 0;
        this.maxSelected = SplashActivity.placeList.get(index).getTime().getMax();
        indexSelected = new String[maxSelected];
        this.selectedPosition = new int[2];
        selectedPosition[0] = -1;
        selectedPosition[1] = -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mInflater = LayoutInflater.from(mContext);

        View view = mInflater.inflate(R.layout.time_line_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int time = position + 9;
        String timeText;

        if(time > 9) {
            timeText = time + ":00";
        } else {
            timeText = "0" + time + ":00";
        }

        holder.mText.setText(timeText);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        String in = index > 9 ? String.valueOf(index) : "0" + index;
        DatabaseReference ref = mDatabase.getReference("places/" + in + "/reservation/" + String.valueOf(date.getCalendar().getTimeInMillis()) + "/" + String.valueOf(time) + "/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("SNAPSHOT==", dataSnapshot.toString());

                if(dataSnapshot.getValue() != null) {
                    holder.mButton.setText("X");
                    holder.mButton.setSelected(false);
                } else {
                    holder.mButton.setText("선택 가능");
                    holder.mButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String btText = ((Button) v).getText().toString();

                            if(btText.equals("선택 가능")) {
                                if(numberOfSelected < maxSelected) {
                                    if(selectedPosition[0] == -1 && selectedPosition[1] == -1) {
                                        selectedPosition[0] = position;
                                        selectedPosition[1] = position;
                                        holder.mButton.setText("V");

                                        indexSelected[numberOfSelected] = holder.mText.getText().toString();
                                        numberOfSelected++;
                                    } else {
                                        if((selectedPosition[0] - 1) == position) {
                                            selectedPosition[0] = position;
                                            holder.mButton.setText("V");

                                            indexSelected[numberOfSelected] = holder.mText.getText().toString();
                                            numberOfSelected++;
                                        } else if((selectedPosition[1] + 1) == position) {
                                            selectedPosition[1] = position;
                                            holder.mButton.setText("V");

                                            indexSelected[numberOfSelected] = holder.mText.getText().toString();
                                            numberOfSelected++;
                                        } else {
                                            Toast.makeText(mContext, "연속으로 된 시간만 가능합니다.", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                } else {
                                    Toast.makeText(mContext, "최대 " + maxSelected + "시간만 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if(numberOfSelected > 0) {
                                    if(selectedPosition[0] == position) {
                                        selectedPosition[0] = position + 1;
                                        holder.mButton.setText("선택 가능");
                                        numberOfSelected--;
                                        indexSelected[numberOfSelected] = null;
                                    } else if(selectedPosition[1] == position) {
                                        selectedPosition[1] = position - 1;
                                        holder.mButton.setText("선택 가능");
                                        numberOfSelected--;
                                        indexSelected[numberOfSelected] = null;
                                    } else {
                                        Toast.makeText(mContext, "연속된 시간 사이는 취소할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return TIME_SIZE;
    }

    public String[] getIndexSelected() {
        return this.indexSelected;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;
        Button mButton;

        ViewHolder(View view) {
            super(view);
            mText = (TextView) view.findViewById(R.id.timeline_item_time);
            mButton = (Button) view.findViewById(R.id.timeline_item_bt);
        }
    }
}
