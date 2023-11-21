package com.example.mybackgroundserviceapp.worker

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mybackgroundserviceapp.Utils
import com.example.mybackgroundserviceapp.Utils.Companion.PERMISSIONS_REQUEST_CODE


class LocationUpdateWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        Utils.fetchLocation(context) {
            Log.d("LocationUpdateWorker", "Latitude: ${it.latitude}")
            Log.d("LocationUpdateWorker", "Longitude: ${it.longitude}")
        }

        Utils.setAlarm(context, YourAlarmReceiver::class.java)

        return Result.success()
    }


}