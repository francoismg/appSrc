package com.archtanlabs.root.essentialoils;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SymptomDetailFragment extends Fragment implements WebServicesDetailCallback<Symptom> {

    private static final String ARG_SYMPTOM_ID = "symptomID";

    private int mIdSymptom;

    public SymptomDetailFragment() {

    }

    public static SymptomDetailFragment newInstance(int symptomId) {
        SymptomDetailFragment fragment = new SymptomDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SYMPTOM_ID, symptomId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdSymptom = getArguments().getInt(ARG_SYMPTOM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_symptom_detail, container, false);

        Utils.hideKeyboard(this.getActivity());

        SymptomRepository.getInstance().getSymptomDetail(this, mIdSymptom);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onWebServiceCallDetalFailure() {
        Snackbar.make(this.getView(), R.string.errorData, 5000).show();
    }

    @Override
    public void onWebServiceCallDetalSuccess(Symptom symptom) {
        TextView tvN = getView().findViewById(R.id.tvNameSymptomDetail);
        TextView tvD = getView().findViewById(R.id.tvDescriptionSymptomDetail);

        tvN.setText(symptom.getName());
        tvD.setText(symptom.getDetail());
        tvD.setMovementMethod(new ScrollingMovementMethod());
    }
}
