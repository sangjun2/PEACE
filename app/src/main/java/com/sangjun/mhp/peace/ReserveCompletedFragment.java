package com.sangjun.mhp.peace;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReserveCompletedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReserveCompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserveCompletedFragment extends Fragment {
    public ReserveDate reserveDate;
    public ReserveUser reserveUser;

    private OnFragmentInteractionListener mListener;

    public Reservation reservation;

    public ReserveCompletedFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReserveCompletedFragment newInstance() {
        ReserveCompletedFragment fragment = new ReserveCompletedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_completed, container, false);

        TextView place = (TextView) view.findViewById(R.id.completed_place);
        TextView date = (TextView) view.findViewById(R.id.completed_date);
        TextView time = (TextView) view.findViewById(R.id.completed_time);
        TextView number = (TextView) view.findViewById(R.id.completed_number);
        TextView name = (TextView) view.findViewById(R.id.completed_name);
        TextView phone = (TextView) view.findViewById(R.id.completed_phone);
        TextView email = (TextView) view.findViewById(R.id.completed_email);
        TextView content = (TextView) view.findViewById(R.id.completed_content);
        TextView price = (TextView) view.findViewById(R.id.completed_price);

        place.setText(ReserveActivity.PLACE.getName());
        date.setText(setDateFormat(reserveDate.getDate()));
        time.setText(reserveDate.getMin() + " ~ " + (reserveDate.getMax() + 1) + " 시");
        number.setText(String.valueOf(reserveUser.getNumber()) + " 명");
        name.setText(reserveUser.getName());
        phone.setText(reserveUser.getPhone());
        email.setText(reserveUser.getEmail());
        content.setText(reserveUser.getContent() == null ? "없음" : reserveUser.getContent());

        String type = reserveUser.getType();
        int total;
        if(type.equals("ADULT")) {
            total = ReserveActivity.PLACE.getPrice().getAdult() * (reserveDate.getMax() - reserveDate.getMin() + 1);
        } else {
            total = ReserveActivity.PLACE.getPrice().getStudent() * (reserveDate.getMax() - reserveDate.getMin() + 1);
        }
        price.setText(String.valueOf(total) + " 원");

        reservation = new Reservation(String.valueOf(MainActivity.KAKAO_PROFILE.getId()),
                ReserveActivity.PLACE.getName(),
                String.valueOf(reserveDate.getDate().getCalendar().getTimeInMillis()),
                setDateFormat(reserveDate.getDate()),
                (reserveDate.getMin() + " ~ " + (reserveDate.getMax() + 1) + " 시"),
                String.valueOf(reserveUser.getNumber()) + " 명",
                reserveUser.getName(),
                reserveUser.getPhone(),
                reserveUser.getEmail(),
                (reserveUser.getContent() == null ? "없음" : reserveUser.getContent()));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onReservePlace(reservation);
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
        void onReservePlace(Reservation reservation);
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
}
