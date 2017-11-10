package com.sangjun.mhp.peace;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReserveEtcFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReserveEtcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserveEtcFragment extends Fragment {
    EditText name;
    EditText phone;
    EditText email;
    EditText content;

    ReserveUser reserveUser;

    private OnFragmentInteractionListener mListener;

    public ReserveEtcFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReserveEtcFragment newInstance() {
        ReserveEtcFragment fragment = new ReserveEtcFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reserveUser = new ReserveUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_etc, container, false);

        ImageView minusButton = (ImageView) view.findViewById(R.id.etc_minus_bt);
        final EditText number = (EditText) view.findViewById(R.id.etc_number_text);
        ImageView plusButton = (ImageView) view.findViewById(R.id.etc_plus_bt);

        reserveUser.setNumber(1);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(number.getText().toString());

                if(num > 1) {
                    num--;
                    number.setText(String.valueOf(num));
                } else {
                    number.setText("1");
                    num = 1;
                }

                reserveUser.setNumber(num);
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(number.getText().toString());

                if(num > 0) {
                    num++;
                    number.setText(String.valueOf(num));
                } else {
                    number.setText("1");
                    num = 1;
                }

                reserveUser.setNumber(num);
            }
        });

        final Button adultButton = (Button) view.findViewById(R.id.etc_type_adult);
        final Button studentButton = (Button) view.findViewById(R.id.etc_type_student);

        adultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adultButton.setBackgroundResource(R.drawable.button_border_clicked);
                adultButton.setTextColor(Color.parseColor("#ffffff"));

                studentButton.setBackgroundResource(R.drawable.button_border);
                studentButton.setTextColor(Color.parseColor("#000000"));

                reserveUser.setType("ADULT");
            }
        });

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentButton.setBackgroundResource(R.drawable.button_border_clicked);
                studentButton.setTextColor(Color.parseColor("#ffffff"));

                adultButton.setBackgroundResource(R.drawable.button_border);
                adultButton.setTextColor(Color.parseColor("#000000"));
                reserveUser.setType("STUDENT");
            }
        });

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                reserveUser.setNumber(Integer.parseInt(s.toString()));
            }
        });

        name = (EditText) view.findViewById(R.id.etc_name_text);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null) {
                    reserveUser.setName(s.toString());
                }
            }
        });

        phone = (EditText) view.findViewById(R.id.etc_phone_text);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null) {
                    reserveUser.setPhone(s.toString());
                }
            }
        });

        email = (EditText) view.findViewById(R.id.etc_email_text);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null) {
                    reserveUser.setEmail(s.toString());
                }
            }
        });
        content = (EditText) view.findViewById(R.id.etc_content_text);
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null) {
                    reserveUser.setContent(s.toString());
                }
            }
        });
        email.setHint(MainActivity.KAKAO_PROFILE.getEmail());

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    email.setText(MainActivity.KAKAO_PROFILE.getEmail());
                }
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null) {
                    reserveUser.setEmail(s.toString());
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onEtcCompleted(reserveUser);
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
        void onEtcCompleted(ReserveUser reserveUser);
    }
}
