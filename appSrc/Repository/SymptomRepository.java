package com.archtanlabs.root.essentialoils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SymptomRepository extends BaseRepository {

    private final String URL_LIST = this.BASE_URL+"/symptom/list";
    private final String URL_DETAIL = this.BASE_URL+"/symptom/detail/";

    private static SymptomRepository symptomRepository;

    private SymptomRepository() {

    }

    public static SymptomRepository getInstance() {

        if(SymptomRepository.symptomRepository == null) SymptomRepository.symptomRepository = new SymptomRepository();

        return SymptomRepository.symptomRepository;
    }

    public void getSymptomList(final WebServicesListCallback<Symptom> callback) {

        final List<Symptom> symptomList = new ArrayList<Symptom>();

        JsonArrayRequest jsonRequest = new JsonArrayRequest(this.URL_LIST,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject mItem = response.getJSONObject(i);

                                symptomList.add(new Symptom(mItem.getInt("id"), mItem.getString("name")));
                            } catch (JSONException e){
                                callback.onWebServiceCallListFailure();
                            }
                        }

                        callback.onWebServiceCallListSuccess(symptomList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onWebServiceCallListFailure();
            }
        });

        BaseRepository.getRequestQueue().add(jsonRequest);
    }

    public void getSymptomDetail(final WebServicesDetailCallback<Symptom> callback, int idSymptom) {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(this.URL_DETAIL+idSymptom, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Symptom symptom = new Symptom(response.getInt("id"), response.getString("name"), response.getString("description"));
                            callback.onWebServiceCallDetalSuccess(symptom);
                        } catch(JSONException e) {
                            callback.onWebServiceCallDetalFailure();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onWebServiceCallDetalFailure();
            }
        });

        BaseRepository.getRequestQueue().add(jsonRequest);
    }

}
