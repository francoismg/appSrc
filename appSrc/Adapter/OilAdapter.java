package com.archtanlabs.root.essentialoils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.archtanlabs.root.essentialoils.OilListFragment.OnOilListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class OilAdapter extends RecyclerView.Adapter<OilAdapter.ViewHolder> implements Filterable {

    private List<Oil> mValues;
    private List<Oil> mModel;
    private final OnOilListFragmentInteractionListener mListener;

    public OilAdapter(List<Oil> oils, OnOilListFragmentInteractionListener listener) {
        mModel = oils;
        mValues = oils;
        mListener = listener;
    }

    public void setValues(List<Oil> oils) {
        mModel = oils;
        mValues = oils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_oil_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onOilListFragmentInteraction(holder.mItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIconView;
        public final TextView mContentView;
        public Oil mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIconView = (ImageView) view.findViewById(R.id.oil_list_icon);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    results.values = mModel;
                    results.count = mModel.size();
                } else {
                    List<Oil> filterResultsData = new ArrayList<Oil>();

                    for (Oil oil : mValues) {
                        if (oil.getName().contains(charSequence)) {
                            filterResultsData.add(oil);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mValues = (List<Oil>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
