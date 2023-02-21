package com.archtanlabs.root.essentialoils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.archtanlabs.root.essentialoils.database.DbHelper;

import java.util.List;

public class SymptomUserFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private SymptomListFragment.OnSymptomListFragmentInteractionListener mListener;

    public SymptomUserFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SymptomUserFragment newInstance(int columnCount) {
        SymptomUserFragment fragment = new SymptomUserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symptom_user_list, container, false);

        Context context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        List<Symptom> list = DbHelper.getInstance(context).getSymptomList();

        recyclerView.setAdapter(new SymptomUserAdapter(list, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SymptomListFragment.OnSymptomListFragmentInteractionListener) {
            mListener = (SymptomListFragment.OnSymptomListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
