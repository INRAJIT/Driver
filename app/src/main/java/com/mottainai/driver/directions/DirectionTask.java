package com.mottainai.driver.directions;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mottainai.driver.directions.model.Distance;
import com.mottainai.driver.directions.model.Leg;
import com.mottainai.driver.directions.model.Route;
import com.mottainai.driver.directions.model.RouteResponse;
import com.mottainai.driver.directions.model.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DirectionTask extends AsyncTask<String, Void, RouteResponse> {

    private IDirectionRoute directionRoute;

    public DirectionTask(IDirectionRoute directionRoute) {
        this.directionRoute = directionRoute;
    }

    @Override
    protected RouteResponse doInBackground(String... url) {
        RouteResponse routeResponse = null;
        try {
            String data = downloadUrl(url[0]);
            try {
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routeResponse = parser.parse(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routeResponse;
    }

    @Override
    protected void onPostExecute(RouteResponse routeResponse) {
        super.onPostExecute(routeResponse);
        PolylineOptions lineOptions = new PolylineOptions();
        int distance = 0;
        int duration = 0;

        for (int i = 0; i < routeResponse.getRoutes().size(); i++) {
            Route route = routeResponse.getRoutes().get(i);
            for (int j = 0; j < route.getLegs().size(); j++) {
                Leg leg = route.getLegs().get(j);
                distance = distance + leg.getDistance().getValue();
                duration = duration + leg.getDuration().getValue();
                for (int k = 0; k < leg.getSteps().size(); k++) {
                    Step step = leg.getSteps().get(k);
                    String point = step.getPolyline().getPoints();
                    List<LatLng> latLngList = DirectionsJSONParser.decodePoly(point);
                    lineOptions.addAll(latLngList);
                }
            }
            lineOptions.width(10);
            lineOptions.color(Color.BLUE);
            lineOptions.geodesic(true);
        }
        this.directionRoute.onRouteReady(lineOptions, distance, duration);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public static String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key = "key=AIzaSyCqGCPc8Ws1hWcjZ3dVQtSAigTT4tk3xzI";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }
}