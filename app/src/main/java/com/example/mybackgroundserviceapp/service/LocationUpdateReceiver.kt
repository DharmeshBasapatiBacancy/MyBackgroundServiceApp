package com.example.mybackgroundserviceapp.service

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class LocationUpdateReceiver: BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        val fusedLocationProviderClient = p0?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }

        fusedLocationProviderClient?.let {
            if (ActivityCompat.checkSelfPermission(
                    p0,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    p0,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //Request permission again
                return
            }else{
                //Permission Granted
                it.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        Log.d("LocationUpdateReceiver", "Latitude: ${location.latitude}")
                        Log.d("LocationUpdateReceiver", "Longitude: ${location.longitude}")
                    }
                }
            }

        }
    }

}