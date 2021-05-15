package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

    private TextView latitudeField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeField = findViewById(R.id.latitude);
        longitudeField = findViewById(R.id.longitude);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);
        if(location!= null){
            System.out.println("Provider"+provider+"has been selected");
        }
        else{
            latitudeField.setText("Location not available");
            longitudeField.setText("Location noy available");
        }


    }
    @Override
    protected void onResume() {

        super.onResume();
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        locationManager.requestLocationUpdates(provider,400,1, (LocationListener) this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates((LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location){
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        latitudeField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));

    }

    @Override
    public void onStatusChanged(String provider,int status ,Bundle extras){

    }
    @Override
    public  void onProviderEnabled(String provider){
        Toast.makeText(this,"Enabled new provide"+ provider,Toast.LENGTH_SHORT).show();
    }
    @Override
    public  void onProviderDisabled(String provider){
        Toast.makeText(this,"Disabled provider"+provider,Toast.LENGTH_SHORT).show();
    }
}