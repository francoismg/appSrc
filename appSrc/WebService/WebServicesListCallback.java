package com.archtanlabs.root.essentialoils;

import java.util.List;

public interface WebServicesListCallback<T> {

    void onWebServiceCallListFailure();

    void onWebServiceCallListSuccess(List<T> list);

}