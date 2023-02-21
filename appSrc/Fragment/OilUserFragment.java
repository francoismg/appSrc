package com.archtanlabs.root.essentialoils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.archtanlabs.root.essentialoils.database.DbHelper;

import java.util.List;

public class OilUserFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OilListFragment.OnOilListFragmentInteractionListener mListener;

    public OilUserFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OilUserFragment newInstance(int columnCount) {
        OilUserFragment fragment = new OilUserFragment();
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
        final View view = inflater.inflate(R.layout.fragment_oil_user_list, container, false);

        final Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        List<Oil> list = DbHelper.getInstance(context).getOilList();

        OilUserAdapter adapter = new OilUserAdapter(list, mListener);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        OilUserAdapter adapter = (OilUserAdapter)recyclerView.getAdapter();
                        int position = viewHolder.getAdapterPosition();
                        if(DbHelper.getInstance(context).removeOil((int)adapter.getItemId(position))) {
                            adapter.removeItemAt(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(view, R.string.oilUserRemoveFail, 5000).show();
                        }
                    }
                });

         mIth.attachToRecyclerView(recyclerView);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OilListFragment.OnOilListFragmentInteractionListener) {
            mListener = (OilListFragment.OnOilListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOilListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
