package com.example.mybackgroundserviceapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mybackgroundserviceapp.Utils


class LocationUpdateWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        Utils.fetchLocation(context){
            Log.d("LocationUpdateWorker", "Latitude: ${it.latitude}")
            Log.d("LocationUpdateWorker", "Longitude: ${it.longitude}")
        }

        Utils.setAlarm(context, YourAlarmReceiver::class.java)

        return Result.success()
    }


}