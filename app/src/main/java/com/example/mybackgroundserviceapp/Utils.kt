package com.example.mybackgroundserviceapp

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

class Utils {

    companion object {

        val PERMISSIONS_REQUEST_CODE = 0

        fun fetchLocation(context: Context, onLocationFetched: (Location) -> Unit) {
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //Permission not granted
            } else {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        onLocationFetched(location)
                    }
                }
            }
        }

        fun setAlarm(context: Context, className: Class<*>) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, className)
            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val intervalInMillis: Long = 2 * 60 * 1000 // 2 minutes
            val triggerAtMillis = SystemClock.elapsedRealtime() + intervalInMillis

            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtMillis,
                intervalInMillis,
                pendingIntent
            )
        }

        fun Activity.hasLocationPermission(onPermissionGranted: () -> Unit) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                requestLocationPermissions()
            }else{
                onPermissionGranted()
            }
        }

        private fun Activity.requestLocationPermissions() {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_CODE)
        }
    }

}