package com.example.alfi.gpsdinamis;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import  com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient ;
    private static final int MY_PERMISSIONS_REQUEST = 99;//int bebas, maks 1 byte
    Location mLastLocation;
    TextView mLatText;
    TextView mLongText;
    double intLat;
    double intLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLatText = (TextView) findViewById(R.id.tvLat);
        mLongText = (TextView) findViewById(R.id.tvLong);
        buildGoogleApiClient();
        createLocationRequest(); //<----- tambah
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ambilLokasi();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        intLat = location.getLatitude();
        intLong = location.getLongitude();

        if(intLat >= -6.860363 && intLat <= -6.860195 && intLong >= 107.589644 && intLong <= 107.590127) {
            ad.setMessage("Anda berada di GIK");
            ad.show();
            mLatText.setText("Lat.:" + String.valueOf(location.getLatitude()));
            mLongText.setText("Lon.:" + String.valueOf(location.getLongitude()));
        }else{
            ad.setMessage("Anda diluar GIK");
            ad.show();
            mLatText.setText("Lat.:" + String.valueOf(location.getLatitude()));
            mLongText.setText("Lon.:" + String.valueOf(location.getLongitude()));
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        //10 detik sekali minta lokasi (10000ms = 10 detik)
        mLocationRequest.setInterval(10000);
        //tapi tidak boleh lebih cepat dari 5 detik
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    private void ambilLokasi() {
   /* mulai Android 6 (API 23), pemberian persmission
    dilakukan secara dinamik (tdk diawal)
    untuk jenis2 persmisson tertentu, termasuk lokasi
    */

        // cek apakah sudah diijinkan oleh user, jika belum tampilkan dialog
        if (ActivityCompat.checkSelfPermission (this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
            return;
        }
        //set agar setiap update lokasi maka UI bisa diupdate
        //setiap ada update maka onLocationChanged akan dipanggil
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                ambilLokasi();
            } else {
                //permssion tidak diberikan, tampilkan pesan
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setMessage("Tidak mendapat ijin, tidak dapat mengambil lokasi");
                ad.show();
            }
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
