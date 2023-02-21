package com.archtanlabs.root.essentialoils;

import java.util.List;

public interface WebServicesDetailCallback<T> {

    void onWebServiceCallDetalFailure();

    void onWebServiceCallDetalSuccess(T item);

}