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

public class OilRepository extends BaseRepository {

    private final String URL_LIST = this.BASE_URL+"/oil/list";
    private final String URL_DETAIL = this.BASE_URL+"/oil/detail/";

    private static OilRepository oilRepository;

    private OilRepository() {

    }

    public static OilRepository getInstance() {

        if(OilRepository.oilRepository == null) OilRepository.oilRepository = new OilRepository();

        return OilRepository.oilRepository;
    }

    public void getOilList(final WebServicesListCallback<Oil> callback) {

        JsonArrayRequest jsonRequest = new JsonArrayRequest(this.URL_LIST,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Oil> oilList = new ArrayList<>();
                        JSONObject obj;

                        for (int i = 0; i < response.length(); i++){
                            try {
                                obj = response.getJSONObject(i);
                                oilList.add(new Oil(obj.getInt("id"), obj.getString("name")));
                            } catch (JSONException e){
                                callback.onWebServiceCallListFailure();
                            }
                        }

                        callback.onWebServiceCallListSuccess(oilList);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onWebServiceCallListFailure();
            }
        });

        BaseRepository.getRequestQueue().add(jsonRequest);
    }

    public void getOilDetail(final WebServicesDetailCallback<Oil> callback, int idOil) {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(this.URL_DETAIL+idOil, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Oil oil = new Oil(response.getInt("id"), response.getString("name"), response.getString("description"));

                            JSONArray arraySymptom = response.getJSONArray("symptomList");
                            JSONObject jsonSymptom;

                            ArrayList<Symptom> listSymptom = new ArrayList<Symptom>();

                            for(int i = 0; i < arraySymptom.length(); i++) {
                                jsonSymptom = arraySymptom.getJSONObject(i);
                                listSymptom.add(new Symptom(jsonSymptom.getInt("id"), jsonSymptom.getString("name")));
                            }

                            oil.setListSymptom(listSymptom);

                            callback.onWebServiceCallDetalSuccess(oil);
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
