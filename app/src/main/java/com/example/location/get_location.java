package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.location.databinding.ActivityGetLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class get_location extends AppCompatActivity {

    ActivityGetLocationBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Geocoder geocoder;
    private  double lat,log;
    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityGetLocationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        geocoder=new Geocoder(this, Locale.ENGLISH);
        locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);


                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                    lat=location.getLatitude();
                    log=location.getLongitude();
                    try {
                         addresses=geocoder.getFromLocation(lat,log,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address=addresses.get(0);
                    String s=address.getCountryName();
                    String countryName=address.getCountryName();

                    String addressLine=address.getAddressLine(0);

                    String subLocality=address.getSubLocality();

                    String locality=address.getLocality();


                    Toast.makeText(get_location.this, "Lat="+lat+" \n Log= "+log +"\n Addess  = "+addressLine, Toast.LENGTH_SHORT).show();


                }

            }
        };

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        createLocation();
    }


    public void createLocation() {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},101);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}