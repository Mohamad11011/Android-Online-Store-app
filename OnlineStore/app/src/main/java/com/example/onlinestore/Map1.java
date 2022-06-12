package com.example.onlinestore;


import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Switch;
        import android.widget.Toast;
        import android.widget.ToggleButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationCallback;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationResult;
        import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class Map1 extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    Switch mapBtn;
    private LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    int PERMISSION_REQUEST_LOCATION = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        mapBtn=findViewById(R.id.MapBtn);
        fusedLocationProviderClient= LocationServices.
                getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapF);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        createLocationRequest();
        createLocationCallback();

        initSwitch();

    }

    private  void MapProccess(){}

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                if(locationResult == null){ return;}
                 else{
                for (Location location: locationResult.getLocations()){
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
//        gMap.moveCamera(cameraUpdate);
        break;
                }
            }
            }
        };
    }
    private void initSwitch() {
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

    }
    private void startLocationUpdates() {
        try {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(Map1.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(Map1.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED)
                return;
            fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, null);
            gMap.setMyLocationEnabled(true);
        }catch (Exception e){
            Toast.makeText(Map1.this,
                    "Error, Location not available",
                    Toast.LENGTH_LONG).show();

        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[], @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(Map1.this,
                    "You may now call from this app",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Map1.this,
                    "You will not be able to make calls from this app",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getCameraPosition();
        LatLng Store= new LatLng (33.845629, 35.498527);
        gMap.addMarker(new MarkerOptions().position(Store).title("Tech Store"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Store,16));
        try {
            if (Build.VERSION.SDK_INT>=23){
                if (ContextCompat.checkSelfPermission(Map1.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(Map1.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)){
                        Snackbar.make(findViewById(R.id.Map),
                                "Require Permission to Place a live Location in the app",
                                Snackbar.LENGTH_INDEFINITE).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                ActivityCompat.requestPermissions(
                                        Map1.this,
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_REQUEST_LOCATION); }
                        } )   .show();

                    }else {
                        ActivityCompat.requestPermissions(Map1.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSION_REQUEST_LOCATION );
                    }
                }
                else {

                    startLocationUpdates();
                }

            }
            else {startLocationUpdates(); }

        }catch (Exception e){
            Toast.makeText(Map1.this, "Error requesting permission",
                    Toast.LENGTH_LONG).show();
        }
    }


    private void stopLocationUpdates(){
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(Map1.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Map1.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    @Override
    public void onPause(){
        super.onPause();
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(Map1.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Map1.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }
        try{

            stopLocationUpdates();
        }
        catch (Exception ignored){}
    }

}


