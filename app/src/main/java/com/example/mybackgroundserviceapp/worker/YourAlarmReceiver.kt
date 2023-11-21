package com.example.mybackgroundserviceapp.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class YourAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val locationWorkRequest = OneTimeWorkRequest.Builder(LocationUpdateWorker::class.java).build()
        WorkManager.getInstance(p0!!).enqueue(locationWorkRequest)
    }
}