package com.archtanlabs.root.essentialoils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public abstract class BaseRepository {

    protected final String BASE_URL = "http://10.0.2.2/apioil";

    protected static RequestQueue rq;
    private static Context context;

    public static void instantiateBaseRepository(Context context) {
        BaseRepository.context = context;
        rq = Volley.newRequestQueue(context.getApplicationContext());
    }

    protected static RequestQueue getRequestQueue() {
        if (rq == null) rq = Volley.newRequestQueue(context.getApplicationContext());

        return rq;
    }

}
