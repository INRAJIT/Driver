package com.mottainai.driver.directions;

import com.google.android.gms.maps.model.PolylineOptions;

public interface IDirectionRoute {
    void onRouteReady(PolylineOptions polylineOptions, int distance, int duration);
}
