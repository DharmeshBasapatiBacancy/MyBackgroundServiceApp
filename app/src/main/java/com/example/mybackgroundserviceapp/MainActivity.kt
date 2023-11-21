package com.example.mybackgroundserviceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
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

            val locationWorkRequest = PeriodicWorkRequest.Builder(LocationUpdateWorker::class.java, 15, TimeUnit.MINUTES).build()
            WorkManager.getInstance(this).enqueue(locationWorkRequest)

        }
    }
}