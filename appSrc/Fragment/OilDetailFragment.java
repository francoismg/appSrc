package com.archtanlabs.root.essentialoils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.archtanlabs.root.essentialoils.database.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OilDetailFragment extends Fragment implements View.OnClickListener, SymptomListFragment.OnSymptomListFragmentInteractionListener, WebServicesDetailCallback<Oil>  {

    private static final String ARG_OIL_ID = "oilID";

    private int mIdOil;
    private Oil mOil;
    ArrayList<Symptom> mListSymptom = new ArrayList<>();
    private SymptomAdapter mAdapter ;

    private OnOilDetailFragmentInteractionListener mListener;

    public OilDetailFragment() {

    }

    public static OilDetailFragment newInstance(int oilId) {
        OilDetailFragment fragment = new OilDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OIL_ID, oilId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdOil = getArguments().getInt(ARG_OIL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_oil_detail, container, false);

        Utils.hideKeyboard(this.getActivity());

        OilRepository.getInstance().getOilDetail(this, mIdOil);

        Button btnAddToCoffret = view.findViewById(R.id.btnAddToCoffret);

        if(!DbHelper.getInstance(view.getContext()).isOilStored(mIdOil)) {
            btnAddToCoffret.setOnClickListener(this);
        } else {
            btnAddToCoffret.setEnabled(false);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOilDetailFragmentInteractionListener) {
            mListener = (OnOilDetailFragmentInteractionListener) context;
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

    @Override
    public void onClick(View v) {
        if(DbHelper.getInstance(this.getContext()).addOil(this.mOil)) {
            v.setEnabled(false);
            Snackbar.make(v, R.string.insertionSuccess, 5000).show();
        } else {
            Snackbar.make(v, R.string.insertionFail, 5000).show();
        }

    }

    @Override
    public void onSymptomListFragmentInteraction(Symptom symptom) {
        MainActivity activity = (MainActivity)getActivity();
        activity.onOilSymptomListFragmentInteraction(symptom);
    }

    @Override
    public void onWebServiceCallDetalFailure() {
        Snackbar.make(getView(), R.string.errorData, 5000).show();
    }

    @Override
    public void onWebServiceCallDetalSuccess(Oil oil) {
        TextView tvN = getView().findViewById(R.id.tvNameOilDetail);
        TextView tvD = getView().findViewById(R.id.tvDescriptionOilDetail);
        RecyclerView list = getView().findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        mOil = oil;

        tvN.setText(oil.getName());
        tvD.setText(oil.getDetail());
        tvD.setMovementMethod(new ScrollingMovementMethod());

        mListSymptom = oil.getListSymptom();

        mAdapter = new SymptomAdapter(mListSymptom, this);
        list.setAdapter(mAdapter);
    }

    public interface OnOilDetailFragmentInteractionListener {
        void onOilDetailFragmentInteraction(Oil oil);
    }

}
