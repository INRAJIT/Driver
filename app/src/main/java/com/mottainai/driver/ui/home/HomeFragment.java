package com.mottainai.driver.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mottainai.driver.DashBoardInterface;
import com.mottainai.driver.DashboardActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.data.model.home.Pickup;
import com.mottainai.driver.directions.DirectionTask;
import com.mottainai.driver.directions.IDirectionRoute;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, IDirectionRoute {

    private GoogleMap mMap;
    private LinearLayout pickupRequestLayout, pickupNavigationLayout;
    private TextView customerName, customerType, address;
    private TextView pickupTravelTime, pickupTravelDistance, pickupCustomerType, pickupCustomerAddress,
            pickupCustomerEmail, pickupCustomerPhone;
    private ImageView btnCall;
    private ImageView accept, reject;
    private Button startNavigation, reached;
    private CustomProgressBar progressBar;

    private DashboardActivity dashboardActivity;
    private DashBoardInterface dashBoardInterface;
    private Marker driverMarker, pickupMarker;
    private Pickup pickup;
    private Polyline polyline;

    private double latitude, longitude;
    private boolean isLocationSet;
    private boolean isPickupAvailable;
    private boolean isAcceptButtonClicked;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 100;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dashboardActivity = (DashboardActivity) context;
        dashBoardInterface = (DashBoardInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pickupRequestLayout = rootView.findViewById(R.id.pickup_request_layout);
        pickupNavigationLayout = rootView.findViewById(R.id.pickup_navigation_layout);
        customerName = rootView.findViewById(R.id.customer_name);
        customerType = rootView.findViewById(R.id.customer_type);
        address = rootView.findViewById(R.id.address);
        accept = rootView.findViewById(R.id.accept);
        reject = rootView.findViewById(R.id.reject);

        pickupTravelTime = rootView.findViewById(R.id.pickup_travel_time);
        pickupTravelDistance = rootView.findViewById(R.id.pickup_travel_distance);
        pickupCustomerType = rootView.findViewById(R.id.pickup_customer_type);
        pickupCustomerAddress = rootView.findViewById(R.id.pickup_customer_address);
        pickupCustomerEmail = rootView.findViewById(R.id.pickup_customer_email);
        pickupCustomerPhone = rootView.findViewById(R.id.pickup_customer_phone);
        startNavigation = rootView.findViewById(R.id.start_navigation);
        reached = rootView.findViewById(R.id.reached);
        btnCall = rootView.findViewById(R.id.btn_call);
        progressBar = new CustomProgressBar();

        accept.setOnClickListener(this);
        reject.setOnClickListener(this);
        startNavigation.setOnClickListener(this);
        reached.setOnClickListener(this);
        btnCall.setOnClickListener(this);

        dashboardActivity.dashBoardViewModel.getDriverPickupResponse().observe(getViewLifecycleOwner(),
                driverPickupResponse -> {
                    if (driverPickupResponse.getStatus()) {
                        isPickupAvailable = true;
                        pickupRequestLayout.setVisibility(View.VISIBLE);
                        pickup = driverPickupResponse.getPickup().get(0);
                        customerName.setText(pickup.getCustomerName());
                        customerType.setText(pickup.getCustomertype());
                        address.setText(pickup.getAddress());
                        // hide the pickup-layout and show navigation-layout if already accepted
                        if (pickup.isAccept()) {
                            setPickupDetails();
                        } else {
                            dashboardActivity.playDefaultRingTone();
                            startShakeAnimation();
                            pickupRequestLayout.setVisibility(View.VISIBLE);
                            pickupNavigationLayout.setVisibility(View.GONE);
                        }

                        // add pickup marker to map
                        addPickupMarkerToMap(pickup.getLatitude(), pickup.getLongitude(), pickup.getCustomertype());
                    } else {
                        isPickupAvailable = false;
                        pickupRequestLayout.setVisibility(View.GONE);
                        pickupNavigationLayout.setVisibility(View.GONE);
                        dashboardActivity.stopRingTone();
                        removePickupMarker();
                        removePolyline();
                        Toast.makeText(getContext(), driverPickupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        dashboardActivity.dashBoardViewModel.getAcceptPickupResponse().observe(getViewLifecycleOwner(),
                acceptPickupResponse -> {
                    if (isAcceptButtonClicked) {
                        progressBar.dialog.dismiss();
                        if (acceptPickupResponse.getStatus()) {
                            dashboardActivity.stopRingTone();
                            setPickupDetails();
                            Toast.makeText(getContext(), acceptPickupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), acceptPickupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            pickupNavigationLayout.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // request driver pickup only if location is available
        if (isLocationSet) {
            requestDiverPickup();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == accept) {
            isAcceptButtonClicked = true;
            List<Integer> pickupList = pickup.getPickupsId();
            progressBar.show(getContext(), getString(R.string.progress_dialog_title));
            dashboardActivity.dashBoardViewModel.acceptPickup(dashboardActivity.driverId, pickupList);
        } else if (view == reject) {
            Intent intent = new Intent(getActivity(), DeclinePickupActivity.class);
            intent.putIntegerArrayListExtra(Constants.KEY_PICKUP_IDS, (ArrayList<Integer>) pickup.getPickupsId());
            startActivity(intent);
        } else if (view == startNavigation) {
            if (canDrawOverlay()) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + pickup.getLatitude() + "," + pickup.getLongitude()));
                startActivity(intent);
            } else {
                startSettingsScreenForOverlay();
            }
        } else if (view == reached) {
            Intent intent = new Intent(getActivity(), PickupActivity.class);
            intent.putIntegerArrayListExtra(Constants.KEY_PICKUP_IDS, (ArrayList<Integer>) pickup.getPickupsId());
            intent.putExtra(Constants.KEY_CUSTOMER_IMAGE, "");
            intent.putExtra(Constants.KEY_CUSTOMER_NAME, pickup.getCustomerName());
            intent.putExtra(Constants.KEY_CUSTOMER_ADDRESS, pickup.getAddress());
            startActivity(intent);
        } else if(view == btnCall) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + pickup.getPhone()));
            startActivity(callIntent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        dashBoardInterface.setHomeFragmentInterface(this);
    }

    public void updateDriverLocationOnMap(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        addDriverMarkerToMap(latitude, longitude, "Vehicle Location");

        // Do not zoom to driver location if pickup is available
        if(!isPickupAvailable) {
            float zoomLevel = (float) 10.0;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoomLevel));
        }

        // As the location set just now so making first api request
        if(!isLocationSet) {
            isLocationSet = true;
            requestDiverPickup();
        }
    }

    private void addDriverMarkerToMap(double latitude, double longitude, String title) {
        removeDriverMarker();
        LatLng latLng = new LatLng(latitude, longitude);
        driverMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(title).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.newmarker)));
    }

    private void removeDriverMarker() {
        if (driverMarker != null) {
            driverMarker.remove();
        }
    }

    private void setPickupDetails() {
        pickupCustomerType.setText(pickup.getCustomertype());
        pickupCustomerAddress.setText(pickup.getAddress());
        pickupCustomerEmail.setText(pickup.getEmail());
        pickupCustomerPhone.setText(pickup.getPhone());
        pickupRequestLayout.setVisibility(View.GONE);
        pickupNavigationLayout.setVisibility(View.VISIBLE);
        LatLng latLngSource = new LatLng(latitude, longitude);
        LatLng latLngDestination = new LatLng(pickup.getLatitude(), pickup.getLongitude());
        DirectionTask directionTask = new DirectionTask(this);
        directionTask.execute(DirectionTask.getDirectionsUrl(latLngSource, latLngDestination));
    }

    private void addPickupMarkerToMap(double latitude, double longitude, String title) {
        removePickupMarker();
        LatLng latLng = new LatLng(latitude, longitude);
        if (mMap != null) {
            pickupMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(title).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.newmarker)));
                   //**not use yet**//
            // icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_trash_marker)));
        }
    }

    private void removePickupMarker() {
        if (pickupMarker != null) {
            pickupMarker.remove();
        }
    }

    private void removePolyline() {
        if(polyline != null) {
            polyline.remove();
        }
    }

    private void requestDiverPickup() {
        dashboardActivity.dashBoardViewModel.getDriverPickup(dashboardActivity.driverId);
    }

    @Override
    public void onRouteReady(PolylineOptions polylineOptions, int distance, int duration) {
        if (polylineOptions != null) {
            pickupTravelTime.setText(duration / 60 + " mins");
            pickupTravelDistance.setText("(" + distance / 1000 + " km)");
            polyline = mMap.addPolyline(polylineOptions);
            zoomRoute(mMap, polylineOptions.getPoints());
        }
    }

    /**
     * Zooms a Route (given a List of LalLng) at the greatest possible zoom level.
     *
     * @param googleMap:      instance of GoogleMap
     * @param lstLatLngRoute: list of LatLng forming Route
     */
    public void zoomRoute(GoogleMap googleMap, List<LatLng> lstLatLngRoute) {

        if (googleMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : lstLatLngRoute)
            boundsBuilder.include(latLngPoint);

        int routePadding = 100;
        LatLngBounds latLngBounds = boundsBuilder.build();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding));
    }

    private boolean canDrawOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getContext())) {
            return false;
        }
        return true;
    }

    private void startSettingsScreenForOverlay() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getContext().getPackageName()));
        startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
    }

    private void startShakeAnimation() {
        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim);
        pickupRequestLayout.startAnimation(animShake);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (canDrawOverlay()) {
                Toast.makeText(getContext(), "Draw over other app permission granted !!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Draw over other app permission not granted",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //TODO - cleanup here
    }

    @Override
    public void onDestroyView() {
        dashBoardInterface.setHomeFragmentInterface(null);
        super.onDestroyView();
    }

}
