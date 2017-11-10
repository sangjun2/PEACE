package com.sangjun.mhp.peace;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReserveCalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReserveCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserveCalendarFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "param1";
    private int index;
    private Place place;

    public ReserveCalendarFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReserveCalendarFragment newInstance(int param1) {
        ReserveCalendarFragment fragment = new ReserveCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
        }

        place = SplashActivity.placeList.get(index);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_calendar, container, false);

        final MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.reserve_calendar);
        calendarView.addDecorator(decorator);
        calendarView.setSelectedDate(CalendarDay.today());

        final TextView selectedDay = (TextView) view.findViewById(R.id.reserve_day);
        selectedDay.setText("날짜를 선택해주세요.");

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {
                if (selected) {
                    calendarView.setSelectedDate(date);

                    View timelineView = inflater.inflate(R.layout.time_line, null);
                    final RecyclerView recyclerView = (RecyclerView) timelineView.findViewById(R.id.timeline_recyclerview);
                    final TimeLineViewAdapter adapter = new TimeLineViewAdapter(index, date);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("시간을 선택해주세요.");
                    builder.setView(timelineView);
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String[] selectedArray = adapter.getIndexSelected();
                            int min = 25, max = -1;
                            int i = 0;
                            while(i < selectedArray.length) {
                                if(selectedArray[i] != null) {
                                    int num = Integer.parseInt(selectedArray[i].split(":")[0]);
                                    if(min > num) {
                                        min = num;
                                    }

                                    if(max < num) {
                                        max = num;
                                    }

                                    i++;
                                } else {
                                    break;
                                }
                            }
                            StringBuffer buf = new StringBuffer(setDateFormat(date));
                            buf.append(" " + min + "시 ~ " + (max + 1) + "시");

                            selectedDay.setText(buf + "");
                            onCalendarSelected(date, min, max);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onCalendarSelected(CalendarDay date, int min, int max) {
        if (mListener != null) {
            mListener.onCalendarSelected(date, min, max);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCalendarSelected(CalendarDay date, int min, int max);
    }

    public String setDateFormat(CalendarDay date) {
        StringBuffer buf = new StringBuffer();
        buf.append((date.getMonth() + 1) + ". ");
        buf.append(date.getDay() + ". (");

        int dayNumber = date.getCalendar().get(Calendar.DAY_OF_WEEK);
        switch (dayNumber) {
            case 1:
                buf.append("일");
                break;
            case 2:
                buf.append("월");
                break;
            case 3:
                buf.append("화");
                break;
            case 4:
                buf.append("수");
                break;
            case 5:
                buf.append("목");
                break;
            case 6:
                buf.append("금");
                break;
            case 7:
                buf.append("토");
                break;
        }

        return buf + ")";
    }

    DayViewDecorator decorator = new DayViewDecorator() {
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            CalendarDay date = CalendarDay.today();

            return day.isBefore(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
            view.areDaysDisabled();
        }
    };
}
