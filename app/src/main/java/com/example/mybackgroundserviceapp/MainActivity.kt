package com.example.mybackgroundserviceapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mybackgroundserviceapp.Utils.Companion.hasLocationPermission
import com.example.mybackgroundserviceapp.databinding.ActivityMainBinding
import com.example.mybackgroundserviceapp.worker.LocationUpdateWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener {

            //While using AlarmManager with Service to update user location every 2 minutes
            //the set alarm manager is not firing after 1 time, but it is one of the ways
            //to perform something at regular intervals using AlarmManager and Service
            /*val intent = Intent(this, LocationUpdateService::class.java)
            startService(intent)*/

            hasLocationPermission {
                val locationWorkRequest = PeriodicWorkRequest.Builder(LocationUpdateWorker::class.java, 15, TimeUnit.MINUTES).build()
                WorkManager.getInstance(this).enqueue(locationWorkRequest)
            }


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out

        String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Utils.PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                val locationWorkRequest = PeriodicWorkRequest.Builder(LocationUpdateWorker::class.java, 15, TimeUnit.MINUTES).build()
                WorkManager.getInstance(this).enqueue(locationWorkRequest)
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}