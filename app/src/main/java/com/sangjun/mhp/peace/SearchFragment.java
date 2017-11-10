package com.sangjun.mhp.peace;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ListView listView;
    RankListViewAdapter rankAdapter;
    RecentListViewAdapter recentAdapter;
    InputMethodManager imm;

    ArrayList<String> list;
    ArrayList<SearchData> strings;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        list = MainActivity.SEARCH_COUNT_LIST;

        SharedPreferences preferences = getActivity().getSharedPreferences("Search", Context.MODE_PRIVATE);
        Map<String, ?> map = preferences.getAll();
        strings = new ArrayList<>(map.size());
        for(Map.Entry<String, ?> entry : map.entrySet()) {
            SearchData data = new SearchData(Long.parseLong(entry.getKey()), entry.getValue().toString());

            strings.add(data);
        }

        Comparator<SearchData> comparator = new Comparator<SearchData>() {
            @Override
            public int compare(SearchData o1, SearchData o2) { // 먼저 검색한 것이 밑에 오기 위해 반대로 리턴
                if(o1.getTime() > o2.getTime()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        };

        Collections.sort(strings, comparator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // InputMethodManager를 가져옴

        final Button rankButton = (Button) view.findViewById(R.id.search_rank);
        final Button recentButton = (Button) view.findViewById(R.id.search_recent);

        listView = (ListView) view.findViewById(R.id.search_list);
        rankAdapter = new RankListViewAdapter(getContext(), R.layout.rank_list_item, list);
        recentAdapter = new RecentListViewAdapter(getContext(), R.layout.recent_list_item, strings);
        listView.setAdapter(rankAdapter);

        rankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
                recentButton.setBackgroundResource(R.drawable.button_bg);

                listView.setAdapter(rankAdapter);

                EditText input = (EditText) getActivity().findViewById(R.id.input_search);
                // 감출 때
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);


            }
        });

        recentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
                rankButton.setBackgroundResource(R.drawable.button_bg);

                listView.setAdapter(recentAdapter);

                EditText input = (EditText) getActivity().findViewById(R.id.input_search);
                // 감출 때
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });


        return view;
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
        void onFragmentInteraction(Uri uri);
    }
}
